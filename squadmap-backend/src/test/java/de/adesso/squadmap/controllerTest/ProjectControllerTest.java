package de.adesso.squadmap.controllerTest;

import de.adesso.squadmap.controller.ProjectController;
import de.adesso.squadmap.port.driver.project.create.CreateProjectCommand;
import de.adesso.squadmap.port.driver.project.get.GetProjectResponse;
import de.adesso.squadmap.port.driver.project.update.UpdateProjectCommand;
import de.adesso.squadmap.service.project.*;
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
class ProjectControllerTest {

    @Mock
    private CreateProjectService createProjectService;
    @Mock
    private DeleteProjectService deleteProjectService;
    @Mock
    private GetProjectService getProjectService;
    @Mock
    private ListProjectService listProjectService;
    @Mock
    private UpdateProjectService updateProjectService;
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
        when(listProjectService.listProjects()).thenReturn(allProjects);

        //when
        MvcResult result = mockMvc.perform(get("/project/all"))
                .andExpect(status().isOk())
                .andReturn();
        List<GetProjectResponse> responses = JsonMapper.asResponseList(result, GetProjectResponse.class);

        //then
        assertThat(responses).isEqualTo(allProjects);
        verify(listProjectService, times(1)).listProjects();
    }

    @Test
    void checkIfGetProjectReturnsTheProject() throws Exception {
        //given
        long projectId = 1;
        GetProjectResponse getProjectResponse = new GetProjectResponse(
                1L, "t", "d", LocalDate.now(), LocalDate.now(), true, Collections.emptyList());
        when(getProjectService.getProject(projectId)).thenReturn(getProjectResponse);

        //when
        MvcResult result = mockMvc.perform(get("/project/{id}", projectId))
                .andExpect(status().isOk())
                .andReturn();
        GetProjectResponse response = JsonMapper.asResponse(result, GetProjectResponse.class);

        //then
        assertThat(response).isEqualTo(getProjectResponse);
        verify(getProjectService, times(1)).getProject(projectId);
    }

    @Test
    void checkIfCreateProjectCreatesTheProject() throws Exception {
        //given
        long projectId = 1;
        CreateProjectCommand createProjectCommand = new CreateProjectCommand(
                "t", "d", LocalDate.now(), LocalDate.now(), true);
        when(createProjectService.createProject(createProjectCommand)).thenReturn(projectId);

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
        verify(createProjectService, times(1)).createProject(createProjectCommand);
    }

    @Test
    void checkIfUpdateProjectUpdatesTheProject() throws Exception {
        //given
        long projectId = 1;
        UpdateProjectCommand updateProjectCommand = new UpdateProjectCommand(
                "t", "d", LocalDate.now(), LocalDate.now(), true);
        doNothing().when(updateProjectService).updateProject(updateProjectCommand, projectId);

        //when
        mockMvc.perform(put("/project/update/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonMapper.asJsonString(updateProjectCommand))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        verify(updateProjectService, times(1)).updateProject(updateProjectCommand, projectId);
    }

    @Test
    void checkIfDeleteProjectDeletesTheProject() throws Exception {
        //given
        long projectId = 1;
        doNothing().when(deleteProjectService).deleteProject(projectId);

        //when
        mockMvc.perform(delete("/project/delete/{id}", projectId))
                .andExpect(status().isOk());

        //then
        verify(deleteProjectService, times(1)).deleteProject(projectId);
    }
}
