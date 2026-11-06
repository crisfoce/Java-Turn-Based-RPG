# Java Turn-Based RPG
Proyecto académico desarrollado en Java que implementa un juego de rol (RPG) por turnos, inspirado en el universo de Final Fantasy VII.

El jugador controla a Cloud, quien debe explorar distintas zonas, enfrentarse a enemigos, adquirir mejoras y fortalecer sus habilidades para alcanzar el Núcleo del Planeta y combatir contra Sephiroth.

El juego se ejecuta mediante una interfaz de consola y utiliza principios de programación orientada a objetos para organizar sus entidades, componentes y mecánicas.



## Funcionalidades implementadas
### 1. Sistema de combate
- Combates por turnos contra distintos tipos de enemigos.
- Ataques físicos y mágicos.
- Sistema de daño elemental con debilidades, resistencias e inmunidades.
- Consumo y recuperación de puntos de vida (HP) y magia (MP).
- Ataque límite con una barra que se carga durante los combates.
- Obtención de experiencia y recompensas al derrotar enemigos.

### 2. Progresión del jugador
- Sistema de niveles y experiencia.
- Incremento de estadísticas al subir de nivel.
- Recuperación de HP y MP al alcanzar un nuevo nivel.
- Obtención de chatarra como recurso para adquirir mejoras.
- Gestión del inventario de materias.
- Equipamiento de materias en la Buster Sword, con un máximo de cinco espacios.

### 3. Exploración de zonas
El juego contempla tres zonas principales:

#### Sector 7
- Zona inicial del jugador.
- Acceso al simulador de combate.
- Tienda de chatarra para adquirir mejoras.
- Gestión de materias equipadas.
- Descanso para recuperar recursos.
- Viajes a otras zonas.

#### Gongaga
- Exploración con encuentros contra enemigos salvajes.
- Obtención de recompensas y materias.
- Acceso condicionado por el nivel del jugador.

#### Núcleo del Planeta
- Zona del enfrentamiento final contra Sephiroth.
- Acceso condicionado por el nivel del jugador y la cantidad de materias equipadas.
- Combate contra el jefe final, que dispone del ataque especial SuperNova.



## Tecnologías utilizadas
- **Java:** implementación del juego, sus entidades y mecánicas.
- **Programación orientada a objetos:** uso de clases, objetos, herencia, polimorfismo, encapsulamiento e interfaces.
- **Makefile:** automatización de la compilación y ejecución.
- **Consola:** interfaz de interacción del jugador con el sistema.



## Estructura del repositorio
```text
Java-Turn-Based-RPG/
├── componentes/
│   ├── Elemento.java
│   ├── Estadisticas.java
│   ├── Materia.java
│   ├── Mejora.java
│   ├── TipoStat.java
│   └── Vulnerable.java
│
├── entidades/
│   ├── Enemigo.java
│   ├── EnemigoSalvaje.java
│   ├── EnemigoSimulador.java
│   ├── Jugador.java
│   └── Sephiroth.java
│
├── mapa/
│   ├── Gongaga.java
│   ├── NucleoPlaneta.java
│   ├── Sector7.java
│   └── Zona.java
│
├── Main.java
├── Makefile
├── .gitignore
└── README.md
```



## Organización del código
- `componentes/`: contiene las estadísticas, materias, mejoras, tipos elementales e interfaces utilizadas por el juego.
- `entidades/`: implementa al jugador y los distintos tipos de enemigos.
- `mapa/`: define las zonas y la lógica relacionada con exploración y combate.
- `Main.java`: punto de entrada del programa y gestión del menú principal.
- `Makefile`: contiene las instrucciones para compilar, ejecutar y limpiar los archivos generados.



## Instalación y ejecución
### Requisitos
Para compilar y ejecutar el proyecto se requiere:
- Java Development Kit (JDK), probado con Java 21.
- Herramienta GNU Make.
- Terminal compatible con los comandos del Makefile.

El proyecto fue desarrollado para ejecutarse en un entorno Linux, utilizando WSL con Ubuntu.

### Compilación
Desde la carpeta principal del proyecto, ejecutar:

```bash
make clean && make
```

Este comando elimina los archivos de compilación anteriores y compila el código fuente.

### Ejecución
Para iniciar el juego, ejecutar:

```bash
make run
```
El programa mostrará el menú principal en la consola y permitirá interactuar mediante las opciones disponibles.

El jugador comienza en Sector 7, controlando a Cloud en nivel 1.



## Consideraciones del juego
- El simulador de combate puede generar uno o dos enemigos por enfrentamiento.
- Al subir de nivel, el jugador aumenta sus estadísticas y recupera completamente HP y MP.
- El acceso a Gongaga requiere alcanzar el nivel correspondiente.
- El acceso al Núcleo del Planeta requiere alcanzar el nivel correspondiente y tener al menos dos materias equipadas.
- Las materias equipadas permiten utilizar magias elementales y de curación.
- La barra del límite aumenta al realizar y recibir daño, y se reinicia después de utilizarlo.
- Durante el combate final, Sephiroth dispone de un contador para SuperNova que disminuye con sus turnos y se reinicia cuando Cloud utiliza su ataque límite.
- Los combates fuera del simulador pueden provocar la derrota del jugador y la pérdida de determinados recursos.



## Contexto académico
Proyecto desarrollado durante el primer semestre de 2026 para la asignatura Lenguajes de Programación de la carrera de Ingeniería Civil Informática de la Universidad Técnica Federico Santa María (USM).

El proyecto corresponde a una implementación académica con fines educativos.