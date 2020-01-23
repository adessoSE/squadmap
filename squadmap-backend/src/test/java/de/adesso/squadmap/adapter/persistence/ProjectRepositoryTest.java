package de.adesso.squadmap.adapter.persistence;

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
        ProjectNeo4JEntity project = new ProjectNeo4JEntity("t", "", null, null, true, new ArrayList<>());
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
