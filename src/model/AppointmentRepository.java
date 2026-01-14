package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class AppointmentRepository {
    private final List<Appointment> appointments = new ArrayList<>();
    private final String csvPath;
    public AppointmentRepository(String csvPath) {
        this.csvPath = csvPath;
        load();
    }
    private void load() {
        try {
            for (String[] row : CsvUtils.readCsv(csvPath)) {
                Appointment a = new Appointment(
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
                        row[12]  
                );
                appointments.add(a);
            }
        } catch (IOException ex) {
            System.err.println("Failed to load appointments: " + ex.getMessage());
        }
    }
    public List<Appointment> getAll() {
        return appointments;
    }
    public String generateNewId() {
        int max = 0;
        for (Appointment a : appointments) {
            try {
                int n = Integer.parseInt(a.getId().substring(1)); 
                if (n > max) max = n;
            } catch (Exception ignore) {}
        }
        return String.format("A%03d", max + 1);
    }
    public void add(Appointment a) {
        appointments.add(a);
    }
    public void addAndAppend(Appointment a) {
        appointments.add(a);
        try {
            CsvUtils.appendLine(csvPath, new String[]{
                    a.getId(),
                    a.getPatientId(),
                    a.getClinicianId(),
                    a.getFacilityId(),
                    a.getAppointmentDate(),
                    a.getAppointmentTime(),
                    a.getDurationMinutes(),
                    a.getAppointmentType(),
                    a.getStatus(),
                    a.getReasonForVisit(),
                    a.getNotes(),
                    a.getCreatedDate(),
                    a.getLastModified()
            });
        } catch (IOException ex) {
            System.err.println("Failed to append appointment: " + ex.getMessage());
        }
    }
    public void remove(Appointment a) {
        appointments.remove(a);
    }
    public Appointment findById(String id) {
        for (Appointment a : appointments)
            if (a.getId().equals(id)) return a;
        return null;
    }
    public void update(Appointment a) {
        int idx = -1;
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId().equals(a.getId())) {
                idx = i;
                break;
            }
        }
        if (idx >= 0) {
            appointments.set(idx, a);
            rewriteCsv();
        }
    }
    private void rewriteCsv() {
        try {
            List<String[]> rows = new ArrayList<>();
            for (Appointment a : appointments) {
                rows.add(new String[]{
                        a.getId(),
                        a.getPatientId(),
                        a.getClinicianId(),
                        a.getFacilityId(),
                        a.getAppointmentDate(),
                        a.getAppointmentTime(),
                        a.getDurationMinutes(),
                        a.getAppointmentType(),
                        a.getStatus(),
                        a.getReasonForVisit(),
                        a.getNotes(),
                        a.getCreatedDate(),
                        a.getLastModified()
                });
            }
            CsvUtils.writeCsv(csvPath, rows);
        } catch (IOException ex) {
            System.err.println("Failed to rewrite appointment CSV: " + ex.getMessage());
        }
    }
}
