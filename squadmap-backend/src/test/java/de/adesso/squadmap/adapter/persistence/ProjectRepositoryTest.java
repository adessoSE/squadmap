package de.adesso.squadmap.adapter.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProjectRepository.class)
@ActiveProfiles("test")
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void checkIfExistsByTitleReturnsTrueWhenExistent() {
        //given
        ProjectNeo4JEntity project = ProjectNeo4JEntityMother.complete().projectId(null).build();
        long id = projectRepository.save(project).getProjectId();
        ProjectNeo4JEntity pr = projectRepository.findById(id).orElse(null);
        assertThat(pr).isNotNull();

        //when
        boolean answer = projectRepository.existsByTitle(project.getTitle());

        //then
        assertThat(answer).isTrue();
    }

    @Test
    void checkIfExistsByTitleReturnsFalseWhenNotExistent() {
        //given

        //when
        boolean answer = projectRepository.existsByTitle("t");

        //then
        assertThat(answer).isFalse();
    }
}
