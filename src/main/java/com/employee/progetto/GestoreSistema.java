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
        //simulazione :
        //now = LocalDate.parse("2022-12-01");
        //dataInizioTrimestre = LocalDate.parse("2022-12-01");
        Period period = Period.between(dataInizioTrimestre,now);
        while(period.getYears()>0) { //in realtà non serve se inserisci una dataInizioTrimestre normale
           DBMS.setDataInizioTrimestre(dataInizioTrimestre.plusMonths(9));
           dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
           period = Period.between(dataInizioTrimestre,now);
        }
        if(period.getMonths() >= 3) {
            DBMS.setDataInizioTrimestre(dataInizioTrimestre.plusMonths(3));
            generaTurni();
        }
        if(now.getDayOfMonth()==1)
           calcolaStipendio();
    }
    public void generaTurni(){
        System.out.println("Generazione Turni");
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
        System.out.println("Calcolo stipendi");
        LocalDate now = LocalDate.now();
        //simulazione :
        //now = LocalDate.parse("2022-12-01");
        List<List<Integer>> impiegati = DBMS.getImpiegati();
        HashMap<String,List<List<LocalDate>>> astensioni = DBMS.getAstensioni(impiegati,true);
        HashMap<String,Integer> giorniAstensioni = new HashMap<>();
        for(Map.Entry<String,List<List<LocalDate>>> m : astensioni.entrySet()){
            giorniAstensioni.put(m.getKey(),0);
            //per ogni astensione
           for(List<LocalDate> l : m.getValue()){
               LocalDate data_fine = l.get(1);
               //se l'anno della data di fine è maggiore dell'anno dell'anno corrente
               // o se il mese della data di fine è maggiore del mese dell'anno corrente
               if(l.get(1).getYear()>now.getYear() || l.get(1).getMonthValue()>=now.getMonthValue()){
                   // la data di fine diventa l'ultimo giorno del mese di cui si calcola lo stipendio
                   l.set(1,now.plusMonths(-1).withDayOfMonth(now.plusMonths(-1).lengthOfMonth()));
                   // aggiorno il record mettendo come data d'inizio il primo giorno del mese dopo
                   LocalDate nuova_data_inizio = now.withDayOfMonth(1);
                   DBMS.aggiornaAstensione(nuova_data_inizio,data_fine,m.getKey());
               }else // se le astensioni rientrano nel mese le cancello semplicemente
                   DBMS.cancellaAstensione(m.getKey(),data_fine);
               int giorni = giorniAstensioni.get(m.getKey()) + Period.between(l.get(0),l.get(1)).getDays();
               giorniAstensioni.put(m.getKey(),giorni);
               //2022-10-03 - 2023-01-03 diventerà 2022-10-03 - 2022-11-30 e aggiungo il record 2022-12-01 - 2023-01-03
               //il prossimo mese, 2022-12-01 - 2023-01-03 dienterà 2022-12-01 - 2022-12-31 -> 2023-01-01 - 2023-01-03 fine
            }
        }
        for(List<Integer> l : impiegati) {
            for (Integer i : l) {
                int stipendio = 0;
                List<Integer> ore = DBMS.getOre(i.toString());
                List<Integer> gratifiche = DBMS.getGratifiche();
                if(giorniAstensioni.containsKey(i.toString())) //se la matricola ha delle astensioni
                    stipendio += giorniAstensioni.get(i.toString()) * 8 * gratifiche.get(impiegati.indexOf(l)); // astensioni * 8 ore * gratifica
                stipendio += ore.get(0)*gratifiche.get(0) + ore.get(1)*gratifiche.get(1) + ore.get(2)*gratifiche.get(2) + ore.get(3)*gratifiche.get(3);
                DBMS.inserisciStipendio(i.toString(),stipendio);
            }
        }
    }
}
