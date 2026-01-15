package view;

import model.Clinician;
import model.ClinicianRepository;
import javax.swing.*;
import java.awt.*;

public class ClinicianLoginFrame extends JFrame {
    private JTextField txtClinicianId;
    private JButton btnLogin, btnBack;
    private ClinicianRepository clinicianRepository;
    
    public ClinicianLoginFrame(ClinicianRepository clinicianRepository) {
        super("Clinician Login - Healthcare Management System");
        this.clinicianRepository = clinicianRepository;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(57, 105, 138));
        headerPanel.setPreferredSize(new Dimension(500, 100));
        headerPanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Clinician Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(57, 105, 138));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titlePanel.add(titleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 247, 250));
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel idLabel = new JLabel("Enter Your Clinician ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(idLabel, gbc);
        
        txtClinicianId = new JTextField();
        txtClinicianId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtClinicianId.setPreferredSize(new Dimension(300, 35));
        gbc.gridy = 1;
        formPanel.add(txtClinicianId, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(new Color(245, 247, 250));
        
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnLogin.setPreferredSize(new Dimension(100, 40));
        btnLogin.setBackground(new Color(57, 105, 138));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBack.setPreferredSize(new Dimension(100, 40));
        btnBack.setBackground(new Color(170, 170, 170));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnBack);
        
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 20, 10);
        formPanel.add(buttonPanel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
    }
    
    public JButton getLoginButton() {
        return btnLogin;
    }
    
    public JButton getBackButton() {
        return btnBack;
    }
    
    public String getClinicianId() {
        return txtClinicianId.getText().trim();
    }
    
    public Clinician validateClinicianLogin() {
        String clinicianId = getClinicianId();
        if (clinicianId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Clinician ID.", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        Clinician clinician = clinicianRepository.findById(clinicianId);
        if (clinician == null) {
            JOptionPane.showMessageDialog(this, "Clinician ID not found. Please try again.", 
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            txtClinicianId.setText("");
            return null;
        }
        
        return clinician;
    }
}
