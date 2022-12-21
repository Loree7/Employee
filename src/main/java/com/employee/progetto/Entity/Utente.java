package com.example.progetto.Entity;

public class Utente {
    //devo mettere il final sugli attributi???
    private String matricola;
    private String nome;
    private String cognome;
    private String ruolo;
    private String email;
    private String password;
    public Utente(String matricola, String nome, String cognome, String ruolo,String email) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
        this.email = email;
    }

    public Utente(String matricola, String nome, String cognome, String ruolo,String email,String password) {
        this(matricola,nome,cognome,ruolo,email);
        this.password = password;
    }
    public String getMatricola() {
        return matricola;
    }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getRuolo(){
       return ruolo;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}
