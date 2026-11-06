package entidades;

import java.util.Random;
import componentes.Estadisticas;

public abstract class Enemigo{
    public String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;
    protected Random random;

    /*
    ==================================================
    Crea un enemigo.
    ***
    Parámetro 1: nombre el cual es un String, es el nombre del enemigo.
    Parámetro 2: xpRecompensa el cual es un int, es la experiencia que entrega al ser derrotado.
    Parámetro 3: chatarraRecompensa el cual es un int, es la chatarra que puede entregar al ser derrotado.
    Parámetro 4: stats el cual es Estadisticas, son las estadísticas base del enemigo.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa los datos principales del enemigo, sus recompensas, estadísticas y el generador aleatorio.
    ==================================================
    */
    public Enemigo(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats){
        this.nombre= nombre;
        this.xpRecompensa= xpRecompensa;
        this.chatarraRecompensa= chatarraRecompensa;
        this.stats= stats;
        this.random= new Random();
    }


    /*
    ==================================================
    Ataca a Cloud.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que recibe el ataque.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función calcula si el enemigo acierta el ataque. Si acierta, hace daño físico a Cloud y carga su barra de límite según el daño recibido.
    ================================================== 
    */
    public void atacar(Jugador Cloud){
        System.out.println("=== TURNO ENEMIGO ===");

        if(random.nextDouble()<=getPrecision()){
            int dano= (int) Math.floor(stats.getFuerza()*1.25);
            int hpAntes= Cloud.getStats().getHpActual();

            Cloud.getStats().recibirDMG(dano);

            int hpDespues= Cloud.getStats().getHpActual();
            int danoReal= hpAntes - hpDespues;

            Cloud.cargarLimitePorDanoRecibido(danoReal);
            System.out.println("[" + nombre + "] ataca a Cloud inflingiendo " + danoReal + " de daño físico.");
        }

        else{
            System.out.println("[" + nombre + "] falló su ataque.");
        }
    }


    /*
    ==================================================
    Entrega experiencia a Cloud.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que recibe la experiencia.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función entrega a Cloud la experiencia guardada como recompensa del enemigo.
    ================================================== 
    */
    public void giveXpRecompensa(Jugador Cloud){
        Cloud.recibirXP(xpRecompensa);
    }


    /*
    ==================================================
    Obtiene la precisión del enemigo.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: double, devuelve la probabilidad de acertar el ataque.
    ***
    La función entrega la precisión base del enemigo para decidir si su ataque falla o acierta.
    ================================================== 
    */
    protected double getPrecision(){
        return 0.85;
    }


    /*
    ==================================================
    Revisa si el enemigo fue derrotado.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: boolean, devuelve true si el enemigo no tiene HP y false si sigue vivo.
    ***
    La función comprueba si el HP actual del enemigo llegó a 0.
    ================================================== 
    */
    public boolean estaDerrotado(){
        return stats.getHpActual()<=0;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre= nombre;
    }

    public int getXpRecompensa(){
        return xpRecompensa;
    }

    public void setXpRecompensa(int xpRecompensa){
        this.xpRecompensa= Math.max(0, xpRecompensa);
    }

    public int getChatarraRecompensa(){
        return chatarraRecompensa;
    }

    public void setChatarraRecompensa(int chatarraRecompensa){
        this.chatarraRecompensa= Math.max(0, chatarraRecompensa);
    }

    public Estadisticas getStats(){
        return stats;
    }

    public void setStats(Estadisticas stats){
        if(stats!=null){
            this.stats= stats;
        }
    }

    public Random getRandom(){
        return random;
    }

    public void setRandom(Random random){
        if(random!=null){
            this.random= random;
        }
    }
}