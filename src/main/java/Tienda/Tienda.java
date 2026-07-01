package Tienda;

import catalogo.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Tienda {

	private Deposito deposito;
	private Catalogo catalogo;
	private List<Venta> ventas = new ArrayList<Venta>();

	public Tienda(Deposito deposito, Catalogo catalogo) {
		this.deposito = deposito;
		this.catalogo = catalogo;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}

	public void registrarVenta(Item item, int cantidad, int precio, LocalDate fecha) {
		deposito.removerDelStock(item, cantidad);
		catalogo.removerItem(item);
		ventas.add(new Venta(item, cantidad, precio, fecha));
	}

	public int cantidadEnStock(Item item) {
		return deposito.cantidadEnStock(item);
	}

	public int cantidadVendida(Item item) {
		return ventas.stream().filter(v -> v.getItem().equals(item)).mapToInt(Venta::getCantidad).sum();
	}

	/* Agrupo las ventas por item groupingBy(Venta::getItem) sumando las cantidades
	 * vendidas summingInt(Venta::getCantidad), para recorrer el Map<Item, Integer>
	 * lo convierto en un conjunto Map.Entry (pares), con el reversed() lo ordeno de
	 * mayor a menor. con getKey agarro el Item y devuelvo la lista final de items,
	 * ya ordenada por ventas de mayor a menor.
	 */
	public List<Item> productosMasVendidos() {
		return ventas.stream().collect(Collectors.groupingBy(Venta::getItem, Collectors.summingInt(Venta::getCantidad)))
				.entrySet().stream().sorted(Map.Entry.<Item, Integer>comparingByValue().reversed())
				.map(Map.Entry::getKey).toList();
	}

	public double precioPromedioCobrado(Item item) {
		return ventas.stream().filter(v -> v.getItem().equals(item)).mapToInt(Venta::getPrecio).average().orElse(0.0);
	}
}
