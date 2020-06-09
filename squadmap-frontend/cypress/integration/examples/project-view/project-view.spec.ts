describe('project-view', () => {

  beforeEach(() => {
    cy.visit('/project');
  });

  it('should show the projects table', () => {
    cy.get('table');
    cy.get('.table-head').contains('th','Project');
    cy.get('.table-head').contains('th','Since');
    cy.get('.table-head').contains('th','Until');
    cy.get('.table-head').contains('th','Actions');
  });

  it('should be able to add a project', function () {
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

  it('should be able to edit a project', function () {
    cy.get('#searchText').type('testproject');
    cy.get('.btn-outline-info').click({multiple: true});
    cy.get('#title').clear().type('testProjectNew');
    cy.get('.row > .btn').click();
  });

  it('should hide the old projects', function () {
    cy.get('#hideOld').click();
    cy.get('#link > :nth-child(1)');
    cy.get('#hideOld').click();
  });


  it('should hide the external projects', function () {
    cy.get('#hideExternal').click();
    cy.get('table').contains('td','testProjectNew').should('not.exist');
    cy.get('#hideExternal').click();
  });

  it('should navigate to detail view of new project', function () {
     cy.get('table').contains('td','testProjectNew').click();
    cy.location().should((loc) => {
      expect(loc.pathname).to.contain('/project/');
    });
  });

  it('should filter and delete the new project', function () {
    cy.get('#searchText').type('testprojectnew');
    cy.get('.btn-outline-danger').click();
    cy.get('#searchText').clear();
    cy.get('table').contains('td','testProjectNew').should('not.exist');
  });

});
