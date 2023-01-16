package domain.model.Servicios.Distancia.Adapters;

import domain.model.GeoReferencia.Punto;
import domain.model.LectorArchivos.Propiedades;
import domain.model.Servicios.Distancia.Entidades.DistanciaEntreDirecciones;
import domain.model.Servicios.Distancia.ServicioDistanciaDirecciones;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AdapterServicio implements ServicioDistanciaAdapter {
    private final static String urlAPI = "https://ddstpa.com.ar/api/";
    private final static String propertiesFile = "src/main/java/domain/model/Servicios/Distancia/Token/TokenApiDistancias.properties";
    private Retrofit retrofit;
    private String token;

    public AdapterServicio(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public DistanciaEntreDirecciones calcularDistancia(Punto ubicacionOrigen, Punto ubicacionDestino) throws IOException {
        String token = this.obtenerToken();
        ServicioDistanciaDirecciones distanciaEntreDirecciones = this.retrofit.create(ServicioDistanciaDirecciones.class);

        Call<DistanciaEntreDirecciones> requestDistancia = distanciaEntreDirecciones.distanciaDirecciones(
                token,
                ubicacionOrigen.getMunicipio().getLocalidadId(),
                ubicacionOrigen.getDireccion(),
                ubicacionOrigen.getAltura(),
                ubicacionDestino.getMunicipio().getLocalidadId(),
                ubicacionDestino.getDireccion(),
                ubicacionDestino.getAltura());

        Response<DistanciaEntreDirecciones> responseDistancia = requestDistancia.execute();
        return responseDistancia.body();
    }

    private String obtenerToken() throws FileNotFoundException{
        Propiedades propiedades = new Propiedades();
        String token = propiedades.LeerPropiedades(propertiesFile);
        return token;
    }
}
