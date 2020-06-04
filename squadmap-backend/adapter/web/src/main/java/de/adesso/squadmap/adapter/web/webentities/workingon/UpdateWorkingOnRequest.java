package de.adesso.squadmap.adapter.web.webentities.workingon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.adesso.squadmap.application.port.driver.workingon.update.UpdateWorkingOnCommand;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonDeserialize(builder = UpdateWorkingOnRequest.UpdateWorkingOnRequestBuilder.class)
public class UpdateWorkingOnRequest {

    private final long employeeId;
    private final long projectId;
    private final LocalDate since;
    private final LocalDate until;
    private final Integer workload;

    public UpdateWorkingOnCommand asCommand() {
        return UpdateWorkingOnCommand.builder()
                .employeeId(employeeId)
                .projectId(projectId)
                .since(since)
                .until(until)
                .workload(workload)
                .build();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class UpdateWorkingOnRequestBuilder{}
}
