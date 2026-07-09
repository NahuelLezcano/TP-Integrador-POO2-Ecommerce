package Pagos;

import Tienda.*;
import catalogo.Item;
import cliente.Cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PagosTest {

    Item unItem;
    Cliente clienteCredito;
    Cliente clienteTransferencia;
    Cliente clienteBilleteraVirtual;
    Tienda unaTienda;
    CarritoDeCompra compraConTarjeta;
    CarritoDeCompra compraConTransferencia;
    CarritoDeCompra compraConBilletera;
    TarjetaDeCredito tarjetaCredito;
    TransferenciaBancaria transferencia;
    BilleteraVirtual billeteraVirtual;

    @BeforeEach
    void setUp() {
        unItem = mock(Item.class);
        unaTienda = mock(Tienda.class);
		clienteCredito = mock(Cliente.class);
		clienteTransferencia = mock(Cliente.class);
		clienteBilleteraVirtual = mock(Cliente.class);
        
        tarjetaCredito = new TarjetaDeCredito();
        transferencia = new TransferenciaBancaria();
        billeteraVirtual = new BilleteraVirtual();
        
        when(clienteCredito.getNombre()).thenReturn("usuario-TarjetaDeCrédito");
        when(clienteTransferencia.getNombre()).thenReturn("usuario-Transferencia");
        when(clienteBilleteraVirtual.getNombre()).thenReturn("usuario-BilleteraVirtual");

        compraConTarjeta = new CarritoDeCompra(unaTienda, tarjetaCredito, clienteCredito);
        compraConTransferencia = new CarritoDeCompra(unaTienda, transferencia, clienteTransferencia);
        compraConBilletera = new CarritoDeCompra(unaTienda, billeteraVirtual, clienteBilleteraVirtual);
    }

    @Test
    public void alComprarConTarjetaDeCredito_SeGeneraUnComprobante() {
        ITarjetaDeCredito api = mock(ITarjetaDeCredito.class);
        tarjetaCredito.setApi(api);

        compraConTarjeta.agregarItem(unItem, 5);
        compraConTarjeta.pagar();
        assertEquals("usuario-TarjetaDeCrédito", tarjetaCredito.getComprobantes().getFirst().getUsuario());
        assertEquals(1000, tarjetaCredito.getComprobantes().getFirst().getNroOperacion());
    }

    @Test
    public void alUtilizarTransferenciaEnUnaCompra_SeGeneraUnComprobante() {
        ITransferenciaBancaria api = mock(ITransferenciaBancaria.class);
        transferencia.setApi(api);

        compraConTransferencia.agregarItem(unItem, 1);
        compraConTransferencia.pagar();
        assertEquals("usuario-Transferencia", transferencia.getComprobantes().getFirst().getUsuario());
        assertEquals(1001, transferencia.getComprobantes().getFirst().getNroOperacion());
    }

    @Test
    public void alUtilizarUnaBilleteraVirtualParaComprar_SeNoSeGeneraComprobante() {
        IBilleteraVirtual api = mock(IBilleteraVirtual.class);
        billeteraVirtual.setApi(api);

        compraConBilletera.agregarItem(unItem, 4);
        compraConBilletera.pagar();
        assertTrue(billeteraVirtual.getComprobantes().isEmpty());
    }

}
