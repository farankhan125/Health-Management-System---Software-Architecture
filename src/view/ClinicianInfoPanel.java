package view;

import model.Clinician;
import javax.swing.*;
import java.awt.*;

public class ClinicianInfoPanel extends JPanel {
    
    public ClinicianInfoPanel(Clinician clinician) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(57, 105, 138));
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Your Profile");
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
        
        // Professional Information Card
        JPanel professionalCard = createInfoCard("Professional Information");
        professionalCard.add(createInfoRow("Clinician ID:", clinician.getId()));
        professionalCard.add(createInfoRow("Title:", clinician.getTitle()));
        professionalCard.add(createInfoRow("First Name:", clinician.getFirstName()));
        professionalCard.add(createInfoRow("Last Name:", clinician.getLastName()));
        professionalCard.add(createInfoRow("Speciality:", clinician.getSpeciality()));
        professionalCard.add(createInfoRow("GMC Number:", clinician.getGmcNumber()));
        contentPanel.add(professionalCard);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Contact Information Card
        JPanel contactCard = createInfoCard("Contact Information");
        contactCard.add(createInfoRow("Phone Number:", clinician.getPhone()));
        contactCard.add(createInfoRow("Email:", clinician.getEmail()));
        contentPanel.add(contactCard);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Workplace Information Card
        JPanel workplaceCard = createInfoCard("Workplace Information");
        workplaceCard.add(createInfoRow("Workplace ID:", clinician.getWorkplaceId()));
        workplaceCard.add(createInfoRow("Workplace Type:", clinician.getWorkplaceType()));
        workplaceCard.add(createInfoRow("Employment Status:", clinician.getEmploymentStatus()));
        workplaceCard.add(createInfoRow("Start Date:", clinician.getStartDate()));
        contentPanel.add(workplaceCard);
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
        labelComp.setPreferredSize(new Dimension(180, 30));
        
        JLabel valueComp = new JLabel(value != null ? value : "N/A");
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        valueComp.setForeground(new Color(50, 50, 50));
        
        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.CENTER);
        
        return row;
    }
}
