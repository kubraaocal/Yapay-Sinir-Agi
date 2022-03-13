/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yapaysiniragi;

import java.util.ArrayList;
import static yapaysiniragi.AraCikisKatmanIslemleri.guncelAgirlik;
import static yapaysiniragi.AraCikisKatmanIslemleri.yamuksm;
import static yapaysiniragi.Arayuz.guncelAgirlikGiris;
import static yapaysiniragi.GirisHesaplamalari.noronDizi;
import static yapaysiniragi.GirisHesaplamalari.randomSatir;
import static yapaysiniragi.GirisHesaplamalari.agirlikDegerleri;
import static yapaysiniragi.Arayuz.noronSayisi;
import static yapaysiniragi.Arayuz.sutunSayisi;
import static yapaysiniragi.Arayuz.ogrenmeKatsayisi;
import static yapaysiniragi.Arayuz.momentum;

/**
 *
 * @author kubra
 */
public class GirisAraKatmanIslemleri {

    Hesaplamalar hesaplamalar = new Hesaplamalar();
    ArrayList<Double> deltaAgirlik = new ArrayList<>();
    ArrayList<Double> oncekiDeltaAgirlik = new ArrayList<>();
    ArrayList<Double> oncekiAgirlik = new ArrayList<>();
    ArrayList<Double> girisGuncelAgirlik = new ArrayList<>();
    ArrayList<Double> yamuks = new ArrayList<>();
    ArrayList<Double> turevliNoron = new ArrayList<>();

    public GirisAraKatmanIslemleri() {
        oncekiAgirlik = sifirUretmeAgirlik();
        oncekiDeltaAgirlik = sifirUretmeAgirlik();
    }

    void girisKatmanAraKatman() {
        for (int i = 0; i < noronSayisi; i++) {
            turevliNoron.add(hesaplamalar.sigmoidTurev(noronDizi.get(i)));
        }
        
        for (int i = 0; i < noronSayisi; i++) {
            //hata değeri normalde diziymiş burada dizi olamdan denedim.
            yamuks.add(turevliNoron.get(i) * yamuksm * guncelAgirlik.get(i));//buradaki formül farklı
        }

        int sayac = 0;
        for (int i = 0; i < yamuks.size(); i++) {
            for (int j = 0; j < sutunSayisi - 1; j++) {
                deltaAgirlik.add((yamuks.get(i) * ogrenmeKatsayisi * randomSatir.get(j)) + (momentum * oncekiDeltaAgirlik.get(sayac)));
                sayac++;
            }
        }
        for (int i = 0; i < (sutunSayisi - 1) * noronSayisi; i++) {
            girisGuncelAgirlik.add(deltaAgirlik.get(i) + agirlikDegerleri.get(i));
        }
        //System.out.println("giris guncel: "+girisGuncelAgirlik);

    }

    void atamaIslemleri() {
        oncekiDeltaAgirlik.clear();
        oncekiDeltaAgirlik = (ArrayList<Double>) deltaAgirlik.clone();
        deltaAgirlik.clear();

        agirlikDegerleri.clear();
        agirlikDegerleri = (ArrayList<Double>) girisGuncelAgirlik.clone();
        girisGuncelAgirlik.clear();
        guncelAgirlikGiris.addAll(agirlikDegerleri);

        turevliNoron.clear();
        yamuks.clear();

    }

    ArrayList<Double> sifirUretmeAgirlik() {
        ArrayList<Double> arrayList = new ArrayList<>();
        for (int i = 0; i < (sutunSayisi - 1) * noronSayisi; i++) {
            arrayList.add(0.0);
        }
        return arrayList;
    }

}
