package entidades;

import componentes.Estadisticas;

public class Sephiroth extends Enemigo{
    private int contadorSuperNova;

    /*
    ==================================================
    Crea a Sephiroth.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa a Sephiroth con sus estadísticas base y deja el contador de SuperNova en 10.
    ================================================== 
    */
    public Sephiroth(){
        super("Sephiroth", 0, 0, new Estadisticas(500, 0, 40, 0));
        contadorSuperNova= 10;
    }


    /*
    ==================================================
    Reinicia SuperNova.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función vuelve a dejar el contador de SuperNova en 10.
    ================================================== 
    */
    public void lanzarSuperNova(){
        contadorSuperNova= 10;
    }


    /*
    ==================================================
    Lanza SuperNova contra Cloud.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que recibe el ataque.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función deja a Cloud sin HP usando SuperNova y luego reinicia el contador del ataque.
    ================================================== 
    */
    public void lanzarSuperNova(Jugador Cloud){
        Cloud.getStats().recibirDMG(Cloud.getStats().getHpActual());
        System.out.println("Sephiroth lanzó SuperNova.");
        lanzarSuperNova();
    }


    /*
    ==================================================
    Ataca a Cloud.
    ***
    Parámetro 1: Cloud el cual es un Jugador, es el jugador que recibe el ataque.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función revisa el contador de SuperNova. Si llegó a 0, lanza SuperNova, si no, baja el contador y hace un ataque normal.
    ================================================== 
    */
    @Override
    public void atacar(Jugador Cloud){
        if(contadorSuperNova<=0){
            lanzarSuperNova(Cloud);
            return;
        }

        System.out.println("SuperNova en: " + contadorSuperNova + " turno(s).");

        contadorSuperNova--;
        super.atacar(Cloud);
    }


    /*
    ==================================================
    Obtiene la precisión de Sephiroth.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: double, devuelve la probabilidad de acertar el ataque.
    ***
    La función entrega la precisión especial de Sephiroth para sus ataques normales.
    ================================================== 
    */
    @Override
    protected double getPrecision(){
        return 0.90;
    }


    /*
    ==================================================
    Reinicia el contador de SuperNova.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función vuelve a dejar el contador de SuperNova en 10.
    ================================================== 
    */
    public void reiniciarContadorSuperNova(){
        contadorSuperNova= 10;
    }

    public int getContadorSuperNova(){
        return contadorSuperNova;
    }

    public void setContadorSuperNova(int contadorSuperNova){
        this.contadorSuperNova= Math.max(0, contadorSuperNova);
    }
}