package logica.vuelo;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTCiudad;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import logica.vuelo.categoria.Categoria;
import logica.vuelo.ciudad.Ciudad;

public class ManejadorRutaVuelo {
	private static ManejadorRutaVuelo instancia = null;
	private Map<String, RutaVuelo> rutasVuelo;
	private Map<String, Categoria> categoria;
	private Set<Ciudad> ciudades;

	private ManejadorRutaVuelo() {
		this.rutasVuelo = new HashMap<>();
		this.ciudades = new HashSet<>();
		this.categoria = new HashMap<>();
	}

	public static ManejadorRutaVuelo getInstance() {
		if (instancia == null) {
			instancia = new ManejadorRutaVuelo();
		}
		return instancia;
	}

	public void agregarCategoria(Categoria cat) {
        this.categoria.put(cat.getNombre(), cat);
    }

	public void agregarCiudad(Ciudad ciudad) {
		this.ciudades.add(ciudad);
	}

	public void agregarRutaVuelo(RutaVuelo rutaVuelo) {
		this.rutasVuelo.put(rutaVuelo.getNombre(), rutaVuelo);
	}

	public void agregarVuelo(String nombre_ruta, Vuelo vuelo) {
		this.rutasVuelo.get(nombre_ruta).agregarVuelo(vuelo);
	}

	public DTRutaVuelo buscarDTRutaVuelo(String rutavuelo) {
		return this.obtenerRuta(rutavuelo).getDT();
	}

	public void clear() { //Usada por los tests
		this.rutasVuelo.clear();
		this.ciudades.clear();
		this.categoria.clear();
	}

	public Boolean existeCategoria(String nombre) {
		return this.categoria.containsKey(nombre);
	}

	public boolean existeRuta(String nombre) {
		return this.rutasVuelo.containsKey(nombre);
	}

	public boolean existeVuelo(String nombre) {
		if (this.rutasVuelo == null) {
			return false;
		} else {
			for (RutaVuelo rutaV : this.rutasVuelo.values()) {
				if (rutaV.existeVuelo(nombre) ) {
					return true;
				}
			}
		}
		return false;
	}

	public Set<String> listarRutaVuelo(){
        Set<String> rutas = new HashSet<String>();
        for (RutaVuelo rv : rutasVuelo.values()) {
                rutas.add(rv.getNombre());
        }
        return rutas;
	}

	public Categoria obtenerCategoria(String nombre) {
		return this.categoria.getOrDefault(nombre, null);
	}

	public Set<String> obtenerCategorias(){
		Set<String> ret = new HashSet<>();
		for (Categoria cat : categoria.values()) {
			ret.add(cat.getNombre());
		}
		return ret;
	}

	public Ciudad obtenerCiudad(String nombreCiudad, String nombrePais) {
		for (Ciudad ciudad : this.ciudades) {
			if (ciudad.getNombre().equals(nombreCiudad) && ciudad.getPais().getPais() == nombrePais) {
				return ciudad;
			}
		}
		return null;
	}

	public Set<Ciudad> obtenerCiudades() {
		return Collections.unmodifiableSet(this.ciudades);
	}

	public DTCiudad obtenerDTCiudadPais(String nombreCiudad, String nombrePais) {
		for (Ciudad ciudad : this.ciudades) {
			if (ciudad.getNombre().equals(nombreCiudad) && ciudad.getPais().getPais().equals(nombrePais)) {
				return ciudad.getDT();
			}
		}
		return null;
	}

	public RutaVuelo obtenerRuta(String nombre) {
		return this.rutasVuelo.getOrDefault(nombre, null);
	}

	public RutaVuelo obtenerRutaDeVuelo(String  rutaVuelo) {
	    	return rutasVuelo.get(rutaVuelo);

		}

	public Set<RutaVuelo> obtenerRutas(){
		return rutasVuelo.values().stream().collect(Collectors.toSet());
	}

	public Vuelo obtenerVuelo(DTReserva reserva) {
		for (RutaVuelo rutaVuelo: rutasVuelo.values()) {
			for (Vuelo vuelo: rutaVuelo.obtenerVuelos()) {
				if (vuelo.tieneReserva(reserva)) return vuelo;
			}
		}
		return null;
	}

	public Vuelo obtenerVuelo(String nombre_ruta, String nombre_vuelo) {
		return this.rutasVuelo.get(nombre_ruta).obtenerVuelo(nombre_vuelo);
	}

	public Set<Vuelo> obtenerVuelos(RutaVuelo rutaVuelo) {
		return rutaVuelo.obtenerVuelos();
	}

}
