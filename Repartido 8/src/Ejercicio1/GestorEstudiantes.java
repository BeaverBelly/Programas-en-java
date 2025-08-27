package Ejercicio1;
import java.util.LinkedList;

class GestorEstudiantes {
    private LinkedList<Estudiante> listaEstudiantes;

    public GestorEstudiantes() {
        this.listaEstudiantes = new LinkedList<>();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        listaEstudiantes.add(estudiante);
        System.out.println("Estudiante agregado: " + estudiante);
    }

    public void eliminarEstudiantePorNumeroMatricula(String numeroMatricula) {
        boolean eliminado = false;
        for (Estudiante e : listaEstudiantes) {
            if (e.getNumeroMatricula().equals(numeroMatricula)) {
                listaEstudiantes.remove(e);
                System.out.println("Estudiante eliminado con matrícula: " + numeroMatricula);
                eliminado = true;
                break;
            }
        }
        if (!eliminado) {
            System.out.println("No se encontró estudiante con matrícula: " + numeroMatricula);
        }
    }

    public void mostrarEstudiantes() {
        if (listaEstudiantes.isEmpty()) {
            System.out.println("No hay estudiantes en la lista.");
        } else {
            System.out.println("Lista de estudiantes:");
            for (Estudiante e : listaEstudiantes) {
                System.out.println(e);
            }
        }
    }
}