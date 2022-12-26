package com.employee.progetto;

import com.employee.progetto.GestioneTurni.Control.GestoreSistemaTurni;

public class BoundarySistema {
    GestoreSistemaTurni gestoreSistemaTurni;
    public  BoundarySistema(GestoreSistemaTurni gestoreSistemaTurni){
        this.gestoreSistemaTurni = gestoreSistemaTurni;
    }
    public void chiediData(){
        gestoreSistemaTurni.chiediData();
    }
}
