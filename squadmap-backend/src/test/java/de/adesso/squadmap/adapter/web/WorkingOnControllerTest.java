package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.webentities.workingon.*;
import de.adesso.squadmap.application.domain.WorkingOn;
import de.adesso.squadmap.application.domain.WorkingOnMother;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.delete.DeleteWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.ListWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnUseCase;
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

@WebMvcTest(WorkingOnController.class)
@ActiveProfiles("test")
class WorkingOnControllerTest {

    private static final String apiUrl = "/api/workingOn";
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
    @MockBean
    private ResponseMapper<WorkingOn, GetWorkingOnResponse> workingOnResponseMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkIfGetAllWorkingOnReturnsAll() throws Exception {
        //given
        WorkingOn workingOn = WorkingOnMother.complete().build();
        GetWorkingOnResponse getWorkingOnResponse = GetWorkingOnResponseMother.complete().build();
        List<WorkingOn> workingOns = Collections.singletonList(workingOn);
        when(listWorkingOnUseCase.listWorkingOn()).thenReturn(workingOns);
        when(workingOnResponseMapper.mapToResponseEntity(workingOn, workingOns)).thenReturn(getWorkingOnResponse);

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetWorkingOnResponse> responses = JsonMapper.asResponseList(result, GetWorkingOnResponse.class);

        //then
        assertThat(responses.size()).isOne();
        assertThat(responses.get(0)).isEqualTo(getWorkingOnResponse);
        verify(listWorkingOnUseCase, times(1)).listWorkingOn();
        verify(workingOnResponseMapper, times(1)).mapToResponseEntity(workingOn, workingOns);
    }

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        WorkingOn workingOn = WorkingOnMother.complete().build();
        GetWorkingOnResponse getWorkingOnResponse = GetWorkingOnResponseMother.complete().build();
        List<WorkingOn> workingOns = new ArrayList<>();
        when(getWorkingOnUseCase.getWorkingOn(workingOnId)).thenReturn(workingOn);
        when(listWorkingOnUseCase.listWorkingOn()).thenReturn(workingOns);
        when(workingOnResponseMapper.mapToResponseEntity(workingOn, workingOns)).thenReturn(getWorkingOnResponse);

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/{id}", workingOnId))
                .andExpect(status().isOk())
                .andReturn();
        GetWorkingOnResponse response = JsonMapper.asResponse(result, GetWorkingOnResponse.class);

        //then
        assertThat(response).isEqualTo(getWorkingOnResponse);
        verify(getWorkingOnUseCase, times(1)).getWorkingOn(workingOnId);
        verify(listWorkingOnUseCase, times(1)).listWorkingOn();
        verify(workingOnResponseMapper, times(1)).mapToResponseEntity(workingOn, workingOns);
    }

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        CreateWorkingOnRequest createWorkingOnRequest = CreateWorkingOnRequestMother.complete().build();
        when(createWorkingOnUseCase.createWorkingOn(any())).thenReturn(workingOnId);

        //when
        MvcResult result = mockMvc.perform(post(apiUrl + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(createWorkingOnRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        long response = Long.parseLong(result.getResponse().getContentAsString());

        //then
        assertThat(response).isEqualTo(workingOnId);
        verify(createWorkingOnUseCase, times(1)).createWorkingOn(any());
    }

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        UpdateWorkingOnRequest updateWorkingOnRequest = UpdateWorkingOnRequestMother.complete().build();
        doNothing().when(updateWorkingOnUseCase).updateWorkingOn(any(), eq(workingOnId));

        //when
        mockMvc.perform(put(apiUrl + "/update/{id}", workingOnId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateWorkingOnRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateWorkingOnUseCase, times(1)).updateWorkingOn(any(), eq(workingOnId));
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
}
