package componentes;

/*
==================================================
 Crea una materia.
 ***
 Parámetro 1: nombre el cual es un String, es el nombre de la materia.
 Parámetro 2: elemento el cual es un Elemento, es el tipo elemental que tendrá la materia.
 ***
 Tipo de retorno: no tiene, es un constructor.
 ***
 La función inicializa una materia con su nombre y el elemento que permite usar en combate.
================================================== 
*/
public class Materia{
    private String nombre;
    private Elemento elemento;

    public Materia(String nombre, Elemento elemento){
        this.nombre= nombre;
        this.elemento= elemento;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre= nombre;
    }

    public Elemento getElemento(){
        return elemento;
    }

    public void setElemento(Elemento elemento){
        this.elemento= elemento;
    }
}