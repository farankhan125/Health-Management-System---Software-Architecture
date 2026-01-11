package model;
import java.io.IOException;
import java.util.*;
public class PrescriptionRepository {
    private final List<Prescription> prescriptions = new ArrayList<>();
    private final String csvPath;
    private static final int COLUMN_COUNT = 15;
    public PrescriptionRepository(String csvPath) {
        this.csvPath = csvPath;
        load();
    }
    private void load() {
        try {
            for (String[] row : CsvUtils.readCsv(csvPath)) {
                if (row.length == 0 || row[0].equalsIgnoreCase("prescription_id"))
                    continue;
                String[] safe = new String[COLUMN_COUNT];
                for (int i = 0; i < COLUMN_COUNT; i++) {
                    safe[i] = (i < row.length) ? row[i] : "";
                }
                Prescription p = new Prescription(
                        safe[0], 
                        safe[1], 
                        safe[2], 
                        safe[3], 
                        safe[4], 
                        safe[5], 
                        safe[6], 
                        safe[7], 
                        safe[8], 
                        safe[9], 
                        safe[10],
                        safe[11],
                        safe[12],
                        safe[13],
                        safe[14] 
                );
                prescriptions.add(p);
            }
        } catch (IOException ex) {
            System.err.println("Failed to load prescriptions: " + ex.getMessage());
        }
    }
    public List<Prescription> getAll() {
        return prescriptions;
    }
    public String generateNewId() {
        int max = 0;
        for (Prescription p : prescriptions) {
            try {
                String id = p.getId();
                if (id != null && id.startsWith("RX")) {
                    int num = Integer.parseInt(id.substring(2));
                    if (num > max) max = num;
                }
            } catch (Exception ignore) {}
        }
        return String.format("RX%03d", max + 1);
    }
    public List<String> getMedicationOptions() {
        Set<String> meds = new TreeSet<>();
        for (Prescription p : prescriptions) {
            if (p.getMedication() != null && !p.getMedication().isBlank())
                meds.add(p.getMedication());
        }
        return new ArrayList<>(meds);
    }
    public List<String> getPharmacyOptions() {
        Set<String> pharms = new TreeSet<>();
        for (Prescription p : prescriptions) {
            if (p.getPharmacyName() != null && !p.getPharmacyName().isBlank())
                pharms.add(p.getPharmacyName());
        }
        return new ArrayList<>(pharms);
    }
    public void addAndAppend(Prescription p) {
        prescriptions.add(p);
        try {
            CsvUtils.appendLine(csvPath, new String[]{
                    p.getId(),
                    p.getPatientId(),
                    p.getClinicianId(),
                    p.getAppointmentId(),
                    p.getPrescriptionDate(),
                    p.getMedication(),
                    p.getDosage(),
                    p.getFrequency(),
                    p.getDurationDays(),
                    p.getQuantity(),
                    p.getInstructions(),
                    p.getPharmacyName(),
                    p.getStatus(),
                    p.getIssueDate(),
                    p.getCollectionDate()
            });
        } catch (IOException ex) {
            System.err.println("Failed to append prescription: " + ex.getMessage());
        }
    }
    public void update(Prescription p) {
        for (int i = 0; i < prescriptions.size(); i++) {
            if (prescriptions.get(i).getId().equals(p.getId())) {
                prescriptions.set(i, p);
                return;
            }
        }
    }
    public void removeById(String id) {
        prescriptions.removeIf(p -> p.getId().equals(id));
    }
}
