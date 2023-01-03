package com.employee.progetto.Entity;

public class Servizio {
    private String nome;
    private String stato;
    public Servizio(String nome, String stato){
        this.nome = nome;
        this.stato = stato;

    }

    public String getNomeServizio(){return nome;}
    public String getStatoServizio(){return stato;}
}
