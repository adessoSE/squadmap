package de.adesso.squadmap.controllerTest;

import de.adesso.squadmap.controller.WorkingOnController;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.workingOn.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingOn.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingOn.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.service.workingOn.*;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class WorkingOnControllerTest {

    @Mock
    private CreateWorkingOnService createWorkingOnService;
    @Mock
    private DeleteWorkingOnService deleteWorkingOnService;
    @Mock
    private GetWorkingOnService getWorkingOnService;
    @Mock
    private ListWorkingOnService listWorkingOnService;
    @Mock
    private UpdateWorkingOnService updateWorkingOnService;
    @InjectMocks
    private WorkingOnController workingOnController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.workingOnController).build();
    }

    @Test
    void checkIfGetAllWorkingOnReturnsAll() throws Exception {
        //given
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse(
                1L, "f", "l", LocalDate.now(), "e.f@g.de", "0", true, Collections.emptyList());
        GetProjectResponse getProjectResponse = new GetProjectResponse(
                1L, "t", "d", LocalDate.now(), LocalDate.now(), true, Collections.emptyList());
        GetWorkingOnResponse getWorkingOnResponse = new GetWorkingOnResponse(
                1L, getEmployeeResponse, getProjectResponse, LocalDate.now(), LocalDate.now());
        List<GetWorkingOnResponse> allWorkingOn = Collections.singletonList(getWorkingOnResponse);
        when(listWorkingOnService.listWorkingOn()).thenReturn(allWorkingOn);

        //when
        MvcResult result = mockMvc.perform(get("/workingOn/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetWorkingOnResponse> responses = JsonMapper.asResponseList(result, GetWorkingOnResponse.class);

        //then
        assertThat(responses).isEqualTo(allWorkingOn);
        verify(listWorkingOnService, times(1)).listWorkingOn();
    }

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        GetEmployeeResponse getEmployeeResponse = new GetEmployeeResponse(
                1L, "f", "l", LocalDate.now(), "e.f@g.de", "0", true, Collections.emptyList());
        GetProjectResponse getProjectResponse = new GetProjectResponse(
                1L, "t", "d", LocalDate.now(), LocalDate.now(), true, Collections.emptyList());
        GetWorkingOnResponse getWorkingOnResponse = new GetWorkingOnResponse(
                workingOnId, getEmployeeResponse, getProjectResponse, LocalDate.now(), LocalDate.now());
        when(getWorkingOnService.getWorkingOn(workingOnId)).thenReturn(getWorkingOnResponse);

        //when
        MvcResult result = mockMvc.perform(get("/workingOn/{id}", workingOnId))
                .andExpect(status().isOk())
                .andReturn();
        GetWorkingOnResponse response = JsonMapper.asResponse(result, GetWorkingOnResponse.class);

        //then
        assertThat(response).isEqualTo(getWorkingOnResponse);
        verify(getWorkingOnService, times(1)).getWorkingOn(workingOnId);
    }

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        CreateWorkingOnCommand createWorkingOnCommand = new CreateWorkingOnCommand(
                0, 0, LocalDate.now(), LocalDate.now());
        when(createWorkingOnService.createWorkingOn(createWorkingOnCommand)).thenReturn(workingOnId);

        //when
        MvcResult result = mockMvc.perform(post("/workingOn/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(createWorkingOnCommand))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        long response = Long.parseLong(result.getResponse().getContentAsString());

        //then
        assertThat(response).isEqualTo(workingOnId);
        verify(createWorkingOnService, times(1)).createWorkingOn(createWorkingOnCommand);
    }

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        UpdateWorkingOnCommand updateWorkingOnCommand = new UpdateWorkingOnCommand(
                0, 0, LocalDate.now(), LocalDate.now());
        doNothing().when(updateWorkingOnService).updateWorkingOn(updateWorkingOnCommand, workingOnId);

        //when
        mockMvc.perform(put("/workingOn/update/{id}", workingOnId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateWorkingOnCommand))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateWorkingOnService, times(1)).updateWorkingOn(updateWorkingOnCommand, workingOnId);
    }

    @Test
    void checkIfDeleteWorkingOnDeletesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        doNothing().when(deleteWorkingOnService).deleteWorkingOn(workingOnId);

        //when
        mockMvc.perform(delete("/workingOn/delete/{id}", workingOnId))
                .andExpect(status().isOk());

        //then
        verify(deleteWorkingOnService, times(1)).deleteWorkingOn(workingOnId);
    }
}
