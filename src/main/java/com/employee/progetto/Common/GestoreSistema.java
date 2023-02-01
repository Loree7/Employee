package com.employee.progetto.Common;

import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.MailUtils;

import java.time.*;
import java.util.*;

public class GestoreSistema {
    public void controlloData(LocalDate now){
        LocalDate dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
        //simulazione genera turni:
        //dataInizioTrimestre = now.plusMonths(-3);
        Period period = Period.between(dataInizioTrimestre,now);
        while(period.getYears()>0) { //in realtà non serve se inserisci una dataInizioTrimestre normale
           DBMS.setDataInizioTrimestre(dataInizioTrimestre.plusMonths(9));
           dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
           period = Period.between(dataInizioTrimestre,now);
        }
        if(period.getMonths() >= 3) {
            DBMS.setDataInizioTrimestre(dataInizioTrimestre.plusMonths(3).plusDays(1));
            generaTurni();
        }
        //simulazione stipendi
        //calcolaStipendio();
        if(now.getDayOfMonth()==1)
           calcolaStipendio();
    }
    public void controlloOrario(LocalTime now){
        List<String> servizi = DBMS.getServizi();
        for(String s : servizi) {
            if(DBMS.getNumDipendentiInTurno(s)==0)
                DBMS.chiudiServizio(servizi.indexOf(s)+1);
            else
                DBMS.apriServizio(servizi.indexOf(s)+1);
        }
        if(now.equals(LocalTime.parse("23:00:00"))) {
            chiudiServizio();
            controlloServizioAlto();
        }
        if(now.equals(LocalTime.parse("16:00:00")))
            gestioneSciopero();
    }
    public void generaTurni(){
        System.out.println("Generazione Turni");
        List<List<Integer>> impiegati = DBMS.getImpiegati();
        int nImpiegati = 0;
        //calcolo num impiegati
        for(List<Integer> l : impiegati)
            nImpiegati += l.size();
        int giorniRiposo = 1;
        //faccio in modo che almeno ogni servizio ha lo stesso numero di impiegati
        for(List<Integer> l : impiegati){
            while(l.size()<nImpiegati/4){
                int max=0;
                int indice=0;
                //prendo la lista più grande
                for(List<Integer> list : impiegati) {
                    if (max < list.size()) {
                        max = list.size();
                        indice = impiegati.indexOf(list);
                    }
                }
                //sposto un elemento dalla lista più grande
                l.add(impiegati.get(indice).remove(0));
            }
        }
        //faccio in modo che il servizio 1 ha più impiegati
        for(int i=3;i>0;i--){
            //finchè una lista di impiegati ha più impiegati del servizio più alto
            while(impiegati.get(0).size()-1<impiegati.get(i).size() && impiegati.get(i).size()>3){
                impiegati.get(0).add(impiegati.get(i).remove(0));
            }
        }
        int numImpiegatiLiberi = (nImpiegati/7)*giorniRiposo;
        HashMap<String,List<List<LocalDate>>> astensioni = DBMS.getAstensioni(impiegati,false);
        LocalDate dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
        LocalDate giorno = dataInizioTrimestre;
        Period period;
        boolean inAstensione = false;
        List<Integer> matricole = new ArrayList<>();
        for(List<Integer> l : impiegati)
            for(Integer i : l)
                matricole.add(i);
        int indiceImpiegatoLibero=0;
        do {
            int controllo = numImpiegatiLiberi;
            for (int i = 0; i < impiegati.size(); i++) {
                int fasciaOraria = 1;
                for (int j = 0; j < impiegati.get(i).size(); j++) {
                    //se controllo è 0 significa che ho dato già finito di dare i giorni di riposo
                    if(matricole.get(indiceImpiegatoLibero)!=impiegati.get(i).get(j) || controllo==0) {
                        //se la matricola è dentro le matricole che hanno delle astensioni
                        if (astensioni.containsKey(impiegati.get(i).get(j).toString())) {
                            //per ogni lista che contiene data inizio e fine di ogni astensione di quella matricola
                            for (List<LocalDate> l : astensioni.get((impiegati.get(i).get(j)).toString())) {
                                //se il giorno non è prima l'inizio astensione o dopo la fine dell'astensione allora è in astensione
                                if (!(giorno.isBefore(l.get(0)) || giorno.isAfter(l.get(1)))) {
                                    inAstensione = true;
                                    break;
                                }
                            }
                        }
                        if (!inAstensione) {
                            //se controllo è 0 significa che ho dato già finito di dare i giorni di riposo
                            if (fasciaOraria == 1)
                                DBMS.inserisciTurno(LocalTime.parse("00:00:00"), LocalTime.parse("08:00:00")
                                        , giorno, (i + 1), impiegati.get(i).get(j).toString());
                            if (fasciaOraria == 2)
                                DBMS.inserisciTurno(LocalTime.parse("08:00:00"), LocalTime.parse("16:00:00")
                                        , giorno, (i + 1), impiegati.get(i).get(j).toString());
                            if (fasciaOraria == 3) {
                                DBMS.inserisciTurno(LocalTime.parse("16:00:00"), LocalTime.parse("23:59:59")
                                        , giorno, (i + 1), impiegati.get(i).get(j).toString());
                                fasciaOraria = 0;
                            }
                            fasciaOraria++;
                        }
                        inAstensione = false;
                    }else{
                        if(indiceImpiegatoLibero==matricole.size()-1)
                            indiceImpiegatoLibero=0;
                        else indiceImpiegatoLibero++;
                        controllo--;
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
        //simulazione stipendi:
        //now = now.withDayOfMonth(1);
        List<List<Integer>> impiegati = DBMS.getImpiegati();
        HashMap<String,List<List<LocalDate>>> astensioni = DBMS.getAstensioni(impiegati,true);
        HashMap<String,Integer> giorniAstensioni = new HashMap<>();
        for(Map.Entry<String,List<List<LocalDate>>> m : astensioni.entrySet()){
            giorniAstensioni.put(m.getKey(),0);
            //per ogni astensione
            //prendo tutte le astensioni che finiscono all'inizio o dopo il mese per cui sto calcolando lo stipendio
            //e che iniziano entro la fine del mese - > se prendo 2022-12-01 - 2023-01-xx diventa 2023-12-01 - 2023-12-xx
           for(List<LocalDate> l : m.getValue()){
               LocalDate data_fine = l.get(1);
               LocalDate data_inizio = l.get(0);
               //se la data d'inizio è prima l'inizio del mese per il quale si calcola lo stipendio
               //setto la data di inizio all'inizio del mese
               if(data_inizio.isBefore(now.plusMonths(-1)))
                   l.set(0,now.plusMonths(-1));
               //se l'astensione inizia dopo o all'inizio del mese per cui si calcola lo stipendio
               if(data_inizio.isAfter(now.plusMonths(-1).withDayOfMonth(1))
                       || data_inizio.isEqual(now.plusMonths(-1).withDayOfMonth(1))){
                   //se la data di fine iniziale è entro la fine del mese per cui si calcola lo stipendio elimino il record
                   if(data_fine.isBefore(now))
                       DBMS.cancellaAstensione(m.getKey(),data_fine);
                   //altrimenti aggiorno la data di astensione con la data di inizio che parte dal mese dopo il calcolo del mese e finisce alla data fine iniziale
                   else {
                       //la data di fine diventa l'ultimo giorno del mese per cui si calcola lo stipendio
                       int ultimoGiorno = now.plusMonths(-1).lengthOfMonth();
                       l.set(1,now.plusMonths(-1).withDayOfMonth(ultimoGiorno));
                       DBMS.aggiornaAstensione(now.withDayOfMonth(1), data_fine, m.getKey());
                   }
               }
               int giorni = giorniAstensioni.get(m.getKey()) + Period.between(l.get(0),l.get(1)).getDays()+1;
               giorniAstensioni.put(m.getKey(),giorni);
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
    public void gestioneSciopero(){
        List<String> email = DBMS.getEmailSciopero();
        if(email.size()>0) {
            if (email.size() >= 10) {
                for (String mail : email) {
                    MailUtils.inviaMail("Gentile impiegato (matricola: " + DBMS.getMatricola(mail) + ") la sua richiesta di sciopero e' stata accettata", "Richiesta di sciopero", mail);
                    DBMS.eliminaTurno(DBMS.getMatricola(mail));
                }
            } else {
                for (String mail : email) {
                    MailUtils.inviaMail("Gentile impiegato (matricola: " + DBMS.getMatricola(mail) + ") la sua richiesta di sciopero e' stata rifiutata", "Richiesta di sciopero", mail);
                }
            }
            DBMS.eliminaRichiesteSciopero();
        }
    }
    public void chiudiServizio(){
        int numDipendenti = 0;
        List<Integer> dipendenti = new ArrayList<>();
        List<String> servizi = DBMS.getServizi();
        for(String s : servizi)
            numDipendenti += DBMS.getNumDipendenti(s,LocalDate.now().plusDays(1));
        //devono essere almeno 13 per coprire tutte le fasce orarie
        if(numDipendenti<=12) {
            //DBMS.chiudiServizio(4); non ho bisogno di chiuderlo qui
            List<Integer> turni = DBMS.getTurniServizio(4);
            for(Integer i : turni) {
                for (String s : servizi)
                    dipendenti.add(DBMS.getNumDipendenti(s,LocalDate.now().plusDays(1)));
                int min = Collections.min(dipendenti);
                int id_servizio = 0;
                if (min+1 == dipendenti.get(0)) { //il servizio 1 ne deve avere 1 in più degli altri
                    DBMS.aggiornaServizioTurno(i, 1);
                    id_servizio = 1;
                } else if (min == dipendenti.get(1)) {
                    DBMS.aggiornaServizioTurno(i, 2);
                    id_servizio = 2;
                }else {
                    DBMS.aggiornaServizioTurno(i, 3);
                    id_servizio = 3;
                }
                String email = DBMS.getEmail(i);
                MailUtils.inviaMail("Gentile impiegato (matricola: " + DBMS.getMatricola(email) + ") a causa di mancanza di personale, le notifichiamo il suo spostamento di servizio (" + DBMS.getServizio(id_servizio) + ")", "Spostamento di servizio", email);
                dipendenti.clear();
            }
        }else
            DBMS.apriServizio(4); // teoricamente non ho bisogno manco di aprirlo qui
    }
    public void controlloServizioAlto(){
        List<Integer> dipendenti = new ArrayList<>();
        for(String s : DBMS.getServizi())
            dipendenti.add(DBMS.getNumDipendenti(s,LocalDate.now().plusDays(1)));
        int min = Collections.min(dipendenti);
        while(dipendenti.get(0)==min){//se il servizio a priorità più alta ha meno dipendenti glieli metto da quello con più dipendenti
            int max = Collections.max(dipendenti);
            List<Integer> turni = DBMS.getTurniServizio(dipendenti.indexOf(max)+1);
            DBMS.aggiornaServizioTurno(turni.get(0),1); //faccio uno alla volta
            String email = DBMS.getEmail(turni.get(0));
            MailUtils.inviaMail("Gentile impiegato (matricola: " + DBMS.getMatricola(email) + ") a causa di mancanza di personale, le notifichiamo il suo spostamento di servizio (" + DBMS.getServizio(1) + ")", "Spostamento di servizio", email);
            dipendenti.clear();
            for(String s : DBMS.getServizi())
                dipendenti.add(DBMS.getNumDipendenti(s,LocalDate.now().plusDays(1)));
        }
    }
}