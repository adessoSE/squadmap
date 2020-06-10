describe('create-project-modal', () => {
  before(() => {
    cy.visit('/map');
    cy.get('#navbardrop').click();
    cy.get('.dropdown-menu > :nth-child(2)').click();
  })

  it('should add a project', function () {
    cy.get('#title').type('testProjectModal');
    cy.get('#description').type('testDescription');
    const mockDate = new Date();
    cy.get('#since').type(mockDate.toISOString().slice(0,10));
    cy.get('#until').type(mockDate.toISOString().slice(0,10));
    cy.get('#sites').type('www.google.de,');
    cy.get('#isExternal').check();
    cy.get('.row > .btn').click();
    cy.visit('/project');
    cy.get('table').contains('td','testProjectModal');
    cy.get('#searchText').type('testprojectmodal');
    cy.get('.btn-outline-danger').click();
  });
});
