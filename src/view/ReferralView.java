package view;
import controller.ReferralController;
import model.Referral;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class ReferralView extends JPanel {
    private ReferralController controller;
    private JTable table;
    private DefaultTableModel model;
        private JTextField txtId, txtReason, txtRequestedService;
        private JFormattedTextField txtCreatedDate, txtLastUpdated, txtReferralDate;
        private JTextArea txtClinicalSummary, txtNotes;
    private JComboBox<String> cbPatientId;
    private JComboBox<String> cbRefClin, cbToClin;
    private JComboBox<String> cbRefFacility, cbToFacility;
    private JComboBox<String> cbUrgency;
    private JComboBox<String> cbAppointmentId;
    private JComboBox<String> cbStatus;     
        private final DateTimeFormatter localDateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public ReferralView() {
        setLayout(new BorderLayout(10,10));
        model = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Patient",
                        "Ref Clin",
                        "To Clin",
                        "Ref Facility",
                        "To Facility",
                        "Date (YYYY-MM-DD)",
                        "Urgency",
                        "Reason",
                        "Clinical Summary",
                        "Requested Service",
                        "Status",
                        "Appointment",
                        "Notes",
                        "Created (YYYY-MM-DD)",
                        "Updated (YYYY-MM-DD)"
                }, 0
        );
        table = new JTable(model);
        table.setRowHeight(22);
        JPanel formContainer = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 15, 10, 15);
        gc.fill = GridBagConstraints.HORIZONTAL;
        txtId = createField();
        txtReason = createField();
        txtRequestedService = createField();
        txtCreatedDate = createDateField(); txtCreatedDate.setEditable(true);
        txtLastUpdated = createDateField(); txtLastUpdated.setEditable(true);
        cbPatientId = createCombo();
        cbRefClin = createCombo();
        cbToClin = createCombo();
        cbRefFacility = createCombo();
        cbToFacility = createCombo();
        cbAppointmentId = createCombo();
        cbUrgency = new JComboBox<>(new String[]{
                "Routine",
                "Urgent",
                "Non-urgent",
                "2-week wait"
        });
        cbUrgency.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cbStatus = new JComboBox<>(new String[]{
                "Pending",
                "Sent",
                "Received",
                "In Review",
                "Accepted",
                "Rejected",
                "Completed",
                "Cancelled"
        });
        cbStatus.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txtReferralDate = createDateField();
        txtClinicalSummary = createArea();
        txtNotes = createArea();
        int row = 0;
        add4(formContainer, gc, row++, "Referral ID:", txtId, "Patient ID:", cbPatientId);
        add4(formContainer, gc, row++, "Referring Clinician ID:", cbRefClin, "Referred-To Clinician ID:", cbToClin);
        add4(formContainer, gc, row++, "Referring Facility ID:", cbRefFacility, "Referred-To Facility ID:", cbToFacility);
        add4(formContainer, gc, row++, "Referral Date (YYYY-MM-DD):", txtReferralDate, "Urgency Level:", cbUrgency);
        add4(formContainer, gc, row++, "Referral Reason:", txtReason, "Requested Service:", txtRequestedService);
        add4(formContainer, gc, row++, "Status:", cbStatus, "Appointment ID:", cbAppointmentId);
        add4(formContainer, gc, row++, "Clinical Summary:", new JScrollPane(txtClinicalSummary), "Notes:", new JScrollPane(txtNotes));
        add4(formContainer, gc, row++, "Created Date (YYYY-MM-DD):", txtCreatedDate, "Last Updated (YYYY-MM-DD):", txtLastUpdated);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formContainer, new JScrollPane(table));
        splitPane.setDividerLocation(0.55);
        splitPane.setResizeWeight(0.55);
        add(splitPane, BorderLayout.CENTER);
        JButton btnAdd = new JButton("Create Referral");
        btnAdd.addActionListener(e -> onAdd());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(btnAdd);
        add(buttonPanel, BorderLayout.NORTH);
    }
    private JTextField createField() {
        return new JTextField();
    }
    private JTextArea createArea() {
        JTextArea a = new JTextArea(2, 10);
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        a.setFont(new Font("SansSerif", Font.PLAIN, 12));
        a.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return a;
    }
    private JFormattedTextField createDateField() {
        try {
            MaskFormatter mf = new MaskFormatter("####-##-##");
            mf.setPlaceholderCharacter('_');
            JFormattedTextField f = new JFormattedTextField(mf);
            f.setFont(new Font("SansSerif", Font.PLAIN, 12));
            f.setInputVerifier(new DateInputVerifier());
            return f;
        } catch (ParseException ex) {
            JFormattedTextField f = new JFormattedTextField();
            f.setFont(new Font("SansSerif", Font.PLAIN, 12));
            f.setInputVerifier(new DateInputVerifier());
            return f;
        }
    }
    private JComboBox<String> createCombo() {
        JComboBox<String> cb = new JComboBox<>();
        cb.setFont(new Font("SansSerif", Font.PLAIN, 12));
        return cb;
    }
    private JPanel labeled(String label, Component field) {
        JPanel p = new JPanel(new BorderLayout(3, 2));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        p.add(lbl, BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }
    private void add4(JPanel panel, GridBagConstraints gc, int row,
                      String label1, JComponent field1,
                      String label2, JComponent field2) {
        gc.gridy = row;
        gc.gridx = 0;
        gc.weightx = 0.15;
        panel.add(new JLabel(label1), gc);
        gc.gridx = 1;
        gc.weightx = 0.35;
        panel.add(field1, gc);
        gc.gridx = 2;
        gc.weightx = 0.15;
        panel.add(new JLabel(label2), gc);
        gc.gridx = 3;
        gc.weightx = 0.35;
        panel.add(field2, gc);
    }
    public void setController(ReferralController controller) {
        this.controller = controller;
        loadCombos();
        refreshAutoId();
        refreshDates();
    }
    private void loadCombos() {
        cbPatientId.removeAllItems();
        cbRefClin.removeAllItems();
        cbToClin.removeAllItems();
        cbRefFacility.removeAllItems();
        cbToFacility.removeAllItems();
        cbAppointmentId.removeAllItems();
        for (String id : controller.getPatientIds()) {
            cbPatientId.addItem(id);
        }
        for (String id : controller.getClinicianIds()) {
            cbRefClin.addItem(id);
            cbToClin.addItem(id);
        }
        for (String id : controller.getFacilityIds()) {
            cbRefFacility.addItem(id);
            cbToFacility.addItem(id);
        }
        for (String id : controller.getAppointmentIds()) {
            cbAppointmentId.addItem(id);
        }
    }
    private void refreshAutoId() {
        txtId.setText(controller.getNextReferralId());
        txtId.setEditable(false);
    }
    private void refreshDates() {
        String today = LocalDate.now().format(localDateFormatter);
        txtCreatedDate.setText(today);
        txtLastUpdated.setText(today);
    }
    private static class DateInputVerifier extends InputVerifier {
        private static final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        static { sdf.setLenient(false); }
        @Override
        public boolean verify(JComponent input) {
            if (!(input instanceof JFormattedTextField)) return true;
            JFormattedTextField f = (JFormattedTextField) input;
            String text = f.getText().trim();
            if (text.isEmpty() || text.contains("_")) return true;
            try { sdf.parse(text); return true; }
            catch (Exception e) {
                JOptionPane.showMessageDialog(f, "Invalid date format! Please use YYYY-MM-DD (e.g., 2025-12-25)",
                        "Date Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }
    public void showReferrals(List<Referral> list) {
        model.setRowCount(0);
        for (Referral r : list) {
            model.addRow(new Object[]{
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
        }
    }
    private void onAdd() {
        String errors = validateForm();
        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(this, errors,
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String now = LocalDate.now().format(localDateFormatter);
        if (txtCreatedDate.getText().trim().isEmpty() || txtCreatedDate.getText().trim().contains("_")) {
            txtCreatedDate.setText(now);
        }
        txtLastUpdated.setText(now);
        Referral r = new Referral(
            txtId.getText().trim(),
            (String) cbPatientId.getSelectedItem(),
            (String) cbRefClin.getSelectedItem(),
            (String) cbToClin.getSelectedItem(),
            (String) cbRefFacility.getSelectedItem(),
            (String) cbToFacility.getSelectedItem(),
            txtReferralDate.getText().trim(),
            (String) cbUrgency.getSelectedItem(),
            txtReason.getText().trim(),
            txtClinicalSummary.getText().trim(),
            txtRequestedService.getText().trim(),
            (String) cbStatus.getSelectedItem(),         
            (String) cbAppointmentId.getSelectedItem(),
            txtNotes.getText().trim(),
            txtCreatedDate.getText().trim(),
            txtLastUpdated.getText().trim()
        );
        controller.addReferral(r);
        JOptionPane.showMessageDialog(this,
                "Referral " + r.getId() + " created successfully.");
        refreshAutoId();
        refreshDates();
        clearFormButKeepIds();
    }
    private String validateForm() {
        StringBuilder sb = new StringBuilder();
        if (cbPatientId.getSelectedItem() == null)
            sb.append("- Patient ID required\n");
        if (cbRefClin.getSelectedItem() == null)
            sb.append("- Referring clinician required\n");
        if (cbToClin.getSelectedItem() == null)
            sb.append("- Referred-to clinician required\n");
        if (cbRefFacility.getSelectedItem() == null)
            sb.append("- Referring facility required\n");
        if (cbToFacility.getSelectedItem() == null)
            sb.append("- Referred-to facility required\n");
        if (txtReferralDate.getText().trim().isEmpty())
            sb.append("- Referral date required\n");
        if (txtReason.getText().trim().isEmpty())
            sb.append("- Referral reason required\n");
        if (txtClinicalSummary.getText().trim().isEmpty())
            sb.append("- Clinical summary required\n");
        return sb.toString();
    }
    private void clearFormButKeepIds() {
        txtReason.setText("");
        txtClinicalSummary.setText("");
        txtRequestedService.setText("");
        txtNotes.setText("");
    }
}
