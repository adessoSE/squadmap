describe('project-detail-view' ,() =>{
  before(() => {
    cy.visit('/project');
    cy.get('#addProjectButton').click();
    cy.get('#title').type('testProject');
    cy.get('#description').type('testDescription');
    const mockDate = new Date();
    cy.get('#since').type(mockDate.toISOString().slice(0,10));
    cy.get('#until').type(mockDate.toISOString().slice(0,10));
    cy.get('#sites').type('www.google.de,');
    cy.get('#isExternal').check();
    cy.get('.row > .btn').click();
  });

  beforeEach(() => {
    cy.visit('/project');
    cy.get('#searchText').type('testproject');
    cy.get('table').contains('td','testProject').click();
  });

  it('should show the project name', function () {
    cy.get('.card').contains('h2','testProject');
  });

  it('should show initially the network canvas', function () {
    cy.get('canvas');
  });

  it('should switch from map to table view', function () {
    cy.get('span').contains('Mapview').click();
    cy.get('table');
  });

  it('should edit the project', function () {
    cy.get('#editButton').click();
    cy.get('.modal-title').contains('h4', 'Update Project');
    cy.get('#description').clear().type('new');
    cy.get(':nth-child(7) > .row > .btn').click();
    cy.get('.card-body').contains('td','new');
  });

  it('should add an employee', function () {
    cy.get('span').contains('Mapview').click();
    cy.get('#openAddEmployeeModal').click();
    const mockDate = new Date();
    cy.get('#since').type(mockDate.toISOString().slice(0,10));
    cy.get('#until').type(mockDate.toISOString().slice(0,10));
    cy.get('#workload').type('50');
    cy.get('#addEmployee').click();
  });

  it('should delete the added employee', function () {
    cy.get('span').contains('Tableview').click();
    cy.get('.btn-outline-danger').click({multiple:true});
  });
});
