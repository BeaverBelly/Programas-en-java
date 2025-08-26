package Ejercicio1;

public class Main {
    public static void main(String[] args) {
        GestorEstudiantes gestor = new GestorEstudiantes();

        // Crear estudiantes
        Estudiante e1 = new Estudiante("Ana", 20, "A123");
        Estudiante e2 = new Estudiante("Carlos", 22, "C456");
        Estudiante e3 = new Estudiante("María", 19, "M789");

        // Agregar estudiantes
        gestor.agregarEstudiante(e1);
        gestor.agregarEstudiante(e2);
        gestor.agregarEstudiante(e3);

        // Mostrar lista
        gestor.mostrarEstudiantes();

        // Eliminar estudiante por matrícula
        gestor.eliminarEstudiantePorNumeroMatricula("C456");

        // Mostrar lista actualizada
        gestor.mostrarEstudiantes();

        // Intentar eliminar uno que no existe
        gestor.eliminarEstudiantePorNumeroMatricula("X999");
    }
}