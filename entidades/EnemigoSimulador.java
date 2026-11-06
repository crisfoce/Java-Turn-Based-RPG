package entidades;

import java.util.Random;
import componentes.Estadisticas;

public class EnemigoSimulador extends Enemigo{

    /*
    ==================================================
    Crea un enemigo simulador.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa al Soldado común del simulador con HP, una fuerza y una recompensa de EXP aleatoria.
    ================================================== 
    */
    public EnemigoSimulador(){
        super("Soldado común", new Random().nextInt(6)+15, 0, new Estadisticas(50, 0, 15, 0));
    }


    /*
    ==================================================
    Revisa si Cloud puede recibir daño seguro.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que participa en el simulador.
    ***
    Tipo de retorno: boolean, devuelve true si Cloud tiene más de 1 HP y false si no.
    ***
    La función revisa si Cloud puede recibir daño sin que el simulador lo deje con 0 de HP.
    ================================================== 
    */
    public boolean checkDanoSeguro(Jugador Cloud){
        return Cloud.getStats().getHpActual()>1;
    }


    /*
    ==================================================
    Ataca a Cloud sin derrotarlo.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que participa en el simulador.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función hace que el enemigo simulador ataque a Cloud. Si acierta, calcula el daño físico,
    pero nunca deja a Cloud con menos de 1 de HP.
    ================================================== 
    */
    @Override
    public void atacar(Jugador Cloud){
        if(random.nextDouble()<=getPrecision()){
            int dano= (int) Math.floor(stats.getFuerza()*1.25);
            int hpAntes= Cloud.getStats().getHpActual();
            int danoSeguro= Math.min(dano, Math.max(0, hpAntes-1));

            Cloud.getStats().recibirDMG(danoSeguro);

            int hpDespues= Cloud.getStats().getHpActual();
            int danoReal= hpAntes-hpDespues;

            Cloud.cargarLimitePorDanoRecibido(danoReal);
            System.out.println("=== TURNO ENEMIGO ===");
            System.out.println("[" + nombre + "] ataca a Cloud inflingiendo " + danoReal + " de daño físico.");
        }
        else{
            System.out.println("=== TURNO ENEMIGO ===");
            System.out.println("[" + nombre + "] falló su ataque.");
        }
    }
}