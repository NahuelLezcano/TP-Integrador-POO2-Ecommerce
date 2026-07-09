package clienteTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cliente.Cliente;

class clienteTest {
	
	Cliente cliente;

	@BeforeEach
	void setUp() {
		cliente = new Cliente("Pepe", "pepe@gmail.com");
	}

	@Test
	void nombreTest() {
		assertEquals("Pepe", cliente.getNombre());
	}
	
	@Test
	void correoTest() {
		assertEquals("pepe@gmail.com", cliente.getCorreo());
	}

}
