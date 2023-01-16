package domain.model.Servicios.Distancia;

import domain.model.GeoReferencia.Punto;
import domain.model.Servicios.Distancia.Adapters.ServicioDistanciaAdapter;
import domain.model.Servicios.Distancia.Entidades.DistanciaEntreDirecciones;


import java.io.IOException;

public class ServicioDistancia {
    private static ServicioDistancia instancia = null;
    private ServicioDistanciaAdapter adapter;

    public void setAdapter(ServicioDistanciaAdapter adapter) {
        this.adapter = adapter;
    }

    public static ServicioDistancia getInstancia(){
        if (instancia == null){
            instancia = new ServicioDistancia();
        }
        return instancia;
    }

    public DistanciaEntreDirecciones calcularDistancia(Punto ubicacionOrigen, Punto ubicacionDestino) throws IOException {
        return this.adapter.calcularDistancia(ubicacionOrigen, ubicacionDestino);
    }
}
