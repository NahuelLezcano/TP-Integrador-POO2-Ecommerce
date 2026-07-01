package Tienda;

import java.time.*;

import catalogo.*;

public class Venta {
	private Item item;
	private int cantidad;
	private int precio;
	private LocalDate fecha;
	
	public Venta(Item item, int cantidad, int precio, LocalDate fecha) {
		this.item = item;
		this.cantidad = cantidad;
		this.precio = precio;
		this.fecha = fecha;
	}

	public Item getItem() {
		return item;
	}
	
	public int getCantidad() {
		return cantidad;
	}

	public int getPrecio() {
		return precio;
	}

	public LocalDate getFecha() {
		return fecha;
	}

}
