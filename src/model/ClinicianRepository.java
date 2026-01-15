package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ClinicianRepository {
    private final List<Clinician> clinicians = new ArrayList<>();
    private final String csvPath;
    public ClinicianRepository(String csvPath) {
        this.csvPath = csvPath;
        load();
    }
    public List<String> getAllIds() {
    List<String> ids = new ArrayList<>();
    for (Clinician c : clinicians) ids.add(c.getId());
    return ids;
}
    private void load() {
        try {
            for (String[] row : CsvUtils.readCsv(csvPath)) {
                Clinician c = new Clinician(
                        row[0],   // id
                        row[3],   // title
                        row[1],   // firstName
                        row[2],   // lastName
                        row[4],   // speciality
                        row[5],   // gmcNumber
                        row[6],   // phone
                        row[7],   // email
                        row[8],   // workplaceId
                        row[9],   // workplaceType
                        row[10],  // employmentStatus
                        row[11]   // startDate
                );
                clinicians.add(c);
            }
        } catch (IOException ex) {
            System.err.println("Failed to load clinicians: " + ex.getMessage());
        }
    }
    public String generateNewId() {
        int max = 0;
        for (Clinician c : clinicians) {
            try {
                int n = Integer.parseInt(c.getId().substring(1));
                if (n > max) max = n;
            } catch (Exception ignored) {}
        }
        return String.format("C%03d", max + 1);
    }
    public void addAndAppend(Clinician c) {
        clinicians.add(c);
        try {
            CsvUtils.appendLine(csvPath, new String[]{
                    c.getId(), c.getFirstName(), c.getLastName(), c.getTitle(),
                    c.getSpeciality(), c.getGmcNumber(), c.getPhone(), c.getEmail(),
                    c.getWorkplaceId(), c.getWorkplaceType(), c.getEmploymentStatus(),
                    c.getStartDate()
            });
        } catch (IOException ex) {
            System.err.println("Failed to append clinician: " + ex.getMessage());
        }
    }
    public List<Clinician> getAll() {
        return clinicians;
    }
    public void remove(Clinician c) {
        clinicians.remove(c);
    }
    public Clinician findById(String id) {
        for (Clinician c : clinicians)
            if (c.getId().equals(id)) return c;
        return null;
    }
    public void update(Clinician c) {
        int idx = -1;
        for (int i = 0; i < clinicians.size(); i++) {
            if (clinicians.get(i).getId().equals(c.getId())) {
                idx = i;
                break;
            }
        }
        if (idx >= 0) {
            clinicians.set(idx, c);
            rewriteCsv();
        }
    }
    private void rewriteCsv() {
        try {
            List<String[]> rows = new ArrayList<>();
            for (Clinician c : clinicians) {
                rows.add(new String[]{
                        c.getId(), c.getFirstName(), c.getLastName(), c.getTitle(),
                        c.getSpeciality(), c.getGmcNumber(), c.getPhone(), c.getEmail(),
                        c.getWorkplaceId(), c.getWorkplaceType(), c.getEmploymentStatus(),
                        c.getStartDate()
                });
            }
            CsvUtils.writeCsv(csvPath, rows);
        } catch (IOException ex) {
            System.err.println("Failed to rewrite clinician CSV: " + ex.getMessage());
        }
    }
}
