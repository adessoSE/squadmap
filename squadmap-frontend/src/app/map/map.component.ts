import { Component, OnInit } from '@angular/core';
import {EmployeeService} from '../service/employee.service';
import {ProjectService} from '../service/project.service';
import { Network, DataSet } from 'vis-network';
import {EmployeeModel} from '../models/employee.model';
import {ProjectModel} from '../models/project.model';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  public employees: EmployeeModel[];
  public projects: ProjectModel[];

  constructor(private employeeService: EmployeeService, private projectService: ProjectService) { }

  ngOnInit() {
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
    // create an array with edges
    let nodeList: any[];
    nodeList = [ {id: -1, label: 'MASTER' , color: '#6E6EFD'}];

    this.employees.forEach( employee => nodeList.push(
      {id: employee.employeeId, label: employee.firstName + ' ' + employee.lastName, color: '#97C2FC'}));
    this.projects.forEach( project => nodeList.push(
      {id: project.projectId, label: project.title, color: '#FFA807'}));
    console.log(nodeList);

    let edgeList: any[];
    edgeList = [];

    this.employees.forEach( employee => {
      employee.projects.forEach( project => edgeList.push( {from: employee.employeeId, to: project.project.projectId}));
    });

    this.projects.forEach( project => edgeList.push( {from: -1, to: project.projectId}));

    // create a network
    const nodes = new DataSet(nodeList);
    const edges = new DataSet(edgeList);
    const container = document.getElementById('mynetwork');

    // provide the data in the vis format
    const data = {
      nodes,
      edges
    };
    const options = {};

    // initialize your network!
    const network = new Network(container, data, options);
  }
}
