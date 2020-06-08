/// <reference types="cypress" />

describe('map-view', () => {
 beforeEach(() => {
   cy.visit('/map');
 });

  it('should show the navbar', function () {
    cy.get('nav').contains('a','Squadmap');
    cy.get('nav').contains('a','Map');
    cy.get('nav').contains('a','Projects');
    cy.get('nav').contains('a','Employees');
    cy.get('nav').contains('a','New');
  });

  it('should open the dropdown menu new', function () {
    cy.get('.dropdown').click();
    cy.get('.dropdown-menu').contains('button','Employee');
    cy.get('.dropdown-menu').contains('button','Project');
    cy.get('.show').should('exist');
    cy.get('.dropdown').click();
    cy.get('.show').should('not.exist');
  });

  it('should navigate to right pages', function () {
    cy.get('a[routerlink="project"]').click();
    cy.location().should((loc) => {
      expect(loc.pathname).to.eq('/project');
    });
    cy.get('a[routerlink="employee"]').click();
    cy.location().should((loc) => {
      expect(loc.pathname).to.eq('/employee');
    });
  });

  it('should trigger the right functions', function () {
    cy.get('[name="save"]');
    cy.get('[name="arrow-down-circle"]');
    cy.get('[name="move"]');
    cy.get('[name="edit-3"]').click();
    cy.get('[name="chevron-left"]');
    cy.get('[name="arrow-up-right"]');
    cy.get('[name="x"]');
    cy.get('[name="chevron-left"]').click();
    cy.get('[name="chevron-left"]').should('not.exist');
    cy.get('[name="arrow-up-right"]').should('not.exist');
    cy.get('[name="x"]').should('not.exist');
  });
});
