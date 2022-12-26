package com.employee.progetto;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BoundarySistema {
    GestoreSistema gestoreSistema;
    public  BoundarySistema(GestoreSistema gestoreSistema){
        this.gestoreSistema = gestoreSistema;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::chiediData, 0, 1, TimeUnit.DAYS);
    }
    public void chiediData(){
        gestoreSistema.controlloData(Instant.now());
    }
}
