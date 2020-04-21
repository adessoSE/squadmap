package de.adesso.squadmap.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adesso.squadmap.adapter.web.webentities.employee.CreateEmployeeRequest;
import de.adesso.squadmap.adapter.web.webentities.employee.CreateEmployeeRequestMother;
import de.adesso.squadmap.adapter.web.webentities.employee.UpdateEmployeeRequest;
import de.adesso.squadmap.adapter.web.webentities.employee.UpdateEmployeeRequestMother;
import de.adesso.squadmap.application.domain.exceptions.AlreadyExistsException;
import de.adesso.squadmap.application.domain.exceptions.NotFoundException;
import de.adesso.squadmap.application.port.driver.employee.create.CreateEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.delete.DeleteEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeResponseMother;
import de.adesso.squadmap.application.port.driver.employee.get.GetEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.get.ListEmployeeUseCase;
import de.adesso.squadmap.application.port.driver.employee.update.UpdateEmployeeUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@ActiveProfiles("test")
class EmployeeControllerTest {

    private static final String API_URL = "/api/employee";
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
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;



    @Test
    void checkIfGetAllEmployeesReturnsAll() throws Exception {
        //given
        List<GetEmployeeResponse> expectedResponse =
                Collections.singletonList(GetEmployeeResponseMother.complete().build());
        when(listEmployeeUseCase.listEmployees()).thenReturn(expectedResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(get(API_URL + "/all"))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
        verify(listEmployeeUseCase, times(1)).listEmployees();
        verifyNoMoreInteractions(listEmployeeUseCase);
        verifyNoInteractions(createEmployeeUseCase, deleteEmployeeUseCase, getEmployeeUseCase, updateEmployeeUseCase);
    }

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        GetEmployeeResponse getEmployeeResponse = GetEmployeeResponseMother.complete().build();
        when(getEmployeeUseCase.getEmployee(employeeId)).thenReturn(getEmployeeResponse);

        //when
        MvcResult result = mockMvc.perform(get(API_URL + "/{id}", employeeId))
                .andExpect(status().isOk())
                .andReturn();
        GetEmployeeResponse response = JsonMapper.asResponse(result, GetEmployeeResponse.class);

        //then
        assertThat(response)
                .isEqualTo(getEmployeeResponse);
        verify(getEmployeeUseCase, times(1)).getEmployee(employeeId);
        verifyNoMoreInteractions(getEmployeeUseCase);
        verifyNoInteractions(createEmployeeUseCase, deleteEmployeeUseCase, listEmployeeUseCase, updateEmployeeUseCase);
    }

    @Test
    void checkIfCreateEmployeeCreatesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        CreateEmployeeRequest createEmployeeRequest = CreateEmployeeRequestMother.complete().build();
        when(createEmployeeUseCase.createEmployee(any())).thenReturn(employeeId);

        //when
        MvcResult result = mockMvc.perform(post(API_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(createEmployeeRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        long response = Long.parseLong(result.getResponse().getContentAsString());

        //then
        assertThat(response).isEqualTo(employeeId);
        verify(createEmployeeUseCase, times(1)).createEmployee(any());
        verifyNoMoreInteractions(createEmployeeUseCase);
        verifyNoInteractions(deleteEmployeeUseCase, getEmployeeUseCase, listEmployeeUseCase, updateEmployeeUseCase);
    }

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        UpdateEmployeeRequest updateEmployeeRequest = UpdateEmployeeRequestMother.complete().build();
        doNothing().when(updateEmployeeUseCase).updateEmployee(any(), eq(employeeId));

        //when
        mockMvc.perform(put(API_URL + "/update/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateEmployeeRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateEmployeeUseCase, times(1)).updateEmployee(any(), eq(employeeId));
        verifyNoMoreInteractions(updateEmployeeUseCase);
        verifyNoInteractions(createEmployeeUseCase, deleteEmployeeUseCase, getEmployeeUseCase, listEmployeeUseCase);
    }

    @Test
    void checkIfDeleteEmployeeDeletesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        doNothing().when(deleteEmployeeUseCase).deleteEmployee(employeeId);

        //when
        mockMvc.perform(delete(API_URL + "/delete/{id}", employeeId))
                .andExpect(status().isOk());

        //then
        verify(deleteEmployeeUseCase, times(1)).deleteEmployee(employeeId);
        verifyNoMoreInteractions(deleteEmployeeUseCase);
        verifyNoInteractions(createEmployeeUseCase, getEmployeeUseCase, listEmployeeUseCase, updateEmployeeUseCase);
    }

    @Test
    void checkIfCreateEmployeeTriggersValidation() throws Exception {
        //given
        CreateEmployeeRequest createEmployeeRequest = CreateEmployeeRequestMother.invalid().build();

        //when
        mockMvc.perform(post(API_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(createEmployeeRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(createEmployeeUseCase, deleteEmployeeUseCase,
                getEmployeeUseCase, listEmployeeUseCase, updateEmployeeUseCase);
    }

    @Test
    void checkIfUpdateEmployeeTriggersValidation() throws Exception {
        //given
        long employeeId = 1;
        UpdateEmployeeRequest updateProjectRequest = UpdateEmployeeRequestMother.invalid().build();

        //when
        mockMvc.perform(put(API_URL + "/update/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateProjectRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(createEmployeeUseCase, deleteEmployeeUseCase,
                getEmployeeUseCase, listEmployeeUseCase, updateEmployeeUseCase);
    }

    @Test
    void checkIfEmployeeNotFoundExceptionGetsHandled() throws Exception {
        //given
        long employeeId = 1;
        UpdateEmployeeRequest updateEmployeeRequest = UpdateEmployeeRequestMother.complete().build();
        doThrow(new NotFoundException()).when(updateEmployeeUseCase).updateEmployee(any(), eq(employeeId));

        //when
        mockMvc.perform(put(API_URL + "/update/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateEmployeeRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then
        verify(updateEmployeeUseCase, times(1)).updateEmployee(any(), eq(employeeId));
        verifyNoMoreInteractions(updateEmployeeUseCase);
        verifyNoInteractions(createEmployeeUseCase, deleteEmployeeUseCase, getEmployeeUseCase, listEmployeeUseCase);
    }

    @Test
    void checkIfEmployeeAlreadyExistsExceptionGetsHandled() throws Exception {
        //given
        CreateEmployeeRequest createEmployeeRequest = CreateEmployeeRequestMother.complete().build();
        when(createEmployeeUseCase.createEmployee(any())).thenThrow(new AlreadyExistsException());

        //when
        mockMvc.perform(post(API_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(createEmployeeRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        //then
        verify(createEmployeeUseCase, times(1)).createEmployee(any());
        verifyNoMoreInteractions(createEmployeeUseCase);
        verifyNoInteractions(deleteEmployeeUseCase, getEmployeeUseCase, listEmployeeUseCase, updateEmployeeUseCase);

    }
}
