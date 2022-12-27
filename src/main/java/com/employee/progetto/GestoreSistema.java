package com.employee.progetto;

import com.employee.progetto.Utils.DBMS;

import java.time.*;

public class GestoreSistema {
    public void controlloData(LocalDate now){
        LocalDate dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
        Period period = Period.between(dataInizioTrimestre,now);
       while(period.getYears()>0) {
           DBMS.setDataInizioTrimestre(dataInizioTrimestre.plusMonths(9));
           dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
           period = Period.between(dataInizioTrimestre,now);
       }if(period.getMonths() >= 3) {
            DBMS.setDataInizioTrimestre(dataInizioTrimestre.plusMonths(3));
            generaTurni();
        }
    }
    public static void generaTurni(){
        //Almeno 5 impiegati per funzionare
        int numImpiegati = DBMS.getNumPersonale();
        int servizio1 = (int) Math.floor(numImpiegati/2.5);
        int servizio2 = numImpiegati/3;
        int servizio3 = numImpiegati/4;
        int servizio4 = numImpiegati - servizio1-servizio2-servizio3;
        if(servizio3 > (servizio4+4)){
            servizio3 -= 2;
            servizio4 += 2;
        }
        System.out.println("Servizio 1: " + servizio1);
        System.out.println("Servizio 2: " + servizio2);
        System.out.println("Servizio 3: " + servizio3);
        System.out.println("Servizio 4: " + servizio4);
    }
}
