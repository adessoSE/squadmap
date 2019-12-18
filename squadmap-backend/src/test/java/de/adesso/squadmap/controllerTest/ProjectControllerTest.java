package de.adesso.squadmap.controllerTest;

import de.adesso.squadmap.controller.ProjectController;
import de.adesso.squadmap.exceptions.project.InvalidProjectDescriptionException;
import de.adesso.squadmap.exceptions.project.InvalidProjectSinceException;
import de.adesso.squadmap.exceptions.project.InvalidProjectTitleException;
import de.adesso.squadmap.exceptions.project.InvalidProjectUntilException;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.create.CreateProjectUseCase;
import de.adesso.squadmap.port.driver.project.delete.DeleteProjectUseCase;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.get.GetProjectUseCase;
import de.adesso.squadmap.port.driver.project.get.ListProjectUseCase;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectUseCase;
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
class ProjectControllerTest {

    @Mock
    private CreateProjectUseCase createProjectUseCase;
    @Mock
    private DeleteProjectUseCase deleteProjectUseCase;
    @Mock
    private GetProjectUseCase getProjectUseCase;
    @Mock
    private ListProjectUseCase listProjectUseCase;
    @Mock
    private UpdateProjectUseCase updateProjectUseCase;
    @InjectMocks
    private ProjectController projectController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.projectController).build();
    }

    @Test
    void checkIfGetAllProjectsReturnsAll() throws Exception {
        //given
        GetProjectResponse getProjectResponse = new GetProjectResponse(
                1L, "t", "d", LocalDate.now(), LocalDate.now(), true, Collections.emptyList());
        List<GetProjectResponse> allProjects = Collections.singletonList(getProjectResponse);
        when(listProjectUseCase.listProjects()).thenReturn(allProjects);

        //when
        MvcResult result = mockMvc.perform(get("/project/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetProjectResponse> responses = JsonMapper.asResponseList(result, GetProjectResponse.class);

        //then
        assertThat(responses).isEqualTo(allProjects);
        verify(listProjectUseCase, times(1)).listProjects();
    }

    @Test
    void checkIfGetProjectReturnsTheProject() throws Exception {
        //given
        long projectId = 1;
        GetProjectResponse getProjectResponse = new GetProjectResponse(
                1L, "t", "d", LocalDate.now(), LocalDate.now(), true, Collections.emptyList());
        when(getProjectUseCase.getProject(projectId)).thenReturn(getProjectResponse);

        //when
        MvcResult result = mockMvc.perform(get("/project/{id}", projectId))
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
        CreateProjectCommand createProjectCommand = new CreateProjectCommand(
                "t", "d", LocalDate.now(), LocalDate.now(), true);
        when(createProjectUseCase.createProject(createProjectCommand)).thenReturn(projectId);

        //when
        MvcResult result = mockMvc.perform(post("/project/create")
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
        UpdateProjectCommand updateProjectCommand = new UpdateProjectCommand(
                "t", "d", LocalDate.now(), LocalDate.now(), true);
        doNothing().when(updateProjectUseCase).updateProject(updateProjectCommand, projectId);

        //when
        mockMvc.perform(put("/project/update/{id}", projectId)
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
        mockMvc.perform(delete("/project/delete/{id}", projectId))
                .andExpect(status().isOk());

        //then
        verify(deleteProjectUseCase, times(1)).deleteProject(projectId);
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectTitleException() {
        //given
        CreateProjectCommand projectTitleNull = new CreateProjectCommand(
                null, "", LocalDate.now(), LocalDate.now(), false);
        CreateProjectCommand projectTitleEmpty = new CreateProjectCommand(
                "", "", LocalDate.now(), LocalDate.now(), false);
        CreateProjectCommand projectTitleTooLong = new CreateProjectCommand(
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "", LocalDate.now(), LocalDate.now(), false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectDescriptionException() {
        //given
        CreateProjectCommand projectDescriptionNull = new CreateProjectCommand(
                "t", null, LocalDate.now(), LocalDate.now(), false);
        CreateProjectCommand projectDescriptionTooLong = new CreateProjectCommand(
                "t", "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
                LocalDate.now(), LocalDate.now(), false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectDescriptionNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectDescriptionException());
        assertThatThrownBy(() ->
                mockMvc.perform(post("/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectDescriptionTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectDescriptionException());
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectSinceException() {
        //given
        CreateProjectCommand projectSinceNull = new CreateProjectCommand(
                "t", "", null, LocalDate.now(), false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectSinceNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectSinceException());
    }

    @Test
    void checkIfCreateProjectThrowsInvalidProjectUntilException() {
        //given
        CreateProjectCommand projectUntilNull = new CreateProjectCommand(
                "t", "", LocalDate.now(), null, false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(post("/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectUntilNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectUntilException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectTitleException() {
        //given
        UpdateProjectCommand projectTitleNull = new UpdateProjectCommand(
                null, "", LocalDate.now(), LocalDate.now(), false);
        UpdateProjectCommand projectTitleEmpty = new UpdateProjectCommand(
                "", "", LocalDate.now(), LocalDate.now(), false);
        UpdateProjectCommand projectTitleTooLong = new UpdateProjectCommand(
                "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "", LocalDate.now(), LocalDate.now(), false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/project/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/project/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleEmpty)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/project/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectTitleTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectTitleException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectDescriptionException() {
        //given
        UpdateProjectCommand projectDescriptionNull = new UpdateProjectCommand(
                "t", null, LocalDate.now(), LocalDate.now(), false);
        UpdateProjectCommand projectDescriptionTooLong = new UpdateProjectCommand(
                "t", "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
                LocalDate.now(), LocalDate.now(), false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/project/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectDescriptionNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectDescriptionException());
        assertThatThrownBy(() ->
                mockMvc.perform(put("/project/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectDescriptionTooLong)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectDescriptionException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectSinceException() {
        //given
        UpdateProjectCommand projectSinceNull = new UpdateProjectCommand(
                "t", "", null, LocalDate.now(), false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/project/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectSinceNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectSinceException());
    }

    @Test
    void checkIfUpdateProjectThrowsInvalidProjectUntilException() {
        //given
        UpdateProjectCommand projectUntilNull = new UpdateProjectCommand(
                "t", "", LocalDate.now(), null, false);

        //then
        assertThatThrownBy(() ->
                mockMvc.perform(put("/project/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(projectUntilNull)))
                        .andExpect(status().isOk()))
                .hasCause(new InvalidProjectUntilException());
    }
}
