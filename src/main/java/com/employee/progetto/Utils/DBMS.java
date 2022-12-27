package com.employee.progetto.Utils;

import com.employee.progetto.Entity.Amministratore;
import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.Entity.Utente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
    public static boolean controllaPeriodo(LocalDate data_inizio,LocalDate data_fine){
        LocalDate inizio_periodo = LocalDate.of(2022, 6, 1);
        LocalDate fine_periodo = LocalDate.of(2022, 7, 1);
        if(data_inizio.isAfter(fine_periodo) || data_fine.isBefore(inizio_periodo)) //se richiedo prima o dopo il periodo
            return true;
        return false;
    }
    public static boolean controllaFerie(LocalDate data_inizio,LocalDate data_fine,String matricola){
        Connection dbConnection = getConnection();
        String cF = "select id from astensioni where exists (select * from astensioni where data_inizio between '" +
                data_inizio + "' and '" + data_fine + "' or data_fine between '" + data_inizio + "' and '" + data_fine +
                "' and id_impiegato = " + matricola + ")";
        System.out.println(cF);
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(cF);
            if(queryResult.next()){
                System.out.println("prova");
                return true;
            }
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
    public static int getNumPersonale(){
        Connection dbConnection = getConnection();
        String gN = "select count(distinct(matricola)) from utente,astensioni where tipo != 'congedo' and id_impiegato=matricola";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gN);
            if(queryResult.next())
                return queryResult.getInt(1);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return 0;
    }
    public static Impiegato getImpiegatoMenoOre(LocalDate giorno){
        Connection dbConnection = getConnection();
        String gI = "select matricola,nome,cognome,ruolo,email from utente,turno where id_impiegato=matricola and matricola not in" +
                "(select id_impiegato from turno where data = '" + giorno + "') and matricola not in" +
                "(select id_impiegato from astensioni where '" + giorno + "' between data_inizio and data_fine)" +
                "group by matricola order by ore asc limit 1";
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
}