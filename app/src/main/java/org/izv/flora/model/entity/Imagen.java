package org.izv.flora.model.entity;

public class Imagen {

    public long id, idflora;
    public String nombre, descripcion;


    public void setId(long id) {
        this.id = id;
    }

    public long getIdflora() {
        return idflora;
    }

    public void setIdflora(long idflora) {
        this.idflora = idflora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
