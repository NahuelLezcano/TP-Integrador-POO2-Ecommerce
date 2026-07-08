package cliente;

import java.util.*;

public class Cliente {
	
	private String nombre;
	private String correo;
	private List<String> bandejaDeEntrada = new ArrayList();
	
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
	
	public void agregarCorreo(String mensaje) {
		bandejaDeEntrada.add(mensaje);
	}
	
	public List<String> getMensajes(){
		return bandejaDeEntrada;
	}
	
	

}
