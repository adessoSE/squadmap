package de.adesso.squadmap.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adesso.squadmap.adapter.web.webentities.workingon.CreateWorkingOnRequest;
import de.adesso.squadmap.adapter.web.webentities.workingon.CreateWorkingOnRequestMother;
import de.adesso.squadmap.adapter.web.webentities.workingon.UpdateWorkingOnRequest;
import de.adesso.squadmap.adapter.web.webentities.workingon.UpdateWorkingOnRequestMother;
import de.adesso.squadmap.application.domain.exceptions.WorkingOnAlreadyExistsException;
import de.adesso.squadmap.application.domain.exceptions.WorkingOnNotFoundException;
import de.adesso.squadmap.application.port.driver.workingon.create.CreateWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.delete.DeleteWorkingOnUseCase;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponse;
import de.adesso.squadmap.application.port.driver.workingon.get.GetWorkingOnResponseMother;
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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkingOnController.class)
@ActiveProfiles("test")
class WorkingOnControllerTest {

    private static final String API_URL = "/api/workingOn";
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
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkIfGetAllWorkingOnReturnsAll() throws Exception {
        //given
        List<GetWorkingOnResponse> expectedResponse =
                Collections.singletonList(GetWorkingOnResponseMother.complete().build());
        when(listWorkingOnUseCase.listWorkingOn()).thenReturn(expectedResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(get(API_URL + "/all"))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
        verify(listWorkingOnUseCase, times(1)).listWorkingOn();
        verifyNoMoreInteractions(listWorkingOnUseCase);
        verifyNoInteractions(createWorkingOnUseCase, deleteWorkingOnUseCase, getWorkingOnUseCase, updateWorkingOnUseCase);
    }

    @Test
    void checkIfGetWorkingOnReturnsTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        GetWorkingOnResponse expectedResponse = GetWorkingOnResponseMother.complete().build();
        when(getWorkingOnUseCase.getWorkingOn(workingOnId)).thenReturn(expectedResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(get(API_URL + "/{id}", workingOnId))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
        verify(getWorkingOnUseCase, times(1)).getWorkingOn(workingOnId);
        verifyNoMoreInteractions(getWorkingOnUseCase);
        verifyNoInteractions(createWorkingOnUseCase, deleteWorkingOnUseCase, listWorkingOnUseCase, updateWorkingOnUseCase);
    }

    @Test
    void checkIfCreateWorkingOnCreatesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        CreateWorkingOnRequest createWorkingOnRequest = CreateWorkingOnRequestMother.complete().build();
        when(createWorkingOnUseCase.createWorkingOn(any())).thenReturn(workingOnId);

        //when
        MvcResult result = mockMvc.perform(post(API_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createWorkingOnRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        long response = Long.parseLong(result.getResponse().getContentAsString());

        //then
        assertThat(response).isEqualTo(workingOnId);
        verify(createWorkingOnUseCase, times(1)).createWorkingOn(any());
        verifyNoMoreInteractions(createWorkingOnUseCase);
        verifyNoInteractions(deleteWorkingOnUseCase, getWorkingOnUseCase, listWorkingOnUseCase, updateWorkingOnUseCase);
    }

    @Test
    void checkIfUpdateWorkingOnUpdatesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        UpdateWorkingOnRequest updateWorkingOnRequest = UpdateWorkingOnRequestMother.complete().build();
        doNothing().when(updateWorkingOnUseCase).updateWorkingOn(any(), eq(workingOnId));

        //when
        mockMvc.perform(put(API_URL + "/update/{id}", workingOnId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateWorkingOnRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateWorkingOnUseCase, times(1)).updateWorkingOn(any(), eq(workingOnId));
        verifyNoMoreInteractions(updateWorkingOnUseCase);
        verifyNoInteractions(createWorkingOnUseCase, deleteWorkingOnUseCase, getWorkingOnUseCase, listWorkingOnUseCase);
    }

    @Test
    void checkIfDeleteWorkingOnDeletesTheRelation() throws Exception {
        //given
        long workingOnId = 1;
        doNothing().when(deleteWorkingOnUseCase).deleteWorkingOn(workingOnId);

        //when
        mockMvc.perform(delete(API_URL + "/delete/{id}", workingOnId))
                .andExpect(status().isOk());

        //then
        verify(deleteWorkingOnUseCase, times(1)).deleteWorkingOn(workingOnId);
        verifyNoMoreInteractions(deleteWorkingOnUseCase);
        verifyNoInteractions(createWorkingOnUseCase, getWorkingOnUseCase, listWorkingOnUseCase, updateWorkingOnUseCase);
    }

    @Test
    void checkIfCreateWorkingOnTriggersValidation() throws Exception {
        //given
        CreateWorkingOnRequest createWorkingOnRequest = CreateWorkingOnRequestMother.invalid().build();

        //when
        mockMvc.perform(post(API_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createWorkingOnRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(createWorkingOnUseCase, deleteWorkingOnUseCase,
                getWorkingOnUseCase, listWorkingOnUseCase, updateWorkingOnUseCase);
    }

    @Test
    void checkIfUpdateWorkingOnTriggersValidation() throws Exception {
        //given
        long workingOnId = 1;
        UpdateWorkingOnRequest updateWorkingOnRequest = UpdateWorkingOnRequestMother.invalid().build();

        //when
        mockMvc.perform(put(API_URL + "/update/{id}", workingOnId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateWorkingOnRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(createWorkingOnUseCase, deleteWorkingOnUseCase,
                getWorkingOnUseCase, listWorkingOnUseCase, updateWorkingOnUseCase);
    }

    @Test
    void checkIfWorkingOnNotFoundExceptionGetsHandled() throws Exception {
        //given
        long workingOnId = 1;
        when(getWorkingOnUseCase.getWorkingOn(workingOnId)).thenThrow(new WorkingOnNotFoundException(workingOnId));

        //when
        mockMvc.perform(get(API_URL + "/get/{id}", workingOnId))
                .andExpect(status().isNotFound());

        //then
        verifyNoInteractions(createWorkingOnUseCase, deleteWorkingOnUseCase,
                getWorkingOnUseCase, listWorkingOnUseCase, updateWorkingOnUseCase);
    }

    @Test
    void checkIfWorkingOnAlreadyExistsExceptionGetsHandled() throws Exception {
        //given
        CreateWorkingOnRequest createWorkingOnRequest = CreateWorkingOnRequestMother.complete().build();
        when(createWorkingOnUseCase.createWorkingOn(any()))
                .thenThrow(new WorkingOnAlreadyExistsException(1, 1));

        //when
        mockMvc.perform(post(API_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createWorkingOnRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());


        //then
        verify(createWorkingOnUseCase, times(1)).createWorkingOn(any());
        verifyNoMoreInteractions(createWorkingOnUseCase);
        verifyNoInteractions(deleteWorkingOnUseCase, getWorkingOnUseCase, listWorkingOnUseCase, updateWorkingOnUseCase);
    }
}
