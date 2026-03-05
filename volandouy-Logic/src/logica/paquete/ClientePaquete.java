package logica.paquete;

import java.time.LocalDate;

import datatypes.DTPaquete;

public class ClientePaquete {

	private float costo;
	private LocalDate vencimiento;
	private LocalDate fechaCompra;
	private Paquete paquete;

	public ClientePaquete(LocalDate fechaCompra, Paquete paquete) {
		this.costo = paquete.getCosto();
		this.vencimiento = fechaCompra.plusDays(paquete.getValidezDias());
		this.fechaCompra = fechaCompra;
	}

	public float getCosto() {
		return this.costo;
	}

	public DTPaquete getDTPaquete() {
		return this.paquete.getDT();
	}

	public LocalDate getFechaCompra() {
		return this.fechaCompra;
	}

	public Paquete getPaquete() {
		return this.paquete;
	}

	public LocalDate getVencimiento() {
		return this.vencimiento;
	}

}
