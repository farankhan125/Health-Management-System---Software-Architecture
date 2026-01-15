package view;

import controller.ReferralController;
import model.Referral;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientReferralsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    
    public PatientReferralsPanel(ReferralController controller, String patientId) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(57, 105, 138));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Your Referrals");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Table
        tableModel = new DefaultTableModel(
                new Object[]{
                        "Date", "Urgency", "Reason", "From Clinician", "To Clinician",
                        "Status", "Requested Service", "Notes"
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
        
        // Load referrals
        loadReferrals(controller, patientId);
    }
    
    private void loadReferrals(ReferralController controller, String patientId) {
        List<Referral> all = controller.getAllReferrals();
        tableModel.setRowCount(0);
        
        for (Referral r : all) {
            if (r.getPatientId().equals(patientId)) {
                tableModel.addRow(new Object[]{
                        r.getReferralDate(),
                        r.getUrgencyLevel(),
                        r.getReferralReason(),
                        r.getReferringClinicianId(),
                        r.getReferredToClinicianId(),
                        r.getStatus(),
                        r.getRequestedService(),
                        r.getNotes()
                });
            }
        }
    }
}
