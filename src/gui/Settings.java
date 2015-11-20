/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import sun.awt.SunToolkit;

/**
 *
 * @author Kristof Dinkgräve
 */
public class Settings extends JDialog implements ActionListener, DocumentListener{
    
    JDialog popup;
    
    private String serverIP = "";
    private static String username = "";
    private String password = "";
    
    private JTextField tf_server = new JTextField("192.168.178.28");
    private JTextField tf_user = new JTextField("Kristof");
    private JPasswordField tf_password = new JPasswordField("Kri321ss");
    
    private JLabel lb_server = new JLabel("Server");
    private JLabel lb_user = new JLabel("Benutzer");
    private JLabel lb_password = new JLabel("Passwort");
    
    private JButton b_save = new JButton("Speichern");
    
    public Settings(JFrame owner){
        super(owner, "Neues Profil", true);
        popup = this;
	popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        popup.setMinimumSize(new Dimension(235,155));
        popup.setLocation((int) (owner.getLocation().getX() + 82), (int) (owner.getLocation().getY() + 72));
        popup.setResizable(false);
        
        SpringLayout sl_settings = new SpringLayout();
	popup.setLayout(sl_settings);
        
        this.lb_server.setFont(new Font(this.lb_server.getFont().getName(), Font.PLAIN, 14));
        this.lb_user.setFont(new Font(this.lb_user.getFont().getName(), Font.PLAIN, 14));
        this.lb_password.setFont(new Font(this.lb_password.getFont().getName(), Font.PLAIN, 14));
        
        sl_settings.putConstraint(SpringLayout.NORTH, this.lb_server, 5, SpringLayout.NORTH, popup);
        sl_settings.putConstraint(SpringLayout.EAST, this.lb_server, 65, SpringLayout.WEST, this.lb_server);
        sl_settings.putConstraint(SpringLayout.SOUTH, this.lb_server, 30, SpringLayout.NORTH, this.lb_server);
        sl_settings.putConstraint(SpringLayout.WEST, this.lb_server, 10, SpringLayout.WEST, popup);
        popup.add(this.lb_server);
        
        sl_settings.putConstraint(SpringLayout.NORTH, this.lb_user, 2, SpringLayout.SOUTH, this.lb_server);
        sl_settings.putConstraint(SpringLayout.EAST, this.lb_user, 0, SpringLayout.EAST, this.lb_server);
        sl_settings.putConstraint(SpringLayout.SOUTH, this.lb_user, 30, SpringLayout.NORTH, this.lb_user);
        sl_settings.putConstraint(SpringLayout.WEST, this.lb_user, 0, SpringLayout.WEST, this.lb_server);
        popup.add(this.lb_user);
        
        sl_settings.putConstraint(SpringLayout.NORTH, this.lb_password, 2, SpringLayout.SOUTH, this.lb_user);
        sl_settings.putConstraint(SpringLayout.EAST, this.lb_password, 0, SpringLayout.EAST, this.lb_server);
        sl_settings.putConstraint(SpringLayout.SOUTH, this.lb_password, 30, SpringLayout.NORTH, this.lb_password);
        sl_settings.putConstraint(SpringLayout.WEST, this.lb_password, 0, SpringLayout.WEST, this.lb_server);
        popup.add(this.lb_password);
        
        sl_settings.putConstraint(SpringLayout.NORTH, this.tf_server, 1, SpringLayout.NORTH, this.lb_server);
        sl_settings.putConstraint(SpringLayout.EAST, this.tf_server, 150, SpringLayout.WEST, this.tf_server);
        sl_settings.putConstraint(SpringLayout.WEST, this.tf_server, 0, SpringLayout.EAST, this.lb_server);
        popup.add(this.tf_server);
        
        sl_settings.putConstraint(SpringLayout.NORTH, this.tf_user, 1, SpringLayout.NORTH, this.lb_user);
        sl_settings.putConstraint(SpringLayout.EAST, this.tf_user, 0, SpringLayout.EAST, this.tf_server);
        sl_settings.putConstraint(SpringLayout.WEST, this.tf_user, 0, SpringLayout.EAST, this.lb_server);
        popup.add(this.tf_user);
        
        sl_settings.putConstraint(SpringLayout.NORTH, this.tf_password, 1, SpringLayout.NORTH, this.lb_password);
        sl_settings.putConstraint(SpringLayout.EAST, this.tf_password, 0, SpringLayout.EAST, this.tf_server);
        sl_settings.putConstraint(SpringLayout.WEST, this.tf_password, 0, SpringLayout.EAST, this.lb_server);
        popup.add(this.tf_password);
        
        sl_settings.putConstraint(SpringLayout.NORTH, this.b_save, 2, SpringLayout.SOUTH, this.lb_password);
        sl_settings.putConstraint(SpringLayout.EAST, this.b_save, 150, SpringLayout.WEST, this.b_save);
        sl_settings.putConstraint(SpringLayout.WEST, this.b_save, 32, SpringLayout.WEST, lb_server);
        popup.add(this.b_save);
        
        this.b_save.addActionListener(this);
	this.tf_server.getDocument().addDocumentListener(this);
        this.tf_user.getDocument().addDocumentListener(this);
        this.tf_password.getDocument().addDocumentListener(this);
        
        this.b_save.setEnabled(false);
    }
    
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == this.b_save) {
            this.password = String.valueOf(this.tf_password.getPassword());
            this.serverIP = this.tf_server.getText();
            this.username = this.tf_user.getText();
            
            Settings.this.dispose();
        }  
    }
    
    public  String getIP() {
        return this.serverIP;
    }
    
    public String getUser() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
	
    public void changedUpdate(DocumentEvent e){
		
    }
		
    public void insertUpdate(DocumentEvent e){
        int length_server = this.tf_server.getText().length();
        int length_user = this.tf_user.getText().length();
        int length_password = this.tf_password.getText().length();
        if (length_server > 0 && length_user > 0 && length_password > 0) {
            this.b_save.setEnabled(true);
        }
            
    }

    public void removeUpdate(DocumentEvent e){
        int length_server = this.tf_server.getText().length();
        int length_user = this.tf_user.getText().length();
        int length_password = this.tf_password.getText().length();
        if (length_server <= 0 || length_user <= 0 || length_password <= 0) {
            this.b_save.setEnabled(false);
        }
    }

}
