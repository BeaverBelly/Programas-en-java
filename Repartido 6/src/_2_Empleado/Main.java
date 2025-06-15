package _2_Empleado;

public class Main {
    public static void main(String[] args) {
    Empleado empleado1 = new Empleado();

    Empleado empleado2 = new Empleado("Tiziano", 50000.0 );

    Empleado empleado3 = new Empleado( "panyfederico", 7000.0, 30);


    System.out.println("El sueldo de "+ empleado1.getNombre() + " es: " + empleado1.calcularSueldoTotal());
    System.out.println("El sueldo de "+ empleado2.getNombre() + " es: " + empleado2.calcularSueldoTotal());
    System.out.println("El sueldo de "+ empleado3.getNombre() + " es: " + empleado3.calcularSueldoTotal());

    }
}
