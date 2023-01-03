package com.employee.progetto.Entity;

public class Servizio {
    private String nome;
    private String stato;
    private int numDipendenti;
    public Servizio(String nome, String stato){
        this.nome = nome;
        this.stato = stato;
    }
    public String getNome() {
        return nome;
    }
    public String getStato() {
        return stato;
    }
    public int getNumDipendenti() {
        return numDipendenti;
    }
    public void setNumDipendenti(int numDipendenti) {
        this.numDipendenti = numDipendenti;
    }
}
