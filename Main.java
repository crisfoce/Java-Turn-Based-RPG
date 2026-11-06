import java.util.Scanner;
import mapa.*;
import entidades.*;
import componentes.*;

public class Main{
    private Scanner scanner;
    private Jugador Cloud;
    private Sector7 sector7;
    private Gongaga gongaga;
    private NucleoPlaneta nucleoPlaneta;
    private Zona zonaActual;


    /*
    ==================================================
    Crea el juego principal.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa el scanner, crea a Cloud, crea las zonas del juego y deja como zona inicial Sector 7.
    ================================================== 
    */
    public Main() {
        scanner= new Scanner(System.in);
        Cloud= new Jugador();
        sector7= new Sector7(scanner);
        gongaga= new Gongaga(scanner);
        nucleoPlaneta= new NucleoPlaneta(scanner);
        zonaActual= sector7;
    }


    /*
    ==================================================
    Inicia el programa.
    ***
    Parámetro 1: args el cual es un String[], son los argumentos recibidos al ejecutar el programa.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función crea una instancia de Main y ejecuta el ciclo principal del juego.
    ================================================== 
    */
    public static void main(String[] args){
        new Main().run();
    }


    /*
    ==================================================
    Ejecuta el ciclo principal del juego.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función mantiene el juego activo mientras el jugador no salga. Muestra el menú, lee opciones y ejecuta acciones según la zona actual.
    ================================================== 
    */
    private void run(){
        boolean jugando= true;

        while(jugando){
            if(Cloud.isDerrotaReciente()){
                zonaActual= sector7;
                Cloud.limpiarDerrotaReciente();
                System.out.println("Cloud fue rescatado y regresó a Sector 7.");
            }

            mostrarMenuPrincipal();

            String opcion= scanner.nextLine().trim();

            if(zonaActual==sector7){
                if(opcion.equals("1")){
                    sector7.iniciarSimulador(Cloud);
                }

                else if(opcion.equals("2")){
                    sector7.abrirTienda(Cloud);
                }

                else if(opcion.equals("3")){
                    viajar();
                }

                else if(opcion.equals("4")){
                    mostrarEstado();
                }

                else if(opcion.equals("5")){
                    gestionarMaterias();
                }

                else if(opcion.equals("6")){
                    descansar();
                }

                else if(opcion.equals("0")){
                    System.out.println();
                    System.out.println("Saliendo del juego...");
                    System.out.println();
                    jugando= false;
                }
                
                else{
                    System.out.println();
                    System.out.println("Opción inválida.");
                }
            }
            
            else{
                if(opcion.equals("1")){
                    zonaActual.accionZona(Cloud);
                }

                else if(opcion.equals("2")){
                    viajar();
                }

                else if(opcion.equals("3")){
                    mostrarEstado();
                }

                else if(opcion.equals("4")){
                    gestionarMaterias();
                }

                else if(opcion.equals("0")){
                    System.out.println();
                    System.out.println("Saliendo del juego...");
                    System.out.println();
                    jugando= false;
                }

                else{
                    System.out.println("");
                    System.out.println("Opción inválida.");
                }
            }
        }
    }


    /*
    ==================================================
    Muestra el menú principal.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra el estado resumido de Cloud y las opciones disponibles según la zona donde se encuentra.
    ================================================== 
    */
    private void mostrarMenuPrincipal(){
        int materiasEquipadas= Cloud.getBusterSword().getMateriasEquipadas().size();
        Estadisticas stats= Cloud.getStats();

        System.out.println();
        System.out.println("========================================");
        System.out.println("ZONA: " + zonaActual.getNombre() + " | NIVEL: " + Cloud.getNivel());
        System.out.println("HP: " + stats.getHpActual() + "/" + stats.getHpMaximo() + " | MP: " + stats.getMpActual() + "/" + stats.getMpMaximo());
        System.out.println("EXP: " + Cloud.getXpActual() + "/" + (10 * Cloud.getNivel()) + " | MATERIAS EQUIPADAS: " + materiasEquipadas + "/5");
        System.out.println("========================================");

        if(zonaActual==sector7){
            System.out.println("1. Entrar al Simulador de Combate");
            System.out.println("2. Abrir Tienda de Chatarra");
            System.out.println("3. Viajar a otra zona");
            System.out.println("4. Ver estadísticas de Cloud");
            System.out.println("5. Gestionar materias");
            System.out.println("6. Descansar en la Posada (Recuperar HP/MP)");
            System.out.println("0. Salir del juego");
        }
        else if(zonaActual==gongaga){
            System.out.println("1. Explorar Gongaga");
            System.out.println("2. Viajar a otra zona");
            System.out.println("3. Ver estadísticas de Cloud");
            System.out.println("4. Gestionar materias");
            System.out.println("0. Salir del juego");
        }
        else if(zonaActual==nucleoPlaneta){
            System.out.println("1. Enfrentar a Sephiroth");
            System.out.println("2. Viajar a otra zona");
            System.out.println("3. Ver estadísticas de Cloud");
            System.out.println("4. Gestionar materias");
            System.out.println("0. Salir del juego");
        }

        System.out.print("> ¿Qué deseas hacer? ");
    }


    /*
    ==================================================
    Permite viajar a otra zona
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra los destinos, lee la acción del jugador, revisa los requisitos de acceso y cambia la zona actual si se pudo viajar.
    ================================================== 
    */
    private void viajar(){
        System.out.println();
        System.out.println("Elegir destino:");
        System.out.println("1) Sector 7");
        System.out.println("2) Gongaga");
        System.out.println("3) Núcleo del Planeta");
        System.out.print("> ");

        String opcion= scanner.nextLine().trim();
        Zona destino= null;

        if(opcion.equals("1")){
            destino= sector7;
        }
        else if(opcion.equals("2")){
            destino= gongaga;
        }
        else if(opcion.equals("3")){
            destino= nucleoPlaneta;
        }
        else{
            System.out.println();
            System.out.println("Opción inválida.");
            return;
        }

        if(destino==zonaActual){
            System.out.println();
            System.out.println("Ya estás en " + zonaActual.getNombre() + ". No se pudo viajar.");
            return;
        }

        if(!destino.validarAcceso(Cloud)){
            System.out.println();
            System.out.println("Cloud no cumple los requisitos para entrar a " + destino.getNombre() + ".");
            return;
        }

        zonaActual= destino;
        System.out.println();
        System.out.println("Viajaste a " + zonaActual.getNombre() + ".");
    }


    /*
    ==================================================
    Muestra las estadísticas de Cloud.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra la chatarra, límite, fuerza, magia y cantidad de materias que tiene Cloud.
    ================================================== 
    */
    private void mostrarEstado(){
        Estadisticas stats= Cloud.getStats();

        System.out.println();
        System.out.println("=== ESTADÍSTICAS ===");
        System.out.println("Chatarra: " + Cloud.getChatarra());
        System.out.println("Límite: " + Cloud.getLimiteActual() + "/100");
        System.out.println("Fuerza: " + stats.getFuerza());
        System.out.println("Magia: " + stats.getMagia());
        System.out.println("Materias en mochila: " + Cloud.getMochila().size());
        System.out.println("Materias equipadas: " + Cloud.getBusterSword().getMateriasEquipadas().size());
    }


    /*
    ==================================================
    Gestiona las materias.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra un submenú para ver la mochila, ver materias equipadas, equipar o desequipar materias.
    ================================================== 
    */
    private void gestionarMaterias(){
        boolean gestionando= true;

        while (gestionando){
            System.out.println();
            System.out.println("=== MATERIAS ===");
            System.out.println("1) Ver mochila");
            System.out.println("2) Ver materias equipadas");
            System.out.println("3) Equipar materia");
            System.out.println("4) Desequipar materia");
            System.out.println("0) Volver");
            System.out.print("> ");

            String opcion= scanner.nextLine().trim();

            if(opcion.equals("1")){
                mostrarMochila();
            }
            else if(opcion.equals("2")){
                mostrarEquipadas();
            }
            else if(opcion.equals("3")){
                equiparMateria();
            }
            else if(opcion.equals("4")){
                desequiparMateria();
            }
            else if(opcion.equals("0")){
                gestionando= false;
            }
            else{
                System.out.println();
                System.out.println("Opción inválida.");
            }
        }
    }


    /*
    ==================================================
    Muestra la mochila.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra todas las materias que Cloud tiene guardadas en la mochila.
    ================================================== 
    */
    private void mostrarMochila(){
        if(Cloud.getMochila().isEmpty()){
            System.out.println();
            System.out.println("La mochila está vacía.");
            return;
        }

        for (int i=0; i<Cloud.getMochila().size(); i++){
            Materia materia= Cloud.getMochila().get(i);
            System.out.println((i+1) + ") " + materia.getNombre() + " [" + materia.getElemento() + "]");
        }
    }


    /*
    ==================================================
    Muestra las materias equipadas.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra todas las materias que están equipadas en la Buster Sword.
    ================================================== 
    */
    private void mostrarEquipadas(){
        if(Cloud.getBusterSword().getMateriasEquipadas().isEmpty()){
            System.out.println();
            System.out.println("No hay materias equipadas.");
            return;
        }

        for (int i=0; i<Cloud.getBusterSword().getMateriasEquipadas().size(); i++){
            Materia materia= Cloud.getBusterSword().getMateriasEquipadas().get(i);
            System.out.println((i+1) + ") " + materia.getNombre() + " [" + materia.getElemento() + "]");
        }
    }


    /*
    ==================================================
    Equipar una materia.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra la mochila, pide una materia y la equipa en la Buster Sword si existe espacio disponible.
    ================================================== 
    */
    private void equiparMateria(){
        if(Cloud.getMochila().isEmpty()){
            System.out.println();
            System.out.println("No hay materias en la mochila para equipar.");
            return;
        }

        System.out.println();
        mostrarMochila();
        System.out.println();
        System.out.print("Elegir materia a equipar: ");

        try{
            int indice= Integer.parseInt(scanner.nextLine().trim()) - 1;

            if(indice<0 || indice>=Cloud.getMochila().size()){
                System.out.println();
                System.out.println("Opción inválida.");
                return;
            }

            Materia materia= Cloud.getMochila().get(indice);

            if(Cloud.getBusterSword().equiparMateria(materia)){
                System.out.println();
                System.out.println("Materia equipada.");
            }
            else{
                System.out.println();
                System.out.println("No hay ranuras disponibles.");
            }
        }
        catch(NumberFormatException e){
            System.out.println();
            System.out.println("Opción inválida.");
        }
    }


    /*
    ==================================================
    Desequipa una materia.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra las materias equipadas, pide una materia y la devuelve a la mochila si la elección es válida.
    ================================================== 
    */
    private void desequiparMateria(){
        if(Cloud.getBusterSword().getMateriasEquipadas().isEmpty()){
            System.out.println();
            System.out.println("No hay materias equipadas.");
            return;
        }

        System.out.println();
        mostrarEquipadas();
        System.out.println();
        System.out.print("Elegir materia a desequipar: ");

        try{
            int indice= Integer.parseInt(scanner.nextLine().trim()) - 1;

            if(indice<0 || indice>=Cloud.getBusterSword().getMateriasEquipadas().size()){
                System.out.println();
                System.out.println("Opción inválida.");
                return;
            }

            Materia materia= Cloud.getBusterSword().getMateriasEquipadas().get(indice);
            Cloud.getBusterSword().desequiparMateria(materia);
           
            System.out.println();
            System.out.println("Materia desequipada.");
        }
        catch(NumberFormatException e){
            System.out.println();
            System.out.println("Opción inválida.");
        }
    }


    /*
    ==================================================
    Descansa en la posada.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función permite recuperar HP y MP al máximo solo si Cloud está en el Sector 7.
    ================================================== 
    */
    private void descansar(){
        if(zonaActual!=sector7){
            System.out.println();
            System.out.println("Solo puedes descansar en Sector 7.");
            return;
        }

        Cloud.getStats().restaurarTodo();
        System.out.println();
        System.out.println("Cloud descansa en una posada del Sector 7...");
        System.out.println("¡HP y MP restaurados al máximo!");
    }
}