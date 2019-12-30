import { Component, OnInit } from '@angular/core';
import {ProjectService} from '../service/project.service';
import {ProjectModel} from '../models/project.model';
import {DataSet, Network} from 'vis-network';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-map-project-detail',
  templateUrl: './map-project-detail.component.html',
  styleUrls: ['./map-project-detail.component.css']
})
export class MapProjectDetailComponent implements OnInit {

  private project: ProjectModel;
  private dateThreshold: Date;

  constructor(private route: ActivatedRoute, private projectService: ProjectService) { }

  ngOnInit() {
    this.dateThreshold = new Date();
    this.dateThreshold.setMonth(this.dateThreshold.getMonth() + 2);
    this.projectService.getProject(this.route.snapshot.params.id).subscribe(res => {
      this.project = new ProjectModel(
        res.projectId,
        res.title,
        res.description,
        res.since,
        res.until,
        res.isExternal,
        res.employees );
      this.createMap();
    });

  }

  createMap() {
    // Nodes
    let nodeList: any[];
    let edgeList: any[];
    nodeList = [];
    edgeList = [];

    nodeList.push(
      { id: this.project.projectId,
        label: this.project.title,
        title: 'Since: ' + this.project.since.toDateString() +
          '<br> Until: ' + this.project.until.toDateString() +
          '<br> Description: ' + this.project.description,
        color: this.project.isExternal ? '#7b7b7b' : '#ffdf58',
        url: 'http://localhost:4200/map/' + this.project.projectId,
        group: 'projectNode'
      });

    this.project.employees.forEach( employee => {
      nodeList.push(
        { id: employee.employee.employeeId,
          label: employee.employee.firstName + ' ' + employee.employee.lastName,
          title: 'Email: ' + employee.employee.email + '<br> Phone: ' + employee.employee.phone,
          color: employee.employee.isExternal ? '#7b7b7b' : '#4f99fc',
          url: 'http://localhost:4200/employee/' + employee.employee.employeeId,
          group: 'employeeNode'
        });
      edgeList.push(
        { from: employee.employee.employeeId,
          to: this.project.projectId,
          title: 'Since: ' + employee.since.toDateString() + '<br> Until: ' + employee.until.toDateString(),
          color: employee.until < this.dateThreshold ? '#ff0002' : '#000000',
          arrows: 'to',
          dashes: employee.employee.isExternal
        });
    });

    // create a network
    const nodes = new DataSet(nodeList);
    const edges = new DataSet(edgeList);
    const height = Math.round(window.innerHeight * 0.95) + 'px';
    document.getElementById('mynetwork').style.height = height;
    const container = document.getElementById('mynetwork');


    // provide the data in the vis format
    const data = {
      nodes,
      edges
    };

    const options = {
      interaction: {
        hover: true
      },
      manipulation: {
        enabled: true,
        addNode: (nodeData, callback) => this.addNode(nodeData, callback),
        deleteNode: (nodeData, callback) => this.deleteNode(nodeData, callback),
        addEdge: (edgeData, callback) => this.addEdge(edgeData, callback),
        deleteEdge: (edgeData, callback) => this.deleteEdge(edgeData, callback)
      },
      edges: {
        title: 'Hover',
        length: 200
      },
      nodes: {},
      physics: {},
      groups: {
        employeeNode: {
          shape: 'ellipse'
        },
        projectNode: {
          shape: 'box',
          margin: 10
        },
        homeNode: {
          shape: 'box',
          margin: 20,
          fixed: true
        }
      }
    };

    // initialize your network!
    const network = new Network(container, data, options);

    network.on('doubleClick', params => {
      if (params.nodes.length === 1) {
        let node: any;
        node = nodes.get(params.nodes[0]);
        if (node.url != null && node.url !== '') {
          window.location = node.url;
        }
      }});


    // Courser Options
    const networkCanvas = document
      .getElementById('mynetwork')
      .getElementsByTagName('canvas')[0];


    function changeCursor(newCursorStyle) {
      networkCanvas.style.cursor = newCursorStyle;
    }
    function changeEventCursor(eventName, cursorType) {
      network.on(eventName, () => {
        changeCursor(cursorType);
      });
    }
    network.on('hoverNode', () => {
      changeCursor('grab');
    });
    network.on('blurNode', () => {
      changeCursor('default');
    });
    network.on('hoverEdge', () => {
      changeCursor('grab');
    });
    network.on('blurEdge', () => {
      changeCursor('default');
    });
    network.on('dragStart', () => {
      changeCursor('grabbing');
    });
    network.on('dragging', () => {
      changeCursor('grabbing');
    });
    network.on('dragEnd', () => {
      changeCursor('grab');
    });
  }

  addNode(nodeData, callback) {
    // show form adn then send it to employee/project Service
    callback(nodeData);
  }

  deleteNode(nodeData, callback) {
    // let service delete the employee/project. Then delete all given connections if necessary
    callback(nodeData);
  }

  addEdge(edgeData, callback) {
    // console.log(this.isEmployee(edgeData.from));
    // console.log(this.isProject(edgeData.to));
    // if (this.isEmployee(edgeData.from) && this.isProject(edgeData.to)) {
    //   this.workingOnService.addWorkingOn(edgeData.from, edgeData.to).subscribe();
    //   callback(edgeData);
    // } else if (this.isEmployee(edgeData.to) && this.isProject(edgeData.from)) {
    //   this.workingOnService.addWorkingOn(edgeData.to, edgeData.from).subscribe();
    //   callback(edgeData);
    // }
    callback(edgeData);
  }

  deleteEdge(edgeData, callback) {
    // check if edge is workingOn relation then delete edge
    callback(edgeData);
  }
}
