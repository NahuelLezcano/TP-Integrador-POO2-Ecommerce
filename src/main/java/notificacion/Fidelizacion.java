package notificacion;

import Pedido.Estado;
import Pedido.Pedido;

public class Fidelizacion implements Observador, MailSender {
	
	private final double descuento = 5.0;
	private Pedido pedido;
	
	public Fidelizacion(Pedido pedido) {
		this.pedido = pedido;
	}

	public double getDescuento() {
		return descuento;
	}
	
	public double calcularDescuento() {
		return getDescuento() / 100.0;
	}

	public Pedido getPedido() {
		return pedido;
	}

	@Override
	public void enviarMail(String direccionDestino, String titulo, String mensaje, String adjunto) {
		System.out.println(direccionDestino + "\n" + titulo + "\n" + mensaje + "\n" + adjunto);
		
	}

	@Override
	public void actualizar(Pedido pedido, Estado estadoAnterior, Estado estadoNuevo) {
		String correo = getPedido().getCliente().getCorreo();
		String mensaje = "Recibió un descuento del " + getDescuento() + "%";
		if ("Cancelado".equalsIgnoreCase(estadoNuevo.getNombreDelEstado())) {
			enviarMail(correo, "Descuento", mensaje, "");
		}
		
	}

}
