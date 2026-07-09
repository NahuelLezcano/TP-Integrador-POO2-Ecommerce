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
			
			String correo = getPedido().getCliente().getCorreo();
			String mensaje = "El estado del pedido cambió de " + anterior.getNombreDelEstado() + " a " + estadoNuevo;

			enviarMail(correo, "Estado actualizado", mensaje, "");
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
		System.out.println(direccionDestino + "\n" + titulo + "\n" + mensaje + "\n" + adjunto);
	}
}
