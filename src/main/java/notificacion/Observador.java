package notificacion;

import Pedido.*;

public interface Observador {
	
	void actualizar(Pedido pedido, Estado estadoAnterior, Estado estadoNuevo);
	
}
