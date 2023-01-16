package domain.model.Servicios.Distancia;

import domain.model.Servicios.Distancia.Entidades.DistanciaEntreDirecciones;
import retrofit2.Call;
import retrofit2.http.*;

public interface ServicioDistanciaDirecciones {

    @GET("distancia")
    Call<DistanciaEntreDirecciones> distanciaDirecciones(
            @Header("Authorization") String token,
            @Query("localidadOrigenId") int localidadOrigenId,
            @Query("calleOrigen") String calleOrigen,
            @Query("alturaOrigen") String alturaOrigen,
            @Query("localidadDestinoId") int localidadDestinoId,
            @Query("calleDestino") String calleDestino,
            @Query("alturaDestino") String alturaDestino);
}
