package notificacion;

import Pedido.Estado;
import Pedido.Pedido;
import cliente.Cliente;

public class NotificadorDeEmail implements Observador, MailSender {

	private Cliente cliente;
	private Pedido pedido;

	public NotificadorDeEmail(Cliente cliente, Pedido pedido) {
		this.cliente = cliente;
		this.pedido = pedido;
	}

	@Override
	public void actualizar(Pedido pedido, Estado anterior, Estado nuevo) {
		String estadoNuevo = nuevo.getNombreDelEstado();
		if ("CONFIRMADO".equalsIgnoreCase(estadoNuevo) 
			|| "ENVIADO".equalsIgnoreCase(estadoNuevo)
			|| "ENTREGADO".equalsIgnoreCase(estadoNuevo)) {
			
			System.out.println("El pedido cambió de " +
			anterior.getNombreDelEstado() +
			" a " +
			estadoNuevo);
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Pedido getPedido() {
		return pedido;
	}

	@Override
	public void enviarMail(String direccionDestino, String titulo, String mensaje, String adjunto) {
		System.out.println("Para: " + direccionDestino + "\n" + titulo + "\n" + mensaje + "\n" + adjunto);
	}
}
