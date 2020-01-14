package de.adesso.squadmap.controllerTest;

import de.adesso.squadmap.controller.WorkingOnController;
import de.adesso.squadmap.exceptions.workingon.InvalidWorkingOnSinceException;
import de.adesso.squadmap.exceptions.workingon.InvalidWorkingOnUntilException;
import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingon.create.CreateWorkingOnUseCase;
import de.adesso.squadmap.port.driver.workingon.delete.DeleteWorkingOnUseCase;
import de.adesso.squadmap.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.port.driver.workingon.get.GetWorkingOnUseCase;
import de.adesso.squadmap.port.driver.workingon.get.ListWorkingOnUseCase;
import de.adesso.squadmap.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.port.driver.workingon.update.UpdateWorkingOnUseCase;
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
class WorkingOnControllerTest {

    @Mock
    private CreateWorkingOnUseCase createWorkingOnUseCase;
    @Mock
    private DeleteWorkingOnUseCase deleteWorkingOnUseCase;
    @Mock
    private GetWorkingOnUseCase getWorkingOnUseCase;
    @Mock
    private ListWorkingOnUseCase listWorkingOnUseCase;
    @Mock
    private UpdateWorkingOnUseCase updateWorkingOnUseCase;
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
        when(listWorkingOnUseCase.listWorkingOn()).thenReturn(allWorkingOn);

        //when
        MvcResult result = mockMvc.perform(get("/workingOn/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetWorkingOnResponse> responses = JsonMapper.asResponseList(result, GetWorkingOnResponse.class);

        //then
        assertThat(responses).isEqualTo(allWorkingOn);
        verify(listWorkingOnUseCase, times(1)).listWorkingOn();
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
        when(getWorkingOnUseCase.getWorkingOn(workingOnId)).thenReturn(getWorkingOnResponse);

        //when
        MvcResult result = mockMvc.perform(get("/workingOn/{id}", workingOnId))
                .andExpect(status().isOk())
                .andReturn();
        GetWorkingOnResponse response = JsonMapper.asResponse(result, GetWorkingOnResponse.class);

        //then
        assertThat(response).isEqualTo(getWorkingOnResponse);
        verify(getWorkingOnUseCase, times(1)).getWorkingOn(workingOnId);
    }

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        CreateWorkingOnCommand createWorkingOnCommand = new CreateWorkingOnCommand(
                0, 0, LocalDate.now(), LocalDate.now());
        when(createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand)).thenReturn(workingOnId);

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
        verify(createWorkingOnUseCase, times(1)).createWorkingOn(createWorkingOnCommand);
    }

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        UpdateWorkingOnCommand updateWorkingOnCommand = new UpdateWorkingOnCommand(
                0, 0, LocalDate.now(), LocalDate.now());
        doNothing().when(updateWorkingOnUseCase).updateWorkingOn(updateWorkingOnCommand, workingOnId);

        //when
        mockMvc.perform(put("/workingOn/update/{id}", workingOnId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateWorkingOnCommand))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateWorkingOnUseCase, times(1)).updateWorkingOn(updateWorkingOnCommand, workingOnId);
    }

    @Test
    void checkIfDeleteWorkingOnDeletesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        doNothing().when(deleteWorkingOnUseCase).deleteWorkingOn(workingOnId);

        //when
        mockMvc.perform(delete("/workingOn/delete/{id}", workingOnId))
                .andExpect(status().isOk());

        //then
        verify(deleteWorkingOnUseCase, times(1)).deleteWorkingOn(workingOnId);
    }

    @Test
    void checkIfCreateWorkingOnThrowsInvalidWorkingONSinceException() {
        //given
        CreateWorkingOnCommand workingOnSinceNull = new CreateWorkingOnCommand(0, 0, null, LocalDate.now());

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/workingOn/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnSinceNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnSinceException());
    }

    @Test
    void checkIfCreateWorkingOnThrowsInvalidWorkingOnUntilException() {
        //given
        CreateWorkingOnCommand workingOnUntilNull = new CreateWorkingOnCommand(0, 0, LocalDate.now(), null);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/workingOn/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnUntilNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnUntilException());
    }

    @Test
    void checkIfUpdateWorkingOnThrowsInvalidWorkingONSinceException() {
        //given
        UpdateWorkingOnCommand workingOnSinceNull = new UpdateWorkingOnCommand(0, 0, null, LocalDate.now());

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/workingOn/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnSinceNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnSinceException());
    }

    @Test
    void checkIfUpdateWorkingOnThrowsInvalidWorkingOnUntilException() {
        //given
        UpdateWorkingOnCommand workingOnUntilNull = new UpdateWorkingOnCommand(0, 0, LocalDate.now(), null);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/workingOn/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnUntilNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnUntilException());
    }
}
