package clienteTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cliente.Cliente;

class clienteTest {
	
	Cliente cliente;

	@BeforeEach
	void setUp() {
		cliente = new Cliente("Pepe");
	}

	@Test
	void nombreTest() {
		assertEquals("Pepe", cliente.getNombre());
	}

}
