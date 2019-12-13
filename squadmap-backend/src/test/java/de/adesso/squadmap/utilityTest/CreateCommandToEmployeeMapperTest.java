package de.adesso.squadmap.utilityTest;

import de.adesso.squadmap.utility.CreateCommandToEmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CreateCommandToEmployeeMapperTest {

    @Autowired
    private CreateCommandToEmployeeMapper mapper;

}
