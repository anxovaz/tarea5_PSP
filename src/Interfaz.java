import java.util.Scanner;

public class Interfaz {
    public static void main(String[] args){
        while(true){
            Scanner sc = new Scanner(System.in);
            sc.useDelimiter("\n");

            System.out.println("Introduce un número o escribe 'Salir': ");
            String str1 = sc.next();
            if (str1.compareTo("Salir") == 0){
                break;
            }
            if(str1.length() == 1){
                String[] numeros = {"1","2","3","4","5","6","7","8","9","0"};
                boolean esNumero = false;
                for(int i = 0; i<numeros.length;i++){
                    if(numeros[i].compareTo(str1) == 0){
                        esNumero = true;
                        break;
                    }
                }

                if (esNumero){
                    break;
                }
            }
            System.out.println("Valor incorrecto, vuelve a intentarlo");


        }
    }
}
