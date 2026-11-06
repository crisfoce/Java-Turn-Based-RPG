package mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entidades.*;

public class NucleoPlaneta extends Zona{
    private int materiasMinimasRequeridas;
    private Scanner scanner;


    /*
    ==================================================
    Crea la zona Núcleo del Planeta.
    ***
    Parámetro 1: scanner el cual es un Scanner, es el lector usado para recibir entradas del jugador.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa el Núcleo del Planeta, define el nivel requerido y la cantidad mínima de materias equipadas para entrar.
    ================================================== 
    */
    public NucleoPlaneta(Scanner scanner){
        super("Núcleo del Planeta", 20);
        this.scanner= scanner;
        materiasMinimasRequeridas= 2;
    }


    /*
    ==================================================
    Ejecuta la acción principal del Núcleo del Planeta.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que entra a la zona.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función inicia directamente el combate contra Sephiroth.
    ================================================== 
    */
    @Override
    public void accionZona(Jugador Cloud){
        iniciarCombate(Cloud);
    }


    /*
    ==================================================
    Valida si Cloud puede entrar al Núcleo del Planeta.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que intenta entrar.
    ***
    Tipo de retorno: boolean, devuelve true si cumple los requisitos y false si no.
    ***
    La función revisa que Cloud tenga el nivel requerido y la cantidad mínima de materias equipadas.
    ================================================== 
    */
    @Override
    public boolean validarAcceso(Jugador Cloud){
        return Cloud.getNivel()>=nivelRequerido && Cloud.getBusterSword().getMateriasEquipadas().size()>=materiasMinimasRequeridas;
    }


    /*
    ==================================================
    Inicia el combate final.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que combatirá contra Sephiroth.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función crea a Sephiroth, inicia el combate final sin opción de huir y termina el juego si Cloud gana.
    ================================================== 
    */
    public void iniciarCombate(Jugador Cloud){
        List<Enemigo> grupo= new ArrayList<Enemigo>();
        grupo.add(new Sephiroth());

        boolean gano= iniciarCombateBase(Cloud, grupo, false, false, scanner);

        if(gano){
            System.out.println("Cloud ha derrotado a Sephiroth y ha salvado el planeta");
            System.exit(0);
        }

        if(Cloud.getStats().getHpActual()<=0){
            System.out.println("Cloud fue derrotado por Sephiroth.");
            Cloud.penalizarDerrota();
        }
    }

    public int getMateriasMinimasRequeridas(){
        return materiasMinimasRequeridas;
    }

    public void setMateriasMinimasRequeridas(int materiasMinimasRequeridas){
        this.materiasMinimasRequeridas= Math.max(0, materiasMinimasRequeridas);
    }

    public Scanner getScanner(){
        return scanner;
    }

    public void setScanner(Scanner scanner){
        if(scanner!=null){
            this.scanner= scanner;
        }
    }
}