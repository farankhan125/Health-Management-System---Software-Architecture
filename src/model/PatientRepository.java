package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class PatientRepository {
    private final List<Patient> patients = new ArrayList<>();
    private final String csvPath;
    public PatientRepository(String csvPath) {
        this.csvPath = csvPath;
        load();
    }
    public List<String> getAllIds() {
    List<String> ids = new ArrayList<>();
    for (Patient p : patients) ids.add(p.getId());
    return ids;
}
    private void load() {
        try {
            for (String[] row : CsvUtils.readCsv(csvPath)) {
                Patient p = new Patient(
                        row[0],   
                        row[1],   
                        row[2],   
                        row[3],   
                        row[4],   
                        row[5],   
                        row[6],   
                        row[7],   
                        row[8],   
                        row[9],   
                        row[10],  
                        row[11],  
                        row[12],  
                        row[13]   
                );
                patients.add(p);
            }
        } catch (IOException ex) {
            System.err.println("Failed to load patients: " + ex.getMessage());
        }
    }
    public String generateNewId() {
        int max = 0;
        for (Patient p : patients) {
            try {
                int num = Integer.parseInt(p.getId().substring(1));
                if (num > max) max = num;
            } catch (Exception ignore) {}
        }
        return String.format("P%03d", max + 1);
    }
    public void addAndAppend(Patient p) {
        patients.add(p);
        try {
            CsvUtils.appendLine(csvPath, new String[]{
                    p.getId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getDateOfBirth(),
                    p.getNhsNumber(),
                    p.getGender(),
                    p.getPhoneNumber(),
                    p.getEmail(),
                    p.getAddress(),
                    p.getPostcode(),
                    p.getEmergencyContactName(),
                    p.getEmergencyContactPhone(),
                    p.getRegistrationDate(),
                    p.getGpSurgeryId()
            });
        } catch (IOException ex) {
            System.err.println("Failed to append patient: " + ex.getMessage());
        }
    }
    public List<Patient> getAll() {
        return patients;
    }
    public void remove(Patient p) {
        patients.remove(p);
    }
    public Patient findById(String id) {
        for (Patient p : patients) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    public void update(Patient p) {
        int idx = -1;
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(p.getId())) {
                idx = i;
                break;
            }
        }
        if (idx >= 0) {
            patients.set(idx, p);
            rewriteCsv();
        }
    }
    private void rewriteCsv() {
        try {
            List<String[]> rows = new ArrayList<>();
            for (Patient p : patients) {
                rows.add(new String[]{
                        p.getId(),
                        p.getFirstName(),
                        p.getLastName(),
                        p.getDateOfBirth(),
                        p.getNhsNumber(),
                        p.getGender(),
                        p.getPhoneNumber(),
                        p.getEmail(),
                        p.getAddress(),
                        p.getPostcode(),
                        p.getEmergencyContactName(),
                        p.getEmergencyContactPhone(),
                        p.getRegistrationDate(),
                        p.getGpSurgeryId()
                });
            }
            CsvUtils.writeCsv(csvPath, rows);
        } catch (IOException ex) {
            System.err.println("Failed to rewrite patient CSV: " + ex.getMessage());
        }
    }
}
