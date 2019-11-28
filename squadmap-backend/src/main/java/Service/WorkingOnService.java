package Service;

import Repository.WorkingOnRepository;
import models.WorkingOn;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkingOnService {

    private WorkingOnRepository workingOnRepository;

    public WorkingOnService(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    public Iterable<WorkingOn> getAllWorkingOn() {
        return workingOnRepository.findAll();
    }

    public Optional<WorkingOn> getWorkingOnById(long workingOnId) {
        return workingOnRepository.findById(workingOnId);
    }

    public WorkingOn saveWorkingOn(WorkingOn workingOn) {
        return workingOnRepository.save(workingOn);
    }

    public void deleteWorkingOnById(long workingOnId) {
        workingOnRepository.deleteById(workingOnId);
    }
}
