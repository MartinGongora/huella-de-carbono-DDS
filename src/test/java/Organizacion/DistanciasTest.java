package Organizacion;

import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.GeoReferencia.Tramo;
import domain.model.GeoReferencia.Ubicacion;
import domain.model.Medios.APulmon;
import domain.model.Medios.TransportePublico.Estacion;
import domain.model.Medios.TransportePublico.TipoTransportePublico;
import domain.model.Medios.TransportePublico.TransportePublico;
import domain.model.Servicios.Distancia.Adapters.AdapterServicio;
import domain.model.Servicios.Distancia.ServicioDistancia;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DistanciasTest {


    Ubicacion destino = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"), "Avenida siempre viva", "742");
    Estacion malabia = new Estacion("Malabia", destino, null, null, 100.0);
    Estacion dorrego = new Estacion("Dorrego", null, null, null, 150.0);
    Estacion lacroze = new Estacion("Lacroze", null, null, null, 200.0);
    Ubicacion origen = new Ubicacion(new Municipio("Palermo", 1), new Provincia("BSAS"), "Calle falsa", "123");

    APulmon aPie = new APulmon("A pata");

    Tramo tramoPrueba = new Tramo(origen, malabia, aPie, null);

    TransportePublico subteB = new TransportePublico("B", TipoTransportePublico.SUBTE);

    private ServicioDistancia servicioDistancia;

    @Before
    public void init(){
     subteB.agregarEstacion(malabia);
     subteB.agregarEstacion(dorrego);
     subteB.agregarEstacion(lacroze);
     servicioDistancia = ServicioDistancia.getInstancia();
     servicioDistancia.setAdapter(new AdapterServicio());
     aPie.setServicio(servicioDistancia);
    }

  @Test
  public void laDistanciaTotalDa100(){
    Assert.assertEquals(100.0, subteB.calcularDistancia(malabia, lacroze),0.0);
  }

  @Test
  public void calculandoHaciaElOtroLadoDa100Tmb(){
    Assert.assertEquals(100.0, subteB.calcularDistancia(lacroze, malabia), 0.0);
  }
  @Test
  public void  calculandoDeUnaUbicacionAEstacionMalabia() throws IOException {
   Assert.assertNotEquals(0,  tramoPrueba.calcularDistancia());
  }

  @Test
  public void distanciaDeUnaSolaEstacion(){
    Assert.assertEquals(50.0, subteB.calcularDistancia(malabia, dorrego), 0.0);
    Assert.assertEquals(50.0, subteB.calcularDistancia(dorrego, malabia), 0.0);
  }
}
