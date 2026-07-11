package notificacion;

import Pedido.Estado;
import Pedido.Pedido;
import factura.Factura;

public class GeneradorDeFactura implements Observador, MailSender {

	private Pedido pedido;

	public GeneradorDeFactura(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public void actualizar(Pedido pedido, Estado anterior, Estado nuevo) {
		String correo = getPedido().getCliente().getCorreo();
		if ("Entregado".equalsIgnoreCase(nuevo.getNombreDelEstado())) {
			Factura factura = crearFactura();
			enviarMail(correo, "Factura", "Desglose de su pedido:", factura.desgloseDeFactura());
		}
	}

	public Factura crearFactura() {
		return new Factura(getPedido().getItems());
	}

	public Pedido getPedido() {
		return pedido;
	}

	@Override
	public void enviarMail(String direccionDestino, String titulo, String mensaje, String adjunto) {
		System.out.println(direccionDestino + "\n" + titulo + "\n" + mensaje + "\n" + adjunto);

	}

}
