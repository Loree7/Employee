package com.employee.progetto.Utils;

import com.employee.progetto.Entity.Amministratore;
import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.Entity.Turno;
import com.employee.progetto.Entity.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBMS {
    public static Connection databaseLink;
    public static Connection getConnection() {
        String databaseName = "dbTeam";
        String databaseUser = "root";
        String databasePassword = "Lorenzo10";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
    private static void erroreComunicazioneDBMS() {
        Utils.creaPannelloErrore("C'è stato un problema durante la comunicazione con la base di dati, riprova");
    }
    public static Utente verificaCredenziali(String matricola, String password) {
        Connection dbConnection = getConnection();
        String vP = "select nome,cognome,ruolo,email,ferie,password from utente where matricola =" + matricola;
        String vC = "SELECT nome,cognome,ruolo,email,ferie FROM utente WHERE matricola = " + matricola + " AND password = '" + password + "'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(vP);
            if(queryResult.next()){
                if(password.equals(queryResult.getString(6)))
                    if (queryResult.getString(3).equals("Amministratore"))
                        return new Amministratore(matricola, queryResult.getString(1), queryResult.getString(2)
                                , queryResult.getString(3), queryResult.getString(4));
                    else return new Impiegato(matricola, queryResult.getString(1), queryResult.getString(2)
                            , queryResult.getString(3), queryResult.getString(4),queryResult.getInt(5));
            }
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static boolean controllaEmail(String email) {
        Connection dbConnection = getConnection();
        String cE = "SELECT count(1) FROM utente WHERE email = '" + email + "'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(cE);
            queryResult.next();
            if (queryResult.getInt(1) == 1)
                return true;
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }
    public static void registraImpiegato(String nome, String cognome, String ruolo, String email, String password) {
        Connection dbConnection = getConnection();
        String rI = "insert into utente (nome,cognome,ruolo,email,password,ferie) " +
                "values ('" + nome + "','" + cognome + "','" + ruolo.toLowerCase() + "','" + email +
                "','" + password + "'," + 28 + ")";
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(rI);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static Impiegato ricercaImpiegato(String matricola) {
        Connection dbConnection = getConnection();
        String rI = "SELECT nome,cognome,ruolo,email,password FROM utente WHERE matricola = '" + matricola + "'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(rI);
            if (queryResult.next()) {
                return new Impiegato(matricola, queryResult.getString(1), queryResult.getString(2)
                        , queryResult.getString(3), queryResult.getString(4), queryResult.getString(5));
            }
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static void elimina(String matricola) {
        Connection dbConnection = getConnection();
        String el = "delete from utente where matricola=" + matricola;
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(el);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static void modifica(String matricola, String nome, String cognome, String ruolo, String email, String password) {
        Connection dbConnection = getConnection();
        String m = "update utente set nome='"+nome+"',cognome='"+cognome+"',ruolo='"+ruolo+"',password='"+password+"' where matricola =" + matricola;
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(m);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static int getMatricola(String email) {
        Connection dbConnection = getConnection();
        String gM = "select matricola from utente where email= '" + email + "'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gM);
            if(queryResult.next())
                return queryResult.getInt(1);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return 0;
    }
    public static List<List<LocalDate>> getPeriodi(){
        List<List<LocalDate>> periodi = new ArrayList<>();
        Connection dbConnection = getConnection();
        String gP = "select data_inizio,data_fine from periodo";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gP);
            while(queryResult.next()){
                List<LocalDate> l = new ArrayList<>();
                l.add(LocalDate.parse(queryResult.getString(1)));
                l.add(LocalDate.parse(queryResult.getString(2)));
                periodi.add(l);
            }
            return periodi;
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static boolean controllaPeriodo(LocalDate data_inizio,LocalDate data_fine){
        List<List<LocalDate>> periodi = DBMS.getPeriodi();
        for(List<LocalDate> l : periodi) {
            if(data_fine.isBefore(l.get(0)) || data_inizio.isAfter(l.get(1)))
                continue;
            return false;
        }
        return true;
    }
    public static boolean controllaFerie(LocalDate data_inizio,LocalDate data_fine,String matricola){
        //mi dice se la matricola è in ferie nelle date inserite
        Connection dbConnection = getConnection();
        String cF = "select id from astensioni where exists (select * from astensioni where data_inizio between '" +
                data_inizio + "' and '" + data_fine + "' or data_fine between '" + data_inizio + "' and '" + data_fine +
                "' and id_impiegato = " + matricola + ")";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(cF);
            if(queryResult.next())
                return true;
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return false;
    }
    public static void inserisciFerie(LocalDate data_inizio,LocalDate data_fine,String matricola,int ferieRimanenti){
        Connection dbConnection = getConnection();
        String iF = "insert into astensioni (data_inizio,data_fine,tipo,id_impiegato) " +
                "values ('" + data_inizio + "','" + data_fine + "','ferie'," + matricola + ")";
        String uF ="update utente set ferie=" + ferieRimanenti + " where matricola =" + matricola;
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(iF);
            statement.executeUpdate(uF);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static String getDataInizioTrimestre(){
        Connection dbConnection = getConnection();
        String gD = "select data from dataTrimestre where id=1";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gD);
            if(queryResult.next())
                return queryResult.getString(1);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static void setDataInizioTrimestre(LocalDate dataInizioTrimestre){
        Connection dbConnection = getConnection();
        String sD = "update dataTrimestre set data='" + dataInizioTrimestre + "' where id=1";
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(sD);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static Impiegato getImpiegatoMenoOre(LocalDate giorno){
        Connection dbConnection = getConnection();
        System.out.println(giorno);
        String gI = "select matricola,nome,cognome,ruolo,email,(oreServizio1+oreServizio2+oreServizio3+oreServizio4) as ore " +
                "from utente where ruolo != 'amministratore' and matricola not in " +
                "(select id_impiegato from turno where data = '"+giorno+"') and matricola not in" +
                "(select id_impiegato from astensioni where '"+giorno+"' between data_inizio and data_fine)" +
                "order by ore limit 1";
        System.out.println(gI);
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gI);
            if(queryResult.next())
                return new Impiegato(queryResult.getString(1),queryResult.getString(2)
                        ,queryResult.getString(3),queryResult.getString(4),queryResult.getString(5));
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static List<String> getServizi(){
        List<String> servizi = new ArrayList<>();
        Connection dbConnection = getConnection();
        String gS = "select nome from servizio";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gS);
            while(queryResult.next())
                servizi.add(queryResult.getString(1).toLowerCase());
            return servizi;
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static void inserisciTurno(LocalTime ora_inizio,LocalTime ora_fine,LocalDate giorno,int id_servizio,String id_impiegato){
        Connection dbConnection = getConnection();
        String iT = "insert into turno (ora_inizio,ora_fine,data,id_servizio,id_impiegato) " +
                "values ('" + ora_inizio + "','" + ora_fine + "','" + giorno + "'," + id_servizio + "," + id_impiegato + ")";
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(iT);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static List<List<Integer>> getImpiegati(){
        List<List<Integer>> impiegati = new ArrayList<>();
        Connection dbConnection = getConnection();
        String mA = "select matricola from utente where ruolo= 'Alto'";
        String mI = "select matricola from utente where ruolo= 'Intermedio'";
        String mM = "select matricola from utente where ruolo= 'Medio'";
        String mB = "select matricola from utente where ruolo= 'Basso'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet matricole = statement.executeQuery(mA);
            impiegati.add(new ArrayList<>());
            impiegati.add(new ArrayList<>());
            impiegati.add(new ArrayList<>());
            impiegati.add(new ArrayList<>());
            while (matricole.next())
                impiegati.get(0).add(matricole.getInt(1));

            matricole = statement.executeQuery(mI);
            while (matricole.next())
                impiegati.get(1).add(matricole.getInt(1));

            matricole = statement.executeQuery(mM);
            while (matricole.next())
                impiegati.get(2).add(matricole.getInt(1));

            matricole = statement.executeQuery(mB);
            while (matricole.next())
                impiegati.get(3).add(matricole.getInt(1));
            return impiegati;
        }catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static List<Integer> getNumImpiegati() {
        List<Integer> num = new ArrayList<>();
        Connection dbConnection = getConnection();
        String gA = "select count(matricola) from utente where ruolo= 'Alto'";
        String gI = "select count(matricola) from utente where ruolo= 'Intermedio'";
        String gM = "select count(matricola) from utente where ruolo= 'Medio'";
        String gB = "select count(matricola) from utente where ruolo= 'Basso'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet numImpiegati = statement.executeQuery(gA);
            if(numImpiegati.next())
                num.add(numImpiegati.getInt(1));
            numImpiegati = statement.executeQuery(gI);
            if(numImpiegati.next())
                num.add(numImpiegati.getInt(1));
            numImpiegati = statement.executeQuery(gM);
            if(numImpiegati.next())
                num.add(numImpiegati.getInt(1));
            numImpiegati = statement.executeQuery(gB);
            if(numImpiegati.next())
                num.add(numImpiegati.getInt(1));
            return num;
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static HashMap<String,List<List<LocalDate>>> getAstensioni(List<List<Integer>> impiegati,boolean flag){
        HashMap<String,List<List<LocalDate>>> astensioni = new HashMap<>();
        Connection dbConnection = getConnection();
        LocalDate now = LocalDate.now();
        //simulazione :
        //now = LocalDate.parse("2022-12-01");
        try{
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult;
            for(List<Integer> l : impiegati){
                for(Integer i : l){ //per ogni matricola prendo le astensioni e le metto in una lista
                    String gA;
                    if(!flag)
                        gA = "select data_inizio,data_fine from astensioni where id_impiegato=" + i + " and data_fine > '" + now + "'";
                    else gA = "select data_inizio,data_fine from astensioni where id_impiegato=" + i +
                            " and data_fine > '" + now.plusMonths(-1) + "' and data_inizio < '" + now+"'";
                    queryResult = statement.executeQuery(gA);
                    List<List<LocalDate>> temp = new ArrayList<>();
                    if(queryResult.next()) {
                        List<LocalDate> t = new ArrayList<>();
                        t.add(LocalDate.parse(queryResult.getString(1)));
                        t.add(LocalDate.parse(queryResult.getString(2)));
                        temp.add(t);
                        while (queryResult.next()) {
                            List<LocalDate> t2 = new ArrayList<>();
                            t2.add(LocalDate.parse(queryResult.getString(1)));
                            t2.add(LocalDate.parse(queryResult.getString(2)));
                            temp.add(t2);
                        }
                        astensioni.put(i.toString(), temp);
                    }
                }
            }
            return astensioni;
        }catch (Exception e){
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static void aggiornaAstensione(LocalDate data_inizio,LocalDate data_fine,String id_impiegato){
        Connection dbConnection = getConnection();
        String cercaAstensione = "select id from astensioni where id_impiegato = " + id_impiegato + " and data_fine = '"+data_fine+"'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(cercaAstensione);
            if(queryResult.next()) {
                String uA = "update astensioni set data_inizio = '" + data_inizio  + "' where id = " + queryResult.getInt(1);
                statement.executeUpdate(uA);
            }
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static void cancellaAstensione(String id_impiegato,LocalDate data_fine){
        Connection dbConnection = getConnection();
        String cercaAstensione = "select id from astensioni where id_impiegato = " + id_impiegato + " and data_fine = '"+data_fine+"'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(cercaAstensione);
            if(queryResult.next()) {
                String cA = "delete from astensioni where id = " + queryResult.getInt(1);
                statement.executeUpdate(cA);
            }
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static void inserisciStipendio(String id_impiegato,int stipendio){
        Connection dbConnection = getConnection();
        String iS = "insert into stipendio (id_impiegato,stipendio,data) " +
                "values (" + id_impiegato + "," + stipendio + ",'" + LocalDate.now() + "')";
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(iS);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static List<Integer> getOre(String matricola){
        List<Integer> ore = new ArrayList<>();
        Connection dbConnection = getConnection();
        String gO = "select oreServizio1,oreServizio2,oreServizio3,oreServizio4 from utente where matricola="+matricola;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gO);
            if(queryResult.next()) {
                ore.add(queryResult.getInt(1));
                ore.add(queryResult.getInt(2));
                ore.add(queryResult.getInt(3));
                ore.add(queryResult.getInt(4));
            }
            String updateOre = "update utente set oreServizio1=0,oreServizio2=0,oreServizio3=0,oreServizio4=0 where matricola="+matricola;
            statement.executeUpdate(updateOre);
            return ore;
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static List<Integer> getGratifiche(){
        List<Integer> gratifiche = new ArrayList<>();
        Connection dbConnection = getConnection();
        String gG = "select gratifica from servizio";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gG);
            while(queryResult.next())
                gratifiche.add(queryResult.getInt(1));
            return gratifiche;
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static int controllaTurno(String nome,String cognome,String matricola){
        Connection dbConnection = getConnection();
        String cI = "select matricola from utente where matricola="+matricola+" and nome='"+nome+"' and cognome='"+cognome+"'";
        String cT = "select id,ora_inizio,rilevato from turno where id_impiegato=" + matricola + " and data = '" + LocalDate.now() + "'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(cI);
            if(queryResult.next()) { //se esiste quell'impiegato
                queryResult = statement.executeQuery(cT);
                if (queryResult.next()) {
                    if (queryResult.getBoolean(3))
                        return -2;
                    LocalTime now = LocalTime.now();
                    //se l'orario corrente è dopo l'ora_inizio + 10 minuti
                    if (now.isAfter(LocalTime.parse(queryResult.getString(2)).plusMinutes(10)))
                        return -1;
                    return queryResult.getInt(1);
                }else return 0;
            }else return -3;
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return 0;
    }
    public static String getRuolo(int id){
        List<String> servizi = new ArrayList<>();
        Connection dbConnection = getConnection();
        String gR = "select ruolo from servizio where id=" + id;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gR);
            queryResult.next();
            return queryResult.getString(1);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
    public static void rilevaPresenza(int id){
        Connection dbConnection = getConnection();
        String rP = "select ora_inizio,ora_fine,id_impiegato,id_servizio from turno where id=" + id;
        String rilevaTurno = "update turno set rilevato=true where id=" + id;
        String updateOre;
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(rP);
            queryResult.next();
            String ruolo = DBMS.getRuolo(queryResult.getInt(4));
            long ore = Duration.between(LocalTime.parse(queryResult.getString(1)),LocalTime.parse(queryResult.getString(2))).toHours();
            if(ruolo.toLowerCase().equals("alto"))
                updateOre = "update utente set oreServizio1 = oreServizio1 +"+(int) ore+" where matricola="+queryResult.getInt(3);
            else if(ruolo.toLowerCase().equals("intermedio"))
                updateOre = "update utente set oreServizio2 = oreServizio2 +"+(int) ore+" where matricola="+queryResult.getInt(3);
            else if(ruolo.toLowerCase().equals("medio"))
                updateOre = "update utente set oreServizio3 = oreServizio3 +"+(int) ore+" where matricola="+queryResult.getInt(3);
            else
                updateOre = "update utente set oreServizio3 = oreServizio3 +"+(int) ore+" where matricola="+queryResult.getInt(3);
            statement.executeUpdate(rilevaTurno);
            statement.executeUpdate(updateOre);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }
    public static ObservableList<Turno> getTurni(LocalDate data) {
        ObservableList<Turno> turni = FXCollections.observableArrayList();
        Connection dbConnection = getConnection();
        String mT = "Select ora_inizio, ora_fine, data, id_servizio, id_impiegato from turno where data='" + data + "'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(mT);
            if(queryResult.next()) {
                Turno t = new Turno(LocalTime.parse(queryResult.getString(1)), LocalTime.parse(queryResult.getString(2)), LocalDate.parse(queryResult.getString(3)), queryResult.getInt(4), queryResult.getString(5));
                turni.add(t);
            }
            return turni;
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
}