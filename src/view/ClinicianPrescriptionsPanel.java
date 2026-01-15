package view;

import controller.PrescriptionController;
import model.Prescription;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClinicianPrescriptionsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    
    public ClinicianPrescriptionsPanel(PrescriptionController controller, String clinicianId) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(57, 105, 138));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("My Prescriptions");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        tableModel = new DefaultTableModel(
                new Object[]{
                        "Patient", "Drug", "Dosage", "Frequency", "Duration (days)", "Quantity",
                        "Status", "Issue Date", "Instructions"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(230, 240, 250));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scrollPane.getViewport().setBackground(new Color(245, 247, 250));
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Load prescriptions
        loadPrescriptions(controller, clinicianId);
    }
    
    private void loadPrescriptions(PrescriptionController controller, String clinicianId) {
        List<Prescription> all = controller.getAllPrescriptions();
        tableModel.setRowCount(0);
        
        for (Prescription p : all) {
            if (p.getClinicianId().equals(clinicianId)) {
                tableModel.addRow(new Object[]{
                        p.getPatientId(),
                        p.getMedication(),
                        p.getDosage(),
                        p.getFrequency(),
                        p.getDurationDays(),
                        p.getQuantity(),
                        p.getStatus(),
                        p.getIssueDate(),
                        p.getInstructions()
                });
            }
        }
    }
}
