public class Empleado {
    private String nombre;
    private String cargo;
    private double sueldoBase;

    public Empleado(){
        this("Rami", "Florista", 0);
    }

    public Empleado(String nombre, String cargo, double sueldoBase){
        setNombre(nombre);
        setCargo(cargo);
        setSueldoBase(sueldoBase);
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
        if (nombre == null || nombre == "".trim()){
            throw new IllegalArgumentException("No se ha proveído nombre");
        }
        this.nombre = nombre;
    }

    public void setCargo(String cargo) {
        if (cargo == null || cargo == "".trim()){
            throw new IllegalArgumentException("No se ha proveído un cargo");
        }

        this.cargo = cargo;
    }

    public void setSueldoBase(double sueldoBase) {
        if (sueldoBase < 0) {
            throw new IllegalArgumentException("El sueldo base no puede ser negativo.");
        }

        this.sueldoBase = sueldoBase;
    }
}
