describe('employee-view', ()=>{
  beforeEach(() => {
    cy.visit('/employee');
  });

  it('should show the employee table', () => {
    cy.get('table');
    cy.get('.table-head').contains('th','ID');
    cy.get('.table-head').contains('th','First Name');
    cy.get('.table-head').contains('th','Last Name');
    cy.get('.table-head').contains('th','Actions');
  });

  it('should be able to add an employee', function () {
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

  it('should be able to edit an employee', function () {
    cy.get('#searchText').type('testname');
    cy.get('.btn-outline-info').click();
    cy.get('#firstName').clear().type('testEmployee');
    cy.get(':nth-child(11) > .row > .btn').click();
  });

  it('should hide the external employees', function () {
    cy.get('table').contains('td','testEmployee');
    cy.get('#hideExternal').click();
    cy.get('table').contains('td','testEmployee').should('not.exist');
    cy.get('#hideExternal').click();
  });

  it('should navigate to detail view of new employee', function () {
    cy.get('table').contains('td','testEmployee').click();
    cy.location().should((loc) => {
      expect(loc.pathname).to.contain('/employee/');
    });
  });

  it('should filter and delete the new employee', function () {
    cy.get('#searchText').type('testemployee');
    cy.get('.btn-outline-danger').click();
    cy.get('#searchText').clear();
    cy.get('table').contains('td','testProjectNew').should('not.exist');
  });
});
