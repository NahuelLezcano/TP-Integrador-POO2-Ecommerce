package envio;

import java.util.Map;

import Pedido.Pedido;
import Tienda.Tienda;
import catalogo.Item;

public class RetiroSucursal implements MetodoDeEnvio {

	private Tienda tienda; // La tienda seria la sucursal.

	public RetiroSucursal(Tienda tienda) {
		this.tienda = tienda;
	}

	@Override
	public double costoDeEnvio(Pedido pedido) {
		return 0;
	}

	@Override
	public int estimacionDeDias(Pedido pedido) {
		if (hayTodosLosItemsDisponibles(pedido)) {
			return diasPorStockLocal();
		}
		return diasPorTrasladoInterno();
	}

	public Tienda getTienda() {
		return tienda;
	}

	public int diasPorStockLocal() {
		return 0;
	}

	private int diasPorTrasladoInterno() {
		return 1;
	}

	private boolean hayTodosLosItemsDisponibles(Pedido pedido) {
		for (Map.Entry<Item, Integer> items : pedido.getItems().entrySet()) {
			Item item = items.getKey();
			int cantidad = items.getValue();

			if (!tienda.getDeposito().validar(item, cantidad)) {
				return false;
			}
		}
		return true;
	}
}
