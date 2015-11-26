/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftp;


import gui.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

/**
 *
 * @author Kristof Dinkräve
 */
public class Ftp{
    
    FTPSClient ftpsClient;
    Window window;
    Calendar calendar;
    
    public Ftp(Window window){
        this.ftpsClient = new FTPSClient("SSL", false);
        this.ftpsClient.addProtocolCommandListener(new PrintCommandListener(System.out, true));
        
        this.window = window;
        this.ftpsClient.setControlKeepAliveTimeout(300);
        this.ftpsClient.setConnectTimeout(5000);
        this.ftpsClient.setDefaultTimeout(10000);
    }
    
    public boolean logIn(String server, String user, String pass){
        try {
            this.ftpsClient.connect(server, 21);
            System.out.println(ftpsClient.getReplyCode());
            
            boolean login = this.ftpsClient.login(user, pass);
            if(login){
                this.ftpsClient.enterLocalPassiveMode();
                return login;
            }
            else {
                window.setLBLoginStatus("Login failed");
                this.ftpsClient.disconnect();
                return login;
            }
        } catch (IOException ex) {
            window.setLBLoginStatus(ex.getMessage());
            System.err.println(ex.getMessage());
        }
        return false;
        
    }
    
    public boolean  logOut(){
        System.out.println(this.ftpsClient.isConnected());
        try {
            this.ftpsClient.logout();
            this.ftpsClient.disconnect();
            return true;
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
            return true;
        }
    }
    
    public boolean Connected(){
        return this.ftpsClient.isConnected();
    }
    
    public boolean testConnection() {
        try {
            return this.ftpsClient.sendNoOp();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            try {
                this.ftpsClient.disconnect();
            } catch (IOException ex1) {
                window.setLBLoginStatus(ex1.getMessage());
            }
            return false;
        }
    }
    public boolean test(){
        return this.ftpsClient.isConnected();
    }
    
    public void upload(File movie, String dozent, String titel, String beschreibung, String workflow){
        
        new Thread( new Runnable(){
            @Override 
            public void run(){
                
                calendar = Calendar.getInstance();
                
                String metadaten = dozent + "\n" + titel + "\n" + beschreibung + "\n" + workflow;
                String dateiname = dozent + "_" + calendar.get(Calendar.DATE) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + 
                                    calendar.get(Calendar.YEAR) + "_" + calendar.get(Calendar.HOUR_OF_DAY) + "-" + 
                                    calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND);
                
                
                FileWriter fw;
                File meta = new File(movie.getParent(), dateiname + ".txt");
                
		try {
                    fw = new FileWriter(meta);
                    fw.write(metadaten);
                    fw.close();
		} catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Could not create Metadata!", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("error.png"));
		}

                try {
                    
                    ftpsClient.setFileType(FTP.BINARY_FILE_TYPE);
                    byte[] bytesIn = new byte[4096];
                    int read = 0;
                    long groeße = (movie.length() / 1024) + (meta.length() / 1024);
                    window.getPBProgress().setMinimum(0);
                    window.getPBProgress().setMaximum((int) groeße);
                    
                    String fileMeta = "/private/Spielwiese/" + dateiname + meta.getName().substring(meta.getName().lastIndexOf('.'));
                    String fileMovie = "/private/Spielwiese/" + dateiname + movie.getName().substring(movie.getName().lastIndexOf('.'));
                    
                    InputStream inputStreamMovie = new FileInputStream(movie);
                    OutputStream outputStreamMovie = ftpsClient.storeFileStream(fileMovie);
 
                    window.setLBUploadStatus("Upload ...");
                    
                    while ((read = inputStreamMovie.read(bytesIn)) != -1) {
                        outputStreamMovie.write(bytesIn, 0, read);
                        window.getPBProgress().setValue(window.getPBProgress().getValue() + (read/1024));
                    }
                    
                    inputStreamMovie.close();
                    outputStreamMovie.close();
                    
                    boolean completedMovie = ftpsClient.completePendingCommand();
                    
                    InputStream inputStreamMeta = new FileInputStream(meta);
                    OutputStream outputStreamMeta = ftpsClient.storeFileStream(fileMeta);
                    
                    while ((read = inputStreamMeta.read(bytesIn)) != -1) {
                        outputStreamMeta.write(bytesIn, 0, read);
                        window.getPBProgress().setValue(window.getPBProgress().getValue() + (read/1024));
                
                    }
 
                    inputStreamMeta.close();
                    outputStreamMeta.close();
                    
                    meta.delete();
 
                    boolean completedMeta = ftpsClient.completePendingCommand();
                    
                    if (completedMovie && completedMeta) {
                        window.setLBUploadStatus("Upload successfully");
                        window.getBUpload().setEnabled(true);
                        window.getBConnect().setEnabled(true);
                        window.getBFileChooser().setEnabled(true);
                        window.setUpload(false);
                    }
                    else {
                        if (!completedMovie && !completedMeta){
                            window.setLBUploadStatus("Upload failed!");
                        }
                        else if (!completedMovie){
                            window.setLBUploadStatus("Upload Movie failed!");
                        }
                        else if (!completedMeta){
                            window.setLBUploadStatus("Upload Metadaten failed!");
                        }
                    }
            
                } catch (IOException ex) {
                    window.setLBUploadStatus("Error: " + ex.getMessage());
                    ex.printStackTrace();
                    try {
                        ftpsClient.disconnect();
                    } catch (IOException ex1) {
                        window.setLBLoginStatus(ex1.getMessage());
                        
                    }
                    window.getBConnect().setText("Connect");
                    window.getLBIndicator().setIcon(new ImageIcon("red_light.png"));
                    window.getBConnect().setEnabled(true);
                    window.getBFileChooser().setEnabled(true);
                    window.getBUpload().setEnabled(window.enableUpload());
                }
            }
        } ).start();
    }
}
