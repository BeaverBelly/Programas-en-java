package equipo_Deportivo;

public class Equipo {
    private Jugador[] jugadores = new Jugador[15];
    private int tope = 0;
    private String nombreEquipo;

    public Equipo(String nombreEquipo){
        setNombreEquipo(nombreEquipo);
    }

    public boolean agregarJugador(Jugador jugador){
        if(tope < jugadores.length){
            jugadores[tope] = jugador;
            tope++;
            return true;
        }
        else {
            System.out.println("No se puede agregar más jugadores. Equipo lleno.");
            return false;
        }
    }

    public void mostrarJugadores(){
        for (int i = 0; i < tope; i++){
            System.out.println(jugadores[i].getNombre() + " - " + jugadores[i].getPosicion() + " - " + jugadores[i].getNumeroCamiseta());
        }
    }

    public void setNombreEquipo(String nombreEquipo) {
        if(nombreEquipo == null || nombreEquipo.trim().isEmpty()){
            throw new IllegalArgumentException("No podes poner un nombre vacio hermano");
        }

        this.nombreEquipo = nombreEquipo;
    }


}
