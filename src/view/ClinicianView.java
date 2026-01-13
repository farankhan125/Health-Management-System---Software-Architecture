package view;
import controller.ClinicianController;
import model.Clinician;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class ClinicianView extends JPanel {
    private ClinicianController controller;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblId;
    private JTextField txtFirstName, txtLastName, txtSpeciality, txtGmc, txtEmail, txtWorkplaceId;
    private JTextField txtPhone;
    private JComboBox<String> cmbTitle, cmbWorkplaceType, cmbEmployment;
    private JFormattedTextField txtStartDate;
    private JButton btnAdd, btnUpdate;
    public ClinicianView() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        model = new DefaultTableModel(
                new Object[]{
                        "ID","Title","First","Last","Speciality","GMC",
                        "Phone","Email","Workplace ID","Workplace Type",
                        "Employment","Start Date"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(22);
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 15, 10, 15);
        gc.fill = GridBagConstraints.HORIZONTAL;
        lblId = new JLabel("C001");
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtSpeciality = new JTextField();
        txtGmc = new JTextField();
        txtPhone = new JTextField();
        ((AbstractDocument) txtPhone.getDocument()).setDocumentFilter(new DigitsOnlyFilter());
        txtEmail = new JTextField();
        txtWorkplaceId = new JTextField();
        cmbTitle = new JComboBox<>(new String[]{"GP","Consultant","Nurse","Specialist"});
        cmbWorkplaceType = new JComboBox<>(new String[]{"GP Surgery","Hospital","Clinic"});
        cmbEmployment = new JComboBox<>(new String[]{"Full-time","Part-time"});
        try {
            MaskFormatter dateMask = new MaskFormatter("####-##-##");
            dateMask.setPlaceholderCharacter('_');
            txtStartDate = new JFormattedTextField(dateMask);
            txtStartDate.setColumns(10);
            txtStartDate.setInputVerifier(new DateInputVerifier());
        } catch (ParseException ex) {
            txtStartDate = new JFormattedTextField();
        }
        int row = 0;
        add4(form, gc, row++, "Clinician ID:", lblId,    "Title:", cmbTitle);
        add4(form, gc, row++, "First Name:", txtFirstName, "Last Name:", txtLastName);
        add4(form, gc, row++, "Speciality:", txtSpeciality, "GMC:", txtGmc);
        add4(form, gc, row++, "Phone Number:", txtPhone, "Email:", txtEmail);
        add4(form, gc, row++, "Workplace ID:", txtWorkplaceId, "Workplace Type:", cmbWorkplaceType);
        add4(form, gc, row++, "Employment:", cmbEmployment, "Start Date (YYYY-MM-DD):", txtStartDate);
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
        btnAdd = new JButton("Add Clinician");
        btnUpdate = new JButton("Update Selected");
        JButton btnDelete = new JButton("Delete Selected");
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
                    clearClinicianForm();
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
    public void setController(ClinicianController controller) {
        this.controller = controller;
    }
    public void showClinicians(List<Clinician> list) {
        model.setRowCount(0);
        for (Clinician c : list) {
            model.addRow(new Object[]{
                    c.getId(), c.getTitle(), c.getFirstName(), c.getLastName(),
                    c.getSpeciality(), c.getGmcNumber(), c.getPhone(), c.getEmail(),
                    c.getWorkplaceId(), c.getWorkplaceType(),
                    c.getEmploymentStatus(), c.getStartDate()
            });
        }
        if (controller != null)
            lblId.setText(controller.generateId());
    }
    private void onAdd() {
        if (controller == null) return;
        String startDate = txtStartDate.getText().trim();
        Clinician c = new Clinician(
                lblId.getText(),
                (String) cmbTitle.getSelectedItem(),
                txtFirstName.getText(),
                txtLastName.getText(),
                txtSpeciality.getText(),
                txtGmc.getText(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtWorkplaceId.getText(),
                (String) cmbWorkplaceType.getSelectedItem(),
                (String) cmbEmployment.getSelectedItem(),
                startDate
        );
        controller.addClinician(c);
    }
    private void onUpdate() {
        String startDate = txtStartDate.getText().trim().isEmpty() ? "" : txtStartDate.getText().trim();
        Clinician c = new Clinician(
                lblId.getText(),
                (String) cmbTitle.getSelectedItem(),
                txtFirstName.getText(),
                txtLastName.getText(),
                txtSpeciality.getText(),
                txtGmc.getText(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtWorkplaceId.getText(),
                (String) cmbWorkplaceType.getSelectedItem(),
                (String) cmbEmployment.getSelectedItem(),
                startDate
        );
        controller.updateClinician(c);
        clearClinicianForm();
        btnAdd.setVisible(true);
        btnUpdate.setVisible(false);
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
    private void onDelete() {
        if (controller == null) return;
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Select a clinician to delete!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = (String) model.getValueAt(row, 0);
        controller.deleteById(id);
    }
    private void loadSelectedRowIntoForm() {
        int row = table.getSelectedRow();
        if (row < 0) {
            clearClinicianForm();
            return;
        }
        lblId.setText(val(row, 0));
        cmbTitle.setSelectedItem(val(row, 1));
        txtFirstName.setText(val(row, 2));
        txtLastName.setText(val(row, 3));
        txtSpeciality.setText(val(row, 4));
        txtGmc.setText(val(row, 5));
        txtPhone.setText(val(row, 6));
        txtEmail.setText(val(row, 7));
        txtWorkplaceId.setText(val(row, 8));
        cmbWorkplaceType.setSelectedItem(val(row, 9));
        cmbEmployment.setSelectedItem(val(row, 10));
        txtStartDate.setText(val(row, 11));
    }
    private void clearClinicianForm() {
        lblId.setText("C001");
        cmbTitle.setSelectedIndex(0);
        txtFirstName.setText("");
        txtLastName.setText("");
        txtSpeciality.setText("");
        txtGmc.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtWorkplaceId.setText("");
        cmbWorkplaceType.setSelectedIndex(0);
        cmbEmployment.setSelectedIndex(0);
        txtStartDate.setText("");
    }
    private String val(int row, int col) {
        Object v = model.getValueAt(row, col);
        return v == null ? "" : v.toString();
    }
}
