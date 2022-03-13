/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yapaysiniragi;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import static yapaysiniragi.GirisHesaplamalari.noronDizi;
import static yapaysiniragi.GirisHesaplamalari.randomSatir;
import static yapaysiniragi.GirisHesaplamalari.randomSayi;
import static yapaysiniragi.AraCikisKatmanIslemleri.hataDegerleri;
import static yapaysiniragi.AraCikisKatmanIslemleri.hataDegeri;
import static yapaysiniragi.GirisHesaplamalari.gercekHata;
import static yapaysiniragi.TestVerileri.cikisGercekHata;
import static yapaysiniragi.TestVerileri.testHataDegeri;

/**
 *
 * @author kubra
 */
public class Arayuz extends javax.swing.JFrame {

    /**
     * Creates new form Arayuz
     */
    Grafik grafik = new Grafik();

    public Arayuz() {
        initComponents();

    }

    static String dosyaYolu;
    static int satirSayisi, sutunSayisi, noronSayisi, egitimSeti, testSeti;
    static double toplamHata = 0, mse = 0, mape=0;
    final static double ogrenmeKatsayisi = 0.01, momentum = 0.02;
    ArrayList hataDegeriMutlak = new ArrayList();
    ArrayList gercekCikis = new ArrayList();
    
    static ArrayList<Double> guncelAgirlikCikis=new ArrayList<>();
    static ArrayList<Double> guncelAgirlikGiris=new ArrayList<>();
    int epok = 0;

    void islemler() {

        if (Integer.parseInt(noronSayisiTextField.getText()) >= 2 && Integer.parseInt(noronSayisiTextField.getText()) <= 20) {
            satirSayisi = 768;
            sutunSayisi = 9;
            noronSayisi = Integer.parseInt(noronSayisiTextField.getText());
            egitimSeti = ((satirSayisi * 70) / 100);
            testSeti = ((satirSayisi * 30) / 100) + 1;
            Hesaplamalar hesaplamalar = new Hesaplamalar();
            HesaplamaVeriler hesaplamaVeriler = new HesaplamaVeriler();
            hesaplamaVeriler.verileriAl();
            hesaplamaVeriler.veriSetleriniOlustur();
            GirisHesaplamalari girisHesaplamalari = new GirisHesaplamalari();
            AraCikisKatmanIslemleri araCikisKatmanIslemleri = new AraCikisKatmanIslemleri();
            GirisAraKatmanIslemleri girisAraKatmanIslemleri = new GirisAraKatmanIslemleri();
            TestVerileri testVerileri=new TestVerileri();
            do {
                randomSayi = 0;
                for (int i = 0; i < egitimSeti; i++) {
                    girisHesaplamalari.ilkGelenVeri();
                    hataDegerleri.add(hataDegeri * hataDegeri);
                    hataDegeriMutlak.add(Math.abs(hataDegeri));
                    gercekCikis.add(gercekHata);
                    araCikisKatmanIslemleri.araKatmanCikisKatmani();
                    girisAraKatmanIslemleri.girisKatmanAraKatman();
                    araCikisKatmanIslemleri.atamaIslemeleri();
                    girisAraKatmanIslemleri.atamaIslemleri();
                    noronDizi.clear();
                    randomSatir.clear();
                    randomSayi++;
                    if ((i + 1) % 50 == 0) {
                        mse = hesaplamalar.mseHesapla(hataDegerleri);
                        //System.out.println("mse: "+mse);
                        grafik.list.add(mse);
                        hataDegerleri.clear();
                    }                  
                }
                //System.out.println("epok: "+epok);
                epok++;
                mape=hesaplamalar.mapeHesapla(hataDegeriMutlak, gercekCikis,egitimSeti);
                System.out.println("mape eğitim: " + mape);
                hataDegeriMutlak.clear();
                gercekCikis.clear();
                if(mape==3){
                    break;
                }
            } while (epok < 100);
            grafik.setVisible(true);
            grafik.calistir();
            System.out.println("Eğitim sona erdi..");
            //grafik = new Grafik();
            //grafik.setVisible(true);
            
            Collections.reverse(guncelAgirlikGiris);
            Collections.reverse(guncelAgirlikCikis);
            
            System.out.println("Test sürecine girildi..");
            for(int i=0;i<testSeti;i++){
                testVerileri.testVerileri();
                hataDegerleri.add(testHataDegeri*testHataDegeri);
                hataDegeriMutlak.add(Math.abs(testHataDegeri));
                gercekCikis.add(cikisGercekHata); 
                if ((i + 1) % 50 == 0) {
                        mse = hesaplamalar.mseHesapla(hataDegerleri);
                        //System.out.println("mse: "+mse);
                        //grafik.list.add(mse);
                        hataDegerleri.clear();
                    } 
            }
            mape=hesaplamalar.mapeHesapla(hataDegeriMutlak, gercekCikis,testSeti);
            System.out.println("mape: "+mape);
            

        } else {
            JOptionPane.showMessageDialog(null, "Noron değerini 2-20 arasında giriniz");
        }
    }

    void dosyaYolu() {
        //dosyaYoluTextField.setText("");
        dosyaYolu = dosyaSecme();
        //dosyaYoluTextField.setText(dosyaYolu);
    }

    public String dosyaSecme() {
        //Buralar tek duruma indirilebilir
        String yol = "enerji verimliliği veri-seti.xlsx";
        /*JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "excel dosyası", "xlsx");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            yol = chooser.getSelectedFile().getPath();
            System.out.println("Seçilen dosya: "
                    + yol);
        }*/
        return yol;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        calistirButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        noronSayisiTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Seçilen Dosya:");

        jLabel4.setText("Satır Sayısı:");

        calistirButton.setText("Çalıştır");
        calistirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calistirButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Sütun Sayısı:");

        jLabel6.setText("Nöron Sayısı:");

        jLabel2.setText("9");

        jLabel3.setText("768");

        jLabel7.setText("enerji verimliliği veri-seti.xlsx");
        jLabel7.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calistirButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(25, 25, 25)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel6)))
                        .addGap(29, 29, 29)
                        .addComponent(noronSayisiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(noronSayisiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(calistirButton)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void calistirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calistirButtonActionPerformed
        // TODO add your handling code here:
        dosyaYolu();
        islemler();
    }//GEN-LAST:event_calistirButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Arayuz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Arayuz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Arayuz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Arayuz.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Arayuz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calistirButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTextField noronSayisiTextField;
    // End of variables declaration//GEN-END:variables
}
