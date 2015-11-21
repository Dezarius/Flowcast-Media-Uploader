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
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Kristof Dinkräve
 */
public class Ftp {
    
    FTPClient ftpClient;
    Window window;
    
    public Ftp(Window window){
        ftpClient = new FTPClient();
        this.window = window;
    }
    
    public boolean logIn(String server, String user, String pass){
        try {
            ftpClient.connect(server, 21);
            boolean login = ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            return login;
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }
        return false;
        
    }
    
    public boolean  logOut(){
        System.out.println(ftpClient.isConnected());
        try {
            ftpClient.logout();
            ftpClient.disconnect();
            return true;
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
            return true;
        }
    }
    
    public boolean isConnected(){
        try {
            ftpClient.isAvailable();
            return ftpClient.sendNoOp();
        } catch (IOException ex) {
            return false;
        }
    }
    
    public String getDefaultTimeout(){
        try {
            return ftpClient.getStatus();
        } catch (IOException ex) {
            Logger.getLogger(Ftp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void upload(File movie, String metadaten){
        
        new Thread( new Runnable(){
            @Override 
            public void run(){
                //System.out.println(movie.getParent());
                
                
                FileWriter fw;
                File datei = new File(movie.getParent(), movie.getName().split("\\.", 2)[0] + ".txt");
		try {
                    fw = new FileWriter(datei);
                    fw.write(metadaten);
                    fw.close();
		} catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Could not create Metadata!", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon("error.png"));
                    e.printStackTrace();
		}
                
                try {
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
                    //File LocalFile = new File(path);
                    String RemoteFile = "/Axel Dinkgräve/Videos/" + movie.getName();
                    InputStream inputStream = new FileInputStream(movie);
 
                    System.out.println("Start uploading second file");
                    window.setLBStatus("Upload ...");
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
                        window.setLBStatus("Upload successfully");
                        window.getBUpload().setEnabled(true);
                        window.getBConnect().setEnabled(true);
                        window.getBFileChooser().setEnabled(true);
                    }
            
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } ).start();
    }
}
