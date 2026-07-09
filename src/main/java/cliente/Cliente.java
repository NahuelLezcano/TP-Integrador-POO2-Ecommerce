package cliente;

import Pedido.Pedido;

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
	
	public void recibirMail(Pedido pedido) {
        // no hace nada.
    }

}
