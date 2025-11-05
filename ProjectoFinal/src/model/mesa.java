package model;

import java.io.Serializable;

/**
 * Modelo de datos para representar una Mesa en el sistema.
 * Implementa Serializable para poder ser guardado/cargado en archivos binarios.
 */
public class mesa implements Serializable {

    private static final long serialVersionUID = 1L; // Recomendado para Serializable

    private int id;
    private String mozo;
    private String nombre;
    private String estado;

    /**
     * Constructor completo de una mesa.
     * * @param id Identificador único de la mesa.
     * @param mozo Nombre del mozo asignado o "—" si está libre.
     * @param nombre Nombre descriptivo de la mesa (ej. "Mesa 5", "Terraza B").
     * @param estado Estado actual de la mesa (LIBRE, OCUPADA, RESERVADA).
     */
    public mesa(int id, String mozo, String nombre, String estado) {
        this.id = id;
        this.mozo = mozo;
        this.nombre = nombre;
        this.estado = estado;
    }

    // --- Getters ---

    public int getId() {
        return id;
    }

    public String getMozo() {
        return mozo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }

    // --- Setters (Solo para campos modificables) ---

    public void setMozo(String mozo) {
        this.mozo = mozo;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // El ID y Nombre no se pueden cambiar después de la creación.
}