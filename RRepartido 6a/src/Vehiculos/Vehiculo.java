package Vehiculos;

public class Vehiculo {
    private String marca;
    private String modelo;
    private String anio;

    public Vehiculo(String marca, String modelo, String anio){
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;

    }

    public void mostrarInformacion(){
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Año: " + anio);
    }
}
