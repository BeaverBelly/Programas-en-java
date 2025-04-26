import java.util.Random;

public class _4_Matriz_Aleatoria_Suma_Fila {
    public static void main(String[] args){
        int[][] Matriz = new int[3][3];
        int suma = 0;
        Random rand = new Random();

        for (int fila = 0; fila<Matriz.length; fila++){
            for (int columna = 0; columna<Matriz[fila].length; columna++){
                Matriz[fila][columna] = rand.nextInt(9) + 1;
            }
        }

        System.out.println();

        for(int fila = 0; fila<Matriz.length; fila++){
            for(int columna = 0; columna< Matriz[fila].length; columna++){
                System.out.print(Matriz[fila][columna] + "\t");
            }
            System.out.println();
        }


        for(int fila = 0; fila < Matriz.length; fila++){
            for(int columna = 0; columna < Matriz[fila].length; columna++){
                suma += Matriz[fila][columna];
            }
            System.out.println("suma fila "+ (fila+1) + ": " + suma);
            suma = 0;
        }
    }
}
