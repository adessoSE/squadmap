package de.adesso.squadmap.service;

import de.adesso.squadmap.repository.WorkingOnRepository;
import de.adesso.squadmap.models.WorkingOn;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkingOnService {

    private WorkingOnRepository workingOnRepository;

    public WorkingOnService(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    public Iterable<WorkingOn> findAll() {
        return workingOnRepository.findAll();
    }

    public Optional<WorkingOn> findById(long workingOnId) {
        return workingOnRepository.findById(workingOnId);
    }

    public WorkingOn save(WorkingOn workingOn) {
        return workingOnRepository.save(workingOn);
    }

    public void deleteById(long workingOnId) {
        workingOnRepository.deleteById(workingOnId);
    }
}
