// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })

Cypress.Commands.add('addDummyProject',() =>{
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
