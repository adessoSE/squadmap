package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.exceptions.InvalidWorkingOnSinceException;
import de.adesso.squadmap.adapter.web.exceptions.InvalidWorkingOnUntilException;
import de.adesso.squadmap.adapter.web.exceptions.InvalidWorkingOnWorkloadException;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnCommandMother;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.delete.DeleteWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponseMother;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.ListWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommandMother;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnUseCase;
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

    @MockBean
    private CreateWorkingOnUseCase createWorkingOnUseCase;
    @MockBean
    private DeleteWorkingOnUseCase deleteWorkingOnUseCase;
    @MockBean
    private GetWorkingOnUseCase getWorkingOnUseCase;
    @MockBean
    private ListWorkingOnUseCase listWorkingOnUseCase;
    @MockBean
    private UpdateWorkingOnUseCase updateWorkingOnUseCase;
    @Autowired
    private WorkingOnController workingOnController;
    private MockMvc mockMvc;
    private static final String apiUrl = "/api/workingOn";
    
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.workingOnController).build();
    }

    @Test
    void checkIfGetAllWorkingOnReturnsAll() throws Exception {
        //given
        GetWorkingOnResponse getWorkingOnResponse = GetWorkingOnResponseMother.complete().build();
        when(listWorkingOnUseCase.listWorkingOn()).thenReturn(Collections.singletonList(getWorkingOnResponse));

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetWorkingOnResponse> responses = JsonMapper.asResponseList(result, GetWorkingOnResponse.class);

        //then
        assertThat(responses.size()).isOne();
        assertThat(responses.get(0)).isEqualTo(getWorkingOnResponse);
        verify(listWorkingOnUseCase, times(1)).listWorkingOn();
    }

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        GetWorkingOnResponse getWorkingOnResponse = GetWorkingOnResponseMother.complete().build();
        when(getWorkingOnUseCase.getWorkingOn(workingOnId)).thenReturn(getWorkingOnResponse);

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/{id}", workingOnId))
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
        CreateWorkingOnCommand createWorkingOnCommand = CreateWorkingOnCommandMother.complete().build();
        when(createWorkingOnUseCase.createWorkingOn(createWorkingOnCommand)).thenReturn(workingOnId);

        //when
        MvcResult result = mockMvc.perform(post(apiUrl + "/create")
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
        UpdateWorkingOnCommand updateWorkingOnCommand = UpdateWorkingOnCommandMother.complete().build();
        doNothing().when(updateWorkingOnUseCase).updateWorkingOn(updateWorkingOnCommand, workingOnId);

        //when
        mockMvc.perform(put(apiUrl + "/update/{id}", workingOnId)
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
        mockMvc.perform(delete(apiUrl + "/delete/{id}", workingOnId))
                .andExpect(status().isOk());

        //then
        verify(deleteWorkingOnUseCase, times(1)).deleteWorkingOn(workingOnId);
    }

    @Test
    void checkIfCreateWorkingOnThrowsInvalidWorkingONSinceException() {
        //given
        CreateWorkingOnCommand workingOnSinceNull = CreateWorkingOnCommandMother.complete().since(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnSinceNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnSinceException());
    }

    @Test
    void checkIfCreateWorkingOnThrowsInvalidWorkingOnUntilException() {
        //given
        CreateWorkingOnCommand workingOnUntilNull = CreateWorkingOnCommandMother.complete().until(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnUntilNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnUntilException());
    }

    @Test
    void checkIfCreateWorkingOnThrowsInvalidWorkingOnWorkloadException() {
        //given
        CreateWorkingOnCommand workingOnWorkloadOutOfBound = CreateWorkingOnCommandMother.complete().workload(-1).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnWorkloadOutOfBound)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnWorkloadException());
    }

    @Test
    void checkIfUpdateWorkingOnThrowsInvalidWorkingONSinceException() {
        //given
        UpdateWorkingOnCommand workingOnSinceNull = UpdateWorkingOnCommandMother.complete().since(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnSinceNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnSinceException());
    }

    @Test
    void checkIfUpdateWorkingOnThrowsInvalidWorkingOnUntilException() {
        //given
        UpdateWorkingOnCommand workingOnUntilNull = UpdateWorkingOnCommandMother.complete().until(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnUntilNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnUntilException());
    }

    @Test
    void checkIfUpdateWorkingOnThrowsInvalidWorkingOnWorkloadException() {
        //given
        UpdateWorkingOnCommand workingOnWorkloadOutOfBound = UpdateWorkingOnCommandMother.complete().workload(-1).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(workingOnWorkloadOutOfBound)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidWorkingOnWorkloadException());
    }
}
