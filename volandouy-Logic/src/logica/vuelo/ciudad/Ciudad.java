package logica.vuelo.ciudad;

import java.time.LocalDate;

import datatypes.DTCiudad;
import logica.enums.Pais;

public class Ciudad {

    private String aeropuerto;
    private String descripcion;
    private LocalDate fechaAlta;
    private String nombre;
    private Pais pais;
    private String web;

    /*
    public Ciudad(String nombre, String descripcion, String web, Pais pais, String aeropuerto, LocalDate fechaAlta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.web = web;
        this.pais = pais;
        this.aeropuerto = aeropuerto;
        this.fechaAlta = fechaAlta;
    }
    */
    
    public Ciudad(DTCiudad ciudad) {
        this.nombre = ciudad.getNombre();
        this.descripcion = ciudad.getDescripcion();
        this.web = ciudad.getWeb();
        this.pais = Pais.desdeString(ciudad.getPais());
        this.aeropuerto = ciudad.getAeropuerto();
        this.fechaAlta = ciudad.getFechaAlta();
    }
    
    /*
    public String getAeropuerto() {
        return this.aeropuerto;
    }

	public String getDescripcion() {
        return this.descripcion;
    }
    */

    public DTCiudad getDT() {
    	return new DTCiudad(this.nombre, this.descripcion, this.web, this.pais.getPais(), this.aeropuerto, this.fechaAlta);
    }

    /*
    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }
    */

    public String getNombre() {
        return this.nombre;
    }

    public Pais getPais() {
        return this.pais;
    }

    /*
    public String getWeb() {
        return this.web;
    }
    */

}
