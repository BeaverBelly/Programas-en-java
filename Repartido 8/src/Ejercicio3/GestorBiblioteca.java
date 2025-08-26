package Ejercicio3;

import java.util.HashSet;

public class GestorBiblioteca {
    private HashSet<Libro> libros;

    public GestorBiblioteca() {
        this.libros = new HashSet<>();
    }

    // Agregar un libro
    public void agregarLibro(Libro libro) {
        if (libros.add(libro)) {
            System.out.println("Libro agregado: " + libro);
        } else {
            System.out.println("Ya existe un libro con ISBN " + libro.getIsbn());
        }
    }

    // Eliminar un libro por ISBN
    public void eliminarLibro(String isbn) {
        Libro libroAEliminar = null;
        for (Libro l : libros) {
            if (l.getIsbn().equals(isbn)) {
                libroAEliminar = l;
                break;
            }
        }
        if (libroAEliminar != null) {
            libros.remove(libroAEliminar);
            System.out.println("Libro eliminado con ISBN: " + isbn);
        } else {
            System.out.println("No se encontró libro con ISBN: " + isbn);
        }
    }

    // Buscar libro por ISBN
    public Libro buscarPorIsbn(String isbn) {
        for (Libro l : libros) {
            if (l.getIsbn().equals(isbn)) {
                return l;
            }
        }
        return null;
    }

    // Buscar libro por título
    public Libro buscarPorTitulo(String titulo) {
        for (Libro l : libros) {
            if (l.getTitulo().equalsIgnoreCase(titulo)) {
                return l;
            }
        }
        return null;
    }

    // Mostrar todos los libros
    public void mostrarLibros() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros en la biblioteca.");
        } else {
            System.out.println("Colección de libros:");
            for (Libro l : libros) {
                System.out.println(l);
            }
        }
    }
}

