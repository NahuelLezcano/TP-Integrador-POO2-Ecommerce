package notificacion;

import Pedido.Estado;
import Pedido.Pedido;

public class GeneradorDeFactura implements Observador, MailSender {
	
	private Pedido pedido;
	
	public GeneradorDeFactura (Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
    public void actualizar(Pedido pedido, Estado anterior, Estado nuevo) {
		String correo = getPedido().getCliente().getCorreo();
        if ("Entregado".equalsIgnoreCase(nuevo.getNombreDelEstado())) {
            enviarMail(correo, "Factura", "Se adjunta la factura del pedido", crearFactura());
        }
    }

	public String crearFactura() {
		String nombres = getPedido().nombresDeItems();
	    int total = getPedido().montoTotal();

	    StringBuilder sb = new StringBuilder();
	    sb.append("Factura:\n");
	    sb.append(nombres).append(".\n");
	    sb.append("Monto Final: $").append(total);
	    return sb.toString();
    }
	
	public Pedido getPedido() {
		return pedido;
	}

	@Override
	public void enviarMail(String direccionDestino, String titulo, String mensaje, String adjunto) {
		System.out.println(direccionDestino + "\n" + titulo + "\n" + mensaje + "\n" + adjunto);
		
	}

}
