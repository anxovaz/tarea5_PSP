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
        while (true) {
            //Declaración escanner
            Scanner sc = new Scanner(System.in);
            sc.useDelimiter("\n"); //por defecto es un epacio en vez de un Enter o salto de línea

            System.out.println("Introduce un número o escribe 'Salir': ");
            String teclado = sc.next();
            if (teclado.compareTo("Salir") == 0) {
                break; //Sale del bucle y finaliza el programa
            }

            //Compruebo si es numérico
            if (teclado.length() == 1) {
                String[] numeros = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
                boolean esNumero = false;
                for (int i = 0; i < numeros.length; i++) {
                    if (numeros[i].compareTo(teclado) == 0) {
                        esNumero = true;
                        break;
                    }
                }
                if (esNumero) {
                    break;
                } else {
                    System.out.println("Valor incorrecto, vuelve a intentarlo");
                }
            }


        }
    }
}
