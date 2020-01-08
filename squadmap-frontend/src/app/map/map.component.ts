import { Component, OnInit } from '@angular/core';
import {EmployeeService} from '../service/employee.service';
import {ProjectService} from '../service/project.service';
import {Network, DataSet} from 'vis-network';
import {EmployeeModel} from '../models/employee.model';
import {ProjectModel} from '../models/project.model';
import {WorkingOnService} from '../service/workingOn.service';
import {WorkingOnProjectModel} from '../models/workingOnProject.model';
import {CreateWorkingOnModel} from '../models/createWorkingOn.model';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  private employees: EmployeeModel[];
  private projects: ProjectModel[];
  private dateThreshold: Date;

  constructor(private employeeService: EmployeeService,
              private projectService: ProjectService,
              private workingOnService: WorkingOnService) { }

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
    let negativeId = -1;
    nodeList = [
      { id: negativeId,
        label: 'OpenSource Team',
        color: '#58fd5b',
        group: 'homeNode'
      }];
    edgeList = [];

    this.employees.forEach( employee => {
      nodeList.push(
        { id: employee.employeeId,
          label: employee.firstName + ' ' + employee.lastName,
          title: 'Id: ' + employee.employeeId +
            '<br>Email: ' + employee.email +
            '<br> Phone: ' + employee.phone,
          color: employee.isExternal ? '#7b7b7b' : '#4f99fc',
          url: 'http://localhost:4200/employee/' + employee.employeeId,
          group: 'employeeNode'
      });
    });

    this.projects.forEach( project => {
      nodeList.push(
        { id: project.projectId,
          label: project.title,
          title: 'Id: ' + project.projectId +
            '<br>Since: ' + project.since.toDateString() +
            '<br> Until: ' + project.until.toDateString() +
            '<br> Description: ' + project.description,
          color: project.isExternal ? '#7b7b7b' : '#ffdf58',
          url: 'http://localhost:4200/map/' + project.projectId,
          group: 'projectNode'
        });
    });


    // Edges
    this.projects.forEach( project => {
      negativeId --;
      edgeList.push(
        { id: negativeId,
          from: -1,
          to: project.projectId,
          title: 'ID: ' + negativeId +
            '<br>Since: ' + project.since.toDateString() +
            '<br> Until: ' + project.until.toDateString(),
          color: project.until < this.dateThreshold ? '#ff0002' : '#000000',
          dashes: project.isExternal
        });
    });


    this.employees.forEach( employee => {
      employee.projects.forEach( project => {
        edgeList.push(
          { id: project.workingOnId,
            from: employee.employeeId,
            to: project.project.projectId,
            title: 'Id: ' + project.workingOnId +
              '<br>Since: ' + project.since.toDateString() +
              '<br> Until: ' + project.until.toDateString(),
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
        addEdge: (edgeData, callback) => this.addEdge(edgeData, callback),
        deleteEdge: (edgeData, callback) => this.deleteEdge(edgeData, callback)
      },
      edges: {
        shadow: true,
        title: 'Hover',
        length: 200,
      },
      nodes: {
        shadow: true,
        physics: false,
      },
      physics: {},
      groups: {
        employeeNode: {
          shape: 'ellipse',
        },
        projectNode: {
          shape: 'box',
          margin: 10
        },
        homeNode: {
          shape: 'box',
          margin: 20
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
      this.employees.forEach(employee => {
        console.log(employee.lastName, employee.projects.length, employee.projects);
      });
      this.projects.forEach(project => {
        console.log(project.title, project.employees.length, project.employees);
      });
    });
    network.on('doubleClick', params => {
      if (params.nodes.length === 1) {
        let node: any;
        node = nodes.get(params.nodes[0]);
        if (node.url != null && node.url !== '') {
          window.location = node.url;
        }
      }
    });
    network.on('oncontext', () => {
      this.projects.forEach( project => {
        if (project.isExternal) {
          const node = nodes.get(project.projectId);
          nodes.update({id: node.id, hidden: !node.hidden});
        }
      });
      this.employees.forEach(employee => {
        if (employee.isExternal) {
          const node = nodes.get(employee.employeeId);
          nodes.update({id: node.id, hidden: !node.hidden});
        }
      });
    });
  }

  addNode(nodeData, callback) {
    // show form and then send it to employee/project Service
    callback(nodeData);
    this.refresh();
  }

  deleteNode(nodeData, callback) {
    const validNodes = [];
    const validEdges = [];
    nodeData.nodes.forEach( node => {
      if (this.isEmployee(node)) {
        validNodes.push(node);
        this.employeeService.deleteEmployee(node).subscribe(() => this.refreshEmployee());
      } else if (this.isProject(node)) {
        validNodes.push(node);
        this.projectService.deleteProject(node).subscribe(() => this.refreshProjects());
      }
    });
    nodeData.edges.forEach( edge => {
      if (this.isWorkingOn(edge)) {
        validEdges.push(edge);
        this.workingOnService.deleteWorkingOn(edge).subscribe(() => this.refresh());
      }
    });
    nodeData.nodes = validNodes;
    nodeData.edges = validEdges;
    if (nodeData.nodes.length === 0 && nodeData.edges.length === 0) {
      window.alert('Given nodes can\'t be deleted');
    }
    callback(nodeData);
  }

  addEdge(edgeData, callback) {
    if (!this.isEmployee(edgeData.from) && !this.isProject(edgeData.to)) {
      const temp = edgeData.from;
      edgeData.from = edgeData.to;
      edgeData.to = temp;
    }
    if (this.isEmployee(edgeData.from ) && this.isProject(edgeData.to)) {
      this.workingOnService.createWorkingOn(new CreateWorkingOnModel(edgeData.from, edgeData.to, new Date(), new Date())).subscribe( () => {
        let employee: EmployeeModel;
        this.employeeService.getEmployee(edgeData.from).subscribe(res => {
          employee = new EmployeeModel(
            res.employeeId,
            res.firstName,
            res.lastName,
            res.birthday,
            res.email,
            res.phone,
            res.isExternal,
            res.projects);
          let workingOn: WorkingOnProjectModel;
          let i: number;
          for (i = 0; i < employee.projects.length; i++) {
            if (employee.projects[i].project.projectId === edgeData.to) {
              workingOn = employee.projects[i];
              break;
            }
          }
          edgeData = {
            id: workingOn.workingOnId,
            from: edgeData.from,
            to: edgeData.to,
            title: 'Id: ' + workingOn.workingOnId +
              '<br>Since: ' + workingOn.since.toDateString() +
              '<br> Until: ' + workingOn.until.toDateString(),
            color: workingOn.until < this.dateThreshold ? '#ff0002' : '#000000',
            arrows: 'to',
            dashes: employee.isExternal
          };
          this.refresh();
          callback(edgeData);
        });
      });
    } else {
      window.alert('Only edges between employees and projects are supported');
    }
  }

  deleteEdge(edgeData, callback) {
    const validEdges = [];
    edgeData.edges.forEach( edge => {
      if (this.isWorkingOn(edge)) {
        validEdges.push(edge);
        this.workingOnService.deleteWorkingOn(edge).subscribe(() => this.refresh());
      }
    });
    edgeData.edges = validEdges;
    if (edgeData.edges.length === 0) {
      window.alert('Given edges can\'t be deleted');
    }
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

  isWorkingOn(id: number): boolean {
    let i;
    let j;
    for (i = 0; i < this.projects.length; i++) {
      for (j = 0; j < this.projects[i].employees.length; j++) {
        if (this.projects[i].employees[j].workingOnId === id) {
          return true;
        }
      }
    }
    return false;
  }

  refreshEmployee() {
    this.employees = [];
    this.employeeService.getEmployees().subscribe(() => {
      this.employees = this.employeeService.employees;
    });
  }

  refreshProjects() {
    this.projects = [];
    this.projectService.getProjects().subscribe(() => {
      this.projects = this.projectService.projects;
    });
  }

  refresh() {
    this.employees = [];
    this.projects = [];
    this.employeeService.getEmployees().subscribe(() => {
      this.employees = this.employeeService.employees;
      this.projectService.getProjects().subscribe(() => {
        this.projects =  this.projectService.projects;
      });
    });
  }
}
