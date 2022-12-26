package com.employee.progetto;

import com.employee.progetto.Utils.DBMS;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class GestoreSistema {
    public void controlloData(Instant now){
        LocalDate dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
        Instant instant = dataInizioTrimestre.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Duration duration = Duration.between(instant,now);
        if (duration.toDays() >= 90) {
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
