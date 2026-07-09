package cliente;

import Pedido.Estado;
import factura.Factura;

public class Cliente {
	
	private String nombre;
	private String correo;
	
	public Cliente (String nombre, String correo) {
		this.nombre = nombre;
		this.correo = correo;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void recibirFactura(Factura factura) {
        // no hace nada.
    }
	
	public void recibirActualizacion(Estado estado) {
		// no hace nada.
	}

}
