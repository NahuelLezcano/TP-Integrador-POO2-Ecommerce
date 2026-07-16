package Pagos;

import Tienda.*;
import catalogo.Item;
import cliente.Cliente;
import envio.MetodoDeEnvio;
import envioTest.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
    TarjetaDeCredito tarjetaCredito2;
    TransferenciaBancaria transferencia;
    TransferenciaBancaria transferencia2;
    BilleteraVirtual billeteraVirtual;
    BilleteraVirtual billeteraVirtual2;
    IBilleteraVirtual apiBV;
    ITransferenciaBancaria apiTrans;
    ITarjetaDeCredito apiTC;
    MetodoDeEnvio metodoDeEnvio;
    
    @BeforeEach
    void setUp() {
        unItem = mock(Item.class);
        unaTienda = mock(Tienda.class);
		clienteCredito = mock(Cliente.class);
		clienteTransferencia = mock(Cliente.class);
		clienteBilleteraVirtual = mock(Cliente.class);
		apiBV = mock(IBilleteraVirtual.class);
		apiTrans = mock(ITransferenciaBancaria.class);
		apiTC = mock(ITarjetaDeCredito.class);
        metodoDeEnvio = mock(MetodoDeEnvio.class);

        tarjetaCredito = new TarjetaDeCredito();
        tarjetaCredito2 = new TarjetaDeCredito(01234567, 555, "07/26");
        transferencia = new TransferenciaBancaria();
        transferencia2 = new TransferenciaBancaria(987654321, 753159828, "Alias");
        billeteraVirtual = new BilleteraVirtual();
        billeteraVirtual2 = new BilleteraVirtual(850.0);
        
        when(clienteCredito.getNombre()).thenReturn("usuario-TarjetaDeCrédito");
        when(clienteTransferencia.getNombre()).thenReturn("usuario-Transferencia");
        when(clienteBilleteraVirtual.getNombre()).thenReturn("usuario-BilleteraVirtual");

        compraConTarjeta = new CarritoDeCompra(unaTienda, tarjetaCredito, clienteCredito, metodoDeEnvio);
        compraConTransferencia = new CarritoDeCompra(unaTienda, transferencia, clienteTransferencia, metodoDeEnvio);
        compraConBilletera = new CarritoDeCompra(unaTienda, billeteraVirtual, clienteBilleteraVirtual, metodoDeEnvio);
    }

    @Test
    public void alComprarConTarjetaDeCredito_SeGeneraUnComprobante() {
        tarjetaCredito.setApi(apiTC);

        compraConTarjeta.agregarItem(unItem, 5);
        compraConTarjeta.pagar();
        assertEquals("usuario-TarjetaDeCrédito", tarjetaCredito.getComprobantes().getFirst().getUsuario());
        assertEquals(1000, tarjetaCredito.getComprobantes().getFirst().getNroOperacion());
    }

    @Test
    public void alUtilizarTransferenciaEnUnaCompra_SeGeneraUnComprobante() {
        transferencia.setApi(apiTrans);

        compraConTransferencia.agregarItem(unItem, 1);
        compraConTransferencia.pagar();
        assertEquals("usuario-Transferencia", transferencia.getComprobantes().getFirst().getUsuario());
        assertEquals(1001, transferencia.getComprobantes().getFirst().getNroOperacion());
    }

    @Test
    public void alUtilizarUnaBilleteraVirtualParaComprar_SeNoSeGeneraComprobante() {
        billeteraVirtual.setApi(apiBV);

        compraConBilletera.agregarItem(unItem, 4);
        compraConBilletera.pagar();
        assertTrue(billeteraVirtual.getComprobantes().isEmpty());
    }
    
    @Test
    void validarDatosTarjetaInvocaApi() {
        tarjetaCredito.setApi(apiTC);

        tarjetaCredito.validarDatos();
        verify(apiTC).validarDatos();
    }

    @Test
    void reservarFondosTarjetaInvocaPreAutorizacion() {
        tarjetaCredito.setApi(apiTC);

        tarjetaCredito.reservarFondos();
        verify(apiTC).preAutorizacion();
    }

    @Test
    void ejecutarTransaccionTransferenciaInvocaApi() {
        transferencia.setApi(apiTrans);

        transferencia.ejecutarTransaccion();
        verify(apiTrans).transferir();
    }

    @Test
    void notificarResultadoBilleteraInvocaPush() {
        billeteraVirtual.setApi(apiBV);

        billeteraVirtual.notificarResultado("usuario-BilleteraVirtual");
        verify(apiBV).notificacionPush("usuario-BilleteraVirtual");
    }

}
