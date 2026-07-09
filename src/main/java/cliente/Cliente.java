package cliente;

import Pedido.Estado;
import factura.Factura;

public class Cliente {
	
	private String nombre;
	
	public Cliente (String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void recibirFactura(Factura factura) {
        // recibir correo en el mail, no hace nada.
    }
	
	public void recibirActualizacion(Estado estado) {
		// recibir correo en el mail, no hace nada.
	}
	
	

}
