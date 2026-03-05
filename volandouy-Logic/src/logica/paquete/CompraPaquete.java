package logica.paquete;

import java.time.LocalDate;
import java.util.Set;

import datatypes.DTReserva;
import logica.usuario.Cliente;

public class CompraPaquete {

	private float costo;
	private LocalDate fechaCompra;
	private LocalDate vencimiento;
	private Cliente cliente;
	private Paquete paquete;
	private Set<DTReserva> reservas;

	public CompraPaquete(float costo, LocalDate fechaCompra, LocalDate vencimiento, Cliente cliente, Paquete paquete, Set<DTReserva> reservas) {
		this.costo = costo;
		this.fechaCompra = fechaCompra;
		this.vencimiento = vencimiento;
		this.cliente = cliente;
		this.paquete = paquete;
		this.reservas = reservas;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public float getCosto() {
		return this.costo;
	}

	public LocalDate getFechaCompra() {
		return this.fechaCompra;
	}

	public Paquete getPaquete() {
		return this.paquete;
	}

	public Set<DTReserva> getReservas() {
		return this.reservas;
	}

	public LocalDate getVencimiento() {
		return this.vencimiento;
	}

}
