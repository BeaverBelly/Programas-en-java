package Vehiculos;

public class Camion extends Vehiculo{
    private double capacidadCarga;
    private int numeroEjes;

    public Camion(String marca, String modelo, String anio, double capacidadCarga, int numeroEjes){
        super(marca, modelo, anio);
        this.capacidadCarga = capacidadCarga;
        this.numeroEjes = numeroEjes;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Capacidad de carga: " + capacidadCarga);
        System.out.println("Numero de Ejes: " + numeroEjes);
    }
}
