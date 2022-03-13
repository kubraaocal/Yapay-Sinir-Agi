/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yapaysiniragi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import static yapaysiniragi.Arayuz.egitimSeti;

/**
 *
 * @author kubra
 */
public class Hesaplamalar {
    //bias
    //ağırlık
    //sigmoid
    //türevli sigmoid

    double randomSayiUretme() {
        double randomSayi = ThreadLocalRandom.current().nextDouble(0, 1);
        return randomSayi;

    }

    ArrayList<Double> girisAgirlikDegerleri(int sutunSayisi, int noronSayisi) {
        ArrayList<Double> agirlik = new ArrayList<>();
        for (int i = 0; i < ((sutunSayisi - 1) * noronSayisi); i++) {
            agirlik.add(randomSayiUretme());
        }
        //System.out.println("giriş ağırlık değerleri: "+agirlik);
        return agirlik;
    }

    ArrayList<Double> girisBiasDegerleri(int noronSayisi) {
        ArrayList<Double> bias = new ArrayList<>();
        for (int i = 0; i < noronSayisi; i++) {
            bias.add(randomSayiUretme());
        }
        //System.out.println("giriş bias değerleri: "+bias);
        return bias;
    }

    ArrayList<Double> cikisAgirlikDegerleri(int noronSayisi) {
        ArrayList<Double> cikisAgirlik = new ArrayList<>();
        for (int i = 0; i < noronSayisi; i++) {
            cikisAgirlik.add(randomSayiUretme());
        }
        //System.out.println("giriş bias değerleri: "+bias);
        return cikisAgirlik;
    }

    double sigmoid(double deger) {
        return (double) (1 / (1 + Math.exp(-deger)));
    }

    double sigmoidTurev(double tahminDeger) {
        return tahminDeger * (1 - tahminDeger);
    }

    ArrayList<Double> normalizasyonHesaplama(ArrayList<Double> gecici) {
        double deger;
        double tutucu = 0;
        double min = 0, max = 0;
        ArrayList<Double> normalizeEdilmisDeger = new ArrayList<>();
        min = gecici.indexOf(Collections.min(gecici));
        min = gecici.get((int) min);
        max = gecici.indexOf(Collections.max(gecici));
        max = gecici.get((int) max);
        //System.out.println(min+" "+max);
        for (int i = 0; i < gecici.size(); i++) {
            deger = gecici.get(i);
            tutucu = (deger - min) / (max - min);
            normalizeEdilmisDeger.add(tutucu);
        }
        return normalizeEdilmisDeger;
    }

    double mseHesapla(ArrayList<Double> hata) {
        double toplam = 0;
        double sonuc = 0;
        for (int i = 0; i < hata.size(); i++) {
            toplam += hata.get(i);
        }
        sonuc = toplam / 50;
        return sonuc;
    }

    public double mapeHesapla(ArrayList<Double> hataDegeri, ArrayList<Double> gercekHata,double sayi) {
        double toplam = 0;
        double hata = 0;
        for (int i = 0; i < hataDegeri.size(); i++) {
            if (gercekHata.get(i) != 0.0) {
                toplam += (hataDegeri.get(i) / gercekHata.get(i));
            } else {
               hata++;
            }
        }
        return (toplam / sayi) * 100;

    }

}
