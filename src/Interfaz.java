import java.util.Scanner;

/**
 * Clase Interfaz que pide datos al usuario
 * @author Anxo Vázuqez
 * @version 1.0
 */
public class Interfaz {
    /**
     * Métod0 main que contiene un selector de nivel
     * @param args
     */
    public static void main(String[] args) {
        //Declaración escanner
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n"); //por defecto es un epacio en vez de un Enter o salto de línea
        int opcion = 0;
        while(true) {
            System.out.println("¿Qué nivel quieres usar (1,2,3,4,5(salir))? ");

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
                nivel1();
            }else if(opcion == 5){
                System.out.println("Saliendo...");
                break;
            }

        }
    }

    /**
     * Métod0 del nivel 1 que pide números hasta que el usuario introduzc "Salir"
     */
    public static void nivel1(){
        while (true) {
            //Scanner
            Scanner sc = new Scanner(System.in);
            sc.useDelimiter("\n");

            System.out.println("Introduce un número o escribe 'Salir': ");
            String teclado = sc.next();
            if (teclado.compareTo("Salir") == 0) {
                break; //Sale del bucle y finaliza el programa
            }

            //Si es número lo lanza y si no imprime un mensaje

            System.out.println("Operación completada, código de salida: " + Lanzador.lanzarConFactor(teclado));


            //Vuelve al principio del for

        }
    }
}
