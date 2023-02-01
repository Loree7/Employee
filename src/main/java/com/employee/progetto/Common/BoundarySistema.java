package com.employee.progetto.Common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BoundarySistema {
    GestoreSistema gestoreSistema;
    public  BoundarySistema(GestoreSistema gestoreSistema){
        this.gestoreSistema = gestoreSistema;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::chiediData, 0, 1, TimeUnit.DAYS);
        executor.scheduleAtFixedRate(this::chiediOrario, 0, 1, TimeUnit.MINUTES);
    }
    public void chiediData(){
        gestoreSistema.controlloData(LocalDate.now());
    }
    public void chiediOrario(){
        gestoreSistema.controlloOrario(LocalTime.now());
    }
}
