package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.exceptions.*;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private CreateEmployeeUseCase createEmployeeUseCase;
    @MockBean
    private DeleteEmployeeUseCase deleteEmployeeUseCase;
    @MockBean
    private GetEmployeeUseCase getEmployeeUseCase;
    @MockBean
    private ListEmployeeUseCase listEmployeeUseCase;
    @MockBean
    private UpdateEmployeeUseCase updateEmployeeUseCase;
    @Autowired
    private EmployeeController employeeController;
    private MockMvc mockMvc;
    private static final String validPhone = "0123456789";
    private static final LocalDate validBirthday = LocalDate.now().minusDays(1);
    private static final String validEmail = "a@b.de";
    private static final String validImage = "file://somewhere/file.sth";

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.employeeController).build();
    }

    @Test
    void checkIfGetAllEmployeesReturnsAll() throws Exception {
        //given
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse(1L, "f", "l",
                validBirthday, validEmail, validPhone, true, validImage, Collections.emptyList());
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
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse(1L, "f", "l",
                validBirthday, validEmail, validPhone, true, validImage, Collections.emptyList());
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
                "f", "l", validBirthday, validEmail, validPhone, true, validImage);
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
                "f", "l", validBirthday, validEmail, validPhone, true, validImage);
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
                null, "l", validBirthday, validEmail, validPhone, false, validImage);
        CreateEmployeeCommand employeeEmptyFirstName = new CreateEmployeeCommand(
                "", "l", validBirthday, validEmail, validPhone, false, validImage);
        CreateEmployeeCommand employeeTooLongFirstName = new CreateEmployeeCommand(
                "fffffffffffffffffffffffffffffffffffffffffffffffffff", "l",
                validBirthday, validEmail, validPhone, false, validImage);

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
                "f", null, validBirthday, validEmail, validPhone, false, validImage);
        CreateEmployeeCommand employeeEmptyFirstName = new CreateEmployeeCommand(
                "f", "", validBirthday, validEmail, validPhone, false, validImage);
        CreateEmployeeCommand employeeTooLongFirstName = new CreateEmployeeCommand(
                "f", "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll",
                validBirthday, validEmail, validPhone, false, validImage);

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
                "f", "l", null, validEmail, validPhone, true, validImage);
        CreateEmployeeCommand employeeBirthdayInFuture = new CreateEmployeeCommand(
                "f", "l", LocalDate.now().plusDays(1), validEmail, validPhone, true, validImage);

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
                "f", "l", validBirthday, null, validPhone, true, validImage);
        CreateEmployeeCommand employeeEmailEmpty = new CreateEmployeeCommand(
                "f", "l", validBirthday, "", validPhone, true, validImage);
        CreateEmployeeCommand employeeEmailNotValid = new CreateEmployeeCommand(
                "f", "l", validBirthday, "something", validPhone, true, validImage);


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
                "f", "l", validBirthday, validEmail, null, false, validImage);
        CreateEmployeeCommand employeePhoneEmpty = new CreateEmployeeCommand(
                "f", "l", validBirthday, validEmail, "", false, validImage);
        CreateEmployeeCommand employeePhoneNotValid = new CreateEmployeeCommand(
                "f", "l", validBirthday, validEmail, "1", false, validImage);

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
    void checkIfCreateEmployeeThrowsInvalidEmployeeImageException() {
        //given
        CreateEmployeeCommand employeeImageNull = new CreateEmployeeCommand("f", "l",
                validBirthday, validEmail, validPhone, false, null);
        CreateEmployeeCommand employeeWrongImage = new CreateEmployeeCommand("f", "l",
                validBirthday, validEmail, validPhone, false, "noUrl");

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeImageNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeImageException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeWrongImage)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeImageException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeFirstNameException() {
        //given
        UpdateEmployeeCommand employeeNullFirstName = new UpdateEmployeeCommand(
                null, "l", validBirthday, validEmail, validPhone, false, validImage);
        UpdateEmployeeCommand employeeEmptyFirstName = new UpdateEmployeeCommand(
                "", "l", validBirthday, validEmail, validPhone, false, validImage);
        UpdateEmployeeCommand employeeTooLongFirstName = new UpdateEmployeeCommand(
                "fffffffffffffffffffffffffffffffffffffffffffffffffff", "l",
                validBirthday, validEmail, validPhone, false, validImage);

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
                "f", null, validBirthday, validEmail, validPhone, false, validImage);
        UpdateEmployeeCommand employeeEmptyFirstName = new UpdateEmployeeCommand(
                "f", "", validBirthday, validEmail, validPhone, false, validImage);
        UpdateEmployeeCommand employeeTooLongFirstName = new UpdateEmployeeCommand(
                "f", "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll",
                validBirthday, validEmail, validPhone, false, validImage);

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
                "f", "l", null, validEmail, validPhone, true, validImage);
        UpdateEmployeeCommand employeeBirthdayInFuture = new UpdateEmployeeCommand(
                "f", "l", LocalDate.now().plusDays(1), validEmail, validPhone, true, validImage);

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
                "f", "l", validBirthday, null, validPhone, true, validImage);
        UpdateEmployeeCommand employeeEmailEmpty = new UpdateEmployeeCommand(
                "f", "l", validBirthday, "", validPhone, true, validImage);
        UpdateEmployeeCommand employeeEmailNotValid = new UpdateEmployeeCommand(
                "f", "l", validBirthday, "something", validPhone, true, validImage);

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
                "f", "l", validBirthday, validEmail, null, false, validImage);
        UpdateEmployeeCommand employeePhoneEmpty = new UpdateEmployeeCommand(
                "f", "l", validBirthday, validEmail, "", false, validImage);
        UpdateEmployeeCommand employeePhoneNotValid = new UpdateEmployeeCommand(
                "f", "l", validBirthday, validEmail, "1", false, validImage);

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

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeImageException() {
        UpdateEmployeeCommand employeeImageNull = new UpdateEmployeeCommand("f", "l",
                validBirthday, validEmail, validPhone, false, null);
        UpdateEmployeeCommand employeeWrongImage = new UpdateEmployeeCommand("f", "l",
                validBirthday, validEmail, validPhone, false, "noUrl");

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeImageNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeImageException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeWrongImage)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeImageException());
    }
}
