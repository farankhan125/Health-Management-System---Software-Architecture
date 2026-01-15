package controller;
import model.Clinician;
import model.ClinicianRepository;
import view.ClinicianView;
import java.util.List;
public class ClinicianController {
    public final ClinicianRepository repository;
    private final ClinicianView view;
    public ClinicianController(ClinicianRepository repo, ClinicianView view) {
        this.repository = repo;
        this.view = view;
        this.view.setController(this);
        refresh();
    }
public ClinicianView getView() {
    return view;
}
    public ClinicianRepository getRepository() {
        return repository;
    }
    public String generateId() {
        return repository.generateNewId();
    }
    public void refresh() {
        view.showClinicians(repository.getAll());
    }
    public void addClinician(Clinician c) {
        repository.addAndAppend(c);
        refresh();
    }
    public void updateClinician(Clinician c) {
        repository.update(c);
        refresh();
    }
    public void deleteById(String id) {
        Clinician c = repository.findById(id);
        if (c != null) {
            repository.remove(c);
            refresh();
        }
    }
}
