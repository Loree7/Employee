package com.employee.progetto.Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Turno {
    private LocalTime ora_Inizio; //Metterle Date?
    private LocalTime ora_Fine;
    private LocalDate data;
    private int id_Servizio;
    private String matricola;


    public Turno(LocalTime ora_Inizio, LocalTime ora_Fine, LocalDate data, int id_Servizio, String matricola) {
        this.ora_Inizio = ora_Inizio;
        this.ora_Fine = ora_Fine;
        this.data = data;
        this.id_Servizio = id_Servizio;
        this.matricola = matricola;
    }
}
