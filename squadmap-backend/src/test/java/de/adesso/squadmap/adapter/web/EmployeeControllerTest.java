package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.webentities.employee.*;
import de.adesso.squadmap.application.domain.Employee;
import de.adesso.squadmap.application.domain.EmployeeMother;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.ListWorkingOnUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@ActiveProfiles("test")
class EmployeeControllerTest {

    private static final String apiUrl = "/api/employee";
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
    @MockBean
    private ListWorkingOnUseCase listWorkingOnUseCase;
    @MockBean
    private ResponseMapper<Employee, GetEmployeeResponse> employeeResponseMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkIfGetAllEmployeesReturnsAll() throws Exception {
        //given
        Employee employee = EmployeeMother.complete().build();
        GetEmployeeResponse getEmployeeResponse = GetEmployeeResponseMother.complete().build();
        List<WorkingOn> workingOns = new ArrayList<>();
        when(listEmployeeUseCase.listEmployees()).thenReturn(Collections.singletonList(employee));
        when(listWorkingOnUseCase.listWorkingOn()).thenReturn(workingOns);
        when(employeeResponseMapper.mapToResponseEntity(employee, workingOns)).thenReturn(getEmployeeResponse);

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetEmployeeResponse> responses = JsonMapper.asResponseList(result, GetEmployeeResponse.class);

        //then
        assertThat(responses.size()).isOne();
        assertThat(responses.get(0)).isEqualTo(getEmployeeResponse);
        verify(employeeResponseMapper, times(1)).mapToResponseEntity(employee, workingOns);
        verify(listWorkingOnUseCase, times(1)).listWorkingOn();
        verify(listEmployeeUseCase, times(1)).listEmployees();
    }

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        Employee employee = EmployeeMother.complete().build();
        GetEmployeeResponse getEmployeeResponse = GetEmployeeResponseMother.complete().build();
        List<WorkingOn> workingOns = new ArrayList<>();
        when(getEmployeeUseCase.getEmployee(employeeId)).thenReturn(employee);
        when(listWorkingOnUseCase.listWorkingOn()).thenReturn(workingOns);
        when(employeeResponseMapper.mapToResponseEntity(employee, workingOns)).thenReturn(getEmployeeResponse);

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/{id}", employeeId))
                .andExpect(status().isOk())
                .andReturn();
        GetEmployeeResponse response = JsonMapper.asResponse(result, GetEmployeeResponse.class);

        //then
        assertThat(response).isEqualTo(getEmployeeResponse);
        verify(employeeResponseMapper, times(1)).mapToResponseEntity(employee, workingOns);
        verify(listWorkingOnUseCase, times(1)).listWorkingOn();
        verify(getEmployeeUseCase, times(1)).getEmployee(employeeId);
    }

    @Test
    void checkIfCreateEmployeeCreatesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        CreateEmployeeRequest createEmployeeRequest = CreateEmployeeRequestMother.complete().build();
        when(createEmployeeUseCase.createEmployee(any())).thenReturn(employeeId);

        //when
        MvcResult result = mockMvc.perform(post(apiUrl + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(createEmployeeRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        long response = Long.parseLong(result.getResponse().getContentAsString());

        //then
        assertThat(response).isEqualTo(employeeId);
        verify(createEmployeeUseCase, times(1)).createEmployee(any());
    }

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        UpdateEmployeeRequest updateEmployeeRequest = UpdateEmployeeRequestMother.complete().build();
        doNothing().when(updateEmployeeUseCase).updateEmployee(any(), eq(employeeId));

        //when
        mockMvc.perform(put(apiUrl + "/update/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateEmployeeRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateEmployeeUseCase, times(1)).updateEmployee(any(), eq(employeeId));
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
}
