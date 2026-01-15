package controller;
import model.*;
import view.ReferralView;
import java.util.ArrayList;
import java.util.List;
public class ReferralController {
    private final ReferralManager referralManager;
    private final PatientRepository patientRepo;
    private final ClinicianRepository clinicianRepo;
    private final FacilityRepository facilityRepo;
    private final AppointmentRepository appointmentRepo;
    private final ReferralView view;
    public ReferralController(ReferralManager rm,
                              PatientRepository pr,
                              ClinicianRepository cr,
                              FacilityRepository fr,
                              AppointmentRepository ar,
                              ReferralView view) {
        this.referralManager = rm;
        this.patientRepo = pr;
        this.clinicianRepo = cr;
        this.facilityRepo = fr;
        this.appointmentRepo = ar;
        this.view = view;
        this.view.setController(this);
        refreshReferrals();
    }
    public ReferralView getView() {
        return view;
    }
    public void refreshReferrals() {
        view.showReferrals(referralManager.getAllReferrals());
    }
    public List<Referral> getAllReferrals() {
        return referralManager.getAllReferrals();
    }
    public List<String> getPatientIds() {
        List<String> ids = new ArrayList<>();
        for (Patient p : patientRepo.getAll()) {
            ids.add(p.getId());
        }
        return ids;
    }
    public List<String> getClinicianIds() {
        List<String> ids = new ArrayList<>();
        for (Clinician c : clinicianRepo.getAll()) {
            ids.add(c.getId());
        }
        return ids;
    }
    public List<String> getFacilityIds() {
        List<String> ids = new ArrayList<>();
        for (Facility f : facilityRepo.getAll()) {
            ids.add(f.getId());
        }
        return ids;
    }
    public List<String> getAppointmentIds() {
        List<String> ids = new ArrayList<>();
        for (Appointment a : appointmentRepo.getAll()) {
            ids.add(a.getId());
        }
        return ids;
    }
    public String getNextReferralId() {
        int max = 0;
        for (Referral r : referralManager.getAllReferrals()) {
            String id = r.getId();   
            if (id != null && id.startsWith("R")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        int next = max + 1;
        return String.format("R%03d", next);
    }
    public void addReferral(Referral r) {
        referralManager.createReferral(r);   
        refreshReferrals();
    }
    public void updateReferral(Referral r) {
        referralManager.updateReferral(r);
        refreshReferrals();
    }
}
