package Empleado;

public class Main {
    public static void main(String[] args) {
        EmpleadoTiempoCompleto emp = new EmpleadoTiempoCompleto(30000);

        System.out.println("Salario mensual: " + emp.calcularSalario());

        emp.solicitarLicencia(5);

        emp.solicitarLicencia(6);

        double pagoExtras = emp.calcularHorasExtras(10);
        System.out.println("Pago por 10 horas extra: " + pagoExtras);

        emp.tomarVacaciones(15);
    }
}
