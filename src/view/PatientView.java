package view;
import controller.PatientController;
import model.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
public class PatientView extends JPanel {
    private PatientController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblAutoId;
    private JTextField txtFirstName, txtLastName, txtNhs;
    private JComboBox<String> cbGender;
    private JFormattedTextField txtDob, txtRegistrationDate;
    private JTextField txtPhone, txtEmail;
    private JTextField txtAddress, txtPostcode;
    private JTextField txtEmergencyName, txtEmergencyPhone;
    private JTextField txtGpSurgery;
    private JButton btnAdd, btnUpdate, btnDelete;
    public PatientView() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tableModel = new DefaultTableModel(
                new Object[]{
                        "ID", "First Name", "Last Name", "DOB", "NHS",
                        "Gender", "Phone", "Email", "Address", "Postcode",
                        "Emergency Name", "Emergency Phone",
                        "Registration Date", "GP Surgery ID"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(22);
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 15, 10, 15);
        gc.fill = GridBagConstraints.HORIZONTAL;
        lblAutoId = new JLabel("P001");
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        try {
            MaskFormatter dateMask = new MaskFormatter("####-##-##");
            dateMask.setPlaceholderCharacter('_');
            txtDob = new JFormattedTextField(dateMask);
            txtDob.setColumns(15);
            txtDob.setInputVerifier(new DateInputVerifier());
            txtRegistrationDate = new JFormattedTextField(dateMask);
            txtRegistrationDate.setColumns(15);
            txtRegistrationDate.setInputVerifier(new DateInputVerifier());
        } catch (ParseException e) {
            txtDob = new JFormattedTextField();
            txtRegistrationDate = new JFormattedTextField();
        }
        txtNhs = new JTextField();
        ((AbstractDocument) txtNhs.getDocument()).setDocumentFilter(new DigitsOnlyFilter());
        cbGender = new JComboBox<>(new String[]{"M", "F", "Not Specified"});
        txtPhone = new JTextField();
        ((AbstractDocument) txtPhone.getDocument()).setDocumentFilter(new DigitsOnlyFilter());
        txtEmail = new JTextField();
        txtAddress = new JTextField();
        txtPostcode = new JTextField();
        txtEmergencyName = new JTextField();
        txtEmergencyPhone = new JTextField();
        ((AbstractDocument) txtEmergencyPhone.getDocument()).setDocumentFilter(new DigitsOnlyFilter());
        txtGpSurgery = new JTextField();
        int row = 0;
        add4(form, gc, row++, "Patient ID:", lblAutoId, "First Name:", txtFirstName);
        add4(form, gc, row++, "Last Name:", txtLastName, "DOB (YYYY-MM-DD):", txtDob);
        add4(form, gc, row++, "NHS Number:", txtNhs, "Gender:", cbGender);
        add4(form, gc, row++, "Phone Number:", txtPhone, "Email:", txtEmail);
        add4(form, gc, row++, "Address:", txtAddress, "Postcode:", txtPostcode);
        add4(form, gc, row++, "Emergency Name:", txtEmergencyName,
                "Emergency Phone:", txtEmergencyPhone);
        add4(form, gc, row++, "Registration Date (YYYY-MM-DD):", txtRegistrationDate,
                "GP Surgery ID:", txtGpSurgery);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, form, new JScrollPane(table));
        splitPane.setDividerLocation(0.55);
        splitPane.setResizeWeight(0.55);
        add(splitPane, BorderLayout.CENTER);
        form.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (table.getSelectedRow() >= 0) {
                    table.clearSelection();
                }
            }
        });
        btnAdd = new JButton("Add Patient");
        btnUpdate = new JButton("Update Selected");
        btnDelete = new JButton("Delete Selected");
        btnAdd.addActionListener(e -> onAdd());
        btnUpdate.addActionListener(e -> onUpdate());
        btnDelete.addActionListener(e -> onDelete());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        add(buttons, BorderLayout.NORTH);
        btnUpdate.setVisible(false);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRowIntoForm();
                boolean hasSelection = table.getSelectedRow() >= 0;
                btnAdd.setVisible(!hasSelection);
                btnUpdate.setVisible(hasSelection);
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row < 0) {
                    table.clearSelection();
                    clearForm();
                    btnAdd.setVisible(true);
                    btnUpdate.setVisible(false);
                }
            }
        });
    }
    private void add4(JPanel panel, GridBagConstraints gc, int row,
                      String label1, JComponent field1,
                      String label2, JComponent field2) {
        gc.gridy = row;
        gc.gridx = 0; gc.weightx = 0.15;
        panel.add(new JLabel(label1), gc);
        gc.gridx = 1; gc.weightx = 0.35;
        panel.add(field1, gc);
        gc.gridx = 2; gc.weightx = 0.15;
        panel.add(new JLabel(label2), gc);
        gc.gridx = 3; gc.weightx = 0.35;
        panel.add(field2, gc);
    }
    public void setController(PatientController controller) {
        this.controller = controller;
    }
    public void showPatients(List<Patient> list) {
        tableModel.setRowCount(0);
        updateAutoId(list);
        for (Patient p : list) {
            tableModel.addRow(new Object[]{
                    p.getId(), p.getFirstName(), p.getLastName(),
                    p.getDateOfBirth(), p.getNhsNumber(), p.getGender(),
                    p.getPhoneNumber(), p.getEmail(), p.getAddress(),
                    p.getPostcode(), p.getEmergencyContactName(),
                    p.getEmergencyContactPhone(), p.getRegistrationDate(),
                    p.getGpSurgeryId()
            });
        }
    }
    private void updateAutoId(List<Patient> list) {
        if (list.isEmpty()) {
            lblAutoId.setText("P001");
            return;
        }
        String lastId = list.get(list.size() - 1).getId();
        int num = Integer.parseInt(lastId.substring(1)) + 1;
        lblAutoId.setText(String.format("P%03d", num));
    }
    private void onAdd() {
        if (controller == null) return;
        Patient p = new Patient(
                lblAutoId.getText(),
                txtFirstName.getText(),
                txtLastName.getText(),
                txtDob.getText(),
                txtNhs.getText(),
                (String) cbGender.getSelectedItem(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtAddress.getText(),
                txtPostcode.getText(),
                txtEmergencyName.getText(),
                txtEmergencyPhone.getText(),
                txtRegistrationDate.getText(),
                txtGpSurgery.getText()
        );
        controller.addPatient(p);
    }
    private void onUpdate() {
        if (controller == null) return;
        Patient p = new Patient(
                lblAutoId.getText(),
                txtFirstName.getText(),
                txtLastName.getText(),
                txtDob.getText(),
                txtNhs.getText(),
                (String) cbGender.getSelectedItem(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtAddress.getText(),
                txtPostcode.getText(),
                txtEmergencyName.getText(),
                txtEmergencyPhone.getText(),
                txtRegistrationDate.getText(),
                txtGpSurgery.getText()
        );
        controller.updatePatient(p);
        JOptionPane.showMessageDialog(this,
            "Patient " + p.getId() + " updated successfully.");
        clearForm();
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
    }
    private void onDelete() {
        if (controller == null) return;
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a patient first!");
            return;
        }
        String id = tableModel.getValueAt(row, 0).toString();
        Patient p = controller.findById(id);
        if (p != null) controller.deletePatient(p);
    }
    private void loadSelectedRowIntoForm() {
        int row = table.getSelectedRow();
        if (row < 0) {
            clearForm();
            return;
        }
        lblAutoId.setText(val(row, 0));
        txtFirstName.setText(val(row, 1));
        txtLastName.setText(val(row, 2));
        txtDob.setText(val(row, 3));
        txtNhs.setText(val(row, 4));
        cbGender.setSelectedItem(val(row, 5));
        txtPhone.setText(val(row, 6));
        txtEmail.setText(val(row, 7));
        txtAddress.setText(val(row, 8));
        txtPostcode.setText(val(row, 9));
        txtEmergencyName.setText(val(row, 10));
        txtEmergencyPhone.setText(val(row, 11));
        txtRegistrationDate.setText(val(row, 12));
        txtGpSurgery.setText(val(row, 13));
    }
    private void clearForm() {
        updateNextId();
        txtFirstName.setText("");
        txtLastName.setText("");
        txtDob.setText("");
        txtNhs.setText("");
        cbGender.setSelectedIndex(0);
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtPostcode.setText("");
        txtEmergencyName.setText("");
        txtEmergencyPhone.setText("");
        txtRegistrationDate.setText("");
        txtGpSurgery.setText("");
    }
    private void updateNextId() {
        String nextId = "P001";
        if (tableModel.getRowCount() > 0) {
            String lastId = (String) tableModel.getValueAt(tableModel.getRowCount() - 1, 0);
            try {
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                nextId = String.format("P%03d", num);
            } catch (Exception e) {
                nextId = "P001";
            }
        }
        lblAutoId.setText(nextId);
    }
    private String val(int row, int col) {
        Object v = tableModel.getValueAt(row, col);
        return v == null ? "" : v.toString();
    }
    private static class DigitsOnlyFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches("\\d*")) {
                super.insertString(fb, offset, string, attr);
            }
        }
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
    private static class DateInputVerifier extends InputVerifier {
        private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        static {
            sdf.setLenient(false);
        }
        @Override
        public boolean verify(JComponent input) {
            if (!(input instanceof JFormattedTextField)) return true;
            JFormattedTextField field = (JFormattedTextField) input;
            String text = field.getText().trim();
            if (text.isEmpty() || text.contains("_")) return true;
            try {
                sdf.parse(text);
                return true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(field, 
                    "Invalid date format! Please use YYYY-MM-DD (e.g., 2025-12-25)", 
                    "Date Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }
}
