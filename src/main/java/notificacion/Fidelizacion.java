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
	
	public void enviarDescuento() {
		
	}

	public Pedido getPedido() {
		return pedido;
	}

	@Override
	public void enviarMail(String direccionDestino, String titulo, String mensaje, String adjunto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizar(Pedido pedido, Estado estadoAnterior, Estado estadoNuevo) {
		// TODO Auto-generated method stub
		
	}

}
