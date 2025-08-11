package Vehiculos;


public class Main {
    public static void main(String[] args) {
        Vehiculo[] vehiculos = {
                new Camion("Fiat", "350", "2005", 325.5, 2),
                new Automovil("Volkswagen", "260", "2024", "Diesel")
        };
        for(int i = 0; i<vehiculos.length; i++){
            vehiculos[i].mostrarInformacion();
        }


        }
}