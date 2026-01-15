package view;

import controller.*;
import model.Patient;
import javax.swing.*;

public class PatientPortalFrame extends JFrame {
    public PatientPortalFrame(
            Patient patient,
            PatientController pc,
            AppointmentController ac,
            PrescriptionController prc,
            ReferralController rc) {
        super("Patient Portal - " + patient.getFirstName() + " " + patient.getLastName());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new java.awt.Color(57, 105, 138));
        headerPanel.setPreferredSize(new java.awt.Dimension(800, 60));
        headerPanel.setLayout(new java.awt.BorderLayout());
        
        JLabel titleLabel = new JLabel("Patient Portal");
        titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        titleLabel.setForeground(java.awt.Color.WHITE);
        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        headerPanel.add(titleLabel, java.awt.BorderLayout.WEST);
        
        JTabbedPane tabs = new JTabbedPane();
        
        // Add patient info panel
        PatientInfoPanel infoPanel = new PatientInfoPanel(patient);
        tabs.addTab("My Record", infoPanel);
        
        // Add appointments panel
        PatientAppointmentsPanel appointmentsPanel = new PatientAppointmentsPanel(ac, patient.getId());
        tabs.addTab("My Appointments", appointmentsPanel);
        
        // Add prescriptions panel
        PatientPrescriptionsPanel prescriptionsPanel = new PatientPrescriptionsPanel(prc, patient.getId());
        tabs.addTab("My Prescriptions", prescriptionsPanel);
        
        // Add referrals panel
        PatientReferralsPanel referralsPanel = new PatientReferralsPanel(rc, patient.getId());
        tabs.addTab("My Referrals", referralsPanel);
        
        JPanel mainPanel = new JPanel(new java.awt.BorderLayout());
        mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
        mainPanel.add(tabs, java.awt.BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
}
