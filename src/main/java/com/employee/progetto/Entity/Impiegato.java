package com.employee.progetto.Entity;

public class Impiegato extends Utente{
    private int ferie;
    public Impiegato(String matricola, String nome, String cognome, String ruolo,String email){
        super(matricola,nome,cognome,ruolo,email);
    }
    public Impiegato(String matricola, String nome, String cognome, String ruolo,String email,String password){
        super(matricola,nome,cognome,ruolo,email,password);
    }
    public Impiegato(String matricola, String nome, String cognome, String ruolo,String email,int ferie){
        super(matricola,nome,cognome,ruolo,email);
        this.ferie = ferie;
    }

    public int getFerie(){
        return ferie;
    }

    public void setFerie(int ferie){
        this.ferie = ferie;
    }
}
