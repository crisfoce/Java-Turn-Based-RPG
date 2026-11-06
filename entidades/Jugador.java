package entidades;

import java.util.ArrayList;
import java.util.List;
import componentes.*;

public class Jugador{
    public String nombre= "Cloud";
    private int nivel;
    private int xpActual;
    private int chatarra;
    private int limiteActual;
    private Estadisticas stats;
    private List<Materia> mochila;
    private Arma busterSword;
    private boolean derrotaReciente;


    /*
    ==================================================
    Crea al jugador.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa a Cloud con nivel 1, experiencia 0, chatarra 0, barra de límite en 0, sus estadísticas base, mochila vacía y su Buster Sword.
    ================================================== 
    */
    public Jugador(){
        nivel= 1;
        xpActual= 0;
        chatarra= 0;
        limiteActual= 0;
        stats= new Estadisticas(200, 50, 15, 15);
        mochila= new ArrayList<Materia>();
        busterSword= new Arma();
        derrotaReciente= false;
    }


    /*
    ==================================================
    Recibe experiencia.
    ***
    Parámetro 1: xp el cual es un int, es la experiencia que gana Cloud.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función suma experiencia a Cloud y revisa si alcanza para subir de nivel. Al subir, aumenta sus estadísticas y recupera HP y MP.
    ================================================== 
    */
    public void recibirXP(int xp){
        xpActual+= Math.max(0, xp);

        while(xpActual>=10*nivel){
            xpActual-= 10*nivel;
            nivel++;
            stats.aumentarHpMaximo(10);
            stats.aumentarMpMaximo(5);
            stats.aumentarFuerza(4);
            stats.aumentarMagia(6);
            stats.restaurarTodo();

            System.out.println("¡LEVEL UP! Cloud ha alcanzado el Nivel " + nivel + ".");
            System.out.println("Tus estadísticas máximas han aumentado y te has curado por completo.");
        }
    }


    /*
    ==================================================
    Recibe chatarra.
    ***
    Parámetro 1: cantidad el cual es un int, es la cantidad de chatarra que se quiere agregar.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función suma chatarra a Cloud, evitando agregar cantidades negativas.
    ================================================== 
    */
    public void recibirChatarra(int cantidad){
        chatarra+= Math.max(0, cantidad);
    }


    /*
    ==================================================
    Gasta chatarra.
    ***
    Parámetro 1: cantidad el cual es un int, es la cantidad de chatarra que se quiere gastar.
    ***
    Tipo de retorno: boolean, devuelve true si se pudo gastar y false si no alcanzaba la chatarra.
    ***
    La función revisa si Cloud tiene chatarra suficiente. Si alcanza, descuenta la cantidad indicada.
    ================================================== 
    */
    public boolean gastarChatarra(int cantidad){
        if(chatarra<cantidad){
            return false;
        }

        chatarra-=cantidad;
        return true;
    }


    /*
    ==================================================
    Agrega una materia a la mochila.
    ***
    Parámetro 1: materia el cual es Materia, es la materia que se quiere guardar.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función añade una materia a la mochila de Cloud.
    ================================================== 
    */
    public void agregarMateria(Materia materia){
        mochila.add(materia);
    }


    /*
    ==================================================
    Penaliza una derrota.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función elimina la chatarra y las materias de la mochila, restaura HP y MP, y marca que Cloud fue derrotado recientemente.
    ================================================== 
    */
    public void penalizarDerrota(){
        chatarra= 0;
        mochila.clear();
        stats.restaurarTodo();
        derrotaReciente= true;
    }


    /*
    ==================================================
    Carga límite por daño recibido.
    ***
    Parámetro 1: dano el cual es un int, es el daño que recibió Cloud.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función aumenta la barra de límite según el daño recibido, sin permitir que pase de 100.
    ================================================== 
    */
    public void cargarLimitePorDanoRecibido(int dano){
        limiteActual= Math.min(100, limiteActual + Math.max(0, dano)/2);
    }


    /*
    ==================================================
    Carga límite por daño realizado.
    ***
    Parámetro 1: dano el cual es un int, es el daño que Cloud hizo al enemigo.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función aumenta la barra de límite según el daño realizado, sin permitir que pase de 100.
    ================================================== 
    */
    public void cargarLimitePorDanoRealizado(int dano){
        limiteActual= Math.min(100, limiteActual + Math.max(0, dano)/5);
    }


    /*
    ==================================================
    Reinicia la barra de límite.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función deja la barra de límite de Cloud en 0 después de usar el ataque límite.
    ================================================== 
    */
    public void reiniciarLimite(){
        limiteActual= 0;
    }

    public boolean isDerrotaReciente(){
        return derrotaReciente;
    }

    public void setDerrotaReciente(boolean derrotaReciente){
        this.derrotaReciente= derrotaReciente;
    }


    /*
    ==================================================
    Limpia la derrota reciente.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función quita la marca de derrota reciente para que Cloud no sea enviado nuevamente al Sector 7.
    ================================================== 
    */
    public void limpiarDerrotaReciente(){
        derrotaReciente= false;
    }

    public int getNivel(){
        return nivel;
    }

    public void setNivel(int nivel){
        this.nivel= Math.max(1, nivel);
    }

    public int getXpActual(){
        return xpActual;
    }

    public void setXpActual(int xpActual){
        this.xpActual= Math.max(0, xpActual);
    }

    public int getChatarra(){
        return chatarra;
    }

    public void setChatarra(int chatarra){
        this.chatarra= Math.max(0, chatarra);
    }

    public int getLimiteActual(){
        return limiteActual;
    }

    public void setLimiteActual(int limiteActual){
        this.limiteActual= Math.max(0, Math.min(100, limiteActual));
    }

    public Estadisticas getStats(){
        return stats;
    }

    public void setStats(Estadisticas stats){
        if(stats!=null){
            this.stats= stats;
        }
    }

    public List<Materia> getMochila(){
        return mochila;
    }

    public void setMochila(List<Materia> mochila){
        if(mochila!=null){
            this.mochila= mochila;
        }
    }

    public Arma getBusterSword(){
        return busterSword;
    }

    public void setBusterSword(Arma busterSword){
        if(busterSword!=null){
            this.busterSword= busterSword;
        }
    }

    public class Arma{
        public String nombre= "Buster Sword";
        private List<Materia> materiasEquipadas;


        /*
        ==================================================
        Crea el arma de Cloud.
        ***
        Parámetro 1: no recibe parámetros.
        ***
        Tipo de retorno: no tiene, es un constructor.
        ***
        La función inicializa la Buster Sword con una lista de materias equipadas vacía.
        ================================================== 
        */
        public Arma(){
            materiasEquipadas= new ArrayList<Materia>();
        }
        

        /*
        ==================================================
        Equipa una materia.
        ***
        Parámetro 1: materia el cual es Materia, es la materia que se quiere equipar.
        ***
        Tipo de retorno: boolean, devuelve true si se pudo equipar y false si no hay espacio.
        ***
        La función equipa una materia en el arma si hay ranuras disponibles y elimina de la mochila.
        ================================================== 
        */
        public boolean equiparMateria(Materia materia){
            if(materiasEquipadas.size()>=5){
                return false;
            }

            materiasEquipadas.add(materia);
            mochila.remove(materia);
            return true;
        }


        /*
        ==================================================
        Desequipa una materia.
        ***
        Parámetro 1: materia el cual es Materia, es la materia que se quiere quitar del arma.
        ***
        Tipo de retorno: void, no devuelve valor.
        ***
        La función quita una materia equipada y la devuelve a la mochila de Cloud.
        ================================================== 
        */
        public void desequiparMateria(Materia materia){
            if(materiasEquipadas.remove(materia)){
                mochila.add(materia);
            }
        }


        /*
        ==================================================
        Calcula el daño magico.
        ***
        Parámetro 1: elemento el cual es Elemento, es el elemento de la magia que se quiere usar.
        ***
        Tipo de retorno: int, devuelve el daño mágico base calculado.
        ***
        La función calcula el daño mágico usando la magia de Cloud y la cantidad de materias iguales equipadas.
        ================================================== 
        */
        public int calcularDanoMagico(Elemento elemento){
            int n= contarMaterias(elemento);
            return (int) Math.floor(stats.getMagia()*(1.0 + 0.5*n));
        }


        /*
        ==================================================
        Calcula el daño físico.
        ***
        Parámetro 1: no recibe parámetros.
        ***
        Tipo de retorno: int, devuelve el daño físico calculado.
        ***
        La función calcula el daño físico usando la fuerza de Cloud.
        ================================================== 
        */
        public int calcularDanoFisico(){
            return (int) Math.floor(stats.getFuerza()*1.25);
        }


        /*
        ==================================================
        Calcula el daño límite.
        ***
        Parámetro 1: no recibe parámetros.
        ***
        Tipo de retorno: int, devuelve el daño del ataque límite.
        ***
        La función calcula el daño del ataque límite usando la fuerza de Cloud.
        ================================================== 
        */
        public int calcularDanoLimite(){
            return stats.getFuerza()*5;
        }


        /*
        ==================================================
        Calcula el costo de MP.
        ***
        Parámetro 1: elemento el cual es Elemento, es el elemento de la magia que se quiere usar.
        ***
        Tipo de retorno: int, devuelve el costo de MP de la magia.
        ***
        La función calcula cuánto MP cuesta usar en magia según la cantidad de materias iguales equipadas.
        ================================================== 
        */
        public int calcularCostoMP(Elemento elemento){
            int n= contarMaterias(elemento);
            return 10 + 5 * n;
        }


        /*
        ==================================================
        Revisa si se puede usar un elemento.
        ***
        Parámetro 1: elemento el cual es Elemento, es el elemento que se quiere usar.
        ***
        Tipo de retorno: boolean, devuelve true si hay al menos una materia de ese elemento equipada y false si no.
        ***
        La función revisa si Cloud tiene equipada una materia del elemento indicado.
        ================================================== 
        */
        public boolean puedeUsarElemento(Elemento elemento){
            return contarMaterias(elemento)>0;
        }


        /*
        ==================================================
        Cuenta materias de un elemento.
        ***
        Parámetro 1: elemento el cual es Elemento, es el elemento que se quiere contar.
        ***
        Tipo de retorno: int, devuelve la cantidad de materias equipadas de ese elemento.
        ***
        La función recorre las materias equipadas y cuenta cuántas coinciden con el elemento recibido.
        ================================================== 
        */
        public int contarMaterias(Elemento elemento){
            int total= 0;

            for(Materia materia : materiasEquipadas){
                if(materia.getElemento()==elemento){
                    total++;
                }
            }
            return total;
        }

        public List<Materia> getMateriasEquipadas(){
            return materiasEquipadas;
        }

        public void setMateriasEquipadas(List<Materia> materiasEquipadas){
            if(materiasEquipadas!=null){
                this.materiasEquipadas= materiasEquipadas;
            }
        }
    }
}