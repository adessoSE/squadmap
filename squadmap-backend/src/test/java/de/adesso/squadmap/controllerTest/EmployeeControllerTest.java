package de.adesso.squadmap.controllerTest;

import de.adesso.squadmap.controller.EmployeeController;
import de.adesso.squadmap.exceptions.employee.*;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeControllerTest {

    @Mock
    private CreateEmployeeUseCase createEmployeeUseCase;
    @Mock
    private DeleteEmployeeUseCase deleteEmployeeUseCase;
    @Mock
    private GetEmployeeUseCase getEmployeeUseCase;
    @Mock
    private ListEmployeeUseCase listEmployeeUseCase;
    @Mock
    private UpdateEmployeeUseCase updateEmployeeUseCase;
    @InjectMocks
    private EmployeeController employeeController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.employeeController).build();
    }

    @Test
    void checkIfGetAllEmployeesReturnsAll() throws Exception {
        //given
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse(
                1L, "f", "l", LocalDate.now().minusDays(1), "e.f@g.de", "03456345667", true, Collections.emptyList());
        List<GetEmployeeResponse> allEmployees = Collections.singletonList(getEmployeeResponse);
        when(listEmployeeUseCase.listEmployees()).thenReturn(allEmployees);

        //when
        MvcResult result = mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetEmployeeResponse> responses = JsonMapper.asResponseList(result, GetEmployeeResponse.class);

        //then
        assertThat(responses).isEqualTo(allEmployees);
        verify(listEmployeeUseCase, times(1)).listEmployees();
    }

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse(
                1L, "f", "l", LocalDate.now().minusDays(1), "e.f@g.de", "03456345667", true, Collections.emptyList());
        when(getEmployeeUseCase.getEmployee(employeeId)).thenReturn(getEmployeeResponse);

        //when
        MvcResult result = mockMvc.perform(get("/employee/{id}", employeeId))
                .andExpect(status().isOk())
                .andReturn();
        GetEmployeeResponse response = JsonMapper.asResponse(result, GetEmployeeResponse.class);

        //then
        assertThat(response).isEqualTo(getEmployeeResponse);
        verify(getEmployeeUseCase, times(1)).getEmployee(employeeId);
    }

    @Test
    void checkIfCreateEmployeeCreatesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "e.f@g.de", "+493456345667", true);
        when(createEmployeeUseCase.createEmployee(createEmployeeCommand)).thenReturn(employeeId);

        //when
        MvcResult result = mockMvc.perform(post("/employee/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(createEmployeeCommand))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        long response = Long.parseLong(result.getResponse().getContentAsString());

        //then
        assertThat(response).isEqualTo(employeeId);
        verify(createEmployeeUseCase, times(1)).createEmployee(createEmployeeCommand);
    }

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "e.f@g.de", "03456345667", true);
        doNothing().when(updateEmployeeUseCase).updateEmployee(updateEmployeeCommand, employeeId);

        //when
        mockMvc.perform(put("/employee/update/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateEmployeeCommand))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateEmployeeUseCase, times(1)).updateEmployee(updateEmployeeCommand, employeeId);
    }

    @Test
    void checkIfDeleteEmployeeDeletesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        doNothing().when(deleteEmployeeUseCase).deleteEmployee(employeeId);

        //when
        mockMvc.perform(delete("/employee/delete/{id}", employeeId))
                .andExpect(status().isOk());

        //then
        verify(deleteEmployeeUseCase, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeFirstNameException() {
        //given
        CreateEmployeeCommand employeeNullFirstName = new CreateEmployeeCommand(
                null, "l", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);
        CreateEmployeeCommand employeeEmptyFirstName = new CreateEmployeeCommand(
                "", "l", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);
        CreateEmployeeCommand employeeTooLongFirstName = new CreateEmployeeCommand(
                "fffffffffffffffffffffffffffffffffffffffffffffffffff", "l", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeNullFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmptyFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeTooLongFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeLastNameException() {
        //given
        CreateEmployeeCommand employeeNullFirstName = new CreateEmployeeCommand(
                "f", null, LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);
        CreateEmployeeCommand employeeEmptyFirstName = new CreateEmployeeCommand(
                "f", "", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);
        CreateEmployeeCommand employeeTooLongFirstName = new CreateEmployeeCommand(
                "f", "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeNullFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmptyFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeTooLongFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeBirthdayException() {
        //given
        CreateEmployeeCommand employeeBirthdayNull = new CreateEmployeeCommand(
                "f", "l", null, "a.b@c.de", "0123456789", true);
        CreateEmployeeCommand employeeBirthdayInFuture = new CreateEmployeeCommand(
                "f", "l", LocalDate.now().plusDays(1), "a.b@c.de", "0123456789", true);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeBirthdayNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeBirthdayException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeBirthdayInFuture)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeBirthdayException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeEmailException() {
        //given
        CreateEmployeeCommand employeeEmailNull = new CreateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), null, "0123456789", true);
        CreateEmployeeCommand employeeEmailEmpty = new CreateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "", "0123456789", true);
        CreateEmployeeCommand employeeEmailNotValid = new CreateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "something", "0123456789", true);


        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailNotValid)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeePhoneException() {
        //given
        CreateEmployeeCommand employeePhoneNull = new CreateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "a.b@c.de", null, false);
        CreateEmployeeCommand employeePhoneEmpty = new CreateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "a.b@c.de", "", false);
        CreateEmployeeCommand employeePhoneNotValid = new CreateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "a.b@c.de", "1", false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneNotValid)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
    }


    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeFirstNameException() {
        //given
        UpdateEmployeeCommand employeeNullFirstName = new UpdateEmployeeCommand(
                null, "l", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);
        UpdateEmployeeCommand employeeEmptyFirstName = new UpdateEmployeeCommand(
                "", "l", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);
        UpdateEmployeeCommand employeeTooLongFirstName = new UpdateEmployeeCommand(
                "fffffffffffffffffffffffffffffffffffffffffffffffffff", "l", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeNullFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmptyFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeTooLongFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeLastNameException() {
        //given
        UpdateEmployeeCommand employeeNullFirstName = new UpdateEmployeeCommand(
                "f", null, LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);
        UpdateEmployeeCommand employeeEmptyFirstName = new UpdateEmployeeCommand(
                "f", "", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);
        UpdateEmployeeCommand employeeTooLongFirstName = new UpdateEmployeeCommand(
                "f", "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll", LocalDate.now().minusDays(1), "a.b@c.de", "0123456789", false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeNullFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmptyFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeTooLongFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeBirthdayException() {
        //given
        UpdateEmployeeCommand employeeBirthdayNull = new UpdateEmployeeCommand(
                "f", "l", null, "a.b@c.de", "0123456789", true);
        UpdateEmployeeCommand employeeBirthdayInFuture = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now().plusDays(1), "a.b@c.de", "0123456789", true);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeBirthdayNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeBirthdayException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeBirthdayInFuture)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeBirthdayException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeEmailException() {
        //given
        UpdateEmployeeCommand employeeEmailNull = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), null, "0123456789", true);
        UpdateEmployeeCommand employeeEmailEmpty = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "", "0123456789", true);
        UpdateEmployeeCommand employeeEmailNotValid = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "something", "0123456789", true);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailNotValid)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeePhoneException() {
        //given
        UpdateEmployeeCommand employeePhoneNull = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "a.b@c.de", null, false);
        UpdateEmployeeCommand employeePhoneEmpty = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "a.b@c.de", "", false);
        UpdateEmployeeCommand employeePhoneNotValid = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now().minusDays(1), "a.b@c.de", "1", false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneNotValid)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
    }
}
