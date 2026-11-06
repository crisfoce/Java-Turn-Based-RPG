package componentes;

/*
==================================================
Crea una mejora para la tienda.
***
Parámetro 1: nombre el cual es un String, es el nombre de la mejora.
Parámetro 2: costoChatarra el cual es un int, es la cantidad de chatarra que cuesta comprarla.
Parámetro 3: statAfectado el cual es un TipoStat, indica que estadística va a mejorar.
Parámetro 4: valorBono el cual es un int, es la cantidad que se suma a la estadística.
***
Tipo de retorno: no tiene, es un constructor.
***
La función inicializa una mejora con su nombre, costo, estadística afectada y valor del bono.
==================================================
*/
public class Mejora{
    private String nombre;
    private int costoChatarra;
    private TipoStat statAfectado;
    private int valorBono;

    public Mejora(String nombre, int costoChatarra, TipoStat statAfectado, int valorBono){
        this.nombre= nombre;
        this.costoChatarra= costoChatarra;
        this.statAfectado= statAfectado;
        this.valorBono= valorBono;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre= nombre;
    }

    public int getCostoChatarra(){
        return costoChatarra;
    }

    public void setCostoChatarra(int costoChatarra){
        this.costoChatarra= Math.max(0, costoChatarra);
    }

    public TipoStat getStatAfectado(){
        return statAfectado;
    }

    public void setStatAfectado(TipoStat statAfectado){
        this.statAfectado= statAfectado;
    }

    public int getValorBono(){
        return valorBono;
    }

    public void setValorBono(int valorBono){
        this.valorBono= valorBono;
    }
}