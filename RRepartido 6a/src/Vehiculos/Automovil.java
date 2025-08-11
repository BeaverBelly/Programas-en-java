package Vehiculos;

public class Automovil extends Vehiculo {
    private String tipoCombustible;

    public Automovil(String marca, String modelo, String anio, String tipoCombustible){
        super(marca, modelo, anio);
        this.tipoCombustible = tipoCombustible;
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println(tipoCombustible);
    }
}
