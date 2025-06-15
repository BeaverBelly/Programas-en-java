package _5_Vehiculos;

public class Main {
    public static void main(String[] args){
        Vehiculo[] vehiculos = {new Auto(), new Motocicleta()};

        for (Vehiculo vehiculitos : vehiculos){
            System.out.println(vehiculitos.velocidadMaxima());
        }

    }
}
