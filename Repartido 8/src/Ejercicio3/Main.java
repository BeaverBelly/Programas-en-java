package Ejercicio3;

public class Main {
    public static void main(String[] args) {
        GestorBiblioteca biblioteca = new GestorBiblioteca();

        Libro l1 = new Libro("Cien Años de Soledad", "Gabriel García Márquez", "111");
        Libro l2 = new Libro("El Principito", "Antoine de Saint-Exupéry", "222");
        Libro l3 = new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "333");
        Libro l4 = new Libro("El Principito", "Antoine de Saint-Exupéry", "222"); // Duplicado

        biblioteca.agregarLibro(l1);
        biblioteca.agregarLibro(l2);
        biblioteca.agregarLibro(l3);
        biblioteca.agregarLibro(l4);

        biblioteca.mostrarLibros();

        System.out.println("\nBuscando por ISBN '222': " + biblioteca.buscarPorIsbn("222"));

        System.out.println("Buscando por título 'Cien Años de Soledad': " + biblioteca.buscarPorTitulo("Cien Años de Soledad"));

        biblioteca.eliminarLibro("333");

        biblioteca.mostrarLibros();
    }
}

