package de.adesso.squadmap.port.driver.project.get;

import de.adesso.squadmap.port.driver.employee.get.GetEmployeeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectResponse {

    private Long projectId;
    private String title;
    private String description;
    private LocalDate since;
    private LocalDate until;
    private Boolean isExternal;
    private List<GetEmployeeResponse> employees;
}
