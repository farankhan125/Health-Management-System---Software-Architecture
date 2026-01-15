package view;

import controller.*;
import javax.swing.*;

public class ClinicianPortalFrame extends JFrame {
    
    public ClinicianPortalFrame(
            PatientController pc,
            AppointmentController ac,
            PrescriptionController prc,
            ReferralController rc) {
        super("Healthcare Management System - Clinician Portal");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new java.awt.Color(57, 105, 138));
        headerPanel.setPreferredSize(new java.awt.Dimension(800, 60));
        headerPanel.setLayout(new java.awt.BorderLayout());
        
        JLabel titleLabel = new JLabel("Clinician Portal");
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        titleLabel.setForeground(java.awt.Color.WHITE);
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        headerPanel.add(titleLabel, java.awt.BorderLayout.WEST);
        
        JTabbedPane tabs = new JTabbedPane();
        
        // Add Patients tab (full access)
        tabs.addTab("Patients", pc.getView());
        
        // Add Appointments tab (full access)
        tabs.addTab("Appointments", ac.getView());
        
        // Add Prescriptions tab (full access)
        tabs.addTab("Prescriptions", prc.getView());
        
        // Add Referrals tab (full access)
        tabs.addTab("Referrals", rc.getView());
        
        JPanel mainPanel = new JPanel(new java.awt.BorderLayout());
        mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
        mainPanel.add(tabs, java.awt.BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
}

