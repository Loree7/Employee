package com.employee.progetto;

import com.employee.progetto.Utils.DBMS;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestoreSistema {
    public void controlloData(LocalDate now){
        LocalDate dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
        dataInizioTrimestre = LocalDate.parse("2022-12-01");
        now = LocalDate.parse("2023-03-01");
        System.out.println(dataInizioTrimestre);
        Period period = Period.between(dataInizioTrimestre,now);
       while(period.getYears()>0) { //in realtà non serve se inserisci una dataInizioTrimestre normale
           DBMS.setDataInizioTrimestre(dataInizioTrimestre.plusYears(1));
           dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
           period = Period.between(dataInizioTrimestre,now);
       }
       if(period.getMonths() >= 3) {
            DBMS.setDataInizioTrimestre(dataInizioTrimestre.plusMonths(3));
            generaTurni();
        }
    }
    public static void generaTurni(){
        List<Integer> numImpiegati = DBMS.getNumImpiegati();
        List<List<Integer>> impiegati = DBMS.getImpiegati();
        for(int i=3;i>0;i--){
            if(numImpiegati.get(0)<numImpiegati.get(i)){
                int k = numImpiegati.get(i) - numImpiegati.get(0);
                numImpiegati.set(0,numImpiegati.get(0)+k);
                numImpiegati.set(i,numImpiegati.get(i)-k);
                List<Integer> l = new ArrayList<>();
                for(int j=0;j<k;j++) {
                    l.add(impiegati.get(i).get(j));
                    impiegati.get(i).remove(j);
                }
                for(Integer integer:l)
                    impiegati.get(0).add(integer);
            }
        }
        for(List<Integer> l : impiegati){
            for(Integer i : l);
                //DBMS.inserisciTurno("06:00:00");
        }
    }
}
