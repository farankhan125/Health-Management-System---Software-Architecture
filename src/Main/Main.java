package Main;
import controller.*;
import model.*;
import view.*;
import javax.swing.SwingUtilities;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UiTheme.apply();
            System.out.println("Health Management System Starting...");
            try {
                // Initialize repositories
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
                
                // Initialize views
                PatientView pv = new PatientView();
                ClinicianView cv = new ClinicianView();
                AppointmentView av = new AppointmentView();
                PrescriptionView presV = new PrescriptionView();
                ReferralView rv = new ReferralView();
                
                // Initialize controllers
                PatientController pc = new PatientController(pr, fr, pv);
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
                
                // Show role selection screen
                RoleSelectionFrame roleFrame = new RoleSelectionFrame();
                
                // Handle role selection
                roleFrame.getPatientButton().addActionListener(e -> {
                    roleFrame.dispose();
                    // Show patient login
                    PatientLoginFrame loginFrame = new PatientLoginFrame(pr);
                    loginFrame.getLoginButton().addActionListener(e2 -> {
                        model.Patient patient = loginFrame.validatePatientLogin();
                        if (patient != null) {
                            loginFrame.dispose();
                            PatientPortalFrame portal = new PatientPortalFrame(patient, pc, ac, prc, rc);
                            portal.setVisible(true);
                        }
                    });
                    loginFrame.getBackButton().addActionListener(e2 -> {
                        loginFrame.dispose();
                        RoleSelectionFrame roleFrame2 = new RoleSelectionFrame();
                        setupRoleSelectionListeners(roleFrame2, pr, pc, cc, ac, prc, rc);
                        roleFrame2.setVisible(true);
                    });
                    loginFrame.setVisible(true);
                });
                
                roleFrame.getClinicianButton().addActionListener(e -> {
                    roleFrame.dispose();
                    ClinicianPortalFrame portal = new ClinicianPortalFrame(pc, ac, prc, rc);
                    portal.setVisible(true);
                });
                
                roleFrame.getAdministratorButton().addActionListener(e -> {
                    roleFrame.dispose();
                    MainFrame frame = new MainFrame(pc, cc, ac, prc, rc);
                    frame.setVisible(true);
                });
                
                roleFrame.setVisible(true);
            } catch (Exception ex) {
                System.err.println("Unhandled exception during GUI startup:");
                ex.printStackTrace();
            }
        });
    }
    
    private static void setupRoleSelectionListeners(
            RoleSelectionFrame roleFrame,
            PatientRepository pr,
            PatientController pc,
            ClinicianController cc,
            AppointmentController ac,
            PrescriptionController prc,
            ReferralController rc) {
        roleFrame.getPatientButton().addActionListener(e -> {
            roleFrame.dispose();
            PatientLoginFrame loginFrame = new PatientLoginFrame(pr);
            loginFrame.getLoginButton().addActionListener(e2 -> {
                model.Patient patient = loginFrame.validatePatientLogin();
                if (patient != null) {
                    loginFrame.dispose();
                    PatientPortalFrame portal = new PatientPortalFrame(patient, pc, ac, prc, rc);
                    portal.setVisible(true);
                }
            });
            loginFrame.getBackButton().addActionListener(e2 -> {
                loginFrame.dispose();
                RoleSelectionFrame roleFrame2 = new RoleSelectionFrame();
                setupRoleSelectionListeners(roleFrame2, pr, pc, cc, ac, prc, rc);
                roleFrame2.setVisible(true);
            });
            loginFrame.setVisible(true);
        });
        
        roleFrame.getClinicianButton().addActionListener(e -> {
            roleFrame.dispose();
            ClinicianPortalFrame portal = new ClinicianPortalFrame(pc, ac, prc, rc);
            portal.setVisible(true);
        });
        
        roleFrame.getAdministratorButton().addActionListener(e -> {
            roleFrame.dispose();
            MainFrame frame = new MainFrame(pc, cc, ac, prc, rc);
            frame.setVisible(true);
        });
    }
}
