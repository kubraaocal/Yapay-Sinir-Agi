/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yapaysiniragi;

/**
 *
 * @author kubra
 */

import java.util.ArrayList;
import static yapaysiniragi.AraCikisKatmanIslemleri.biasDegeri;
import static yapaysiniragi.Arayuz.guncelAgirlikCikis;
import static yapaysiniragi.Arayuz.guncelAgirlikGiris;
import static yapaysiniragi.Arayuz.sutunSayisi;
import static yapaysiniragi.Arayuz.noronSayisi;
import static yapaysiniragi.GirisHesaplamalari.biasDegerleri;
import static yapaysiniragi.HesaplamaVeriler.testVeriSeti;

public class TestVerileri {
    int satirSayisi=0,sayacGiris=0,sayacCikis=0;
    double testCikisNoron=0,tahminEdilenHataDegeri=0;
    static double testHataDegeri=0,cikisGercekHata=0;
    ArrayList<Double> secilenSatir=new ArrayList<>();
    ArrayList<Double> testNoron=new ArrayList<>();
    Hesaplamalar hesaplamalar=new Hesaplamalar();
    
    void testVerileri(){
        for (int j = 0; j < sutunSayisi - 1; j++) {
            secilenSatir.add(testVeriSeti[satirSayisi][j]);
        }
        cikisGercekHata = testVeriSeti[satirSayisi][sutunSayisi - 1];
        
        double toplam=0;
        for (int i = 0; i < noronSayisi; i++) {
            for (int j = 0; j < sutunSayisi - 1; j++) {
                toplam += secilenSatir.get(j) * guncelAgirlikGiris.get(sayacGiris);
                
                sayacGiris++;
            }
            
            toplam += biasDegerleri.get(i);
            //System.out.println("toplam simoidsiz" + toplam);
            toplam = hesaplamalar.sigmoid(toplam);
            testNoron.add(toplam);
            toplam = 0;
        }

        testCikisNoron = 0;
        
        for (int i = 0; i < noronSayisi; i++) {
            testCikisNoron += testNoron.get(i) * guncelAgirlikCikis.get(sayacCikis);
            sayacCikis++;
        }
        testCikisNoron = testCikisNoron + biasDegeri;
        tahminEdilenHataDegeri = hesaplamalar.sigmoid(testCikisNoron);
        testHataDegeri = cikisGercekHata - tahminEdilenHataDegeri;
        
        //System.out.println("Gerçek Hata: "+cikisGercekHata);
        //System.out.println("Tahmin Hata: "+tahminEdilenHataDegeri);
        
                
        testNoron.clear();
        satirSayisi+=1;
    }
    
}
