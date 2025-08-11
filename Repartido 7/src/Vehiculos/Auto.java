package Vehiculos;

public class Auto implements Vehiculo {
    private int velocidadActual = 0;

    @Override
    public void arrancar(){
        System.out.println("El auto ha arrancado.");
    }

    @Override
    public void acelerar(int velocidad) {
        velocidadActual += velocidad;
        System.out.println("El auto ha acelerado. Velocidad actual: " + velocidadActual + " km/h");
    }

    @Override
    public void frenar() {
        velocidadActual = 0;
        System.out.println("El auto se detuvo.");
    }

}
