describe('employee-detail-view' ,() =>{
  before(() => {
    cy.visit('/employee');
    cy.get('#addEmployeeButton').click();
    cy.get('#firstName').type('testName');
    cy.get('#lastName').type('testName');
    const mockDate = new Date('2000-01-01');
    cy.get('#birthday').type(mockDate.toISOString().slice(0,10));
    cy.get('#email').type('test123@test.de');
    cy.get('#phone').type('+0123456789');
    cy.get('#isExternal').check();
    cy.get(':nth-child(11) > .row > .btn').click();
  });

  beforeEach(() => {
    cy.visit('/employee');
    cy.get('#searchText').type('testname');
    cy.get('table').contains('td','testName').click();
  });

  it('should show the employee name', function () {
    cy.get('.card').contains('h2','testName');
  });

  it('should show initially the network canvas', function () {
    cy.get('canvas');
  });

  it('should switch from map to table view', function () {
    cy.get('span').contains('Tableview').click();
    cy.get('table');
  });

  it('should edit the employee', function () {
    cy.get('#editEmployeeButton').click();
    cy.get('#isExternal').check();
    cy.get(':nth-child(11) > .row > .btn').click();
    cy.get('.card-body').contains('td','true');
  });

  it('should add a project', function () {
    cy.get('span').contains('Mapview').click();
    cy.get('#addProjectButton').click();
    cy.get('#searchText').type('test');
    const mockDate = new Date();
    cy.get('#since').type(mockDate.toISOString().slice(0,10));
    cy.get('#until').type(mockDate.toISOString().slice(0,10));
    cy.get('#workload').type('50');
    cy.get('tr button').click({multiple:true});
  });

  it('should delete the added employee', function () {
    cy.get('span').contains('Tableview').click();
    cy.get('.btn-outline-danger').click({multiple:true});
  });
});
