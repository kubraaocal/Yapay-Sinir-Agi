/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yapaysiniragi;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static yapaysiniragi.Arayuz.dosyaYolu;
import static yapaysiniragi.Arayuz.egitimSeti;
import static yapaysiniragi.Arayuz.satirSayisi;
import static yapaysiniragi.Arayuz.sutunSayisi;
import static yapaysiniragi.Arayuz.testSeti;

/**
 *
 * @author kubra
 */
public class HesaplamaVeriler {

    ArrayList<Double> veriler = new ArrayList<>();
    ArrayList<Double> gecici = new ArrayList<>();
    Double[][] tumVeriler = new Double[satirSayisi][sutunSayisi];
    Double[][] normalizeEdilmisVeriler = new Double[satirSayisi][sutunSayisi];
    static Double[][] egitimVeriSeti = new Double[egitimSeti][sutunSayisi];
    static Double[][] testVeriSeti = new Double[testSeti][sutunSayisi];

    Hesaplamalar hesaplamalar = new Hesaplamalar();

    void verileriAl() {
        //benim y değerlerimde normalize edildi
        IDosyaTipi dosyaTipi = new Excel();
        veriler = dosyaTipi.verileriCek(dosyaYolu);

        int sayac = 0;
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                tumVeriler[i][j] = veriler.get(sayac);
                sayac++;
            }
        }
        /*for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(tumVeriler[i][j]);
                System.out.print(" ");
            }
            System.out.println(" ");
        }*/
        veriler.clear();
        for (int j = 0; j < sutunSayisi; j++) {
            for (int i = 0; i < satirSayisi; i++) {
                gecici.add(tumVeriler[i][j]);
            }
            veriler.addAll(hesaplamalar.normalizasyonHesaplama(gecici));
            gecici.clear();
        }
        int s = 0;
        for (int j = 0; j < sutunSayisi; j++) {
            for (int i = 0; i < satirSayisi; i++) {
                normalizeEdilmisVeriler[i][j] = veriler.get(s);
                s++;
            }
        }
        /*System.out.println("-------------------------------------------------------------------------------");
         for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(normalizeEdilmisVeriler[i][j]);
                System.out.print(" ");
            }
            System.out.println(" ");
        }*/
        veriler.clear();
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                veriler.add(normalizeEdilmisVeriler[i][j]);
            }
        }


        /*for (int i = 0; i < egitimSeti; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                if(j<sutunSayisi-1){
                    egitimGirisVerileri[i][j]=egitimVeriSeti[i][j];
                }else{
                    egitimCikisVerileri[i] = egitimVeriSeti[i][j];
                }
            }
        }
        System.out.println("-------------------------------------------------------------------------------");
        for (int i = 0; i < egitimSeti; i++) {
            for (int j = 0; j < sutunSayisi - 1; j++) {
                System.out.print(egitimGirisVerileri[i][j]);
                System.out.print(" ");
            }
            System.out.println(" ");
        }*/
    }
    ArrayList<Integer> randomUretilenDizi = new ArrayList<>();
    void randomUret() {
        for (int a = 0; a < satirSayisi;) {
            int sayi = ThreadLocalRandom.current().nextInt(0, satirSayisi);
            if (!randomUretilenDizi.contains(sayi)) {
                randomUretilenDizi.add(sayi);
                a++;
            } else {
                continue;
            }
        }
    }
    int adet = 0;
    void veriSetleriniOlustur() {
        randomUret();
        for (int i = 0; i < satirSayisi; i++) {
            int randomUretilenSayi = randomUretilenDizi.get(i);
            //System.out.println("random sayı: " + randomUretilenSayi);
            for (int j = 0; j < sutunSayisi; j++) {
                if (i < egitimSeti) {
                    egitimVeriSeti[i][j] = normalizeEdilmisVeriler[randomUretilenSayi][j];
                } else {
                    testVeriSeti[adet][j] = normalizeEdilmisVeriler[randomUretilenSayi][j];
                    if (j == sutunSayisi - 1) {
                        adet++;
                    }
                }
            }
        }
        
        /*for (int i = 0; i < testSeti; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(testVeriSeti[i][j]);
                System.out.print(" ");
            }
            System.out.println(" ");
        }
        //System.out.println("egitim boyutu: " + egitimVeriSeti.length);
        System.out.println("test boyutu: " + testVeriSeti.length);*/
    }
}
