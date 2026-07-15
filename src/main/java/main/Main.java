package main;

import Pagos.*;
import Pedido.*;
import Tienda.*;
import catalogo.*;
import cliente.Cliente;
import envio.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        // Demostración de la Tienda

        // Se instancian los Items.
        Item laptop = new Producto("1", "Laptop Pro", "x", "Computación", 1, 1500, "Computadora personal");
        Item mouse = new Producto("2", "Mouse Inalámbrico", "x", "Computación", 1, 25, "Mouse para computadora");
        Item escritorio = new Producto("3", "Escritorio Madera", "x", "Muebles", 15, 200, "Mueble de madera");
        Item silla = new Producto("4", "Silla Ergonómica Pro", "x", "Muebles", 5, 150, "Silla para oficina");
        Item teclado = new Producto("5", "Teclado Mecánico", "x", "Computación", 1, 80, "Teclado para computadora");
        Item packOficina = new Paquete("Pack oficina", "Paquete oficina", "Oficina", 1, List.of(laptop, escritorio, silla));

        // Se crean el Deposito, el Catalogo y la Tienda.
        Deposito unDeposito = new Deposito(new HashMap<>(Map.of(laptop, 5, mouse, 20, escritorio, 4, silla, 8, teclado, 20, packOficina, 1)));
        Catalogo unCatalogo = new Catalogo(new ArrayList<>(List.of(laptop, mouse,escritorio, silla, teclado, packOficina)), unDeposito);
        Tienda tienda = new Tienda(unDeposito, unCatalogo);

        // Objetos necesarios.
        TarjetaDeCredito tarjeta = new TarjetaDeCredito();
        tarjeta.setApi(new ApiDePrueba());
        Cliente juan = new Cliente("Juan", "juan@example.com");
        MetodoDeEnvio envioExpress = new EnvioExpress(500); // Falta completar aun.

        // Instancia de carrito, el carrito crea un Pedido.
        CarritoDeCompra carrito = new CarritoDeCompra(tienda, tarjeta, juan, envioExpress);

        carrito.agregarItem(laptop, 2);
        carrito.pagar();

        // Del carrito se pueden obtener los objetos Pedido y Envio.
        Pedido pedido = carrito.getPedido();
        Envio envio = pedido.getEnvio();

        // Prueba
        System.out.println("El estado del pedido es: " + pedido.estadoActual().getNombreDelEstado());
        System.out.println(tienda.getDeposito().cantidadEnStock(laptop));

    }
}
