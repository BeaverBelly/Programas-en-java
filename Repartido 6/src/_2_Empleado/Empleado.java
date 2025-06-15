package _2_Empleado;

public class Empleado {
    private String nombre;
    private double sueldoBase;
    private int horasExtras;

    public Empleado() {

        this("Sin nombre", 0.0, 0);
        System.out.println("se ha llamado al constructor por defecto");

    }

    public Empleado(String nombre, double sueldoBase) {
        this(nombre, sueldoBase, 0);
        System.out.println("Se ha llamado al constructor2");
    }

    public Empleado(String nombre, double sueldoBase, int horasExtras) {
        System.out.println("se ha llamado al constructor3");
        setNombre(nombre);
        setSueldoBase(sueldoBase);
        setHorasExtras(horasExtras);
    }

    public void mostrarInfo(){
        System.out.println(nombre + " " + sueldoBase + " " + horasExtras);
    }

    public double calcularSueldoTotal(){

        return sueldoBase +(250*horasExtras);
    }

    public String getNombre() {

        return nombre;

    }

    public void setSueldoBase(double sueldoBase) {
        if (sueldoBase < 0){
            throw new IllegalArgumentException("El valor de sueldo base < 0");
        }
        this.sueldoBase = sueldoBase;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("No se ha proveído nombre");
        }
        if (nombre.length() < 2){
            throw new IllegalArgumentException("No hay nombres tan pequeños");
        }

        this.nombre = nombre;
    }

    public void setHorasExtras(int horasExtras) {
        if(horasExtras < 0){
            throw new IllegalArgumentException("No pueden haber horas extras negativas");
        }

        this.horasExtras = horasExtras;
    }
}
