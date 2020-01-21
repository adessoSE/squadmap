package de.adesso.squadmap.repositoryTest;

import de.adesso.squadmap.domain.Project;
import de.adesso.squadmap.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void checkIfExistsByTitleReturnsTrueWhenExistent() {
        //given
        Project project = new Project("t", "", null, null, true, new ArrayList<>());
        projectRepository.save(project);

        //when
        boolean answer = projectRepository.existsByTitle(project.getTitle());

        //then
        assertThat(answer).isTrue();
    }

    @Test
    void checkIfExistsByTitleReturnsFalseWhenNotExistent() {
        //given

        //when
        boolean answer = projectRepository.existsByTitle("");

        //then
        assertThat(answer).isFalse();
    }
}
