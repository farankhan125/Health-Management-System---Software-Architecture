package view;

import controller.PrescriptionController;
import model.Prescription;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientPrescriptionsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    
    public PatientPrescriptionsPanel(PrescriptionController controller, String patientId) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(57, 105, 138));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Your Prescriptions");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        tableModel = new DefaultTableModel(
                new Object[]{
                        "Drug", "Dosage", "Frequency", "Duration (days)", "Quantity",
                        "Status", "Issue Date", "Collection Date", "Instructions"
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
        loadPrescriptions(controller, patientId);
    }
    
    private void loadPrescriptions(PrescriptionController controller, String patientId) {
        List<Prescription> all = controller.getAllPrescriptions();
        tableModel.setRowCount(0);
        
        for (Prescription p : all) {
            if (p.getPatientId().equals(patientId)) {
                tableModel.addRow(new Object[]{
                        p.getMedication(),
                        p.getDosage(),
                        p.getFrequency(),
                        p.getDurationDays(),
                        p.getQuantity(),
                        p.getStatus(),
                        p.getIssueDate(),
                        p.getCollectionDate(),
                        p.getInstructions()
                });
            }
        }
    }
}
