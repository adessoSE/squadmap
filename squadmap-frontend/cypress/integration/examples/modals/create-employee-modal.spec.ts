describe('create-employee-modal', () => {
  before(() => {
    cy.visit('/map');
    cy.get('#navbardrop').click();
    cy.get('.dropdown-menu > :nth-child(1)').click();
  })

  it('should add an employee', function () {
    cy.get('#firstName').type('testNameModal');
    cy.get('#lastName').type('testNameModal');
    const mockDate = new Date('2000-01-01');
    cy.get('#birthday').type(mockDate.toISOString().slice(0,10));
    cy.get('#email').type('test12345@test.de');
    cy.get('#phone').type('+0123456789');
    cy.get('#isExternal').check();
    cy.get('#imageType').select('male');
    cy.get(':nth-child(9) > .btn > i-feather > .feather').click();
    cy.get(':nth-child(11) > .row > .btn').click();
    cy.visit('/employee');
    cy.get('table').contains('td','testNameModal');
    cy.get('#searchText').type('testnamemodal');
    cy.get('.btn-outline-danger').click();
  });
});
