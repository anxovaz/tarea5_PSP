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
     * @param numero que usará factor int
     * @return código de salida Int
     */
    public static int lanzarConFactor(String numero){
        try {
            //declaro el proceso usando processbuilder e indico que se rranque
            Process p = new ProcessBuilder("factor", numero).start();

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

            //Muestra la salida del proceso, si no hay salida se muestra la de errores
            String salida = salidaProceso(p);
            if (salida.compareTo("") != 0){
                System.out.println(salida);
            }else{
                System.out.println(salidaErroresProceso(p));
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

    /**
     * Devuelve un String con la salida estándar del proceso
     * @param p El proceso
     * @return String con la salida
     * @throws IOException Si no se puede leer la saida
     */
    public static String salidaProceso(Process p) throws IOException {
        String respuesta = "";
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

    /**
     * Devuelve un String con la salida de errores del proceso
     * @param p El proceso
     * @return String con la salida
     * @throws IOException Si no se puede leer la saida
     */
    public static String salidaErroresProceso(Process p) throws IOException{
        String respuesta = "";
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
}
