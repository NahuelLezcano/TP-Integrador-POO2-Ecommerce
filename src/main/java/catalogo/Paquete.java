package catalogo;

import java.util.*;

public class Paquete implements Item  {
	
	private String nombre;
	private String descripcion;
	private int descuento;
	private List<Item> items = new ArrayList<>();
	
	public Paquete(String nombre, String descripcion, int descuento, List<Item> items) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.descuento = descuento;
		this.items = items;
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String getDescripcion() {
		return descripcion;
	}
	
	public int getDescuento() {
		return descuento;
	}
	
	public List<Item> getItems(){
		return items;
	}
	
	public void agregarItems(Item item) {
		items.add(item);
	}
	
	public void removerItem(Item item) {
        items.remove(item);
    }
	
	@Override
	public int getPrecioBase() {
		return getItems().stream()
						.mapToInt(Item::getPrecioFinal)
						.sum();
	}
	
	@Override
	public int getPrecioFinal() {
	    return (int) (getPrecioBase() * (1 - descuento / 100.0));
	}
	
	@Override
	public boolean validar() {
		return  tieneItems() && itemsSonValidos();
	}
	
	public boolean tieneItems() {
		return !getItems().isEmpty();
	}
	
	public boolean itemsSonValidos() {
		return getItems().stream().allMatch(Item::validar);
	}

}
