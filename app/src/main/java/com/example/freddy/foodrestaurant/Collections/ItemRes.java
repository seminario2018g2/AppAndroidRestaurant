package com.example.freddy.foodrestaurant.Collections;

public class ItemRes {
    private String nombre;
    private String nit;
    private String propietario;
    private String calle;
    private String telefono;
    private String longitud;
    private String latitud;
    private int foto;

    public ItemRes(String nombre, String nit, String propietario, String calle, String telefono, String longitud, String latitud, int foto) {
        this.nombre = nombre;
        this.nit = nit;
        this.propietario = propietario;
        this.calle = calle;
        this.telefono = telefono;
        this.longitud = longitud;
        this.latitud = latitud;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getPropietario() {
        return propietario;
    }

    public String getCalle() {
        return calle;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public int getFoto() {
        return foto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
