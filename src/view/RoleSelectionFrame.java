package view;

import controller.*;
import javax.swing.*;
import java.awt.*;

public class RoleSelectionFrame extends JFrame {
    private JButton btnPatient, btnClinician, btnAdministrator;
    
    public RoleSelectionFrame() {
        super("Healthcare Management System (HMS) - Role Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Main panel with gradient-like background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
        };
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setLayout(new BorderLayout());
        
        // Header section
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(57, 105, 138));
        headerPanel.setPreferredSize(new Dimension(900, 120));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Healthcare Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel subtitleLabel = new JLabel("Select Your Role to Continue");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(200, 220, 240));
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 10, 5));
        textPanel.setBackground(new Color(57, 105, 138));
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        headerPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Button panel - centered
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setLayout(new GridLayout(1, 3, 30, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        // Patient Button
        btnPatient = createRoleButton("Patient", 
            "Access your records, appointments, prescriptions and referrals", 
            new Color(120, 130, 145));
        buttonPanel.add(btnPatient);
        
        // Clinician Button
        btnClinician = createRoleButton("Clinician", 
            "Manage patients, appointments, prescriptions and referrals", 
            new Color(120, 130, 145));
        buttonPanel.add(btnClinician);
        
        // Administrator Button
        btnAdministrator = createRoleButton("Administrator", 
            "System management\nand administration", 
            new Color(120, 130, 145));
        buttonPanel.add(btnAdministrator);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Footer section
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(230, 240, 250));
        footerPanel.setPreferredSize(new Dimension(900, 50));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel footerLabel = new JLabel("© 2026 Healthcare Management System. All Rights Reserved.");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(100, 100, 100));
        footerLabel.setHorizontalAlignment(JLabel.CENTER);
        
        footerPanel.add(footerLabel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JButton createRoleButton(String title, String description, Color color) {
        JButton button = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isArmed()) {
                    g2d.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(color);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
                } else {
                    g2d.setColor(color);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                
                super.paintComponent(g);
            }
        };
        
        button.setLayout(new BorderLayout());
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Create text panel
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(30, 15, 30, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(230, 240, 250));
        descLabel.setHorizontalAlignment(JLabel.CENTER);
        
        textPanel.add(titleLabel);
        textPanel.add(descLabel);
        
        button.add(textPanel, BorderLayout.CENTER);
        
        return button;
    }
    
    public JButton getPatientButton() {
        return btnPatient;
    }
    
    public JButton getClinicianButton() {
        return btnClinician;
    }
    
    public JButton getAdministratorButton() {
        return btnAdministrator;
    }
}
