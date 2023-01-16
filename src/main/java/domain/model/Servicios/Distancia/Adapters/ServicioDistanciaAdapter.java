package domain.model.Servicios.Distancia.Adapters;

import domain.model.GeoReferencia.Punto;
import domain.model.Servicios.Distancia.Entidades.DistanciaEntreDirecciones;

import java.io.IOException;

public interface ServicioDistanciaAdapter {
    public DistanciaEntreDirecciones calcularDistancia(Punto ubicacionOrigen, Punto ubicacionDestino) throws IOException;

}
