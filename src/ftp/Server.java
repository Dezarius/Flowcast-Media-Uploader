/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftp;

import gui.Window;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Kristof
 */
public class Server {
    
    SecretKeySpec secretKeySpec;
    Cipher cipher;
    Window window;
    
    String serverFTPS = "V83WmZvMCCYHavNv1odAIaQt067kAd6zEhTzc8+0hQg=";
    String userFTPS = "jmPi3qdd5h4dB6S8XEN0QA==";
    String passFTPS = "nbTVPMKYiYwoT7fABu2gIg==";
    String videoFTPS = "n/3mUe1dlmvZ7QtcqQmOyQ==";
    String metaFTPS = "cxv7mu4KCABPjt9BljTSkA==";
    
    String serverFTP = "RcrX8OwL2uoxSlkIkGQQgg==" ;
    String userFTP = "899D4PBMyWytA7fVW3XU9Z8Z53GbuZFh2jm92Swk9C4=";
    String passFTP = "5p8c26xhkKTQyD0zEFbGNQ==";
    String videoFTP = "grn9UWc9NIJ+UEBp7jo93A==";
    String metaFTP = "QNyS//qMzx7puB/YyxUD7g==";
    
    public Server(Window window){
        try {
            this.window = window;
            this.cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void deleatKey(){
        this.secretKeySpec = null;
    }
    
    public void key(char[] pass) throws Exception{
        try {
            byte[] key = (String.valueOf(pass)).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("MD5");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            this.secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (IllegalArgumentException | UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            window.setLBLoginStatus("Could not create Key!");
            throw new Exception(ex);
        }
    }
    
    public String encrypt(String text){
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
            byte[] encrypted = this.cipher.doFinal(text.getBytes());
            
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            return null;
        }
    } 
    
    public String decrypt(String text, boolean ftp) throws Exception{
        try {
            if(ftp) {
                if (text.equals("server")) {
                    text = this.serverFTP;
                }
                else if (text.equals("user")) {
                    text = this.userFTP;
                }
                else if (text.equals("pass")) {
                    text = this.passFTP;
                }
                else if (text.equals("meta")) {
                    text = this.metaFTP;
                } 
                else if (text.equals("video")) {
                    text = this.videoFTP;
                } 
            } else {
                if (text.equals("server")) {
                    text = this.serverFTPS;
                }
                else if (text.equals("user")) {
                    text = this.userFTPS;
                }
                else if (text.equals("pass")) {
                    text = this.passFTPS;
                }
                else if (text.equals("meta")) {
                    text = this.metaFTPS;
                } 
                else if (text.equals("video")) {
                    text = this.videoFTPS;
                } 
            }
            
            
            byte[] crypted = Base64.getDecoder().decode(text);
            this.cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] cipherData = this.cipher.doFinal(crypted);
            return new String(cipherData);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new Exception(ex);
        }
    } 
}

