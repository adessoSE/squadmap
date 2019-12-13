package de.adesso.squadmap.controllerTest;

import de.adesso.squadmap.controller.EmployeeController;
import de.adesso.squadmap.port.driver.employee.create.CreateEmployeeCommand;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.employee.update.UpdateEmployeeCommand;
import de.adesso.squadmap.service.employee.*;
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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeControllerTest {

    @Mock
    private CreateEmployeeService createEmployeeService;
    @Mock
    private DeleteEmployeeService deleteEmployeeService;
    @Mock
    private GetEmployeeService getEmployeeService;
    @Mock
    private ListEmployeeService listEmployeeService;
    @Mock
    private UpdateEmployeeService updateEmployeeService;
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
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse(1L, "f", "l", null, "e", "0", true, Collections.emptyList());
        List<GetEmployeeResponse> allEmployees = Collections.singletonList(getEmployeeResponse);
        when(listEmployeeService.listEmployees()).thenReturn(allEmployees);

        //when
        MvcResult result = mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetEmployeeResponse> responses = JsonMapper.asResponseList(result, GetEmployeeResponse.class);

        //then
        assertThat(responses).isEqualTo(allEmployees);
        verify(listEmployeeService, times(1)).listEmployees();
    }

    @Test
    void checkIfGetEmployeeReturnsTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse(1L, "f", "l", null, "e", "0", true, Collections.emptyList());
        when(getEmployeeService.getEmployee(employeeId)).thenReturn(getEmployeeResponse);

        //when
        MvcResult result = mockMvc.perform(get("/employee/{id}", employeeId))
                .andExpect(status().isOk())
                .andReturn();
        GetEmployeeResponse response = JsonMapper.asResponse(result, GetEmployeeResponse.class);

        //then
        assertThat(response).isEqualTo(getEmployeeResponse);
        verify(getEmployeeService, times(1)).getEmployee(employeeId);
    }

    @Test
    void checkIfCreateEmployeeCreatesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand("f", "l", null, "e", "0", true);
        when(createEmployeeService.createEmployee(createEmployeeCommand)).thenReturn(employeeId);

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
        verify(createEmployeeService, times(1)).createEmployee(createEmployeeCommand);
    }

    @Test
    void checkIfUpdateEmployeeUpdatesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        UpdateEmployeeCommand updateEmployeeCommand = new UpdateEmployeeCommand();
        doNothing().when(updateEmployeeService).updateEmployee(updateEmployeeCommand, employeeId);

        //when
        mockMvc.perform(put("/employee/update/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateEmployeeCommand))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateEmployeeService, times(1)).updateEmployee(updateEmployeeCommand, employeeId);
    }

    @Test
    void checkIfDeleteEmployeeDeletesTheEmployee() throws Exception {
        //given
        long employeeId = 1;
        doNothing().when(deleteEmployeeService).deleteEmployee(employeeId);

        //when
        mockMvc.perform(delete("/employee/delete/{id}", employeeId))
                .andExpect(status().isOk());

        //then
        verify(deleteEmployeeService, times(1)).deleteEmployee(employeeId);
    }
}
