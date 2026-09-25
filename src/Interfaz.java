import java.util.Scanner;

/**
 * Clase Interfaz que pide datos al usuario
 *
 * @author Anxo Vázuqez
 *
 * @version 1.0
 */
public class Interfaz {
    public static void main(String[] args) {
        //Declaración escanner
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n"); //por defecto es un epacio en vez de un Enter o salto de línea
        while (true) {
            System.out.println("Introduce un número o escribe 'Salir': ");
            String teclado = sc.next();
            boolean esNumero = true;
            if (teclado.compareTo("Salir") == 0) {
                break; //Sale del bucle y finaliza el programa
            }

            //Si se intenta convertir a integer un String que contiene letras salta una excepción
            try {
                Integer.parseInt(teclado);
            }catch (NumberFormatException e) { //si salta significa que no es numérico
                esNumero = false;
            }

            //Si es número lo lanza y si no imprime un mensaje
            if (esNumero) {
                System.out.println("Código de salida" + Lanzador.lanzarConFactor(Integer.parseInt(teclado)));
            } else {
                System.out.println("Valor incorrecto, vuelve a intentarlo");
            }

            //Vuelve al principio del for



        }
    }
}
