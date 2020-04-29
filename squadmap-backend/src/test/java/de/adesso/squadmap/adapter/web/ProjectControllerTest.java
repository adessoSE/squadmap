package de.adesso.squadmap.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adesso.squadmap.adapter.web.webentities.project.CreateProjectRequest;
import de.adesso.squadmap.adapter.web.webentities.project.CreateProjectRequestMother;
import de.adesso.squadmap.adapter.web.webentities.project.UpdateProjectRequest;
import de.adesso.squadmap.adapter.web.webentities.project.UpdateProjectRequestMother;
import de.adesso.squadmap.application.domain.exceptions.AlreadyExistsException;
import de.adesso.squadmap.application.domain.exceptions.NotFoundException;
import de.adesso.squadmap.application.domain.exceptions.ProjectAlreadyExistsException;
import de.adesso.squadmap.application.domain.exceptions.ProjectNotFoundException;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.delete.DeleteProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectUseCase;
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

@WebMvcTest(ProjectController.class)
@ActiveProfiles("test")
class ProjectControllerTest {

    private static final String API_URL = "/api/project";
    @MockBean
    private CreateProjectUseCase createProjectUseCase;
    @MockBean
    private DeleteProjectUseCase deleteProjectUseCase;
    @MockBean
    private GetProjectUseCase getProjectUseCase;
    @MockBean
    private ListProjectUseCase listProjectUseCase;
    @MockBean
    private UpdateProjectUseCase updateProjectUseCase;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkIfGetAllProjectsReturnsAll() throws Exception {
        //given
        List<GetProjectResponse> expectedResponse =
                Collections.singletonList(GetProjectResponseMother.complete().build());
        when(listProjectUseCase.listProjects()).thenReturn(expectedResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(get(API_URL + "/all"))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
        verify(listProjectUseCase, times(1)).listProjects();
        verifyNoMoreInteractions(listProjectUseCase);
        verifyNoInteractions(createProjectUseCase, deleteProjectUseCase, getProjectUseCase, updateProjectUseCase);
    }

    @Test
    void checkIfGetProjectReturnsTheProject() throws Exception {
        //given
        long projectId = 1;
        GetProjectResponse expectedResponse = GetProjectResponseMother.complete().build();
        when(getProjectUseCase.getProject(projectId)).thenReturn(expectedResponse);

        //when
        MvcResult mvcResult = mockMvc.perform(get(API_URL + "/{id}", projectId))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(expectedResponse));
        verify(getProjectUseCase, times(1)).getProject(projectId);
        verifyNoMoreInteractions(getProjectUseCase);
        verifyNoInteractions(createProjectUseCase, deleteProjectUseCase, listProjectUseCase, updateProjectUseCase);
    }

    @Test
    void checkIfCreateProjectCreatesTheProject() throws Exception {
        //given
        long projectId = 1;
        CreateProjectRequest createProjectRequest = CreateProjectRequestMother.complete().build();
        when(createProjectUseCase.createProject(any())).thenReturn(projectId);

        //when
        MvcResult result = mockMvc.perform(post(API_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProjectRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        long response = Long.parseLong(result.getResponse().getContentAsString());

        //then
        assertThat(response).isEqualTo(projectId);
        verify(createProjectUseCase, times(1)).createProject(any());
        verifyNoMoreInteractions(createProjectUseCase);
        verifyNoInteractions(deleteProjectUseCase, getProjectUseCase, listProjectUseCase, updateProjectUseCase);
    }

    @Test
    void checkIfUpdateProjectUpdatesTheProject() throws Exception {
        //given
        long projectId = 1;
        UpdateProjectRequest updateProjectRequest = UpdateProjectRequestMother.complete().build();
        doNothing().when(updateProjectUseCase).updateProject(any(), eq(projectId));

        //when
        mockMvc.perform(put(API_URL + "/update/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProjectRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateProjectUseCase, times(1)).updateProject(any(), eq(projectId));
        verifyNoMoreInteractions(updateProjectUseCase);
        verifyNoInteractions(createProjectUseCase, deleteProjectUseCase, getProjectUseCase, listProjectUseCase);
    }

    @Test
    void checkIfDeleteProjectDeletesTheProject() throws Exception {
        //given
        long projectId = 1;
        doNothing().when(deleteProjectUseCase).deleteProject(projectId);

        //when
        mockMvc.perform(delete(API_URL + "/delete/{id}", projectId))
                .andExpect(status().isOk());

        //then
        verify(deleteProjectUseCase, times(1)).deleteProject(projectId);
        verifyNoMoreInteractions(deleteProjectUseCase);
        verifyNoInteractions(createProjectUseCase, getProjectUseCase, listProjectUseCase, updateProjectUseCase);
    }

    @Test
    void checkIfCreateProjectTriggersValidation() throws Exception {
        //given
        CreateProjectRequest createProjectRequest = CreateProjectRequestMother.invalid().build();

        //when
        mockMvc.perform(post(API_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProjectRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(createProjectUseCase, deleteProjectUseCase,
                getProjectUseCase, listProjectUseCase, updateProjectUseCase);
    }

    @Test
    void checkIfUpdateProjectTriggersValidation() throws Exception {
        //given
        long projectId = 1;
        UpdateProjectRequest updateProjectRequest = UpdateProjectRequestMother.invalid().build();

        //when
        mockMvc.perform(put(API_URL + "/update/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProjectRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(createProjectUseCase, deleteProjectUseCase,
                getProjectUseCase, listProjectUseCase, updateProjectUseCase);
    }

    @Test
    void checkIfProjectNotFoundExceptionGetsHandled() throws Exception {
        //given
        long projectId = 1;
        when(getProjectUseCase.getProject(projectId)).thenThrow(new ProjectNotFoundException(projectId));

        //when
        mockMvc.perform(get(API_URL + "/get/{id}", projectId))
                .andExpect(status().isNotFound());

        //then
        verifyNoInteractions(createProjectUseCase, deleteProjectUseCase,
                getProjectUseCase, listProjectUseCase, updateProjectUseCase);
    }

    @Test
    void checkIfProjectAlreadyExistsExceptionGetsHandled() throws Exception {
        //given
        CreateProjectRequest createProjectRequest = CreateProjectRequestMother.complete().build();
        when(createProjectUseCase.createProject(any())).thenThrow(new ProjectAlreadyExistsException(""));

        //when
        mockMvc.perform(post(API_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProjectRequest))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        //then
        verify(createProjectUseCase, times(1)).createProject(any());
        verifyNoMoreInteractions(createProjectUseCase);
        verifyNoInteractions(deleteProjectUseCase, getProjectUseCase, listProjectUseCase, updateProjectUseCase);
    }
}
