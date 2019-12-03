package de.adesso.squadmap.service;

import de.adesso.squadmap.models.WorkingOn;
import de.adesso.squadmap.repository.WorkingOnRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkingOnService {

    private WorkingOnRepository workingOnRepository;

    public WorkingOnService(WorkingOnRepository workingOnRepository) {
        this.workingOnRepository = workingOnRepository;
    }

    public Iterable<WorkingOn> findAllWorkingOn() {
        return workingOnRepository.findAll();
    }

    public Optional<WorkingOn> findWorkingOnById(long workingOnId) {
        return workingOnRepository.findById(workingOnId);
    }

    public WorkingOn saveWorkingOn(WorkingOn workingOn) {
        return workingOnRepository.save(workingOn);
    }

    public void deleteWorkingOn(WorkingOn workingOn){ workingOnRepository.delete(workingOn); }

    public void deleteWorkingOnById(long workingOnId) {
        workingOnRepository.deleteById(workingOnId);
    }
}
