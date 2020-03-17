package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.exceptions.*;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectCommandMother;
import de.adesso.squadmap.application.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.delete.DeleteProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectResponseMother;
import de.adesso.squadmap.application.port.driver.project.get.GetProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectCommandMother;
import de.adesso.squadmap.application.port.driver.project.update.UpdateProjectUseCase;
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

@SpringBootTest(classes = ProjectController.class)
@ActiveProfiles("test")
class ProjectControllerTest {

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
    private ProjectController projectController;
    private MockMvc mockMvc;
    private static final String apiUrl = "/api/project";

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.projectController).build();
    }

    @Test
    void checkIfGetAllProjectsReturnsAll() throws Exception {
        //given
        GetProjectResponse getProjectResponse = GetProjectResponseMother.complete().build();
        when(listProjectUseCase.listProjects()).thenReturn(Collections.singletonList(getProjectResponse));

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetProjectResponse> responses = JsonMapper.asResponseList(result, GetProjectResponse.class);

        //then
        assertThat(responses.size()).isOne();
        assertThat(responses.get(0)).isEqualTo(getProjectResponse);
        verify(listProjectUseCase, times(1)).listProjects();
    }

    @Test
    void checkIfGetProjectReturnsTheProject() throws Exception {
        //given
        long projectId = 1;
        GetProjectResponse getProjectResponse = GetProjectResponseMother.complete().build();
        when(getProjectUseCase.getProject(projectId)).thenReturn(getProjectResponse);

        //when
        MvcResult result = mockMvc.perform(get(apiUrl + "/{id}", projectId))
                .andExpect(status().isOk())
                .andReturn();
        GetProjectResponse response = JsonMapper.asResponse(result, GetProjectResponse.class);

        //then
        assertThat(response).isEqualTo(getProjectResponse);
        verify(getProjectUseCase, times(1)).getProject(projectId);
    }

    @Test
    void checkIfCreateProjectCreatesTheProject() throws Exception {
        //given
        long projectId = 1;
        CreateProjectCommand createProjectCommand = CreateProjectCommandMother.complete().build();
        when(createProjectUseCase.createProject(createProjectCommand)).thenReturn(projectId);

        //when
        MvcResult result = mockMvc.perform(post(apiUrl + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(createProjectCommand))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        long response = Long.parseLong(result.getResponse().getContentAsString());

        //then
        assertThat(response).isEqualTo(projectId);
        verify(createProjectUseCase, times(1)).createProject(createProjectCommand);
    }

    @Test
    void checkIfUpdateProjectUpdatesTheProject() throws Exception {
        //given
        long projectId = 1;
        UpdateProjectCommand updateProjectCommand = UpdateProjectCommandMother.complete().build();
        doNothing().when(updateProjectUseCase).updateProject(updateProjectCommand, projectId);

        //when
        mockMvc.perform(put(apiUrl + "/update/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateProjectCommand))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateProjectUseCase, times(1)).updateProject(updateProjectCommand, projectId);
    }

    @Test
    void checkIfDeleteProjectDeletesTheProject() throws Exception {
        //given
        long projectId = 1;
        doNothing().when(deleteProjectUseCase).deleteProject(projectId);

        //when
        mockMvc.perform(delete(apiUrl + "/delete/{id}", projectId))
                .andExpect(status().isOk());

        //then
        verify(deleteProjectUseCase, times(1)).deleteProject(projectId);
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectTitleException() {
        //given
        CreateProjectCommand projectTitleNull = CreateProjectCommandMother.complete().title(null).build();
        CreateProjectCommand projectTitleEmpty = CreateProjectCommandMother.complete().title("").build();
        CreateProjectCommand projectTitleTooLong = CreateProjectCommandMother.complete()
                .title("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff")
                .build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectDescriptionException() {
        //given
        CreateProjectCommand projectDescriptionNull = CreateProjectCommandMother.complete().description(null).build();
        CreateProjectCommand projectDescriptionTooLong = CreateProjectCommandMother.complete()
                .description("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff")
                .build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectDescriptionNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectDescriptionException());
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectDescriptionTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectDescriptionException());
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectSinceException() {
        //given
        CreateProjectCommand projectSinceNull = CreateProjectCommandMother.complete().since(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectSinceNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectSinceException());
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectUntilException() {
        //given
        CreateProjectCommand projectUntilNull = CreateProjectCommandMother.complete().until(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectUntilNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectUntilException());
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectURLException() {
        //given
        CreateProjectCommand projectInvalidURL = CreateProjectCommandMother.complete()
                .sites(Collections.singletonList("null"))
                .build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectInvalidURL)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectUrlListException());
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectIsExternalException() {
        //given
        CreateProjectCommand projectIsExternalNull = CreateProjectCommandMother.complete().isExternal(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post(apiUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectIsExternalNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectIsExternalException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectTitleException() {
        //given
        UpdateProjectCommand projectTitleNull = UpdateProjectCommandMother.complete().title(null).build();
        UpdateProjectCommand projectTitleEmpty = UpdateProjectCommandMother.complete().title("").build();
        UpdateProjectCommand projectTitleTooLong = UpdateProjectCommandMother.complete()
                .title("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff")
                .build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectDescriptionException() {
        //given
        UpdateProjectCommand projectDescriptionNull = UpdateProjectCommandMother.complete().description(null).build();
        UpdateProjectCommand projectDescriptionTooLong = UpdateProjectCommandMother.complete()
                .description("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff")
                .build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectDescriptionNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectDescriptionException());
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectDescriptionTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectDescriptionException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectSinceException() {
        //given
        UpdateProjectCommand projectSinceNull = UpdateProjectCommandMother.complete().since(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectSinceNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectSinceException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectUntilException() {
        //given
        UpdateProjectCommand projectUntilNull = UpdateProjectCommandMother.complete().until(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectUntilNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectUntilException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectURLException() {
        //given
        UpdateProjectCommand projectInvalidURL = UpdateProjectCommandMother.complete()
                .sites(Collections.singletonList("null"))
                .build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectInvalidURL)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectUrlListException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectIsExternalException() {
        //given
        UpdateProjectCommand projectIsExternalNull = UpdateProjectCommandMother.complete().isExternal(null).build();

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put(apiUrl + "/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectIsExternalNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectIsExternalException());
    }
}
