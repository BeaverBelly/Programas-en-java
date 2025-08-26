package Ejercicio6;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CajeroAutomatico cajero = new CajeroAutomatico(1000); // saldo inicial

        System.out.println("Bienvenido al Cajero Automático");
        System.out.println("Saldo inicial: " + cajero.getSaldo());
        System.out.print("Ingrese la cantidad que desea retirar: ");

        try {
            double cantidad = scanner.nextDouble();
            cajero.retirarDinero(cantidad);

        } catch (CantidadInvalidaException e) {
            System.out.println("Error de cantidad: " + e.getMessage());
        } catch (FondosInsuficientesException e) {
            System.out.println("Error de fondos: " + e.getMessage());
        } finally {
            System.out.println("Operación finalizada. Saldo disponible: " + cajero.getSaldo());
            scanner.close();
        }
    }
}

