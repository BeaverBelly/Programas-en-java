package Ejercicio1;

public class Estudiante {
    private String nombre;
    private int edad;
    private String numeroMatricula;

    public Estudiante(String nombre, int edad, String numeroMatricula) {
        this.nombre = nombre;
        this.edad = edad;
        this.numeroMatricula = numeroMatricula;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", numeroMatricula='" + numeroMatricula + '\'' +
                '}';
    }
}

