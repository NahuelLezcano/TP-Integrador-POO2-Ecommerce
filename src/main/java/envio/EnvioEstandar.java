package envio;

import Pedido.Pedido;

public class EnvioEstandar implements MetodoDeEnvio {

	private Direccion direccionEnvio;
	private CorreoArgentino correoArgentino;

	public EnvioEstandar(Direccion direccionEnvio, CorreoArgentino correoArgentino) {
		this.direccionEnvio = direccionEnvio;
		this.correoArgentino = correoArgentino;
	}

	@Override
	public double costoDeEnvio(Pedido pedido) {
		Direccion direccionDestino = pedido.getTienda().getDeposito().getDireccion();

		return getCorreoArgentino().estimarEnvio(pedido.pesoTotalPedido(), direccionDestino);
	}

	@Override
	public int estimacionDeDias(Pedido pedido) {
		Direccion direccionDeposito = pedido.getTienda().getDeposito().getDireccion();
		double distanciaKm = direccionEnvio.distanciaEnKmA(direccionDeposito);

		// Cada 15 km suma un día desde 5 hasta 7
		int dias = 5 + (int) (distanciaKm / 15);

		return Math.min(dias, 7);
	}

	private CorreoArgentino getCorreoArgentino() {
		return correoArgentino;
	}

}
