package entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import componentes.*;

public class EnemigoSalvaje extends Enemigo implements Vulnerable{
    private List<Elemento> debilidades;
    private List<Elemento> resistencias;
    private List<Elemento> inmunidades;


    /*
    ==================================================
    Crea un enemigo salvaje.
    ***
    Parámetro 1: nombre el cual es un String, es el nombre del enemigo.
    Parámetro 2: stats el cual es Estadisticas, son las estadísticas base del enemigo.
    Parámetro 3: debilidades el cual es List<Elemento>, son los elementos que le hacen más daño.
    Parámetro 4: resistencias el cual es List<Elemento>, son los elementos que le hacen menos daño.
    Parámetro 5: inmunidades el cual es List<Elemento>, son los elementos que no le hacen daño.
    ***
    Tipo de retorno: no tiene, es un constructor
    ***
    La función inicializa un enemigo salvaje con recompensas aleatorias de EXP y Chatarra, además de guardar sus afinidades elementales.
    ================================================== 
    */
    public EnemigoSalvaje(String nombre, Estadisticas stats, List<Elemento> debilidades, List<Elemento> resistencias, List<Elemento> inmunidades){
        super(nombre, new Random().nextInt(21)+80, new Random().nextInt(26)+50, stats);
        this.debilidades= new ArrayList<Elemento>(debilidades);
        this.resistencias= new ArrayList<Elemento>(resistencias);
        this.inmunidades= new ArrayList<Elemento>(inmunidades);
    }


    /*
    ==================================================
    Entrega chatarra a Cloud.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que recibe la chatarra.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función entrega a Cloud la chatarra guardada como recompensa del enemigo salvaje.
    ================================================== 
    */

    public void giveChatarraRecompensa(Jugador Cloud){
        Cloud.recibirChatarra(chatarraRecompensa);
    }


    /*
    ==================================================
    Evalúa una debilidad elemental.
    ***
    Parámetro 1: elementoMagia el cual es un Elemento, es el elemento usado contra el enemigo.
    ***
    Tipo de retorno: double. devuelve el multiplicador de daño que corresponde.
    ***
    La función revisa si el enemigo es inmune, débil o resistente al elemento recibido. Según eso devuelve 0.0, 2.0, 0.5 o 1.0
    ================================================== 
    */

    @Override
    public double evaluarDebilidad(Elemento elementoMagia){
        if(inmunidades.contains(elementoMagia)){
            return 0.0;
        }

        if(debilidades.contains(elementoMagia)){
            return 2.0;
        }

        if(resistencias.contains(elementoMagia)){
            return 0.5;
        }

        return 1.0;
    }

    public List<Elemento> getDebilidades(){
        return debilidades;
    }

    public void setDebilidades(List<Elemento> debilidades){
        if(debilidades!=null){
            this.debilidades= new ArrayList<Elemento>(debilidades);
        }
    }

    public List<Elemento> getResistencias(){
        return resistencias;
    }

    public void setResistencias(List<Elemento> resistencias){
        if(resistencias!=null){
            this.resistencias= new ArrayList<Elemento>(resistencias);
        }
    }

    public List<Elemento> getInmunidades(){
        return inmunidades;
    }

    public void setInmunidades(List<Elemento> inmunidades){
        if(inmunidades!=null){
            this.inmunidades= new ArrayList<Elemento>(inmunidades);
        }
    }


    /*
    ==================================================
    Crea una Planta Carnívora.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: EnemigoSalvaje, devuelve una instancia de Planta Carnívora.
    ***
    La función crea el enemigo Planta Carnívora con sus estadísticas y afinidades elementales propias.
    ================================================== 
    */

    public static EnemigoSalvaje crearPlantaCarnivora(){
        return new EnemigoSalvaje("Planta Carnívora", new Estadisticas(80, 0, 15, 0), Arrays.asList(Elemento.FUEGO, Elemento.HIELO), new ArrayList<Elemento>(), Arrays.asList(Elemento.RAYO));
    }


    /*
    ==================================================
    Crea un Sapo de la Jungla.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: EnemigoSalvaje, devuelve una instancia de Sapo de la Jungla.
    ***
    La función crea el enemigo Sapo de la Jungla con sus estadísticas y afinidades elementales propias.
    ================================================== 
    */
    public static EnemigoSalvaje crearSapoJungla(){
        return new EnemigoSalvaje("Sapo de la Jungla", new Estadisticas(60, 0, 12, 0), Arrays.asList(Elemento.RAYO, Elemento.HIELO), Arrays.asList(Elemento.FUEGO), new ArrayList<Elemento>());
    }


    /*
    ==================================================
    Crea un Robot Centinela.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: EnemigoSalvaje, devuelve una instancia de Robot Centinela.
    ***
    La función crea el enemigo Robot Centinela con sus estadísticas y afinidades elementales propias.
    ================================================== 
    */
    public static EnemigoSalvaje crearRobotCentinela(){
        return new EnemigoSalvaje("Robot Centinela", new Estadisticas(100, 0, 20, 0), Arrays.asList(Elemento.RAYO), Arrays.asList(Elemento.FISICO, Elemento.HIELO), new ArrayList<Elemento>());
    }
}