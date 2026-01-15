package view;

import model.Patient;
import javax.swing.*;
import java.awt.*;

public class PatientInfoPanel extends JPanel {
    
    public PatientInfoPanel(Patient patient) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(57, 105, 138));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Your Medical Record");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(245, 247, 250));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Personal Information Card
        JPanel personalCard = createInfoCard("Personal Information");
        personalCard.add(createInfoRow("Patient ID:", patient.getId()));
        personalCard.add(createInfoRow("First Name:", patient.getFirstName()));
        personalCard.add(createInfoRow("Last Name:", patient.getLastName()));
        personalCard.add(createInfoRow("Date of Birth:", patient.getDateOfBirth()));
        personalCard.add(createInfoRow("Gender:", patient.getGender()));
        contentPanel.add(personalCard);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Contact Information Card
        JPanel contactCard = createInfoCard("Contact Information");
        contactCard.add(createInfoRow("Phone Number:", patient.getPhoneNumber()));
        contactCard.add(createInfoRow("Email:", patient.getEmail()));
        contactCard.add(createInfoRow("Address:", patient.getAddress()));
        contactCard.add(createInfoRow("Postcode:", patient.getPostcode()));
        contentPanel.add(contactCard);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Medical Information Card
        JPanel medicalCard = createInfoCard("Medical Information");
        medicalCard.add(createInfoRow("NHS Number:", patient.getNhsNumber()));
        medicalCard.add(createInfoRow("Registration Date:", patient.getRegistrationDate()));
        medicalCard.add(createInfoRow("GP Surgery ID:", patient.getGpSurgeryId()));
        contentPanel.add(medicalCard);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Emergency Contact Card
        JPanel emergencyCard = createInfoCard("Emergency Contact");
        emergencyCard.add(createInfoRow("Contact Name:", patient.getEmergencyContactName()));
        emergencyCard.add(createInfoRow("Contact Phone:", patient.getEmergencyContactPhone()));
        contentPanel.add(emergencyCard);
        contentPanel.add(Box.createVerticalGlue());
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBackground(new Color(245, 247, 250));
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createInfoCard(String title) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(57, 105, 138));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        
        return card;
    }
    
    private JPanel createInfoRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelComp.setForeground(new Color(100, 100, 100));
        labelComp.setPreferredSize(new Dimension(150, 30));
        
        JLabel valueComp = new JLabel(value != null ? value : "N/A");
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        valueComp.setForeground(new Color(50, 50, 50));
        
        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.CENTER);
        
        return row;
    }
}
