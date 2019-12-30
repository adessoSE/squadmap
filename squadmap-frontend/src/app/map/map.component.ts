import { Component, OnInit } from '@angular/core';
import {EmployeeService} from '../service/employee.service';
import {ProjectService} from '../service/project.service';
import {Network, DataSet} from 'vis-network';
import {EmployeeModel} from '../models/employee.model';
import {ProjectModel} from '../models/project.model';
import {WorkingOnService} from '../service/workingOn.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  private employees: EmployeeModel[];
  private projects: ProjectModel[];
  private dateThreshold: Date;

  constructor(private employeeService: EmployeeService, private projectService: ProjectService, private workingOnService: WorkingOnService) { }

  ngOnInit() {
    this.dateThreshold = new Date();
    this.dateThreshold.setMonth(this.dateThreshold.getMonth() + 2);
    this.employees = [];
    this.projects = [];
    this.employeeService.getEmployees().subscribe(() => {
      this.employees = this.employeeService.employees;
      this.projectService.getProjects().subscribe(() => {
        this.projects =  this.projectService.projects;
        this.createMap();
      });
    });
  }
  createMap() {
    // Nodes
    let nodeList: any[];
    let edgeList: any[];
    nodeList = [
      { id: -1,
        label: 'OpenSource Team' ,
        color: '#58fd5b',
        group: 'homeNode'
      }];
    edgeList = [];

    this.employees.forEach( employee => {
      nodeList.push(
        { id: employee.employeeId,
          label: employee.firstName + ' ' + employee.lastName,
          title: 'Email: ' + employee.email + '<br> Phone: ' + employee.phone,
          color: employee.isExternal ? '#7b7b7b' : '#4f99fc',
          url: 'http://localhost:4200/employee/' + employee.employeeId,
          group: 'employeeNode'
      });
    });

    this.projects.forEach( project => {
      nodeList.push(
        { id: project.projectId,
          label: project.title,
          title: 'Since: ' + project.since.toDateString() +
            '<br> Until: ' + project.until.toDateString() +
            '<br> Description: ' + project.description,
          color: project.isExternal ? '#7b7b7b' : '#ffdf58',
          url: 'http://localhost:4200/map/' + project.projectId,
          group: 'projectNode'
        });
    });


    // Edges
    this.projects.forEach( project => {
      edgeList.push(
        { from: -1,
          to: project.projectId,
          title: 'Since: ' + project.since.toDateString() + '<br> Until: ' + project.until.toDateString(),
          color: project.until < this.dateThreshold ? '#ff0002' : '#000000',
          dashes: project.isExternal
        });
    });


    this.employees.forEach( employee => {
      employee.projects.forEach( project => {
        edgeList.push(
          { from: employee.employeeId,
            to: project.project.projectId,
            title: 'Since: ' + project.since.toDateString() + '<br> Until: ' + project.until.toDateString(),
            color: project.until < this.dateThreshold ? '#ff0002' : '#000000',
            arrows: 'to',
            dashes: employee.isExternal
          });
      });
    });



    // TODO Trennung der Klasse, oberer Teil in einen Service Außlagern


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

  isEmployee(id: number): boolean {
    let i;
    for (i = 0; i < this.employees.length; i++) {
      if (this.employees[i].employeeId === id) {
        return true;
      }
    }
    return false;
  }

  isProject(id: number): boolean {
    let i;
    for (i = 0; i < this.projects.length; i++) {
      if (this.projects[i].projectId === id) {
        return true;
      }
    }
    return false;
  }
}

