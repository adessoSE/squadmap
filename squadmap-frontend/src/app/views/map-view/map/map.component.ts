import { Component, OnInit } from '@angular/core';
import {EmployeeService} from '../../../services/employee/employee.service';
import {ProjectService} from '../../../services/project/project.service';
import {Network, DataSet} from 'vis-network';
import {EmployeeModel} from '../../../models/employee.model';
import {ProjectModel} from '../../../models/project.model';
import {WorkingOnService} from '../../../services/workingOn/workingOn.service';
import {WorkingOnProjectModel} from '../../../models/workingOnProject.model';
import {CreateWorkingOnModel} from '../../../models/createWorkingOn.model';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  private employees: EmployeeModel[];
  private projects: ProjectModel[];
  private dateThreshold: Date;
  private network: any;

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
    nodeList = [];
    edgeList = [];

    this.employees.forEach( employee => {
      nodeList.push(
        { id: employee.employeeId,
          label: employee.firstName + ' ' + employee.lastName,
          title: 'Id: ' + employee.employeeId +
            '<br>Email: ' + employee.email +
            '<br> Phone: ' + employee.phone,
          color: employee.isExternal ? '#c9c9c9' : '#65a4f7',
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
          color: project.isExternal ? '#c9c9c9' : '#ffebad',
          url: 'http://localhost:4200/map/' + project.projectId,
          group: 'projectNode'
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
              '<br> Until: ' + project.until.toDateString() +
              '<br> Workload: ' + project.workload + '%',
            color: project.until < this.dateThreshold ? '#bb0300' : '#000000',
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
      layout: {
        randomSeed: 22222
      },
      interaction: {
        keyboard: true,
        hover: true
      },
      manipulation: {
        enabled: true,
        editEdge: false,
        addNode: false,
        deleteNode: (nodeData, callback) => this.deleteNode(nodeData, callback),
        addEdge: (edgeData, callback) => this.addEdge(edgeData, callback),
        deleteEdge: (edgeData, callback) => this.deleteEdge(edgeData, callback)
      },
      edges: {
        // shadow: true,
        title: 'Hover',
        length: 200,
        width: 0.75
      },
      nodes: {
        // shadow: true,
        physics: true,
        borderWidth: 0,
      },
      physics: {
        maxVelocity: 10,
        repulsion: {
          nodeDistance: 120,
        }
      },
      groups: {
        employeeNode: {
          shape: 'circle',
          widthConstraint: {
            maximum: 60,
            minimum: 60
          },
          heightConstraint: {
            minimum: 60,
            maximum: 60
          }
        },
        projectNode: {
          shape: 'circle',
          margin: 10,
          widthConstraint: {
            maximum: 90,
            minimum: 90
          },
          heightConstraint: {
            minimum: 90,
            maximum: 90
          }
        },
        homeNode: {
          shape: 'box',
          margin: 20
        }
      }
    };

    // initialize your network!
    this.network = new Network(container, data, options);
    // Courser Options
    const networkCanvas = document
      .getElementById('mynetwork')
      .getElementsByTagName('canvas')[0];

    function changeCursor(newCursorStyle) {
      networkCanvas.style.cursor = newCursorStyle;
    }
    function clusterLonelyEmployees(employees: EmployeeModel[]) {
      const clusterOptions = {
        joinCondition: (childOptions) => {
          let isLonely: boolean;
          let i: number;
          for (i = 0; i < employees.length; i++) {
            if (childOptions.id === employees[i].employeeId) {
              isLonely = employees[i].projects.length === 0;
              break;
            }
          }
          return isLonely;
        },
        clusterNodeProperties: {
          id: 'lonelyEmployees',
          label: 'Employees without Project',
          shape: 'box',
          widthConstraint: {
            maximum: 120,
            minimum: 120
          },
          heightConstraint: {
            minimum: 60,
            maximum: 60
          },
          color: '#c9c9c9'
        }
      };
      this.network.cluster(clusterOptions);
    }
    this.network.on('hoverNode', () => {
      changeCursor('grab');
    });
    this.network.on('blurNode', () => {
      changeCursor('default');
    });
    this.network.on('hoverEdge', () => {
      changeCursor('grab');
    });
    this.network.on('blurEdge', () => {
      changeCursor('default');
    });
    this.network.on('dragStart', () => {
      changeCursor('grabbing');
    });
    this.network.on('dragging', () => {
      changeCursor('grabbing');
    });
    this.network.on('dragEnd', () => {
      changeCursor('grab');
    });
    this.network.on('click', params => {
      if (params.nodes.length === 1 && params.nodes[0] === 'lonelyEmployees') {
        this.network.openCluster(params.nodes[0]);
      } else {
        clusterLonelyEmployees(this.employees);
      }
    });
    this.network.on('doubleClick', params => {
      if (params.nodes.length === 1) {
        let node: any;
        node = nodes.get(params.nodes[0]);
        if (node.url != null && node.url !== '') {
          window.location = node.url;
        }
      }
    });
    this.network.on('oncontext', () => {
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

  deleteNode(nodeData, callback) {
    const validNodes = [];
    const validEdges = [];
    nodeData.nodes.forEach( node => {
      if (this.isEmployee(node)) {
        validNodes.push(node);
        this.employeeService.deleteEmployee(node).subscribe(() => this.refresh());
      } else if (this.isProject(node)) {
        validNodes.push(node);
        this.projectService.deleteProject(node).subscribe(() => this.refresh());
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
      // TODO Customize workload
      const createWorkingOn = new CreateWorkingOnModel(edgeData.from, edgeData.to, new Date(), new Date(), 1);
      // TODO get dates from modal
      this.workingOnService.createWorkingOn(createWorkingOn).subscribe( () => {
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
            res.image,
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
            color: workingOn.until < this.dateThreshold ? '#bb0300' : '#000000',
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
