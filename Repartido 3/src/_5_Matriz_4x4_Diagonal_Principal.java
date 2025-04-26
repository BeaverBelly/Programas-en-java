import java.util.Scanner;

public class _5_Matriz_4x4_Diagonal_Principal {
    public static void main(String[] args){
        int[][] Matriz = new int[4][4];
        int[] diagonalPrincipal = new int[4];
        int contador=0;
        Scanner entrada = new Scanner(System.in);

        for(int fila = 0; fila < Matriz.length; fila++){
            for(int columna = 0; columna <Matriz[fila].length; columna++){
                System.out.print("ingrese un elemento en la fila y columna: "+ fila + " " + columna + " ");
                Matriz[fila][columna] = entrada.nextInt();
            }
        }

        for(int fila = 0; fila<Matriz.length; fila++){
            for(int columna = 0; columna< Matriz[fila].length; columna++){
                System.out.print(Matriz[fila][columna] + "\t");

                if(Matriz[fila] == Matriz[columna]){
                    diagonalPrincipal[contador] = Matriz[fila][columna];
                    contador++;
                }
            }
            System.out.println();
        }

        System.out.println();

        System.out.print("diagonal principal: ");
        for(int elementos : diagonalPrincipal){
            System.out.print(elementos + " ");
        }
    }
}
