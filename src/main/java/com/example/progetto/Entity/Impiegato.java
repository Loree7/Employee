package com.example.progetto.Entity;

public class Impiegato extends Utente{
    public Impiegato(String matricola, String nome, String cognome, String ruolo,String email){
        super(matricola,nome,cognome,ruolo,email);
    }
    public Impiegato(String matricola, String nome, String cognome, String ruolo,String email,String password){
        super(matricola,nome,cognome,ruolo,email,password);
    }
}
