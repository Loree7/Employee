package com.employee.progetto.Entity;

import java.time.LocalDate;
import java.time.LocalTime;


public class Turno {
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private LocalDate data;
    private int idServizio;
    private String matricola;

    public LocalTime getOraFine() {
        return oraFine;
    }

    public LocalDate getData() {
        return data;
    }

    public int getIdServizio() {
        return idServizio;
    }

    public String getMatricola() {
        return matricola;
    }

    public LocalTime getOraInizio() {
        return oraInizio;
    }

    public Turno(LocalTime oraInizio, LocalTime oraFine, LocalDate data, int idServizio, String matricola) {
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.data = data;
        this.idServizio = idServizio;
        this.matricola = matricola;
    }
}
