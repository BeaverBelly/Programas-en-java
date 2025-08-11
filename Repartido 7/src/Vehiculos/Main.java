package Vehiculos;

public class Main {
    public static void main(String[] args) {
        Auto miAuto = new Auto();
        miAuto.arrancar();
        miAuto.acelerar(50);
        miAuto.acelerar(30);
        miAuto.frenar();
    }
}
