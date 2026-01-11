package view;
import controller.AppointmentController;
import model.Appointment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.text.ParseException;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class AppointmentView extends JPanel {
    private AppointmentController controller;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblId;
    private JFormattedTextField txtDate, txtCreatedDate, txtLastModified;
    private JFormattedTextField txtTime;
    private JTextField txtDuration, txtType;
    private JTextField txtReason;
    private JComboBox<String> cbStatus;          
    private JComboBox<String> cbPatientId;
    private JComboBox<String> cbClinicianId;
    private JComboBox<String> cbFacilityId;
    private JTextArea txtNotes;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public AppointmentView() {
        setLayout(new BorderLayout(10, 10));
        model = new DefaultTableModel(
                new Object[]{
                        "ID", "Patient", "Clinician", "Facility",
                        "Date", "Time", "Duration (min)", "Type",
                        "Status", "Reason", "Notes", "Created", "Last Modified"
                }, 0
        );
        table = new JTable(model);
        table.setRowHeight(22);
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        lblId = new JLabel();
        cbPatientId = new JComboBox<>();
        cbClinicianId = new JComboBox<>();
        cbFacilityId = new JComboBox<>();
        try {
            MaskFormatter dateMask = new MaskFormatter("####-##-##");
            dateMask.setPlaceholderCharacter('_');
            txtDate = new JFormattedTextField(dateMask);
            txtDate.setColumns(10);
            txtDate.setInputVerifier(new DateInputVerifier());
            txtCreatedDate = new JFormattedTextField(dateMask);
            txtCreatedDate.setColumns(10);
            txtCreatedDate.setInputVerifier(new DateInputVerifier());
            txtCreatedDate.setEditable(true);
            txtLastModified = new JFormattedTextField(dateMask);
            txtLastModified.setColumns(10);
            txtLastModified.setInputVerifier(new DateInputVerifier());
            txtLastModified.setEditable(true);
            MaskFormatter timeMask = new MaskFormatter("##:##");
            timeMask.setPlaceholderCharacter('_');
            timeMask.setAllowsInvalid(false);
            txtTime = new JFormattedTextField(timeMask);
            txtTime.setColumns(5);
            txtTime.setInputVerifier(new TimeInputVerifier());
        } catch (ParseException ex) {
            txtDate = new JFormattedTextField();
            txtCreatedDate = new JFormattedTextField();
            txtLastModified = new JFormattedTextField();
            txtTime = new JFormattedTextField();
        }
        txtDuration = new JTextField();
        ((AbstractDocument) txtDuration.getDocument()).setDocumentFilter(new LimitedDigitsFilter(2));
        txtType = new JTextField();
        txtReason = new JTextField();
        cbStatus = new JComboBox<>(new String[]{
                "SCHEDULED",
                "RESCHEDULED",
                "CANCELLED",
                "COMPLETED",
                "NO-SHOW"
        });
        cbStatus.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txtNotes = new JTextArea(3, 15);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        int row = 0;
        addFieldPair(form, gc, row++, "Appointment ID:", lblId, "Patient ID:", cbPatientId);
        addFieldPair(form, gc, row++, "Clinician ID:", cbClinicianId, "Facility ID:", cbFacilityId);
        addFieldPair(form, gc, row++, "Appointment Date (YYYY-MM-DD):", txtDate, "Time (HH:mm):", txtTime);
        addFieldPair(form, gc, row++, "Duration (min):", txtDuration, "Appointment Type:", txtType);
        addFieldPair(form, gc, row++, "Status:", cbStatus, "Reason for Visit:", txtReason);
        addFieldPair(form, gc, row++, "Created Date (YYYY-MM-DD):", txtCreatedDate, "Last Modified (YYYY-MM-DD):", txtLastModified);
        gc.gridx = 0; gc.gridy = row; gc.gridwidth = 1;
        form.add(new JLabel("Notes:"), gc);
        gc.gridx = 1; gc.gridy = row; gc.gridwidth = 3;
        form.add(new JScrollPane(txtNotes), gc);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, form, new JScrollPane(table));
        splitPane.setDividerLocation(0.55);
        splitPane.setResizeWeight(0.55);
        add(splitPane, BorderLayout.CENTER);
        JButton btnAdd = new JButton("Add Appointment");
        JButton btnDelete = new JButton("Delete Selected");
        btnAdd.addActionListener(e -> addAppointment());
        btnDelete.addActionListener(e -> deleteAppointment());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttons.add(btnAdd);
        buttons.add(btnDelete);
        add(buttons, BorderLayout.NORTH);
    }
    private static class LimitedDigitsFilter extends DocumentFilter {
        private final int maxLen;
        public LimitedDigitsFilter(int maxLen) { this.maxLen = maxLen; }
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string == null) return;
            String newText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String candidate = newText.substring(0, offset) + string + newText.substring(offset);
            if (candidate.length() <= maxLen && string.matches("\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text == null) return;
            String newText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String candidate = newText.substring(0, offset) + text + newText.substring(offset + length);
            if (candidate.length() <= maxLen && text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
    private void addFieldPair(JPanel panel, GridBagConstraints gc, int row,
                              String l1, JComponent f1,
                              String l2, JComponent f2) {
        gc.gridwidth = 1;
        gc.gridx = 0; gc.gridy = row;
        panel.add(new JLabel(l1), gc);
        gc.gridx = 1;
        panel.add(f1, gc);
        gc.gridx = 2;
        panel.add(new JLabel(l2), gc);
        gc.gridx = 3;
        panel.add(f2, gc);
    }
    public void loadDropdowns(List<String> patients, List<String> clinicians, List<String> facilities) {
        cbPatientId.removeAllItems();
        cbClinicianId.removeAllItems();
        cbFacilityId.removeAllItems();
        for (String s : patients) cbPatientId.addItem(s);
        for (String s : clinicians) cbClinicianId.addItem(s);
        for (String s : facilities) cbFacilityId.addItem(s);
        lblId.setText(controller.generateId());
        txtCreatedDate.setText("");
        txtLastModified.setText("");
    }
    public void showAppointments(List<Appointment> list) {
        model.setRowCount(0);
        for (Appointment a : list) {
            model.addRow(new Object[]{
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
    }
    private void addAppointment() {
        String now = java.time.LocalDate.now().toString();
        if (txtCreatedDate.getText().trim().isEmpty()) {
            txtCreatedDate.setText(now);
        }
        txtLastModified.setText(now);
        Appointment a = new Appointment(
                lblId.getText(),
                (String) cbPatientId.getSelectedItem(),
                (String) cbClinicianId.getSelectedItem(),
                (String) cbFacilityId.getSelectedItem(),
                txtDate.getText(),
                txtTime.getText(),
                txtDuration.getText(),
                txtType.getText(),
                (String) cbStatus.getSelectedItem(),   
                txtReason.getText(),
                txtNotes.getText(),
                txtCreatedDate.getText(),
                txtLastModified.getText()
        );
        controller.addAppointment(a);
        lblId.setText(controller.generateId());
    }
    private void deleteAppointment() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an appointment to delete.");
            return;
        }
        String id = model.getValueAt(row, 0).toString();
        controller.deleteById(id);
    }
    public void setController(AppointmentController controller) {
        this.controller = controller;
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
    private static class TimeInputVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            if (!(input instanceof JFormattedTextField)) return true;
            JFormattedTextField f = (JFormattedTextField) input;
            String text = f.getText().trim();
            if (text.isEmpty() || text.contains("_")) return true;
            String[] parts = text.split(":");
            if (parts.length != 2) {
                JOptionPane.showMessageDialog(f, "Invalid time format! Use HH:mm (e.g., 09:30)",
                        "Time Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            try {
                int h = Integer.parseInt(parts[0]);
                int m = Integer.parseInt(parts[1]);
                if (h < 0 || h > 23 || m < 0 || m > 59) {
                    JOptionPane.showMessageDialog(f, "Invalid time value! Hours 00-23, minutes 00-59",
                            "Time Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(f, "Invalid time format! Use digits only.",
                        "Time Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }
}
