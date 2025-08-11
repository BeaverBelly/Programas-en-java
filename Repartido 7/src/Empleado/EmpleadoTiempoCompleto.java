package Empleado;

public class EmpleadoTiempoCompleto implements Empleado, TrabajadorExtra {
    private double salarioBase;
    private int licenciaPendiente;

    public EmpleadoTiempoCompleto(double salarioBase) {
        this.salarioBase = salarioBase;
        this.licenciaPendiente = 0;
    }

    @Override
    public double calcularSalario() {
        return salarioBase;
    }

    @Override
    public void solicitarLicencia(int dias) {
        int totalSiAprueba = licenciaPendiente + dias;
        if (totalSiAprueba <= 10) {
            licenciaPendiente = totalSiAprueba;
            System.out.println("Licencia aprobada por");
        } else {
            System.out.println("Licencia DENEGADA");
        }
    }

    @Override
    public double calcularHorasExtras(int horas) {
        double valorHora = salarioBase / 160.0;
        return 1.5 * valorHora * horas;
    }

    @Override
    public void tomarVacaciones(int dias) {
        System.out.println("Vacaciones registradas:");
    }
}
