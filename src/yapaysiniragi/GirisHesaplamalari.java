/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yapaysiniragi;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static yapaysiniragi.Arayuz.sutunSayisi;
import static yapaysiniragi.Arayuz.noronSayisi;
import static yapaysiniragi.HesaplamaVeriler.egitimVeriSeti;
import static yapaysiniragi.Arayuz.egitimSeti;

/**
 *
 * @author kubra
 */
public class GirisHesaplamalari {

    static int randomSayi = 0;//sıralı satır
    static ArrayList<Double> randomSatir = new ArrayList<>();
    static ArrayList<Double> noronDizi = new ArrayList<>();
    static ArrayList<Double> agirlikDegerleri = new ArrayList<>();
    static ArrayList<Double> biasDegerleri = new ArrayList<>();
    ArrayList<Double> gecici = new ArrayList<>();
    static double gercekHata = 0;

    Hesaplamalar hesaplamalar = new Hesaplamalar();

    public GirisHesaplamalari() {//Bu değerler bu class oluşturulduğunda bir kere oluşacak
        biasDegerleri = hesaplamalar.girisBiasDegerleri(noronSayisi);
        agirlikDegerleri = hesaplamalar.girisAgirlikDegerleri(sutunSayisi, noronSayisi);
        //System.out.println("ağırlık değerleri: " + agirlikDegerleri);
        //System.out.println("bias değerleri: " + biasDegerleri);
    }

    void ilkGelenVeri() {
        //randomSayi = ThreadLocalRandom.current().nextInt(0, egitimSeti);
        //System.out.println("random seçilen sayı: " + randomSayi);
        for (int j = 0; j < sutunSayisi - 1; j++) {
            randomSatir.add(egitimVeriSeti[randomSayi][j]);
        }
        gercekHata = egitimVeriSeti[randomSayi][sutunSayisi - 1];

        //System.out.println("rastgele seçilen satır: " + randomSatir);
        //Random ağırlık değerleri ile çarpıldı
        //System.out.println("Agirlik değerleri--------------: "+agirlikDegerleri);
        int sayac = 0;
        double toplam = 0;
        for (int i = 0; i < noronSayisi; i++) {
            for (int j = 0; j < sutunSayisi - 1; j++) {
                toplam += randomSatir.get(j) * agirlikDegerleri.get(sayac);
                sayac++;
            }
            toplam += biasDegerleri.get(i);
            //System.out.println("toplam simoidsiz" + toplam);
            toplam = hesaplamalar.sigmoid(toplam);
            noronDizi.add(toplam);
            toplam = 0;
        }
        //System.out.println("sigmoidli arakatman:" + noronDizi);
        //SİGMOİDLENMİŞ DEĞERLERİM ARTIK NORON İÇERİSİNDE
        //System.out.println("noron dizi ağırlık çarpılmış: " + noronDizi);
        //sayac = 0;
        //toplam = 0;
        //System.out.println(" ");
        //Random bias değerleri eklendi
        /*for (int i = 0; i < noronSayisi; i++) {
            toplam = noronDizi.get(i) + biasDegerleri.get(i);
            gecici.add(i, toplam);
            toplam = 0;
        }
        noronDizi.clear();
        noronDizi = (ArrayList<Double>) gecici.clone();
        gecici.clear();*/
        //System.out.println("noron dizi bias değeri eklenmiş: " + noronDizi);
        //Nöronların içindeki değerlere sigmoid uygulandı
        /*for (int i = 0; i < noronSayisi; i++) {
            gecici.add(i, hesaplamalar.sigmoid(noronDizi.get(i)));
        }
        noronDizi.clear();
        noronDizi = (ArrayList<Double>) gecici.clone();
        gecici.clear();*/
        //System.out.println("noron dizi sigmoid uygulanmış: " + noronDizi);
    }

}
