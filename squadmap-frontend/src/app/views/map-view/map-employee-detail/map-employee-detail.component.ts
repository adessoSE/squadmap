import {Component, OnInit} from '@angular/core';
import {DataSet, Network} from 'vis-network';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {ActivatedRoute} from '@angular/router';
import {EmployeeService} from '../../../services/employee/employee.service';
import {ProjectService} from '../../../services/project/project.service';
import {WorkingOnService} from '../../../services/workingOn/workingOn.service';
import {MessageModalComponent} from '../../../modals/message-modal/message-modal.component';
import {WorkingOnModalComponent} from '../../../modals/working-on-modal/working-on-modal.component';
import {EmployeeModel} from '../../../models/employee.model';
import {WorkingOnProjectModel} from '../../../models/workingOnProject.model';

@Component({
  selector: 'app-map-employee-detail',
  templateUrl: './map-employee-detail.component.html',
  styleUrls: ['./map-employee-detail.component.css']
})
export class MapEmployeeDetailComponent implements OnInit {

  private employee: EmployeeModel;
  private dateThreshold: Date;
  private network: Network;
  private container: HTMLElement;
  modalRef: BsModalRef;

  constructor(private route: ActivatedRoute,
              private employeeService: EmployeeService,
              private projectService: ProjectService,
              private workingOnService: WorkingOnService,
              private modalService: BsModalService) { }

  ngOnInit() {
    this.container = document.getElementById('mynetwork');
    this.network = new Network(this.container, {}, {});
    this.dateThreshold = new Date();
    this.dateThreshold.setMonth(this.dateThreshold.getMonth() + 2);
    this.employeeService.getEmployee(this.route.snapshot.params.id).subscribe(res => {
      this.employee = new EmployeeModel(
        res.employeeId,
        res.firstName,
        res.lastName,
        res.birthday,
        res.email,
        res.phone,
        res.isExternal,
        res.image,
        res.projects
      );
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
      { id: this.employee.employeeId,
        label: this.employee.firstName,
        color: this.employee.isExternal ? '#c9c9c9' : '#ffebad',
        // url: 'http://localhost:4200/project/' + this.employee.employeeId,
        group: 'projectNode'
      });

    this.employee.projects.forEach( project => {
      nodeList.push(
        { id: project.project.projectId,
          label: project.project.title,
          title: 'Id: ' + project.project.projectId +
            '<br>Since: ' + project.since.toDateString() +
            '<br> Until: ' + project.until.toDateString(),
          color: project.project.isExternal ? '#c9c9c9' : '#65a4f7',
          url: 'http://localhost:4200/employee/' + project.project.projectId,
          group: 'employeeNode'
        });
      edgeList.push(
        { id: project.workingOnId,
          from: this.employee.employeeId,
          to: project.project.projectId,
          title: 'Id: ' + project.workingOnId +
            '<br>Since: ' + project.since.toDateString() +
            '<br> Until: ' + project.until.toDateString() +
            '<br> Workload: ' + project.workload + '%',
          color: project.until < this.dateThreshold ? '#ff0002' : '#000000',
          dashes: project.project.isExternal
        });
    });

    // create a network
    const nodes = new DataSet(nodeList);
    const edges = new DataSet(edgeList);
    document.getElementById('mynetwork').style.height = Math.round(window.innerHeight * 0.80) + 'px';
    const container = document.getElementById('mynetwork');


    // provide the data in the vis format
    const data = {
      nodes,
      edges
    };

    const options = {
      autoResize: true,
      layout: {
        randomSeed: 22222
      },
      interaction: {
        keyboard: false,
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
        title: 'Hover',
        length: 200,
        width: 0.75
      },
      nodes: {
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
    network.on('doubleClick', params => {
      if (params.nodes.length === 1) {
        let node: any;
        node = nodes.get(params.nodes[0]);
        if (node.url != null && node.url !== '') {
          window.location = node.url;
        }
      }});
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
      const config = {
        backdrop: true,
        ignoreBackdropClick: false,
        initialState: {
          message: 'Given nodes cannot be deleted!'
        }
      };
      this.modalService.show(MessageModalComponent, config);
    }
    this.refresh();
    callback(nodeData);
  }

  addEdge(edgeData, callback) {

    if (!this.isEmployee(edgeData.from) && !this.isProject(edgeData.to)) {
      const temp = edgeData.from;
      edgeData.from = edgeData.to;
      edgeData.to = temp;
    }

    if (this.isEmployee(edgeData.from ) && this.isProject(edgeData.to)) {
      let edgeAlreadyExists = false;
      this.network.getConnectedNodes(edgeData.from).forEach(elem => {
        if (edgeData.to === elem) {
          edgeAlreadyExists = true;
        }
      });

      if (!edgeAlreadyExists) {
        const config = {
          backdrop: true,
          ignoreBackdropClick: true,
          initialState: {
            edgeData,
            isNew: true,
          }
        };
        this.modalRef = this.modalService.show(WorkingOnModalComponent, config);
        this.modalRef.content.onClose.subscribe(wasSuccessfully => {
          if (wasSuccessfully) {
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
                  '<br> Until: ' + workingOn.until.toDateString() +
                  '<br> Workload: ' + workingOn.workload + '%',
                color: workingOn.until < this.dateThreshold ? '#bb0300' : '#000000',
                dashes: employee.isExternal
              };
              if (edgeData) {
                callback(edgeData);
              } else {
                callback();
              }
            });
          } else {
            const config2 = {
              backdrop: true,
              ignoreBackdropClick: false,
              initialState: {
                message: 'An error occured! Could not create the edge.'
              }
            };
            this.modalService.show(MessageModalComponent, config2);
          }
        });
      } else {
        const config2 = {
          backdrop: true,
          ignoreBackdropClick: false,
          initialState: {
            message: 'The relationship already exists!'
          }
        };
        this.modalService.show(MessageModalComponent, config2);
        return;
      }
    } else {
      const config2 = {
        backdrop: true,
        ignoreBackdropClick: false,
        initialState: {
          message: 'Only edges between employees and projects are supported!'
        }
      };
      this.modalService.show(MessageModalComponent, config2);
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
      const config = {
        backdrop: true,
        ignoreBackdropClick: false,
        initialState: {
          message: 'Given nodes cannot be deleted!'
        }
      };
      this.modalService.show(MessageModalComponent, config);
    }
    this.refresh();
    callback(edgeData);
  }

  isEmployee(id: number): boolean {
    return this.employee.employeeId === id;
  }

  isProject(id: number): boolean {
    let i;
    for (i = 0; i < this.employee.projects.length; i++) {
      if (this.employee.projects[i].project.projectId === id) {
        return true;
      }
    }
    return false;
  }

  isWorkingOn(id: number): boolean {
    let i;
    for (i = 0; i < this.employee.projects.length; i++) {
      if (this.employee.projects[i].workingOnId === id) {
        return true;
      }
    }
    return false;
  }

  refresh() {
    this.employeeService.getEmployee(this.route.snapshot.params.id).subscribe(res => {
      this.employee = new EmployeeModel(
        res.employeeId,
        res.firstName,
        res.lastName,
        res.birthday,
        res.email,
        res.phone,
        res.isExternal,
        res.image,
        res.projects
      );
    });
  }
}
