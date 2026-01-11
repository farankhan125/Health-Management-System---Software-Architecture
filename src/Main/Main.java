package Main;
import controller.*;
import model.*;
import view.*;
import javax.swing.SwingUtilities;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
                        System.out.println("Health Managegment System Starting...");
                        try {
            PatientRepository pr =
                    new PatientRepository("src/data/patients.csv");
            ClinicianRepository cr =
                    new ClinicianRepository("src/data/clinicians.csv");
            FacilityRepository fr =
                    new FacilityRepository("src/data/facilities.csv");
            AppointmentRepository ar =
                    new AppointmentRepository("src/data/appointments.csv");
            PrescriptionRepository pResR =
                    new PrescriptionRepository("src/data/prescriptions.csv");
            ReferralRepository rR =
                    new ReferralRepository("src/data/referrals.csv");
            ReferralManager rm = ReferralManager.getInstance(
                    rR, pr, cr, fr,
                    "src/data/referrals_output.txt"
            );
            PatientView pv = new PatientView();
            ClinicianView cv = new ClinicianView();
            AppointmentView av = new AppointmentView();
            PrescriptionView presV = new PrescriptionView();
            ReferralView rv = new ReferralView();
            PatientController pc = new PatientController(pr, pv);
            ClinicianController cc = new ClinicianController(cr, cv);
            AppointmentController ac = new AppointmentController(
                    ar,
                    pr,
                    cr,
                    fr,
                    av
            );
            PrescriptionController prc = new PrescriptionController(
                    pResR,
                    pr,
                    cr,
                    ar,
                    presV
            );
            ReferralController rc = new ReferralController(
                    rm,
                    pr,
                    cr,
                    fr,
                    ar,
                    rv
            );
            MainFrame frame = new MainFrame(pc, cc, ac, prc, rc);
                        frame.setVisible(true);
                        } catch (Exception ex) {
                                System.err.println("Unhandled exception during GUI startup:");
                                ex.printStackTrace();
                        }
        });
    }
}
