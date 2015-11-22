/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftp;


import gui.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.io.CRLFLineReader;

/**
 *
 * @author Kristof Dinkräve
 */
public class Ftp extends FTPClient{
    
    FTPClient ftpClient;
    Window window;
    
    public Ftp(Window window){
        this.ftpClient = new FTPClient();
        this.window = window;
        this.ftpClient.setControlKeepAliveTimeout(300);
        ftpClient.setConnectTimeout(5000);
        ftpClient.setDefaultTimeout(10000);
        ftpClient.addProtocolCommandListener(new PrintCommandListener(System.out, true, '0', true));
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
                window.setLBLoginStatus("Login faild");
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
        
    }
    
    public void upload(File movie, String metadaten){
        
        new Thread( new Runnable(){
            @Override 
            public void run(){
                
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
                    String RemoteFile = "/Axel Dinkgräve/Videos/" + movie.getName();
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
