import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class Lanzador {
    public static int lanzarConFactor(int numero){
        try {
            Process p = new ProcessBuilder("factor", String.valueOf(numero)).start();

            /*
            Primero le da 5 segundos para ejecutarse
            Si pasan esos 5 segundos y no ha terminado se entiende que ha fallado o se ha quedado "colgado" y le manda una señal al proceso para que termine
            Si pasan otros 10 segundos más y no ha terminado lo finaliza "a machete"
             */
            if(p.waitFor(5, TimeUnit.SECONDS)){
                return p.exitValue();
            }else{
                p.destroy();
                if(p.waitFor(10, TimeUnit.SECONDS)){
                    p.destroyForcibly();
                }
            }

        }catch (IOException e){
            System.out.println("Error: " + e);
        }catch (Exception e){
            System.out.println("Excepción inesperada: " + e);
        }
        return -1;
    }
}
