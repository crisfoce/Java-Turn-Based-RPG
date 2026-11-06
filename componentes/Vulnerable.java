package componentes;

/*
==================================================
 Evalúa la debilidad frente a un elemento.
 ***
 Parámetro 1: elementoMagia el cual es un Elemento, es el elemento usado para atacar.
 ***
 Tipo de retorno: double, devuelve el multiplicador de daño según debilidad, resistencia, inmunidad o daño normal.
 ***
 La función permite saber cuánto daño debe recibir un enemigo según el elemento que se usó contra él.
================================================== 
*/
public interface Vulnerable{
    double evaluarDebilidad(Elemento elementoMagia);
}