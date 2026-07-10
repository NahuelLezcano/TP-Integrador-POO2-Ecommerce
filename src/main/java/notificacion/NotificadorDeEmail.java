package notificacion;

import Pedido.Estado;
import Pedido.Pedido;

public class NotificadorDeEmail implements Observador, MailSender {

	private Pedido pedido;

	public NotificadorDeEmail(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public void actualizar(Pedido pedido, Estado anterior, Estado nuevo) {
		String estadoNuevo = nuevo.getNombreDelEstado();
		if ("Pago".equalsIgnoreCase(estadoNuevo) 
			|| "Enviado".equalsIgnoreCase(estadoNuevo)
			|| "Entregado".equalsIgnoreCase(estadoNuevo)) {
			
			String correo = getPedido().getCliente().getCorreo();
			String mensaje = "El estado del pedido cambió de " + anterior.getNombreDelEstado() + " a " + estadoNuevo;

			enviarMail(correo, "Estado actualizado", mensaje, "");
		}
	}

	public Pedido getPedido() {
		return pedido;
	}

	@Override
	public void enviarMail(String direccionDestino, String titulo, String mensaje, String adjunto) {
		System.out.println(direccionDestino + "\n" + titulo + "\n" + mensaje + "\n" + adjunto);
	}
}
