package cliente;

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
	
	

}
