/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
      
import gui.Window;
import javax.swing.UIManager;

import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

/**
 *
 * @author janabelmann
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            if(System.getProperty("os.name").startsWith("Windows")){
                UIManager.setLookAndFeel( "javax.swing.plaf.metal.MetalLookAndFeel" );  
                MetalLookAndFeel.setCurrentTheme(new OceanTheme());
            } else {
                UIManager.setLookAndFeel( "com.apple.laf.AquaLookAndFeel" );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        
        Window window = new Window();
    }
    
}
