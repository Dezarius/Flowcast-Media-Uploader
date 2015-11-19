/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Kristof Dinkräve
 */
public class Ftp {
    
    FTPClient ftpClient;
    
    public Ftp(){
        ftpClient = new FTPClient();
    }
    
    public void connect(String server, String user, String pass){
        try {
            ftpClient.connect(server, 21);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }
    }
    
    public void upload(File movie){
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            //File LocalFile = new File(path);
            String RemoteFile = "test/Report.doc";
            InputStream inputStream = new FileInputStream(movie);
 
            System.out.println("Start uploading second file");
            OutputStream outputStream = ftpClient.storeFileStream(RemoteFile);
            byte[] bytesIn = new byte[4096];
            int read = 0;
 
            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();
 
            boolean completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The second file is uploaded successfully.");
            }
            
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
