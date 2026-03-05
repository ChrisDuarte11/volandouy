package logica.fabrica;

import java.time.LocalDate;
import java.util.Set;

import datatypes.DTAerolinea;
import datatypes.DTCliente;
import datatypes.DTPaquete;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import excepciones.ClienteTieneReservaEnVueloException;
import excepciones.ReservaCantidadAsientosInvalidos;
import excepciones.RutaNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import logica.enums.EstadoRuta;
import logica.enums.Pais;
import logica.enums.TipoDoc;
import logica.usuario.Cliente;

public interface IUsuario {

	public abstract void agregarRutaVuelo(String aerolinea, String rutaVuelo);

	public abstract void editarUsuario(DTUsuario usuario) throws UsuarioNoExisteException;

	public abstract Set<DTUsuario> getUsuarios();

	public abstract void ingresarDatosAerolinea(String nickname, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta, String descripcion, String web) throws UsuarioRegistradoException;

	public abstract void ingresarDatosCliente(String nickname, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta, String apellido, LocalDate fechaNac, TipoDoc tipoDoc, String numDoc, Pais nacionalidad) throws UsuarioRegistradoException;

	public abstract void ingresarDatosReserva(DTReserva reserva, String nicknameCliente, String nombreRuta) throws UsuarioNoExisteException, ClienteTieneReservaEnVueloException, ReservaCantidadAsientosInvalidos;

	public abstract Set<String> listarClientes();

	public abstract Set<String> listarUsuarios();

	public abstract DTAerolinea obtenerAerolinea(String nombreRuta) throws UsuarioNoExisteException, RutaNoExisteException;

	public abstract Set<String> obtenerAerolineas();

	public abstract Cliente obtenerCliente(String Cliente) throws UsuarioNoExisteException;

	public abstract DTCliente obtenerClienteReserva(DTReserva reserva) throws UsuarioNoExisteException;

	public abstract Set<DTAerolinea> obtenerDTAerolineas();

	public abstract Set<DTCliente> obtenerDTClientes() throws UsuarioNoExisteException;

	public abstract Set<DTPaquete> obtenerPaquetes(String nickname) throws UsuarioNoExisteException;

	public abstract DTReserva obtenerReserva(int idReserva) throws UsuarioNoExisteException;

	public abstract DTReserva obtenerReserva(String nicknameUsuario, String nombreVuelo) throws UsuarioNoExisteException;

	public abstract Set<DTReserva> obtenerReservas(String nickname) throws UsuarioNoExisteException;

	public abstract Set<DTRutaVuelo> obtenerRutasAerolinea(String aerolinea) throws UsuarioNoExisteException;

	public abstract Set<DTRutaVuelo> obtenerRutasAerolinea(String aerolinea, EstadoRuta estado) throws UsuarioNoExisteException;

	public abstract Set<DTRutaVuelo> obtenerRutasAerolinea(String aerolinea, EstadoRuta estado1, EstadoRuta estado2) throws UsuarioNoExisteException;

	public abstract DTUsuario obtenerUsuario(String nicknameEmail) throws UsuarioNoExisteException;

}
