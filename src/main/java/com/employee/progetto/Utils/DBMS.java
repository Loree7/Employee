package com.employee.progetto.Utils;

import com.employee.progetto.Entity.Amministratore;
import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.Entity.Utente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBMS {
    public static Connection databaseLink;

    public static Connection getConnection() {
        String databaseName = "dbTeam";
        String databaseUser = "root";
        String databasePassword = "root";
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
        String vC = "SELECT nome,cognome,ruolo,email FROM utente WHERE matricola = " + matricola + " AND password = '" + password + "'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(vC);
            if (queryResult.next()) {
                if (queryResult.getString(3).equals("Amministratore"))
                    return new Amministratore(matricola, queryResult.getString(1), queryResult.getString(2)
                            , queryResult.getString(3), queryResult.getString(4));
                else return new Impiegato(matricola, queryResult.getString(1), queryResult.getString(2)
                        , queryResult.getString(3), queryResult.getString(4));
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
        String rI = "insert into utente (nome,cognome,ruolo,email,password) values ('" + nome + "','" + cognome + "','"
                + ruolo.toLowerCase() + "','" + email + "','" + password + "')";
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
        String m = "update utente set nome='" + nome + "',cognome='" + cognome +", Password: "+ password +"Matricola: " + matricola;
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(m);
        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
    }

    public static String getMatricola(String email) {
        Connection dbConnection = getConnection();
        String gM = "select matricola from utente where email= '" + email + "'";
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet queryResult = statement.executeQuery(gM);
            if(queryResult.next())
            return queryResult.getString(1);

        } catch (Exception e) {
            erroreComunicazioneDBMS();
            e.printStackTrace();
            e.getCause();
        }
        return null;
    }
}