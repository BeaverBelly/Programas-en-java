package Ejercicio2;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> listaNum = new ArrayList<>();
        Random random = new Random();

        // Generar 100 números aleatorios entre 1 y 1000
        for (int i = 0; i < 100; i++) {
            listaNum.add(random.nextInt(1000) + 1);
        }

        System.out.println("Lista de números:");
        System.out.println(listaNum);

        // Encontrar valor máximo
        int maximo = listaNum.get(0);
        for (int num : listaNum) {
            if (num > maximo) {
                maximo = num;
            }
        }

        // Calcular suma total
        int suma = 0;
        for (int num : listaNum) {
            suma += num;
        }

        // Calcular promedio
        double promedio = (double) suma / listaNum.size();

        // Mostrar resultados
        System.out.println("\n Resultados del análisis:");
        System.out.println("Valor máximo: " + maximo);
        System.out.println("Suma total: " + suma);
        System.out.println("Promedio: " + promedio);
    }
}
