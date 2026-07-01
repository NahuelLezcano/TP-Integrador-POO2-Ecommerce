package Tienda;

import java.time.*;

import catalogo.*;

public class Venta {
	private Item item;
	private int cantidad;
	private int precioCobrado;
	private LocalDate fecha;
	
	public Venta(Item item, int cantidad, int precioCobrado, LocalDate fecha) {
		this.item = item;
		this.cantidad = cantidad;
		this.precioCobrado = precioCobrado;
		this.fecha = fecha;
	}

	public Item getItem() {
		return item;
	}
	
	public int getCantidad() {
		return cantidad;
	}

	public int getPrecio() {
		return precioCobrado;
	}

	public LocalDate getFecha() {
		return fecha;
	}

}
