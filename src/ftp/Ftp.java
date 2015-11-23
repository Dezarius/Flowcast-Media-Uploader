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
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Kristof Dinkräve
 */
public class Ftp extends FTPClient{
    
    FTPClient ftpClient;
    Window window;
    Calendar calender;
    Date date;
    
    public Ftp(Window window){
        this.ftpClient = new FTPClient();
        this.date = new Date();
        
        this.window = window;
        this.ftpClient.setControlKeepAliveTimeout(300);
        this.ftpClient.setConnectTimeout(5000);
        this.ftpClient.setDefaultTimeout(10000);
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(System.out, true, '0', true));
    }
    
    public boolean logIn(String server, String user, String pass){
        try {
            this.ftpClient.connect(server, 21);
            boolean login = this.ftpClient.login(user, pass);
            if(login){
                this.ftpClient.enterLocalPassiveMode();
                return login;
            }
            else {
                window.setLBLoginStatus("Login failed");
                this.ftpClient.disconnect();
                return login;
            }
        } catch (IOException ex) {
            window.setLBLoginStatus(ex.getMessage());
            System.err.println(ex.getMessage());
        }
        return false;
        
    }
    
    public boolean  logOut(){
        System.out.println(this.ftpClient.isConnected());
        try {
            this.ftpClient.logout();
            this.ftpClient.disconnect();
            return true;
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
            return true;
        }
    }
    
    public boolean Connected(){
        return this.ftpClient.isConnected();
    }
    
    public void Timeout() {
        this.calender = Calendar.getInstance();
        System.out.println(calender.get(Calendar.SECOND));
        /*
        String dateiname = "_" + calender.get(Calendar.DATE) + "-" + calender.get(Calendar.MONTH) + "-" + 
                                    calender.get(Calendar.YEAR) + "_" + calender.get(Calendar.HOUR_OF_DAY) + "-" + 
                                    calender.get(Calendar.MINUTE) + "-" + calender.get(Calendar.SECOND);
        */
        //System.out.println(this.ftpClient.isConnected());
    }
    
    public void upload(File movie, String dozent, String titel, String beschreibung, String workflow){
        
        new Thread( new Runnable(){
            @Override 
            public void run(){
                
                calender = Calendar.getInstance();
                
                String metadaten = dozent + "\n" + titel + "\n" + beschreibung + "\n" + workflow;
                String dateiname = dozent + "_" + calender.get(Calendar.DATE) + "-" + calender.get(Calendar.MONTH) + "-" + 
                                    calender.get(Calendar.YEAR) + "_" + calender.get(Calendar.HOUR_OF_DAY) + "-" + 
                                    calender.get(Calendar.MINUTE) + "-" + calender.get(Calendar.SECOND);
                
                
                FileWriter fw;
                File datei = new File(movie.getParent(), movie.getName().split("\\.", 2)[0] + ".txt");
                
		try {
                    fw = new FileWriter(datei);
                    fw.write(metadaten);
                    fw.close();
		} catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Could not create Metadata!", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("error.png"));
		}

                try {
                    
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
                    //File LocalFile = new File(path);
                    String RemoteFile = "/Spielwiese/" + dateiname + "." + movie.getName().split("\\.", 2)[1];
                    InputStream inputStream = new FileInputStream(movie);
 
                    window.setLBUploadStatus("Upload ...");
                    OutputStream outputStream = ftpClient.storeFileStream(RemoteFile);
                    byte[] bytesIn = new byte[4096];
                    int read = 0;
                    long groeße = (movie.length() / 1024);
                    window.getPBProgress().setMinimum(0);
                    window.getPBProgress().setMaximum((int) groeße);
 
                    while ((read = inputStream.read(bytesIn)) != -1) {
                        outputStream.write(bytesIn, 0, read);
                        window.getPBProgress().setValue(window.getPBProgress().getValue() + (read/1024));
                
                    }
                    inputStream.close();
                    outputStream.close();
 
                    boolean completed = ftpClient.completePendingCommand();
                    if (completed) {
                        window.setLBUploadStatus("Upload successfully");
                        window.getBUpload().setEnabled(true);
                        window.getBConnect().setEnabled(true);
                        window.getBFileChooser().setEnabled(true);
                    }
            
                } catch (IOException ex) {
                    window.setLBUploadStatus("Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
                finally {
                    try {
                        ftpClient.disconnect();
                    } catch (IOException ex) {
                        window.setLBLoginStatus(ex.getMessage());
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
