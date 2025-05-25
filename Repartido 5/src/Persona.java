public class Persona {
    private String Nombre;
    private int Edad;

    public Persona(String Nombre, int Edad){
        this.Nombre = Nombre;
        this.Edad = Edad;

        System.out.println("Persona creada: " + "[" + Nombre + "]");
    }

    public String getNombre() {
        return Nombre;
    }

    public int getEdad() {
        return Edad;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }
}
