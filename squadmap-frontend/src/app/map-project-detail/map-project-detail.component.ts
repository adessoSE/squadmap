import { Component, OnInit } from '@angular/core';
import {ProjectService} from '../service/project.service';
import {ProjectModel} from '../models/project.model';
import {DataSet, Network} from 'vis-network';
import {ActivatedRoute} from '@angular/router';
import {WorkingOnService} from '../service/workingOn.service';
import {EmployeeService} from '../service/employee.service';

@Component({
  selector: 'app-map-project-detail',
  templateUrl: './map-project-detail.component.html',
  styleUrls: ['./map-project-detail.component.css']
})
export class MapProjectDetailComponent implements OnInit {

  private project: ProjectModel;
  private dateThreshold: Date;

  constructor(private route: ActivatedRoute,
              private employeeService: EmployeeService,
              private projectService: ProjectService,
              private workingOnService: WorkingOnService) { }

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
        url: 'http://localhost:4200/project/' + this.project.projectId,
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
        { id: employee.workingOnId,
          from: employee.employee.employeeId,
          to: this.project.projectId,
          title: 'Id: ' + employee.workingOnId +
            '<br>Since: ' + employee.since.toDateString() +
            '<br> Until: ' + employee.until.toDateString(),
          color: employee.until < this.dateThreshold ? '#ff0002' : '#000000',
          arrows: 'to',
          dashes: employee.employee.isExternal
        });
    });

    // create a network
    const nodes = new DataSet(nodeList);
    const edges = new DataSet(edgeList);
    document.getElementById('mynetwork').style.height = Math.round(window.innerHeight * 0.95) + 'px';
    const container = document.getElementById('mynetwork');


    // provide the data in the vis format
    const data = {
      nodes,
      edges
    };

    const options = {
      interaction: {
        keyboard: true,
        hover: true
      },
      manipulation: {
        enabled: true,
        editEdge: false,
        addNode: (nodeData, callback) => this.addNode(nodeData, callback),
        deleteNode: (nodeData, callback) => this.deleteNode(nodeData, callback),
        addEdge: false,
        deleteEdge: (edgeData, callback) => this.deleteEdge(edgeData, callback)
      },
      edges: {
        title: 'Hover',
        length: 200,
        shadow: true
      },
      nodes: {
        physics: false,
        shadow: true
      },
      physics: {},
      groups: {
        employeeNode: {
          shape: 'ellipse'
        },
        projectNode: {
          shape: 'box',
          margin: 10
        }
      }
    };

    // initialize your network!
    const network = new Network(container, data, options);




    // Courser Options
    const networkCanvas = document
      .getElementById('mynetwork')
      .getElementsByTagName('canvas')[0];


    function changeCursor(newCursorStyle) {
      networkCanvas.style.cursor = newCursorStyle;
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
    network.on('click', () => {
      console.log(this.project.title, this.project.employees.length);
    });
    network.on('doubleClick', params => {
      if (params.nodes.length === 1) {
        let node: any;
        node = nodes.get(params.nodes[0]);
        if (node.url != null && node.url !== '') {
          window.location = node.url;
        }
      }});
    network.on('oncontext', () => {
      this.project.employees.forEach(employee => {
        if (employee.employee.isExternal) {
          const node = nodes.get(employee.employee.employeeId);
          nodes.update({id: node.id, hidden: !node.hidden});
        }
      });
    });
  }

  addNode(nodeData, callback) {
    // show form and then send it to employee/project Service
    this.refresh();
    callback(nodeData);
  }

  deleteNode(nodeData, callback) {
    const validNodes = [];
    const validEdges = [];
    nodeData.nodes.forEach( node => {
      if (this.isEmployee(node)) {
        validNodes.push(node);
        this.employeeService.deleteEmployee(node).subscribe();
      } else if (this.isProject(node)) {
        validNodes.push(node);
        this.projectService.deleteProject(node).subscribe();
      }
    });
    nodeData.edges.forEach( edge => {
      if (this.isWorkingOn(edge)) {
        validEdges.push(edge);
        this.workingOnService.removeEmployeeFromProject(edge).subscribe();
      }
    });
    nodeData.nodes = validNodes;
    nodeData.edges = validEdges;
    if (nodeData.nodes.length === 0 && nodeData.edges.length === 0) {
      window.alert('Given nodes can\'t be deleted');
    }
    this.refresh();
    callback(nodeData);
  }

  deleteEdge(edgeData, callback) {
    const validEdges = [];
    edgeData.edges.forEach( edge => {
      if (this.isWorkingOn(edge)) {
        validEdges.push(edge);
        this.workingOnService.removeEmployeeFromProject(edge).subscribe();
      }
    });
    edgeData.edges = validEdges;
    if (edgeData.edges.length === 0) {
      window.alert('Given edges can\'t be deleted');
    }
    this.refresh();
    callback(edgeData);
  }

  isEmployee(id: number): boolean {
    let i;
    for (i = 0; i < this.project.employees.length; i++) {
      if (this.project.employees[i].employee.employeeId === id) {
        return true;
      }
    }
    return false;
  }

  isProject(id: number): boolean {
    return this.project.projectId === id;
  }

  isWorkingOn(id: number): boolean {
    let i;
    for (i = 0; i < this.project.employees.length; i++) {
      if (this.project.employees[i].workingOnId === id) {
        return true;
      }
    }
    return false;
  }

  refresh() {
    this.projectService.getProject(this.route.snapshot.params.id).subscribe(res => {
      this.project = new ProjectModel(
        res.projectId,
        res.title,
        res.description,
        res.since,
        res.until,
        res.isExternal,
        res.employees );
    });
  }
}
