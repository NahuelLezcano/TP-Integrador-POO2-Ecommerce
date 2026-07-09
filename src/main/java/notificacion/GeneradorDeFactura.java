package notificacion;

import Pedido.Estado;
import Pedido.Pedido;

public class GeneradorDeFactura implements Observador {
	
	private Pedido pedido;
	
	public GeneradorDeFactura (Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
    public void actualizar(Pedido pedido, Estado anterior, Estado nuevo) {
        if ("ENTREGADO".equalsIgnoreCase(nuevo.getNombreDelEstado())) {
            crearFactura();
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

}
