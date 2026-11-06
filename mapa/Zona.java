package mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import entidades.*;
import componentes.*;

public abstract class Zona{
    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;
    protected Random random;


    /*
    ==================================================
    Crea una zona.
    ***
    Parámetro 1: nombre el cual es un String, es el nombre de la zona.
    Parámetro 2: nivelRequerido el cual es un int, es el nivel mínimo para poder entrar.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa una zona con su nombre, nivel requerido, lista de enemigos disponibles y generador aleatorio.
    ================================================== 
    */
    public Zona(String nombre, int nivelRequerido){
        this.nombre= nombre;
        this.nivelRequerido= nivelRequerido;
        this.enemigosDisponibles= new ArrayList<Enemigo>();
        this.random= new Random();
    }


    /*
    ==================================================
    Ejecuta la acción principal de la zona.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que está usando la zona.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función queda definida para que cada zona concreta implemente una propia acción.
    ================================================== 
    */
    public abstract void accionZona(Jugador Cloud);


    /*
    ==================================================
    Valida si Cloud puede entrar a la zona.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que intenta entrar.
    ***
    Tipo de retorno: boolean, devuelve true si Cloud cumple con el nivel requerido y false si no.
    ***
    La función revisa si el nivel de Cloud alcanza para acceder a la zona.
    ================================================== 
    */
    public boolean validarAcceso(Jugador Cloud){
        return Cloud.getNivel()>=nivelRequerido;
    }


    /*
    ==================================================
    Inicia el combate base.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que participa en combate.
    Parámetro 2: enemigos el cual es List<Enemigo>, es el grupo de enemigos que aparecerá.
    Parámetro 3: combateSeguro el cual es boolean, indica si Cloud no puede morir en ese combate.
    Parámetro 4: permiteHuida el cual es boolean, indica si Cloud puede intentar huir.
    Parámetro 5: scanner el cual es Scanner, es el lector usado para recibir las acciones del jugador.
    ***
    Tipo de retorno: boolean, devuelve true si Cloud gana el combate y false si pierde o huyó.
    ***
    La función controla el ciclo del combate, muestra el turno, recibe acciones, ejecuta ataques, turnos enemigos y entrega recompensas al ganar.
    ================================================== 
    */
    protected boolean iniciarCombateBase(Jugador Cloud, List<Enemigo> enemigos, boolean combateSeguro, boolean permiteHuida, Scanner scanner){
        List<Enemigo> enemigosOriginales= new ArrayList<Enemigo>(enemigos);

        while(true){
            limpiarDerrotados(enemigos);

            if(enemigos.isEmpty()){
                entregarRecompensas(Cloud, enemigosOriginales);
                return true;
            }

            if(Cloud.getStats().getHpActual()<=0){
                if(combateSeguro){
                    Cloud.getStats().fijarHP(1);
                }
                return false;
            }

            mostrarEstadoCombate(Cloud, enemigos, permiteHuida);

            String opcion= scanner.nextLine().trim();
            boolean accionRealizada= false;

            if(opcion.equals("1")){
                accionRealizada= accionFisica(Cloud, enemigos, scanner);
            }
            else if(opcion.equals("2")){
                accionRealizada= accionMagica(Cloud, enemigos, scanner);
            }
            else if(opcion.equals("3") && Cloud.getLimiteActual()>=100){
                accionRealizada= accionLimite(Cloud, enemigos, scanner);
            }
            else if(opcion.equals("3") && Cloud.getLimiteActual()<100 && permiteHuida){
                if(random.nextDouble()<0.50){
                    System.out.println();
                    System.out.println("Cloud huyó del combate.");
                    return false;
                }

                System.out.println();
                System.out.println("Cloud falló al intentar huir.");
                System.out.println();
                accionRealizada= true;
            }
            else if (opcion.equals("4") && Cloud.getLimiteActual()>=100 && permiteHuida){
                if(random.nextDouble()<0.50){
                    System.out.println();
                    System.out.println("Cloud huyó del combate.");
                    return false;
                }

                System.out.println();
                System.out.println("Cloud falló al intentar huir.");
                System.out.println();
                accionRealizada= true;
            }
            else{
                System.out.println();
                System.out.println("Opción inválida.");
            }

            limpiarDerrotados(enemigos);

            if(enemigos.isEmpty()){
                entregarRecompensas(Cloud, enemigosOriginales);
                return true;
            }

            if(accionRealizada){
                turnoEnemigos(Cloud, enemigos);
            }

            if(Cloud.getStats().getHpActual()<=0){
                if(combateSeguro){
                    Cloud.getStats().fijarHP(1);
                    System.out.println("El combate seguro terminó. Cloud queda con 1 HP.");
                }

                return false;
            }
        }
    }


    /*
    ==================================================
    Muestra el estado del combate.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que está en combate.
    Parámetro 2: enemigos el cual es List<Enemigo>, es la lista de enemigos vivos.
    Parámetro 3: permiteHuida el cual es boolean, indica si se debe mostrar la opción de huir.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función muestra el turno de Cloud, los enemigos presentes y las acciones disponibles según la barra de límite.
    ================================================== 
    */
    private void mostrarEstadoCombate(Jugador Cloud, List<Enemigo> enemigos, boolean permiteHuida){
        System.out.println();
        System.out.println("=== TU TURNO ===");
        System.out.println("HP Cloud: " + Cloud.getStats().getHpActual() + "/" + Cloud.getStats().getHpMaximo());
        System.out.println("MP Cloud: " + Cloud.getStats().getMpActual() + "/" + Cloud.getStats().getMpMaximo());
        System.out.println();

        if(enemigos.size()==1){
            Enemigo enemigo= enemigos.get(0);
            System.out.println("Enemigo: " + enemigo.getNombre() + " | HP: " + enemigo.getStats().getHpActual());
        }
        else{
            mostrarEnemigos(enemigos);
        }

        System.out.println("1. Atacar Físicamente");
        System.out.println("2. Magia (Requiere MP)");

        if(Cloud.getLimiteActual()>=100){
            System.out.println("3. Ataque Límite");

            if(permiteHuida){
                System.out.println("4. Huir");
            }
        }
        else{
            if(permiteHuida){
                System.out.println("3. Huir");
            }
        }

        System.out.print("> Selecciona una acción: ");
    }


    /*
    ==================================================
    Realiza un ataque físico.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que ataca.
    Parámetro 2: enemigos el cual es List<Enemigo>, es la lista de enemigos disponibles como objetivo.
    Parámetro 3: scanner el cual es Scanner, es el lector usado para elegir objetivo si hay más de uno.
    ***
    Tipo de retorno: boolean, devuelve true si se realizó el ataque y false si no se pudo.
    ***
    La función elige un objetivo, calcula el daño físico de Cloud, aplica debilidades si corresponde y daña al enemigo.
    ================================================== 
    */
    private boolean accionFisica(Jugador Cloud, List<Enemigo> enemigos, Scanner scanner){
        Enemigo objetivo= elegirObjetivo(enemigos, scanner);

        if(objetivo==null){
            return false;
        }

        int danoBase= Cloud.getBusterSword().calcularDanoFisico();
        int danoMostrado= danoBase;

        if(objetivo instanceof Vulnerable){
            double multiplicador= ((Vulnerable) objetivo).evaluarDebilidad(Elemento.FISICO);
            danoMostrado= (int) Math.floor(danoBase * multiplicador);
        }

        aplicarDano(Cloud, objetivo, danoBase, Elemento.FISICO, true);

        System.out.println();
        System.out.println("¡Cloud ataca con su Buster Sword!");
        System.out.println("Inflinges " + danoMostrado + " de daño al " + objetivo.getNombre() + ".");

        if(objetivo.estaDerrotado()){
            System.out.println();
            System.out.println("¡Has derrotado al " + objetivo.getNombre() + "!");
        }

        System.out.println();

        return true;
    }


    /*
    ==================================================
    Realiza una acción mágica.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que usa la magia.
    Parámetro 2: enemigos el cual es List<Enemigo>, es la lista de enemigos disponibles como objetivo.
    Parámetro 3: scanner el cual es Scanner, es el lector usado para elegir magia y objetivo.
    ***
    Tipo de retorno: boolean, devuelve true si se pudo usar la magia y false si no se realizó la acción.
    ***
    La función muestra las magias disponibles, revisa si Cloud tiene la materia y MP necesario, y luego aplica daño o curación.
    ================================================== 
    */
    private boolean accionMagica(Jugador Cloud, List<Enemigo> enemigos, Scanner scanner){
        List<Elemento> elementos= new ArrayList<Elemento>();
        elementos.add(Elemento.FUEGO);
        elementos.add(Elemento.HIELO);
        elementos.add(Elemento.RAYO);
        elementos.add(Elemento.CURA);

        System.out.println("Cloud intenta canalizar magia...");
        System.out.println("Elige una materia:");
        System.out.println();
        System.out.println("1. Fuego");
        System.out.println("2. Hielo");
        System.out.println("3. Rayo");
        System.out.println("4. Cura");
        System.out.println("0. Volver");
        System.out.println();
        System.out.print("> Selecciona una magia: ");

        try{
            int indice= Integer.parseInt(scanner.nextLine().trim());

            if(indice==0){
                return false;
            }

            indice= indice-1;

            if(indice<0 || indice>=elementos.size()){
                System.out.println("Opción inválida.");
                return false;
            }

            Elemento elemento= elementos.get(indice);

            if(!Cloud.getBusterSword().puedeUsarElemento(elemento)){
                System.out.println();
                System.out.println("Cloud no tiene una Materia de " + elemento + " equipada.");
                System.out.println();
                return false;
            }

            int costo= Cloud.getBusterSword().calcularCostoMP(elemento);

            if(!Cloud.getStats().gastarMP(costo)){
                System.out.println("Cloud no tiene MP suficiente.");
                return false;
            }

            if(elemento==Elemento.CURA){
                int cura= Cloud.getBusterSword().calcularDanoMagico(elemento);
                Cloud.getStats().curar(cura);
                System.out.println();
                System.out.println("Cloud usa Cura y recupera " + cura + " HP.");
                return true;
            }

            Enemigo objetivo= elegirObjetivo(enemigos, scanner);

            if(objetivo==null){
                return false;
            }

            int danoBase= Cloud.getBusterSword().calcularDanoMagico(elemento);
            int danoReal= aplicarDano(Cloud, objetivo, danoBase, elemento, true);

            System.out.println("Cloud lanzó magia " + elemento + " e hizo " + danoReal + " de daño.");

            if(objetivo.estaDerrotado()){
                System.out.println("¡Has derrotado al " + objetivo.getNombre() + "!");
            }

            return true;
        }
        catch(NumberFormatException e){
            System.out.println("Opción inválida.");
            return false;
        }
    }


    /*
    ==================================================
    Usa una curación directa.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que se quiere curar.
    ***
    Tipo de retorno: boolean, devuelve true si se pudo curar y false si faltaba materia o MP.
    ***
    La función revisa si Cloud tiene la materia CURA equipada, gasta MP y recupera HP.
    ================================================== 
    */
    private boolean accionCura(Jugador Cloud){
        Elemento elemento= Elemento.CURA;

        if(!Cloud.getBusterSword().puedeUsarElemento(elemento)){
            System.out.println("Cloud no tiene materia CURA equipada.");
            return false;
        }

        int costo= Cloud.getBusterSword().calcularCostoMP(elemento);

        if(!Cloud.getStats().gastarMP(costo)){
            System.out.println("MP insuficiente.");
            return false;
        }

        int cura= Cloud.getBusterSword().calcularDanoMagico(elemento);
        Cloud.getStats().curar(cura);

        System.out.println("Cloud recuperó " + cura + " HP.");
        return true;
    }


    /*
    ==================================================
    Realiza el ataque límite.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que usa el límite.
    Parámetro 2: enemigos el cual es List<Enemigo>, es la lista de enemigos disponibles como objetivo.
    Parámetro 3: scanner el cual es Scanner, es el lector usado para elegir objetivo si hay más de uno.
    ***
    Tipo de retorno: boolean, devuelve true si se realizó el ataque límite y false si no se pudo.
    ***
    La función aplica el daño del ataque límite, reinicia la barra de límite y reinicia SuperNova si el objetivo es Sephiroth.
    ================================================== 
    */
    private boolean accionLimite(Jugador Cloud, List<Enemigo> enemigos, Scanner scanner){
        Enemigo objetivo= elegirObjetivo(enemigos, scanner);

        if(objetivo==null){
            return false;
        }

        int danoBase= Cloud.getBusterSword().calcularDanoLimite();
        int danoMostrado= danoBase;

        aplicarDano(Cloud, objetivo, danoBase, Elemento.FISICO, false);

        Cloud.reiniciarLimite();

        if(objetivo instanceof Sephiroth){
            ((Sephiroth) objetivo).reiniciarContadorSuperNova();
        }

        System.out.println();
        System.out.println("¡Cloud libera su Ataque Límite!");
        System.out.println("Cloud hizo " + danoMostrado + " de daño al " + objetivo.getNombre() + ".");

        if(objetivo.estaDerrotado()){
            System.out.println();
            System.out.println("¡Has derrotado al " + objetivo.getNombre() + "!");
        }

        System.out.println();

        return true;
    }


    /*
    ==================================================
    Aplica daño a un enemigo.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que realiza el daño.
    Parámetro 2: enemigo el cual es Enemigo, es un enemigo que recibe el daño
    Parámetro 3: danoBase el cual es un int, es el daño antes de aplicar los multiplicadores.
    Parámetro 4: elemento el cual es Elemento, es el elemento del ataque.
    Parámetro 5: usaMultiplicadores el cual es boolean, indica si se deben aplicar debilidades o resistencias.
    ***
    Tipo de retorno: int, devuelve el daño real que recibió el enemigo.
    ***
    La función calcula el daño final, aplica el daño al enemigo y carga la barra de límite de Cloud según el daño realizado.
    ================================================== 
    */
    private int aplicarDano(Jugador Cloud, Enemigo enemigo, int danoBase, Elemento elemento, boolean usaMultiplicador){
        double multiplicador= 1.0;

        if(usaMultiplicador && enemigo instanceof Vulnerable){
            multiplicador= ((Vulnerable) enemigo).evaluarDebilidad(elemento);
        }

        int dano= (int) Math.floor(danoBase*multiplicador);
        int hpAntes= enemigo.getStats().getHpActual();

        enemigo.getStats().recibirDMG(dano);

        int hpDespues= enemigo.getStats().getHpActual();
        int danoReal= hpAntes - hpDespues;

        Cloud.cargarLimitePorDanoRealizado(danoReal);
        return danoReal;
    }


    /*
    ==================================================
    Ejecuta el turno de los enemigos.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que recibe los ataques.
    Parámetro 2: enemigos el cual es List<Enemigo>. es la lista de enemigos que pueden atacar.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función revisa los enemigos vivos y decide si atacan juntos o si ataca solo uno de ellos.
    ================================================== 
    */
    private void turnoEnemigos(Jugador Cloud, List<Enemigo> enemigos){
        List<Enemigo> vivos= enemigosVivos(enemigos);

        if(vivos.isEmpty()){
            return;
        }

        if(vivos.size()==1){
            vivos.get(0).atacar(Cloud);
            return;
        }

        double probabilidadAtaqueConjunto= vivos.size()==2 ? 0.50 : 0.33;

        if(random.nextDouble()<probabilidadAtaqueConjunto){
            System.out.println("Los enemigos atacan juntos.");

            for(Enemigo enemigo : vivos){
                if(Cloud.getStats().getHpActual()>0){
                    enemigo.atacar(Cloud);
                }
            }
        }
        else{
            Enemigo enemigo= vivos.get(random.nextInt(vivos.size()));
            enemigo.atacar(Cloud);
        }
    }


    /*
    ==================================================
    Muestra los enemigos.
    ***
    Parámetro 1: enemigos el cual es List<Enemigo>, es la lista de enemigos que se quiere mostrar.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función imprime en pantalla los enemigos detectados junto con su número y HP actual.
    ================================================== 
    */
    private void mostrarEnemigos(List<Enemigo> enemigos){
        System.out.println("Enemigos detectados:");

        for(int i=0; i<enemigos.size(); i++){
            Enemigo enemigo= enemigos.get(i);
            System.out.printf(" %d. %-20s HP: %d%n", i+1, enemigo.getNombre(), enemigo.getStats().getHpActual());
        }

        System.out.println();
    }


    /*
    ==================================================
    Elige un objetivo.
    ***
    Parámetro 1: enemigos el cual es List<Enemigo>, es la lista de enemigos disponibles.
    Parámetro 2: scanner el cual es Scanner, es el lector usado para recibir la elección del jugador.
    ***
    Tipo de retorno: Enemigo, devuelve el enemigo elegido o null si la elección no fue válida.
    ***
    La función devuelve automáticamente el único enemigo si solo hay uno. Si hay varios, pide al jugador elegir uno.
    ================================================== 
    */
    private Enemigo elegirObjetivo(List<Enemigo> enemigos, Scanner scanner){
        if(enemigos.size()==1){
            return enemigos.get(0);
        }

        System.out.println();
        mostrarEnemigos(enemigos);
        System.out.print("> Elige un objetivo: ");

        try{
            int indice= Integer.parseInt(scanner.nextLine().trim()) -1;

            if(indice<0 || indice>=enemigos.size()){
                System.out.println("Opción inválida.");
                return null;
            }

            return enemigos.get(indice);
        }
        catch(NumberFormatException e){
            System.out.println("Opción inválida.");
            return null;
        }
    }


    /*
    ==================================================
    Limpia enemigos derrotados.
    ***
    Parámetro 1: enemigos el cual es List<Enemigo>, es la lista de enemigos del combate.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función elimina de la lista a los enemigos que ya tienen 0 de HP.
    ================================================== 
    */
    private void limpiarDerrotados(List<Enemigo> enemigos){
        for(int i=enemigos.size() - 1; i>=0; i--){
            if(enemigos.get(i).estaDerrotado()){
                enemigos.remove(i);
            }
        }
    }


    /*
    ==================================================
    Obtiene los enemigos vivos.
    ***
    Parámetro 1: enemigos el cual es List<Enemigo>, es la lista de enemigos del combate.
    ***
    Tipo de retorno: List<Enemigo>, devuelve una lista con los enemigos que todavía tienen HP.
    ***
    La función recorre el grupo de enemigos y guarda solo a los que no están derrotados.
    ================================================== 
    */
    private List<Enemigo> enemigosVivos(List<Enemigo> enemigos){
        List<Enemigo> vivos= new ArrayList<Enemigo>();

        for(Enemigo enemigo : enemigos){
            if(!enemigo.estaDerrotado()){
                vivos.add(enemigo);
            }
        }

        return vivos;
    }


    /*
    ==================================================
    Entrega las recompensas del combate.
    ***
    Parámetro 1: Cloud el cual es Jugador, es el jugador que recibe las recompensas.
    Parámetro 2: enemigosOriginales el cual es List<Enemigo>. es la lista original de enemigos derrotados.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función suma la experiencia y chatarra ganada, muestra los mensajes correspondientes y entrega las recompensas a Cloud.
    ================================================== 
    */
    private void entregarRecompensas(Jugador Cloud, List<Enemigo> enemigosOriginales){
        int xpTotal= 0;
        int chatarraTotal= 0;

        for(Enemigo enemigo : enemigosOriginales){
            xpTotal+=enemigo.getXpRecompensa();

            if(enemigo instanceof EnemigoSalvaje){
                chatarraTotal+=enemigo.getChatarraRecompensa();
            }
        }

        if(xpTotal>0){
            System.out.println("¡Cloud ha ganado " + xpTotal + " de EXP!");
        }

        if(chatarraTotal>0){
            System.out.println("¡Cloud ha ganado " + chatarraTotal + " de Chatarra!");
        }

        for(Enemigo enemigo : enemigosOriginales){
            enemigo.giveXpRecompensa(Cloud);

            if(enemigo instanceof EnemigoSalvaje){
                ((EnemigoSalvaje) enemigo).giveChatarraRecompensa(Cloud);
            }
        }
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre= nombre;
    }

    public int getNivelRequerido(){
        return nivelRequerido;
    }

    public void setNivelRequerido(int nivelRequerido){
        this.nivelRequerido= Math.max(1, nivelRequerido);
    }

    public List<Enemigo> getEnemigosDisponibles(){
        return enemigosDisponibles;
    }

    public void setEnemigosDisponibles(List<Enemigo> enemigosDisponibles){
        if(enemigosDisponibles!=null){
            this.enemigosDisponibles= enemigosDisponibles;
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