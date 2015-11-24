/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import ftp.Ftp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author janabelmann
 */
public class Window implements ActionListener, DocumentListener, MouseListener{

    private Settings settings;
    private Ftp ftp;
    private JFrame window;
    
    String[] workflows = { "", "elsa Video 720p", "elsa Video", "Maschinenbau HD", "Maschinenbau", "OhneVorUndAbspan" };
    private JComboBox cb_workflows = new JComboBox(workflows);

    private JButton b_connect = new JButton("Connect");
    private JButton b_fileChooser = new JButton("Datein Auswählen");
    private JButton b_upload = new JButton("Upload");
    private JButton b_test = new JButton("Test");
    
    private JLabel lb_dozent = new JLabel("Dozent:");
    private JLabel lb_titel = new JLabel("Titel:");
    private JLabel lb_beschreibung = new JLabel("Beschreibung:");
    private JLabel lb_datei = new JLabel("");
    private JLabel lb_workflows = new JLabel("Workflow:");
    private JLabel lb_serverIP = new JLabel("IP:");
    private JLabel lb_username = new JLabel("User:");
    private JLabel lb_settings = new JLabel(new ImageIcon("settings.png"));
    private JLabel lb_connectIndicator = new JLabel(new ImageIcon("red_light.png"));
    private JLabel lb_uploadStatus = new JLabel(" ");
    private JLabel lb_loginStatus = new JLabel(" ", JLabel.RIGHT);
    
    private JTextField tf_dozent = new JTextField();
    private JTextField tf_titel = new JTextField();
    private JTextField tf_beschreibung = new JTextField();
    
    private JProgressBar pb_progress = new JProgressBar();
    
    
    private JPanel connectIndicator = new JPanel();
    
    private File movie;
    
    public Window() {
        this.window = new JFrame("Flowcast Media Uploader");
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setMinimumSize(new Dimension(400,350));
        this.window.setLocation(300, 150);
        this.window.setLayout(new BorderLayout());
        this.window.setResizable(false);
        
        this.settings = new Settings(window);
        this.ftp = new Ftp(this);
        this.testConnection();
        
        //connectIndicator.setBackground(Color.red);
        //connectIndicator.setSize(new Dimension(30,30));
        
        lb_serverIP.setFont(new Font(lb_serverIP.getFont().getName(), Font.PLAIN, 15));
        lb_username.setFont(new Font(lb_serverIP.getFont().getName(), Font.PLAIN, 15));
        lb_dozent.setFont(new Font(lb_dozent.getFont().getName(), Font.PLAIN, 14));
        lb_titel.setFont(new Font(lb_titel.getFont().getName(), Font.PLAIN, 14));
        lb_beschreibung.setFont(new Font(lb_beschreibung.getFont().getName(), Font.PLAIN, 15));
        lb_workflows.setFont(new Font(lb_workflows.getFont().getName(), Font.PLAIN, 14));
        lb_uploadStatus.setFont(new Font(lb_uploadStatus.getFont().getName(), Font.PLAIN, 11));
        lb_loginStatus.setFont(new Font(lb_loginStatus.getFont().getName(), Font.PLAIN, 11));
        
        this.b_upload.setEnabled(true);
        
        JPanel panel = new JPanel();
        SpringLayout springPanel = new SpringLayout();
        panel.setLayout(springPanel);
        this.pb_progress.setStringPainted(true);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_connectIndicator, 10, SpringLayout.NORTH, panel);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_connectIndicator, -10, SpringLayout.EAST, panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_connectIndicator, 30, SpringLayout.NORTH, this.lb_connectIndicator);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_connectIndicator, -30, SpringLayout.EAST, this.lb_connectIndicator);
        panel.add(this.lb_connectIndicator);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_settings, 10, SpringLayout.NORTH, panel);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_settings, -10, SpringLayout.WEST, this.lb_connectIndicator);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_settings, 30, SpringLayout.NORTH, this.lb_settings);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_settings, -30, SpringLayout.EAST, this.lb_settings);
        panel.add(this.lb_settings);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_connect, 12, SpringLayout.NORTH , panel);
        springPanel.putConstraint(SpringLayout.EAST, this.b_connect, -5, SpringLayout.WEST , this.lb_settings);
        springPanel.putConstraint(SpringLayout.SOUTH, this.b_connect, 25, SpringLayout.NORTH , this.b_connect);
        springPanel.putConstraint(SpringLayout.WEST, this.b_connect, -100, SpringLayout.EAST , this.b_connect);
        panel.add(this.b_connect);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_serverIP, 10, SpringLayout.NORTH , panel);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_serverIP, 10, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_serverIP, 30, SpringLayout.NORTH , panel);
        panel.add(this.lb_serverIP);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_username, 0, SpringLayout.SOUTH , this.lb_serverIP);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_username, 10, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_username, 20, SpringLayout.NORTH , this.lb_username);
        panel.add(this.lb_username);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_loginStatus, 5, SpringLayout.SOUTH , this.lb_connectIndicator);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_loginStatus, 0, SpringLayout.EAST , this.lb_connectIndicator);
        //springPanel.putConstraint(SpringLayout.SOUTH, this.lb_loginStatus, 25, SpringLayout.NORTH , this.lb_connectIndicator);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_loginStatus, -300, SpringLayout.EAST , this.lb_connectIndicator);
        panel.add(this.lb_loginStatus);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_fileChooser, 15, SpringLayout.SOUTH , this.lb_username);
        springPanel.putConstraint(SpringLayout.WEST, this.b_fileChooser, 3, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.b_fileChooser, 25, SpringLayout.NORTH , this.b_fileChooser);
        panel.add(this.b_fileChooser);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_datei, -1, SpringLayout.NORTH , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_datei, -9, SpringLayout.EAST , panel);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_datei, 5, SpringLayout.EAST , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_datei, 27, SpringLayout.NORTH , this.lb_datei);
        panel.add(this.lb_datei);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_dozent, 3, SpringLayout.SOUTH , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_dozent, 7, SpringLayout.WEST , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_dozent, 60, SpringLayout.WEST , this.lb_dozent);
        panel.add(this.lb_dozent);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_titel, 12, SpringLayout.SOUTH , this.lb_dozent);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_titel, 7, SpringLayout.WEST , b_fileChooser);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_titel, 60, SpringLayout.WEST , this.lb_titel);
        panel.add(this.lb_titel);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.tf_dozent, 1, SpringLayout.SOUTH , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.WEST, this.tf_dozent, 0, SpringLayout.EAST , this.lb_dozent);
        springPanel.putConstraint(SpringLayout.EAST, this.tf_dozent, -9, SpringLayout.EAST , panel);
        panel.add(this.tf_dozent);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.tf_titel, 1, SpringLayout.SOUTH , this.tf_dozent);
        springPanel.putConstraint(SpringLayout.WEST, this.tf_titel, 0, SpringLayout.EAST , this.lb_titel);
        springPanel.putConstraint(SpringLayout.EAST, this.tf_titel, -9, SpringLayout.EAST , panel);
        panel.add(this.tf_titel);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_beschreibung, 20, SpringLayout.SOUTH , this.lb_titel);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_beschreibung, 0, SpringLayout.WEST , this.lb_dozent);
        panel.add(this.lb_beschreibung);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.tf_beschreibung, 2, SpringLayout.SOUTH , this.lb_beschreibung);
        springPanel.putConstraint(SpringLayout.WEST, this.tf_beschreibung, 8, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.EAST, this.tf_beschreibung, -9, SpringLayout.EAST , panel);
        panel.add(this.tf_beschreibung);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_workflows, 4, SpringLayout.SOUTH , this.tf_beschreibung);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_workflows, 0, SpringLayout.WEST , this.lb_dozent);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_workflows, 70, SpringLayout.WEST , this.lb_workflows);
        panel.add(this.lb_workflows);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.cb_workflows, -4, SpringLayout.NORTH , this.lb_workflows);
        springPanel.putConstraint(SpringLayout.WEST, this.cb_workflows, 0, SpringLayout.EAST , this.lb_workflows);
        springPanel.putConstraint(SpringLayout.EAST, this.cb_workflows, -6, SpringLayout.EAST , panel);
        panel.add(this.cb_workflows);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_upload, 8, SpringLayout.SOUTH , this.lb_workflows);
        springPanel.putConstraint(SpringLayout.WEST, this.b_upload, 3, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.EAST, this.b_upload, 83, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.b_upload, 32, SpringLayout.NORTH , this.b_upload);
        panel.add(this.b_upload);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_uploadStatus, 2, SpringLayout.NORTH , this.b_upload);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_uploadStatus, 3, SpringLayout.EAST , this.b_upload);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_uploadStatus, -9, SpringLayout.EAST , panel);
        panel.add(this.lb_uploadStatus);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.pb_progress, -4, SpringLayout.SOUTH , this.lb_uploadStatus);
        springPanel.putConstraint(SpringLayout.WEST, this.pb_progress, 0, SpringLayout.EAST , this.b_upload);
        springPanel.putConstraint(SpringLayout.EAST, this.pb_progress, -9, SpringLayout.EAST , panel);
        //springPanel.putConstraint(SpringLayout.SOUTH, this.pb_progress, 13, SpringLayout.NORTH , this.pb_progress);
        panel.add(this.pb_progress);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_test, 5, SpringLayout.SOUTH , this.b_upload);
        springPanel.putConstraint(SpringLayout.WEST, this.b_test, 3, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.EAST, this.b_test, 83, SpringLayout.WEST , panel);
        panel.add(this.b_test);
   
        window.add(panel, BorderLayout.CENTER);
        
        this.b_fileChooser.addActionListener(this);
        this.b_connect.addActionListener(this);
        this.b_upload.addActionListener(this);
        this.b_test.addActionListener(this);
        this.cb_workflows.addActionListener(this);
        
        this.tf_dozent.getDocument().addDocumentListener(this);
        this.tf_titel.getDocument().addDocumentListener(this);        
        this.tf_beschreibung.getDocument().addDocumentListener(this);
        
        this.lb_settings.addMouseListener(this);
        
        window.setVisible(true);
    }
    
    public void testConnection(){
        new Thread( new Runnable(){
            @Override 
            public void run(){
                while (true) {
                    if (ftp.Connected() && !ftp.testConnection()) {
                        lb_connectIndicator.setIcon(new ImageIcon("red_light.png"));
                        b_connect.setText("Connect");
                        b_upload.setEnabled(false);
                    }
                    
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        } ).start();
    }
    
    public boolean enableUpload(){
        boolean datei = this.lb_datei.getText().length() > 0;
        boolean dozent = this.tf_dozent.getText().length() > 0;
        boolean titel = this.tf_titel.getText().length() > 0;
        boolean beschreibung = this.tf_beschreibung.getText().length() > 0;
        boolean workflow = this.cb_workflows.getSelectedItem().toString().length() > 0;
        
        return this.ftp.Connected() && datei && dozent && titel && beschreibung && workflow;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == this.b_connect) {
            if(this.ftp.Connected()){
                if (this.ftp.logOut()) {
                    lb_connectIndicator.setIcon(new ImageIcon("red_light.png"));
                    b_connect.setText("Connect");
                    b_upload.setEnabled(enableUpload());
                }
            }
            else {
                if (this.ftp.logIn(this.settings.getIP(), this.settings.getUser(), this.settings.getPassword())) {
                    this.lb_connectIndicator.setIcon(new ImageIcon("green_light.png"));
                    this.b_connect.setText("Disconnect");
                    this.lb_loginStatus.setText(" ");
                    this.b_upload.setEnabled(this.enableUpload());
                }
            }
            this.b_upload.setEnabled(this.enableUpload());
        }
        else if(o == this.b_fileChooser){
            String pfad = null;
            if ("Mac OS X".equals(System.getProperty("os.name"))) {
                pfad = "/Users/" + System.getProperty("user.name") + "/Movies/";
            } else if (System.getProperty("os.name").startsWith("Windows")) {
                pfad = "C:\\Users\\" + System.getProperty("user.name") + "\\Videos";
            }
            
            JFileChooser chooser = new JFileChooser(pfad);
            FileFilter filter = new FileNameExtensionFilter("Videodatei", "mp4", "mov", "m4v");
            chooser.setFileFilter(filter);
            int open = chooser.showOpenDialog(null);
            if (open == JFileChooser.APPROVE_OPTION){
		this.movie = chooser.getSelectedFile();
                this.lb_datei.setText(this.movie.getName());
                this.b_upload.setEnabled(this.enableUpload());
                this.lb_uploadStatus.setText(" ");
                this.pb_progress.setValue(0);
            }
        }
        else if (o == this.b_upload) {
            this.b_connect.setEnabled(false);
            this.b_upload.setEnabled(false);
            this.b_fileChooser.setEnabled(false);
            if(this.ftp.Connected()){
                this.ftp.upload(this.movie, this.tf_dozent.getText(), this.tf_titel.getText(), this.tf_beschreibung.getText(), this.cb_workflows.getSelectedItem().toString());
            }
            else {
                this.lb_uploadStatus.setText("Connection lost!");
            }
            
        }
        else if (o == this.cb_workflows) {
            this.b_upload.setEnabled(this.enableUpload());
        }
        else if (o == this.b_test) {
            System.out.println(this.ftp.test());
        }
        
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        this.b_upload.setEnabled(this.enableUpload());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        this.b_upload.setEnabled(this.enableUpload());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
    }

    public JButton getBUpload(){
        return this.b_upload;
    }
    
    public JButton getBConnect(){
        return this.b_connect;
    }
    
    public JButton getBFileChooser(){
        return this.b_fileChooser;
    }
    
    public JProgressBar getPBProgress(){
        return this.pb_progress;
    }
    
    public void setLBUploadStatus(String text){
        this.lb_uploadStatus.setText(text);
    }
    
    public void setLBLoginStatus(String text){
        this.lb_loginStatus.setText(text);
    }
    
    public JLabel getLBIndicator(){
        return this.lb_connectIndicator;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o == this.lb_settings) {
            settings.setLocation((int) (window.getLocation().getX() + 82), (int) (window.getLocation().getY() + 72));
            settings.setVisible(true);
            this.lb_serverIP.setText("IP: " + this.settings.getIP());
            this.lb_username.setText("User: " + this.settings.getUser());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
    
