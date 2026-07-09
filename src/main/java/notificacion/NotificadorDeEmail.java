package notificacion;

import Pedido.Estado;
import Pedido.Pedido;
import cliente.Cliente;

public class NotificadorDeEmail implements Observador {

	private Cliente cliente;
	private Pedido pedido;

	public NotificadorDeEmail(Cliente cliente, Pedido pedido) {
		this.cliente = cliente;
		this.pedido = pedido;
	}

	@Override
	public void actualizar(Pedido pedido, Estado anterior, Estado nuevo) {
		String nombreNuevo = nuevo.getNombreDelEstado();
		if ("CONFIRMADO".equalsIgnoreCase(nombreNuevo) 
			|| "ENVIADO".equalsIgnoreCase(nombreNuevo)
			|| "ENTREGADO".equalsIgnoreCase(nombreNuevo)) {
			
			System.out.println("El pedido cambió de " +
			anterior.getNombreDelEstado() +
			" a " +
			nombreNuevo);
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Pedido getPedido() {
		return pedido;
	}
}
