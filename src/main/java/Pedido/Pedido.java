package Pedido;

import Tienda.*;
import catalogo.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import notificacion.*;

public class Pedido {

    private Map<Item, Integer> items = new HashMap<>();
    private Tienda tienda;
    private Estado estadoDelPedido = new Borrador(this);
    private List<Observador> observadores = new ArrayList<>();

    public Pedido(Tienda tienda) {
        this.tienda = tienda;
    }

    //Métodos
    public void agregarItem(Item item, Integer cantidad) {
        estadoDelPedido.agregarItem(item, cantidad);
    }

    public void removerItem(Item item, Integer cantidad) {
        estadoDelPedido.removerItem(item, cantidad);
    }

    public String confirmar() {
        return estadoDelPedido.confirmar();
    }

    public String cancelar() {
        return estadoDelPedido.cancelar();
    }

    protected void cambiarEstado(Estado nuevoEstado) {
        Estado anterior = this.estadoDelPedido;
        this.estadoDelPedido = nuevoEstado;
        notificarObservadores(anterior, nuevoEstado);
    }

	public Tienda getTienda() {
        return tienda;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public int cantidadItemsAgregados() {
        return items.size(); //Solo devuelve los Items agregados, no las cantidades de cada item en total.
    }

    public Estado estadoActual() {
        return estadoDelPedido;
    }

    // Estos métodos solo deberían ser usado por el estado Borrador
    protected void agregarItemAlPedido(Item item, Integer cantidad) {
        items.merge(item, cantidad, Integer::sum);
    }

    protected void quitarItemAlPedido(Item item, Integer cantidad) {
        // Validar entrada inválida
        if (cantidad <= 0) return;

        items.computeIfPresent(item, (key, stockActual) -> {
            if (cantidad >= stockActual) {
                // Caso borde: Se pide más o igual de lo que hay -> Eliminamos del mapa (retorna null)
                return null;
            }
            // Caso normal: Queda stock positivo restante
            return stockActual - cantidad;
        });
    }

	public List<Observador> getObservadores() {
		return observadores;
	}
	
	public void agregarObservador(Observador observador) {
		observadores.add(observador);
	}
	
	private void notificarObservadores(Estado anterior, Estado nuevo) {
	    for (Observador o : observadores) {
	        o.actualizar(this, anterior, nuevo);
	    }
	}
	
	public String nombresDeItems() {
		if (hayItems()) {
			return items.keySet().stream().map(Item::getNombre).collect(Collectors.joining(", "));
		} else {
			return "";
		}
	}

	public int montoTotal() {
		if (hayItems()) {
			return items.entrySet().stream().mapToInt(e -> e.getKey().getPrecioFinal() * e.getValue()).sum();
		} else {
			return 0;
		}
	}

	public boolean hayItems() {
		return !items.isEmpty();
	}
	
}
