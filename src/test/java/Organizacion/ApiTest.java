package Organizacion;
import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.GeoReferencia.Ubicacion;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoVehiculo;
import domain.model.Medios.VehiculoParticular.VehiculoParticular;
import domain.model.Servicios.Distancia.Adapters.ServicioDistanciaAdapter;
import domain.model.Servicios.Distancia.Entidades.DistanciaEntreDirecciones;
import domain.model.Servicios.Distancia.ServicioDistancia;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import static org.mockito.Mockito.*;

import java.io.IOException;




public class ApiTest {

    Ubicacion origen = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"), "Calle falsa", "123");
    Ubicacion destino = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"), "Avenida siempre viva", "742");

    VehiculoParticular homeroMovil = new VehiculoParticular(TipoVehiculo.AUTO);

    private ServicioDistancia servicioDistancia;

    private ServicioDistanciaAdapter adapterMock;

    @Before
    public void init(){
        this.adapterMock = mock(ServicioDistanciaAdapter.class);
        this.servicioDistancia = ServicioDistancia.getInstancia();
        this.servicioDistancia.setAdapter(this.adapterMock);

    }

    @Test
    public void apiTestMockito() throws IOException{
        double distancia = this.distanciaMock();
        DistanciaEntreDirecciones distanciaMock = mock(DistanciaEntreDirecciones.class);

        when(distanciaMock.getValor()).thenReturn(distancia);
        when(this.adapterMock.calcularDistancia(origen,destino)).thenReturn(distanciaMock);

        Assertions.assertEquals(12.6, this.servicioDistancia.calcularDistancia(origen,destino).getValor(),0.0);
    }

    private double distanciaMock(){
        double valor = 12.6;
        return valor;
    }
}
