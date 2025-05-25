import java.time.LocalDate;

public class Libro {

    private String titulo;
    private String autor;
    private int anio;

    public Libro (String titulo, String autor, int anio){
        int anioActual = LocalDate.now().getYear();

        if(anio > anioActual){
            throw new IllegalArgumentException("El año de publicación no puede ser mayor al año actual.");
        }

        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
    }

    public int getAnio() {
        return anio;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
