package com.employee.progetto.Entity;

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

    public Utente(String matricola, String nome, String cognome, String email) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
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
    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
