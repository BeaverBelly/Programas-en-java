package _5_Vehiculos;

public class Auto extends Vehiculo{
    private int cantidadDePuertas;

    @Override
    public int velocidadMaxima() {
        return 180;
    }
}
