# Tarea 5 - Radiografía del sistema

## Alumno

- Anxo Vázquez Lorenzo

## Asignatura

- PSP - Programación de servizos e procesos

## Niveles hechos

- Nivel 1

- Nivel 2

- Nivel 4

## Estructura del proyecto

El proyecto está constituido por dos archivos ( y `Interfaz.java`).

- `Lanzador.java`: Contiene todos los métodos necesarios para los niveles.

- `Interfaz.java`: Contiene un método que sirve para seleccionar el nivel y otro que pide números que luego se le pasan a los métodos de `Lanzador.java` en función del nivel seleccionado.

## Lanzador

### Niveles 1 y 2

La única diferencia entre estos niveles es si la salida sale formateada (con `[OK]` o `[ERROR]`) o no, así que para diferenciarlas simplemente con un `boolean` distingo que nivel a seleccionado el usuario (`salidaFormateada`).

El instancia un `Process` con `factor` y el número indicado y lo arranca, después se controla el tiempo de ejecución del proceso, se muestra su salida (que depende del nivel) y se devuelve el código de salida del proceso.

```
public static int lanzarConFactor(String numero, boolean salidaFormateada) {
        try {
            //declaro el proceso usando processbuilder e indico que se rranque
            Process p = new ProcessBuilder("factor", numero).start();

            //Control del tiempo del proceso
            int intTiempo = controlarTiempoProceso(p);
            if (intTiempo != 0){
                return intTiempo;
            }

            //Muestra la salida del proceso, si no hay salida se muestra la de errores
            String salida = salidaProceso(p, salidaFormateada);
            if ((salida.compareTo("") != 0) && (salida.compareTo("[OK] ") != 0)){
                System.out.println(salida);
            }else{ //si no devolvió nada
                System.out.println(salidaErroresProceso(p, salidaFormateada));
            }

            //devuelve el valor de finalización de ejecución del proceso
            return p.exitValue();

        }catch (IOException e){ //processBuilder.start()
            System.out.println("Error: " + e);
        }catch (Exception e){ //en caso de error no esperado
            System.out.println("Excepción inesperada: " + e);
        }
        return -1; //devuelve -1 en caso de que caiga en alguna excepción
    }
```

Hay dos funciones de mostrar las salidas, una de errores y una estándar, que son las siguientes:

```
public static String salidaProceso(Process p, boolean salidaFormateada) throws IOException {
        //Si el usuario selecciona nivel 2, salida formateada es true, por lo que le añade al principio  [OK]
        String respuesta = "";
        if (salidaFormateada) {
            respuesta = respuesta + "[OK] ";
        }
        //Leer y guardar toda la salida
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) { //al estar en el try el BufferedReader no hace falta hacerle .close(), ya lo hace al final del try
            String linea;
            while ((linea = reader.readLine()) != null) {
                respuesta = respuesta + linea;
            }
        }catch (IOException e){
            throw new IOException("Error, no se puede leer la salida estándar del proceso:", e);
        }
        return respuesta;
    }

    public static String salidaErroresProceso(Process p, boolean salidaFormateada) throws IOException{
        //Si el usuario selecciona nivel 2, salida formateada es true, por lo que le añade al principio  [ERROR]
        String respuesta = "";
        if (salidaFormateada) {
            respuesta = respuesta + "[ERROR] ";
        }
        //Leer y guardar toda la salida
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()))) { //al estar en el try el BufferedReader no hace falta hacerle .close(), ya lo hace al final del try
            String linea;
            while ((linea = reader.readLine()) != null) {
                respuesta = respuesta + linea;
            }
        }catch (IOException e){
            throw new IOException("Error, no se puede leer la salida estándar del proceso:", e);
        }
        return respuesta;
    }
```

La función que controla el tiempo de ejecución primero espera 5 segundos y si no finaliza en ese tiempo le manda al proceso una señal para que se termine, si el proceso sigue sin reponder lo finaliza de forma "bruta".

```
public static int controlarTiempoProceso(Process p) {
        try {
            boolean terminado = p.waitFor(5, TimeUnit.SECONDS);

            if (!terminado) {
                p.destroy();
                if (!p.waitFor(10, TimeUnit.SECONDS)) {
                    p.destroyForcibly();
                    return p.exitValue();
                }
                return p.exitValue();
            }
            return 0; //si no se quedó colgado
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
```

### Nivel 4

Para el nivel 4 utilizo la siguiente función:

```
public static boolean esPrimo(int numero) {
        //los números menores o iguales a 1 no son primos
        if(numero <= 1) { return false; }

        //bucle que recorre los numeros desde el 2 hasta el número -1, si al divir el numero con i da de resto 0, significa que no son primos
        for(int i = 2; i<numero;i++) {
            if(numero % i == 0){
                return false;
            }
        }
        return true;
    }
```

## Interfaz

Esta clase es con la que interactúa directamente el usuario, contiene dos métodos.

### main()

El método que se ejecuta automáticamente y que le pide al usuario que seleccione un nivel.

```
public static void main(String[] args) {
        //Declaración escanner
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n"); //por defecto es un epacio en vez de un Enter o salto de línea
        int opcion = 0;
        while(true) {
            System.out.println("¿Qué nivel quieres usar (1,2,4,5(salir))? ");

            //Compruebo si el usuario metió un número o no
            try{
                opcion = Integer.parseInt(sc.next());
            }catch (NumberFormatException e){
                System.out.println("Valor no válido, por favor introduce un número");
                continue; //vuelve al principio del while
            }

            //Selector de niveles
            if(opcion == 1){
                System.out.println("-- 1 --");
                pedirNumeros(false, false);
            }else if (opcion == 2) {
                pedirNumeros(true, false);
            } else if (opcion == 4) {
                pedirNumeros(false, true);

            }else if(opcion == 5){
                System.out.println("Saliendo...");
                break;
            }
        }
    }
```

### pedirNumeros()

Este métofo recibe dos parámetros que vienen significando los diferentes niveles que el usuario indica en la función anterior.

Este método pide al usuario números hasta que escriba "Salir", esos números se los pasa a `lanzarConFactor` junto a si el usuario seleccionó el nivel 2 (`salidaFormateada`), después en función de si el usuario escogió el nivel 4 o no se llama a la función `esprimo()`.

```
public static void pedirNumeros(boolean salidaFormateada, boolean mostrarPrimo) {
        while (true) {
            //Scanner
            Scanner sc = new Scanner(System.in);
            sc.useDelimiter("\n");

            System.out.println("Introduce un número o escribe 'Salir': ");
            String teclado = sc.next();
            if (teclado.compareTo("Salir") == 0) {
                break; //Sale del bucle y finaliza el programa
            }

            System.out.println("Operación completada, código de salida: " + Lanzador.lanzarConFactor(teclado, salidaFormateada));

            /*
            Nivel 4
            Compruebo si es un número primero, después se lo paso a la función esPrimo()
             */
            boolean esNumero = true;
            try {
                Integer.parseInt(teclado);
            }catch (NumberFormatException e){
                esNumero = false;
            }

            //Sólo entra si es un número y el usuario selecciona el nivel 4
            if(mostrarPrimo && esNumero){
                boolean respuesta = false;
                respuesta = Lanzador.esPrimo(Integer.parseInt(teclado));
                if (respuesta) {
                    System.out.println(teclado + " es primo");
                }else {
                    System.out.println(teclado + " no es primo");
                }
            }
            //Vuelve al principio del for
        }
    }
```

