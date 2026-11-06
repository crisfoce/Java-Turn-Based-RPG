package mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import entidades.*;
import componentes.*;

public class Gongaga extends Zona{
    private List<Materia> poolMaterias;
    private Random random;
    private Scanner scanner;


    /*
    ==================================================
    Crea la zona Gongaga.
    ***
    Parámetro 1: scanner el cual es un Scanner, es el lector usado para recibir entradas del jugador.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa la zona Gongaga, crea la lista de materias disponibles y agrega las materias que se pueden encontrar.
    ================================================== 
    */
    public Gongaga(Scanner scanner){
        super("Gongaga", 5);
        this.scanner= scanner;

        poolMaterias= new ArrayList<Materia>();
        random= new Random();

        poolMaterias.add(new Materia("Materia de Fuego", Elemento.FUEGO));
        poolMaterias.add(new Materia("Materia de Hielo", Elemento.HIELO));
        poolMaterias.add(new Materia("Materia de Rayo", Elemento.RAYO));
        poolMaterias.add(new Materia("Materia de Cura", Elemento.CURA));
    }


    /*
    ==================================================
    Ejecuta la acción principal de Gongaga.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que está explorando la zona.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función decide si Cloud encuentra una materia o si entra en combate contra enemigos salvajes. Si Cloud pierde, aplica penalización de derrota.
    ================================================== 
    */
    @Override
    public void accionZona(Jugador Cloud){
        double evento= random.nextDouble();

        if(evento<0.30){
            Materia materiaBase= poolMaterias.get(random.nextInt(poolMaterias.size()));
            Materia nuevaMateria= new Materia(materiaBase.getNombre(), materiaBase.getElemento());

            Cloud.agregarMateria(nuevaMateria);

            System.out.println();
            System.out.println("Cloud encontró una " + nuevaMateria.getNombre() + ".");
        }
        
        else{
            List<Enemigo> grupo= generarGrupoEnemigo();
            boolean gano= iniciarCombateBase(Cloud, grupo, false, true, scanner);

            if(!gano && Cloud.getStats().getHpActual()<=0){
                System.out.println("Cloud fue derrotado en Gongaga.");
                Cloud.penalizarDerrota();
            }
        }
    }


    /*
    ==================================================
    Genera un grupo de enemigos.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: List<Enemigo>, devuelve la lista de enemigos que aparecerán en el combate.
    ***
    La función decide aleatoriamente si aparecerán 1, 2 o 3 enemigos salvajes y los agrega al grupo.
    ================================================== 
    */
    public List<Enemigo> generarGrupoEnemigo(){
        List<Enemigo> grupo= new ArrayList<Enemigo>();
        double probabilidad= random.nextDouble();
        int cantidad;

        if(probabilidad<0.60){
            cantidad= 1;
        }
        else if(probabilidad < 0.90){
            cantidad= 2;
        }
        else{
            cantidad= 3;
        }

        for(int i=0; i<cantidad; i++){
            EnemigoSalvaje enemigo= crearEnemigoAleatorio();
            grupo.add(enemigo);
        }

        return grupo;
    }


    /*
    ==================================================
    Crea un enemigo salvaje aleatorio.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: EnemigoSalvaje, devuelve el enemigo salvaje creado.
    ***
    La función elige al azar entre Planta Carnívora, Sapo de la Jungla o Robot Centinela.
    ================================================== 
    */
    private EnemigoSalvaje crearEnemigoAleatorio(){
        int opcion= random.nextInt(3);

        if(opcion==0){
            return EnemigoSalvaje.crearPlantaCarnivora();
        }
        else if(opcion==1){
            return EnemigoSalvaje.crearSapoJungla();
        }

        return EnemigoSalvaje.crearRobotCentinela();
    }

    public List<Materia> getPoolMaterias(){
        return poolMaterias;
    }

    public void setPoolMaterias(List<Materia> poolMaterias){
        if(poolMaterias!=null){
            this.poolMaterias= poolMaterias;
        }
    }

    public Random getRandomGongaga(){
        return random;
    }

    public void setRandomGongaga(Random random){
        if(random!=null){
            this.random= random;
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