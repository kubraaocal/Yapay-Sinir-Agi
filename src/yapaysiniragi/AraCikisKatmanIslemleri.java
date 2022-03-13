/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yapaysiniragi;

import java.math.BigDecimal;
import java.util.ArrayList;
import static yapaysiniragi.Arayuz.guncelAgirlikCikis;
import static yapaysiniragi.Arayuz.noronSayisi;
import static yapaysiniragi.Arayuz.ogrenmeKatsayisi;
import static yapaysiniragi.Arayuz.momentum;
import static yapaysiniragi.GirisHesaplamalari.noronDizi;
import static yapaysiniragi.GirisHesaplamalari.gercekHata;

/**
 *
 * @author kubra
 */
public class AraCikisKatmanIslemleri {

    //Bir tane çıkışımız olduğu için bira tane double değişken tanımladım
    static double cikisNoron = 0;
    static double yamuksm = 0;
    static double tahminEdilenHataDegeri = 0, hataDegeri = 0;
    ArrayList<Double> agirlikDegerleri = new ArrayList<>();
    static double biasDegeri = 0;
    ArrayList<Double> oncekiAgirlikDegerleri = new ArrayList<>();
    ArrayList<Double> oncekiDeltaAgirlik = new ArrayList<>();
    ArrayList<Double> deltaAgirlik = new ArrayList<>();
    static ArrayList<Double> guncelAgirlik = new ArrayList<>();
    static ArrayList<Double> hataDegerleri = new ArrayList<>();
    static ArrayList<BigDecimal> mapeHataDegerleri = new ArrayList<>();
    Hesaplamalar hesaplamalar = new Hesaplamalar();

    public AraCikisKatmanIslemleri() {
        //bir kerelik random ağrılık değeri atadık
        agirlikDegerleri = hesaplamalar.cikisAgirlikDegerleri(noronSayisi);//ara katmandaki noron sayısı kadar ağırlık değeri
        biasDegeri = hesaplamalar.randomSayiUretme();//cikis kadar bias ekle
        oncekiDeltaAgirlik = sifirUretmeAgirlik();//delta oncekimiz ilk başta yok o yüzden 1 kerelik 0 üretildi
    }

    void araKatmanCikisKatmani() {
        cikisNoron = 0;

        //BURASI ARA ÇIKIŞ KATMAN ARASINDAKİ O İLK İŞLEM
        for (int i = 0; i < noronSayisi; i++) {
            cikisNoron += noronDizi.get(i) * agirlikDegerleri.get(i);
        }
        cikisNoron = cikisNoron + biasDegeri;
        tahminEdilenHataDegeri = hesaplamalar.sigmoid(cikisNoron);
        hataDegeri = gercekHata - tahminEdilenHataDegeri;
        //System.out.println("Tahmin: "+tahminEdilenHataDegeri);
        //System.out.println("Gerçek: "+gercekHata);

        yamuksm = hesaplamalar.sigmoidTurev(tahminEdilenHataDegeri) * hataDegeri;

        for (int i = 0; i < noronDizi.size(); i++) {
            deltaAgirlik.add((yamuksm * ogrenmeKatsayisi * noronDizi.get(i)) + (oncekiDeltaAgirlik.get(i) * momentum));
        }

        for (int i = 0; i < deltaAgirlik.size(); i++) {
            guncelAgirlik.add(deltaAgirlik.get(i) + agirlikDegerleri.get(i));
        }
        
        

    }

    void atamaIslemeleri() {
        oncekiDeltaAgirlik.clear();
        oncekiDeltaAgirlik = (ArrayList<Double>) deltaAgirlik.clone();
        deltaAgirlik.clear();
        agirlikDegerleri.clear();
        agirlikDegerleri = (ArrayList<Double>) guncelAgirlik.clone();
        guncelAgirlik.clear();
        guncelAgirlikCikis.addAll(agirlikDegerleri);
    }

    ArrayList<Double> sifirUretmeAgirlik() {
        ArrayList<Double> arrayList = new ArrayList<>();
        for (int i = 0; i < noronSayisi; i++) {
            arrayList.add(0.0);
        }
        return arrayList;
    }

}
