package equipo_Deportivo;

public class Jugador {
    private String nombre;
    private String posicion;
    private int numeroCamiseta;

    public Jugador(String nombre, String posicion, int numeroCamiseta){
        System.out.println("Se ha llamado al constructor con 3 variables");
        setNombre(nombre);
        setPosicion(posicion);
        setNumeroCamiseta(numeroCamiseta);
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()){
            throw new IllegalArgumentException("No se ha proveido nombre");
        }

        if (nombre.length() < 2){
            throw new IllegalArgumentException("No existen nombres tan pequeños");
        }

        this.nombre = nombre;
    }

    public void setPosicion(String posicion){
        if (posicion == null || posicion.trim().isEmpty()){
            throw new IllegalArgumentException("No se ha proveido posición");
        }

        if(posicion.length() < 2){
            throw new IllegalArgumentException("No existe posición de jugador tan corta");
        }

        this.posicion = posicion;
    }

    public void setNumeroCamiseta(int numeroCamiseta){
        if(numeroCamiseta < 0){
            throw new IllegalArgumentException("No pueden haber números negativos en las camisetas");
        }

        this.numeroCamiseta = numeroCamiseta;
    }

    public String getNombre(){
        return nombre;
    }

    public String getPosicion(){
        return posicion;
    }

    public int getNumeroCamiseta(){
        return numeroCamiseta;
    }
}
