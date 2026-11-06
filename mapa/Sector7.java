package mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entidades.*;
import componentes.*;

public class Sector7 extends Zona{
    private List<Mejora> tiendaLocal;
    private Scanner scanner;


    /*
    ==================================================
    Crea la zona Sector 7.
    ***
    Parámetro 1: scanner el cual es un Scanner, es el lector usado para recibir entradas del jugador.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa Sector 7, crea las mejoras de la tienda y agrega el enemigo del simulador a la lista de enemigos disponibles.
    ================================================== 
    */
    public Sector7(Scanner scanner){
        super("Sector 7", 1);
        this.scanner= scanner;

        tiendaLocal= new ArrayList<Mejora>();
        tiendaLocal.add(new Mejora("Vitalidad", 100, TipoStat.HP_MAX, 20));
        tiendaLocal.add(new Mejora("Éter", 120, TipoStat.MP_MAX, 10));
        tiendaLocal.add(new Mejora("Mejora Física", 150, TipoStat.FUERZA, 10));

        enemigosDisponibles.add(new EnemigoSimulador());
    }


    /*
    ==================================================
    Ejecuta la acción principal del Sector 7.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que está en la zona.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra las opciones propias del Sector 7 y permite iniciar el simulador de combate o abrir la tienda.
    ================================================== 
    */
    @Override
    public void accionZona(Jugador Cloud){
        System.out.println();
        System.out.println("=== SECTOR 7 ===");
        System.out.println("1) Iniciar simulador");
        System.out.println("2) Abrir tienda");
        System.out.println("0) Volver");
        System.out.print("> ");

        String opcion= scanner.nextLine().trim();

        if(opcion.equals("1")){
            iniciarSimulador(Cloud);
        }
        else if(opcion.equals("2")){
            abrirTienda(Cloud);
        }
        else if(!opcion.equals("0")){
            System.out.println();
            System.out.println("Opción inválida.");
        }
    }


    /*
    ==================================================
    Inicia el simulador de combate.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que entra al simulador.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función genera el grupo de enemigos del simulador, muestra quienes aparecen e inicia un combate seguro donde Cloud no puede morir.
    ================================================== 
    */
    public void iniciarSimulador(Jugador Cloud){
        List<Enemigo> grupo= generarGrupoSimulador();

        System.out.println();
        System.out.println(">>> INICIANDO SIMULADOR DE COMBATE <<<");

        if(grupo.size()==1){
            Enemigo enemigo= grupo.get(0);
            System.out.println("Aparece un " + enemigo.getNombre() + " (HP: " + enemigo.getStats().getHpActual() + ")");
        }

        else{
            System.out.println("Aparecen enemigos de simulación:");

            for(int i=0; i<grupo.size(); i++){
                Enemigo enemigo= grupo.get(i);
                System.out.println((i+1) + ") " + enemigo.getNombre() + " (HP: " + enemigo.getStats().getHpActual() + ")");
            }
        }

        boolean completado= iniciarCombateBase(Cloud, grupo, true, true, scanner);

        if(completado){
            System.out.println(">>> SIMULACIÓN COMPLETADA <<<");
            System.out.println();
        }
    }


    /*
    ==================================================
    Genera el grupo del simulador.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: List<Enemigo>, devuelve la lista de enemigos que aparecerán en el simulador.
    ***
    La función crea un grupo de 1 o 2 enemigos para el combate seguro del Sector 7.
    ================================================== 
    */
    private List<Enemigo> generarGrupoSimulador(){
        List<Enemigo> grupo= new ArrayList<Enemigo>();
        int cantidad= random.nextDouble()<0.50 ? 1 : 2;

        for(int i=0; i<cantidad; i++){
            grupo.add(new EnemigoSimulador());
        }

        return grupo;
    }


    /*
    ==================================================
    Abre la tienda de chatarra.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que comprará mejoras.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra las mejoras disponibles, revisa si Cloud tiene chatarra suficiente y aplica la mejora comprada.
    ================================================== 
    */
    public void abrirTienda(Jugador Cloud){
        System.out.println();
        System.out.println("==============================================");
        System.out.println("             TIENDA DE CHATARRA");
        System.out.println("==============================================");
        System.out.println();
        System.out.println("Chatarra disponible: " + Cloud.getChatarra());
        System.out.println();

        for(int i=0; i<tiendaLocal.size(); i++){
            Mejora mejora= tiendaLocal.get(i);

            System.out.printf("%d) %-13s | Costo: %3d Chatarra | Bono: %s%n", i+1, mejora.getNombre(), mejora.getCostoChatarra(), describirBono(mejora));
        }

        System.out.println("0) Volver");
        System.out.print("> ");

        try{
            int opcion= Integer.parseInt(scanner.nextLine().trim());

            if(opcion==0){
                return;
            }

            int indice= opcion-1;

            if(indice<0 || indice>=tiendaLocal.size()){
                System.out.println();
                System.out.println("Opción inválida.");
                return;
            }

            Mejora mejora= tiendaLocal.get(indice);

            if(!Cloud.gastarChatarra(mejora.getCostoChatarra())){
                System.out.println();
                System.out.println("No tienes chatarra suficiente.");
                return;
            }

            aplicarMejora(Cloud, mejora);

            System.out.println();
            System.out.println("Has comprado la mejora de " + mejora.getNombre() + " y se ha aplicado correctamente.");
        }
        catch (NumberFormatException e){
            System.out.println();
            System.out.println("Opción inválida.");
        }
    }


    /*
    ==================================================
    Describe el bono de una mejora.
    ***
    Parámetro 1: mejora el cual es Mejora, es la mejora que se quiere mostrar en la tienda.
    ***
    Tipo de retorno: String, devuelve el texto que explica el bono de la mejora.
    ***
    La función crea una descripción clara del efecto que tendrá la mejora según la estadística que afecta.
    ================================================== 
    */
    private String describirBono(Mejora mejora){
        if(mejora.getStatAfectado()==TipoStat.HP_MAX){
            return "+" + mejora.getValorBono() + " de vida máxima base (HP)";
        }
        else if(mejora.getStatAfectado()==TipoStat.MP_MAX){
            return "+" + mejora.getValorBono() + " de magia máxima base (MP)";
        }
        else if(mejora.getStatAfectado()==TipoStat.FUERZA){
            return "+" + mejora.getValorBono() + " de fuerza base";
        }

        return "+" + mejora.getValorBono();
    }


    /*
    ==================================================
    Aplica una mejora.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que recibe la mejora.
    Parámetro 2: mejora el cual es Mejora, es la mejora comprada en la tienda.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función revisa qué estadística afecta la mejora y aumenta esa estadística en Cloud.
    ================================================== 
    */
    private void aplicarMejora(Jugador Cloud, Mejora mejora){
        if(mejora.getStatAfectado()==TipoStat.HP_MAX){
            Cloud.getStats().aumentarHpMaximo(mejora.getValorBono());
        }
        else if(mejora.getStatAfectado()==TipoStat.MP_MAX){
            Cloud.getStats().aumentarMpMaximo(mejora.getValorBono());
        }
        else if(mejora.getStatAfectado()==TipoStat.FUERZA){
            Cloud.getStats().aumentarFuerza(mejora.getValorBono());
        }
    }

    public List<Mejora> getTiendaLocal(){
        return tiendaLocal;
    }

    public void setTiendaLocal(List<Mejora> tiendaLocal){
        if(tiendaLocal!=null){
            this.tiendaLocal= tiendaLocal;
        }
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