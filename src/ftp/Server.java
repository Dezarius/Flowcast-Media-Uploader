/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftp;

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
    
    String server = "V83WmZvMCCYHavNv1odAIaQt067kAd6zEhTzc8+0hQg=";
    String user = "jmPi3qdd5h4dB6S8XEN0QA==";
    String pass = "nbTVPMKYiYwoT7fABu2gIg==";
    
    public Server(){
        try {
            this.cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex.getMessage());
        } catch (NoSuchPaddingException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void deleatKey(){
        this.secretKeySpec = null;
    }
    
    public void key(char[] pass){
        try {
            byte[] key = (String.valueOf(pass)).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("MD5");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            this.secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException ex) {
            // byte-Array erzeugen
        } catch (NoSuchAlgorithmException ex) {
            // aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
        }
    }
    
    public String encrypt(String text){
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
            byte[] encrypted = this.cipher.doFinal(text.getBytes());
            
            // bytes zu Base64-String konvertieren (dient der Lesbarkeit)
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            return null;
        }
    } 
    
    public String decrypt(String text){
        try {
            if (text.equals("server")) {
                text = this.server;
            }
            else if (text.equals("user")) {
                text = this.user;
            }
            else if (text.equals("pass")) {
                text = this.pass;
            }
            
            byte[] crypted = Base64.getDecoder().decode(text);
            this.cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] cipherData = this.cipher.doFinal(crypted);
            return new String(cipherData);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    } 
}

