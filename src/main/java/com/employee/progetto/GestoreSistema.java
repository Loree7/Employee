package com.employee.progetto;

import com.employee.progetto.Utils.DBMS;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestoreSistema {
    public void controlloData(LocalDate now){
        calcolaStipendio();
        LocalDate dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
        //se si vuole simulare usare le 2 righe di codice
        //dataInizioTrimestre = LocalDate.parse("2022-12-01");
        //now = LocalDate.parse("2023-03-01");
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
       if(period.getMonths() >= 1)
           calcolaStipendio();
    }
    public void generaTurni(){
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
        HashMap<String,List<List<LocalDate>>> astensioni = DBMS.getAstensioni(impiegati,false);
        LocalDate dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
        LocalDate giorno = dataInizioTrimestre;
        Period period;
        boolean inAstensione = false;
        do {
            for (int i = 0; i < impiegati.size(); i++) {
                for (int j = 0; j < impiegati.get(i).size(); j++) {
                    if(!(giorno.getDayOfWeek().name().equals("SATURDAY") || giorno.getDayOfWeek().name().equals("SUNDAY"))) {
                        //se la matricola è dentro le matricole che hanno delle astensioni
                        if (astensioni.containsKey(impiegati.get(i).get(j))) {
                            //per ogni lista che contiene data inizio e fine di ogni astensione di quella matricola
                            for (List<LocalDate> l : astensioni.get(impiegati.get(i).get(j))) {
                                //se il giorno non è prima l'inizio astensione o dopo la fine dell'astensione allora è in astensione
                                if (!(giorno.isBefore(l.get(0)) || giorno.isAfter(l.get(1)))) {
                                    inAstensione = true;
                                    break;
                                }
                            }
                        }
                        if (!inAstensione) {
                            if (j % 2 == 0)
                                DBMS.inserisciTurno(LocalTime.parse("06:00:00"), LocalTime.parse("14:00:00")
                                        , giorno, (i + 1), impiegati.get(i).get(j).toString());
                            else
                                DBMS.inserisciTurno(LocalTime.parse("14:00:00"), LocalTime.parse("22:00:00")
                                        , giorno, (i + 1), impiegati.get(i).get(j).toString());
                        }
                        inAstensione = false;
                    }
                }
            }
            giorno = giorno.plusDays(1);
            period = Period.between(dataInizioTrimestre,giorno);
        }while(period.getMonths()<3);
    }
    public void calcolaStipendio(){
        LocalDate now = LocalDate.now();
        HashMap<String,List<List<LocalDate>>> astensioni = DBMS.getAstensioni(DBMS.getImpiegati(),true);
        for(Map.Entry<String,List<List<LocalDate>>> m : astensioni.entrySet()){
           for(List<LocalDate> l : m.getValue()){
               LocalDate data_fine = l.get(1);
               while(l.get(1).getYear()>now.getYear()){
                   l.set(1,l.get(1).plusDays(-1));
               }
               if(l.get(1).getMonthValue()>=now.getMonthValue())
                   l.set(1,now.plusMonths(-1).withDayOfMonth(now.plusMonths(-1).lengthOfMonth()));
               if(data_fine.isAfter(now.plusMonths(-1).withDayOfMonth(now.plusMonths(-1).lengthOfMonth())))
                    DBMS.inserisciAstensioni(now.withDayOfMonth(1),data_fine,m.getKey(),'ferie');
            }
        }
        for(Map.Entry<String,List<List<LocalDate>>> m : astensioni.entrySet()){
            System.out.println(m.getKey() + " " + m.getValue());
        }
        //int giorniAstensioni = ;
        //int stipendio = oreServizio1*14+oreServizio2*12+oreServizio3*10+oreServizio4*8 + giorniAstensioni*8*8;
    }
}
