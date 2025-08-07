/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmasuya;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import view.FormProduk;
import view.FormCustomer;
import view.FormTransaksi;
import view.HomePage;

/**
 *
 * @author yiachmad
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(() -> {
//            new FormProduk().setVisible(true);
//        });
        
//        javax.swing.SwingUtilities.invokeLater(() -> {
//            new FormCustomer().setVisible(true);
//        });

//        javax.swing.SwingUtilities.invokeLater(() -> {
//            new FormTransaksi().setVisible(true);
//        });

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(HomePage::new);
    }
    
}
