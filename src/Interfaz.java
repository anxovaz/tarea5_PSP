import java.util.Scanner;

/**
 * Clase Interfaz que pide datos al usuario
 * @author Anxo Vázuqez
 * @version 1.0
 */
public class Interfaz {
    /**
     * Método main que contiene un selector de nivel
     * @param args
     */
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

    /**
     * Método que pide numeros hasta que el usuario introduzca "Salir", en función de los parámetros pasados a este método formateará la salida (o no) o mostrará si es primo o no
     * @param salidaFormateada boolean [OK] o [ERROR] al principio del resultado de factor
     * @param mostrarPrimo boolean imprime si es primo o no
     */
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


}
