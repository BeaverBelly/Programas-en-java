package _3_Conversor;

public class Main {
    public static void main(String[] args) {
        Conversor convirtiendo1 = new Conversor();
        Conversor convirtiendo2 = new Conversor();
        Conversor convirtiendo3 = new Conversor();

        System.out.println(convirtiendo1.convertir(1.0));
        System.out.println(convirtiendo2.convertir(10, "peso"));
        System.out.println(convirtiendo3.convertir(35.0, true));

    }
}
