import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Clase Lanzador ejercicio 1
 * @author Anxo Vázquez
 * @version 1.0
 */
public class Lanzador {
    /**
     * Calcula e imprime el resultado de ejecutar el comando factor con el número pasado por parámetro y devuelve su código de salida
     * @param numero int
     * @return código de salida Int
     */
    public static int lanzarConFactor(int numero){
        try {
            //declaro el proceso usando processbuilder e indico que se rranque
            Process p = new ProcessBuilder("factor", String.valueOf(numero)).start();

            /*
            Primero le da 5 segundos para ejecutarse
            Si pasan esos 5 segundos y no ha terminado se entiende que ha fallado o se ha quedado "colgado" y le manda una señal al proceso para que termine
            Si pasan otros 10 segundos más y no ha terminado lo finaliza "a machete"
             */
            boolean terminado = p.waitFor(5, TimeUnit.SECONDS);

            if (!terminado) {
                p.destroy();
                if (!p.waitFor(10, TimeUnit.SECONDS)) {
                    p.destroyForcibly();
                    return p.exitValue();
                }
            }

            //Mostrar salida factor por pantalla
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) { //al estar en el try el BufferedReader no hace falta hacerle .close(), ya lo hace al final del try
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println(linea);
                }
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
}
