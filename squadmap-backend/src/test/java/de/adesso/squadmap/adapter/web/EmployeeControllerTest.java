package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.exceptions.*;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeCommandMother;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeCommandMother;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeUseCase;
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

@SpringBootTest(classes = EmployeeController.class)
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
    private static final String apiUrl = "/api/employee";

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.employeeController).build();
    }

    @Test
    void checkIfGetAllEmployeesReturnsAll() throws Exception {
        //given
        GetEmployeeResponse employeeResponse = GetEmployeeResponseMother.complete().build();
        when(listEmployeeUseCase.listEmployees()).thenReturn(Collections.singletonList(employeeResponse));

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetEmployeeResponse> responses = JsonMapper.asResponseList(result, GetEmployeeResponse.class);

        //then
        assertThat(responses.size()).isOne();
        assertThat(responses.get(0)).isEqualTo(employeeResponse);
        verify(listEmployeeUseCase, times(1)).listEmployees();
    }

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        GetEmployeeResponse getEmployeeResponse = GetEmployeeResponseMother.complete().build();
        when(getEmployeeUseCase.getEmployee(employeeId)).thenReturn(getEmployeeResponse);

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/{id}", employeeId))
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
        CreateEmployeeCommand createEmployeeCommand = CreateEmployeeCommandMother.complete().build();
        when(createEmployeeUseCase.createEmployee(createEmployeeCommand)).thenReturn(employeeId);

        //when
        MvcResult result = mockMvc.perform(post(apiUrl + "/create")
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
        UpdateEmployeeCommand updateEmployeeCommand = UpdateEmployeeCommandMother.complete().build();
        doNothing().when(updateEmployeeUseCase).updateEmployee(updateEmployeeCommand, employeeId);

        //when
        mockMvc.perform(put(apiUrl + "/update/{id}", employeeId)
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
        mockMvc.perform(delete(apiUrl + "/delete/{id}", employeeId))
                .andExpect(status().isOk());

        //then
        verify(deleteEmployeeUseCase, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeFirstNameException() {
        //given
        CreateEmployeeCommand employeeNullFirstName = CreateEmployeeCommandMother.complete().firstName(null).build();
        CreateEmployeeCommand employeeEmptyFirstName = CreateEmployeeCommandMother.complete().firstName("").build();
        CreateEmployeeCommand employeeTooLongFirstName = CreateEmployeeCommandMother.complete()
                .firstName("fffffffffffffffffffffffffffffffffffffffffffffffffff").build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeNullFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmptyFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeTooLongFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeLastNameException() {
        //given
        CreateEmployeeCommand employeeNullFirstName = CreateEmployeeCommandMother.complete().lastName(null).build();
        CreateEmployeeCommand employeeEmptyFirstName = CreateEmployeeCommandMother.complete().lastName("").build();
        CreateEmployeeCommand employeeTooLongFirstName = CreateEmployeeCommandMother.complete()
                .lastName("lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll").build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeNullFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmptyFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeTooLongFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeBirthdayException() {
        //given
        CreateEmployeeCommand employeeBirthdayNull = CreateEmployeeCommandMother.complete().birthday(null).build();
        CreateEmployeeCommand employeeBirthdayInFuture = CreateEmployeeCommandMother.complete()
                .birthday(LocalDate.now().plusDays(1)).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeBirthdayNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeBirthdayException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeBirthdayInFuture)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeBirthdayException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeEmailException() {
        //given
        CreateEmployeeCommand employeeEmailNull = CreateEmployeeCommandMother.complete().email(null).build();
        CreateEmployeeCommand employeeEmailEmpty = CreateEmployeeCommandMother.complete().email("").build();
        CreateEmployeeCommand employeeEmailNotValid = CreateEmployeeCommandMother.complete().email("null").build();


        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailNotValid)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeePhoneException() {
        //given
        CreateEmployeeCommand employeePhoneNull = CreateEmployeeCommandMother.complete().phone(null).build();
        CreateEmployeeCommand employeePhoneTooLong = CreateEmployeeCommandMother.complete()
                .phone("000000000000000000000").build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeImageException() {
        //given
        CreateEmployeeCommand employeeImageNull = CreateEmployeeCommandMother.complete().image(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeImageNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeImageException());
    }

    @Test
    void checkIfCreateEmployeeThrowsInvalidEmployeeIsExternalException() {
        //given
        CreateEmployeeCommand employeeIsExternalNull = CreateEmployeeCommandMother.complete().isExternal(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeIsExternalNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeIsExternalException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeFirstNameException() {
        //given
        UpdateEmployeeCommand employeeNullFirstName = UpdateEmployeeCommandMother.complete().firstName(null).build();
        UpdateEmployeeCommand employeeEmptyFirstName = UpdateEmployeeCommandMother.complete().firstName("").build();
        UpdateEmployeeCommand employeeTooLongFirstName = UpdateEmployeeCommandMother.complete()
                .firstName("fffffffffffffffffffffffffffffffffffffffffffffffffff").build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeNullFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmptyFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeTooLongFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeFirstNameException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeLastNameException() {
        //given
        UpdateEmployeeCommand employeeNullFirstName = UpdateEmployeeCommandMother.complete().lastName(null).build();
        UpdateEmployeeCommand employeeEmptyFirstName = UpdateEmployeeCommandMother.complete().lastName("").build();
        UpdateEmployeeCommand employeeTooLongFirstName = UpdateEmployeeCommandMother.complete()
                .lastName("lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll").build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeNullFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmptyFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeTooLongFirstName)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeLastNameException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeBirthdayException() {
        //given
        UpdateEmployeeCommand employeeBirthdayNull = UpdateEmployeeCommandMother.complete().birthday(null).build();
        UpdateEmployeeCommand employeeBirthdayInFuture = UpdateEmployeeCommandMother.complete()
                .birthday(LocalDate.now().plusDays(1)).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeBirthdayNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeBirthdayException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeBirthdayInFuture)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeBirthdayException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeEmailException() {
        //given
        UpdateEmployeeCommand employeeEmailNull =  UpdateEmployeeCommandMother.complete().email(null).build();
        UpdateEmployeeCommand employeeEmailEmpty = UpdateEmployeeCommandMother.complete().email("").build();
        UpdateEmployeeCommand employeeEmailNotValid = UpdateEmployeeCommandMother.complete().email("null").build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeEmailNotValid)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeEmailException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeePhoneException() {
        //given
        UpdateEmployeeCommand employeePhoneNull = UpdateEmployeeCommandMother.complete().phone(null).build();
        UpdateEmployeeCommand employeePhoneTooLong = UpdateEmployeeCommandMother.complete()
                .phone("000000000000000000000").build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeePhoneTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeePhoneNumberException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeImageException() {
        UpdateEmployeeCommand employeeImageNull = UpdateEmployeeCommandMother.complete().image(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeImageNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeImageException());
    }

    @Test
    void checkIfUpdateEmployeeThrowsInvalidEmployeeIsExternalException() {
        UpdateEmployeeCommand employeeIsExternalNull = UpdateEmployeeCommandMother.complete().isExternal(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(employeeIsExternalNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidEmployeeIsExternalException());
    }
}
