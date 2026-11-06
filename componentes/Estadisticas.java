package componentes;

public class Estadisticas{
    private int hpActual;
    private int hpMaximo;
    private int mpActual;
    private int mpMaximo;
    private int fuerza;
    private int magia;

    /*
    ==================================================
    Crea las estadísticas iniciales.
    ***
    Parámetro 1: hpMaximo el cual es un int, es la vida máxima inicial.
    Parámetro 2: mpMaximo el cual es un int, es la magia máxima inicial.
    Parámetro 3: fuerza el cual es un int, es la fuerza inicial.
    Parámetro 4: magia el cual es un int, es la magia inicial.
    ***
    Tipo de retorno: no tiene, es un constructor.
    ***
    La función inicializa la vida, la magia, la fuerza y la magia base. También deja el hp y el mp actuales al máximo.
    ================================================== 
    */
    public Estadisticas(int hpMaximo, int mpMaximo, int fuerza, int magia){
        this.hpMaximo= hpMaximo;
        this.hpActual= hpMaximo;
        this.mpMaximo= mpMaximo;
        this.mpActual= mpMaximo;
        this.fuerza= fuerza;
        this.magia= magia;
    }


    /*
    ==================================================
    Recibe el daño y baja el HP actual.
    ***
    Parámetro 1: valor el cual es un int, es el daño que se quiere aplicar.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función resta vida según el daño recibido, pero nunca deja que el HP baje de 0.
    ================================================== 
    */
    public void recibirDMG(int valor){
        hpActual= Math.max(0, hpActual - Math.max(0, valor));
    }


    /*
    ==================================================
    Cura HP.
    ***
    Parámetro 1: valor el cual es un int, es la cantidad de vida que se quiere recuperar.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función aumenta el HP actual, pero no permite que pase del HP máximo.
    ================================================== 
    */
    public void curar(int valor){
        hpActual= Math.min(hpMaximo, hpActual + Math.max(0, valor));
    }


    /*
    ==================================================
    Gasta MP.
    ***
    Parámetro 1: valor el cual es un int, es la cantidad de MP que se quiere gastar.
    ***
    Tipo de retorno: boolean, devuelve true si se pudo gastar MP y false si no alcanzaba.
    ***
    La función revisa si hay MP suficiente. Si alcanza, descuenta el MP y confirma que la acción se pudo hacer.
    ================================================== 
    */
    public boolean gastarMP(int valor){
        if(mpActual<valor){
            return false;
        }

        mpActual-=valor;
        return true;
    }


    /*
    ==================================================
    Restaura todo el HP.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función deja el HP actual igual al HP máximo.
    ================================================== 
    */
    public void restaurarHP(){
        hpActual= hpMaximo;
    }


    /*
    ==================================================
    Restaura todo el MP.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función deja el MP actual igual al MP máximo.
    ================================================== 
    */
    public void restaurarMP(){
        mpActual= mpMaximo;
    }


    /*
    ==================================================
    Restaura todo el HP y MP.
    ***
    Parámetro 1: no recibe parámetros.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función recupera por completo la vida y la magia del personaje.
    ================================================== 
    */
    public void restaurarTodo(){
        restaurarHP();
        restaurarMP();
    }


    /*
    ==================================================
    Fija el HP actual.
    ***
    Parámetro 1: valor el cual es un int, es el HP que se quiere asignar.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función cambia el HP actual, cuidando que no quede bajo 0 ni sobre el HP máximo.
    ================================================== 
    */
    public void fijarHP(int valor){
        hpActual= Math.max(0, Math.min(hpMaximo, valor));
    }


    /*
    ==================================================
    Aumenta el HP máximo.
    ***
    Parámetro 1: valor el cual es un int, es la cantidad que se suma al HP máximo.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función sube la vida máxima y también aumenta la vida actual en la misma cantidad.
    ================================================== 
    */
    public void aumentarHpMaximo(int valor){
        hpMaximo+=valor;
        hpActual+=valor;
    }


    /*
    ==================================================
    Aumenta el MP máximo.
    ***
    Parámetro 1: valor el cual es un int, es la cantidad que se suma al MP máximo.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función sube la magia máxima y también aumenta la magia actual en la misma cantidad.
    ================================================== 
    */
    public void aumentarMpMaximo(int valor){
        mpMaximo+=valor;
        mpActual+=valor;
    }


    /*
    ==================================================
    Aumenta la fuerza.
    ***
    Parámetro 1: valor el cual es un int, es la cantidad que se suma a la fuerza.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función aumenta la fuerza base usada para calcular el daño físico.
    ================================================== 
    */
    public void aumentarFuerza(int valor){
        fuerza+=valor;
    }


    /*
    ==================================================
    Aumenta la magia.
    ***
    Parámetro 1: valor el cual es un int, es la cantidad que se suma a la magia.
    ***
    Tipo de retorno: void, no devuelve valor.
    ***
    La función aumenta la magia base usada para calcular el daño mágico y las curaciones.
    ================================================== 
    */
    public void aumentarMagia(int valor){
        magia+=valor;
    }

    public int getHpActual(){
        return hpActual;
    }

    public void setHpActual(int hpActual){
        this.hpActual= Math.max(0, Math.min(hpMaximo, hpActual));
    }

    public int getHpMaximo(){
        return hpMaximo;
    }

    public void setHpMaximo(int hpMaximo){
        this.hpMaximo= Math.max(1, hpMaximo);

        if(hpActual>this.hpMaximo){
            hpActual= this.hpMaximo;
        }
    }

    public int getMpActual(){
        return mpActual;
    }

    public void setMpActual(int mpActual){
        this.mpActual= Math.max(0, Math.min(mpMaximo, mpActual));
    }

    public int getMpMaximo(){
        return mpMaximo;
    }

    public void setMpMaximo(int mpMaximo){
        this.mpMaximo= Math.max(0, mpMaximo);

        if(mpActual>this.mpMaximo){
            mpActual= this.mpMaximo;
        }
    }

    public int getFuerza(){
        return fuerza;
    }

    public void setFuerza(int fuerza){
        this.fuerza= Math.max(0, fuerza);
    }

    public int getMagia(){
        return magia;
    }

    public void setMagia(int magia){
        this.magia= Math.max(0, magia);
    }
}