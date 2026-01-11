package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ReferralRepository {
    private final List<Referral> referrals = new ArrayList<>();
    private final String csvPath;
    public ReferralRepository(String csvPath) {
        this.csvPath = csvPath;
        load();
    }
    private void load() {
        try {
            for (String[] row : CsvUtils.readCsv(csvPath)) {
                Referral r = new Referral(
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
                        row[13], 
                        row[14], 
                        row[15]  
                );
                referrals.add(r);
            }
        } catch (IOException ex) {
            System.err.println("Failed to load referrals: " + ex.getMessage());
        }
    }
    public List<Referral> getAll() {
        return referrals;
    }
    /**
     * Add referral and append to CSV (ALL 16 COLUMNS)
     */
    public void addAndAppend(Referral r) {
        referrals.add(r);
        try {
            CsvUtils.appendLine(csvPath, new String[] {
                    r.getId(),
                    r.getPatientId(),
                    r.getReferringClinicianId(),
                    r.getReferredToClinicianId(),
                    r.getReferringFacilityId(),
                    r.getReferredToFacilityId(),
                    r.getReferralDate(),
                    r.getUrgencyLevel(),
                    r.getReferralReason(),
                    r.getClinicalSummary(),
                    r.getRequestedService(),
                    r.getStatus(),
                    r.getAppointmentId(),
                    r.getNotes(),
                    r.getCreatedDate(),
                    r.getLastUpdated()
            });
        } catch (IOException ex) {
            System.err.println("Failed to append referral: " + ex.getMessage());
        }
    }
}
