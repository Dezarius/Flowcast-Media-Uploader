/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import java.io.File;

/**
 *
 * @author janabelmann
 */
public class Window implements ActionListener{

    private Settings settings;
    private JFrame window;
    
    String[] workflows = { "", "elsa Video 720p", "elsa Video", "Maschinenbau HD", "Maschinenbau", "OhneVorUndAbspan" };
    private JComboBox cb_workflows = new JComboBox(workflows);

    private JButton b_connect = new JButton("Connect");
    private JButton b_settings = new JButton();
    private JButton b_fileChooser = new JButton("Datein Auswählen");
    
    private JLabel lb_servername = new JLabel("Server:");
    private JLabel lb_dozent = new JLabel("Dozent:");
    private JLabel lb_titel = new JLabel("Titel:");
    private JLabel lb_beschreibung = new JLabel("Beschreibung:");
    private JLabel lb_datei = new JLabel("");
    private JLabel lb_workflows = new JLabel("Workflow:");
    
    private JTextField tf_dozent = new JTextField();
    private JTextField tf_titel = new JTextField();
    private JTextField tf_beschreibung = new JTextField();
    
    private JPanel connectIndicator = new JPanel();
    
    private File movie;
    
    public Window() {
        window = new JFrame("Flowcast Media Uploader");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setMinimumSize(new Dimension(400,300));
        window.setLocation(300, 150);
        window.setLayout(new BorderLayout());
        window.setResizable(false);
        
        settings = new Settings(window);
        
        connectIndicator.setBackground(Color.red);
        connectIndicator.setSize(new Dimension(30,30));
        
        lb_servername.setFont(new Font(lb_servername.getFont().getName(), Font.PLAIN, 16));
        lb_dozent.setFont(new Font(lb_dozent.getFont().getName(), Font.PLAIN, 14));
        lb_titel.setFont(new Font(lb_titel.getFont().getName(), Font.PLAIN, 14));
        lb_beschreibung.setFont(new Font(lb_beschreibung.getFont().getName(), Font.PLAIN, 15));
        lb_workflows.setFont(new Font(lb_workflows.getFont().getName(), Font.PLAIN, 14));
        
        JPanel panel = new JPanel();
        SpringLayout springPanel = new SpringLayout();
        panel.setLayout(springPanel);
        b_settings.setIcon(new ImageIcon("settings.png"));
        
        springPanel.putConstraint(SpringLayout.NORTH, this.connectIndicator, 10, SpringLayout.NORTH, panel);
        springPanel.putConstraint(SpringLayout.EAST, this.connectIndicator, -10, SpringLayout.EAST, panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.connectIndicator, 30, SpringLayout.NORTH, this.connectIndicator);
        springPanel.putConstraint(SpringLayout.WEST, this.connectIndicator, -30, SpringLayout.EAST, this.connectIndicator);
        panel.add(this.connectIndicator);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_settings, 10, SpringLayout.NORTH, panel);
        springPanel.putConstraint(SpringLayout.EAST, this.b_settings, -10, SpringLayout.WEST, this.connectIndicator);
        springPanel.putConstraint(SpringLayout.SOUTH, this.b_settings, 30, SpringLayout.NORTH, this.b_settings);
        springPanel.putConstraint(SpringLayout.WEST, this.b_settings, -30, SpringLayout.EAST, this.b_settings);
        panel.add(this.b_settings);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_connect, 12, SpringLayout.NORTH , panel);
        springPanel.putConstraint(SpringLayout.EAST, this.b_connect, -5, SpringLayout.WEST , this.b_settings);
        springPanel.putConstraint(SpringLayout.SOUTH, this.b_connect, 25, SpringLayout.NORTH , this.b_connect);
        springPanel.putConstraint(SpringLayout.WEST, this.b_connect, -100, SpringLayout.EAST , this.b_connect);
        panel.add(this.b_connect);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_servername, 10, SpringLayout.NORTH , panel);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_servername, 10, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_servername, 20, SpringLayout.NORTH , this.lb_servername);
        panel.add(this.lb_servername);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.b_fileChooser, 42, SpringLayout.SOUTH , this.lb_servername);
        springPanel.putConstraint(SpringLayout.WEST, this.b_fileChooser, 3, SpringLayout.WEST , panel);
        springPanel.putConstraint(SpringLayout.SOUTH, this.b_fileChooser, 25, SpringLayout.NORTH , this.b_fileChooser);
        panel.add(this.b_fileChooser);
        
        springPanel.putConstraint(SpringLayout.NORTH, this.lb_datei, -1, SpringLayout.NORTH , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.EAST, this.lb_datei, -9, SpringLayout.EAST , panel);
        springPanel.putConstraint(SpringLayout.WEST, this.lb_datei, 5, SpringLayout.EAST , this.b_fileChooser);
        springPanel.putConstraint(SpringLayout.SOUTH, this.lb_datei, 30, SpringLayout.NORTH , this.lb_datei);
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
        
        
        window.add(panel, BorderLayout.CENTER);
        
        this.b_fileChooser.addActionListener(this);
        this.b_connect.addActionListener(this);
        this.b_settings.addActionListener(this);
        
        window.setVisible(true);
    }
    
    
    private boolean isConnected = false;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o == this.b_connect) {
            if(isConnected){
                isConnected = false;
                this.connectIndicator.setBackground(Color.red);
                this.b_connect.setText("Connect");
            }
            else {
                isConnected = true;
                this.connectIndicator.setBackground(Color.green);
                this.b_connect.setText("Disconnect");
            }
        }
        else if(o == this.b_settings){
            settings.setLocation((int) (window.getLocation().getX() + 82), (int) (window.getLocation().getY() + 72));
            settings.setVisible(true);
        }
        else if(o == this.b_fileChooser){
            String pfad;
            if ("Mac OS X".equals(System.getProperty("os.name"))) {
                pfad = "/Users/" + System.getProperty("user.name") + "/Movies/";
            } else {
                pfad = "C:\\users\\" + System.getProperty("user.name") + "\\Movies";
            }
            
            JFileChooser chooser = new JFileChooser(pfad);
            FileFilter filter = new FileNameExtensionFilter("Videodatei", "mp4", "mov", "m4v");
            chooser.setFileFilter(filter);
            int open = chooser.showOpenDialog(null);
            if (open == JFileChooser.APPROVE_OPTION){
		this.movie = chooser.getSelectedFile();
                this.lb_datei.setText(this.movie.getName());
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    
