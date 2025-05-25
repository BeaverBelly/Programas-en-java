public class Empleado {
    private String nombre;
    private String cargo;
    private double sueldoBase;

    public Empleado(String nombre, String cargo, double sueldoBase){
        if (sueldoBase < 0) {
            throw new IllegalArgumentException("El sueldo base no puede ser negativo.");
        }

        this.nombre = nombre;
        this.cargo = cargo;
        this.sueldoBase = sueldoBase;
    }

    public double getSueldoFinal() {
        if (cargo.equalsIgnoreCase("Jefe")) {
            return sueldoBase * 1.20;
        }
        return sueldoBase;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSueldoBase() {
        return sueldoBase;
    }

    public String getCargo() {
        return cargo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setSueldoBase(double sueldoBase) {
        this.sueldoBase = sueldoBase;
    }
}
