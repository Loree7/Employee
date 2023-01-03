package com.employee.progetto.Entity;

public class Impiegato extends Utente{
    private int ferie;
    private int orePermesso;
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
    public Impiegato(String matricola, String nome, String cognome, String ruolo,String email,int ferie,int orePermesso){
        this(matricola,nome,cognome,ruolo,email,ferie);
        this.orePermesso = orePermesso;
    }

    public int getFerie(){
        return ferie;
    }
    public void setFerie(int ferie){
        this.ferie = ferie;
    }
    public int getOrePermesso(){
        return orePermesso;
    }
    public void setOrePermesso(int orePermesso){
        this.orePermesso = orePermesso;
    }
}
