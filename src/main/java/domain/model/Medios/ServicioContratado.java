package domain.model.Medios;

import domain.model.Entidades.Persona;
import domain.model.GeoReferencia.ViajeCompartido;
import domain.model.GeoReferencia.Punto;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoCombustible;
import domain.model.Servicios.Distancia.ServicioDistancia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
@Entity
@DiscriminatorValue("servicio_contratado")
@Getter
@Setter
@NoArgsConstructor
public class ServicioContratado extends MedioDeTransporte {

    @Column(name = "tipo_servicio_contratado")
    private String tipoServicioContratado;
    @Transient
    private ServicioDistancia servicio;
    @Enumerated(EnumType.STRING)
    private TipoCombustible tipoCombustible;
    @Column(name = "consumo_x_km")
    private Double consumoXKm;

    public void setServicio(ServicioDistancia servicio) {
        this.servicio = servicio;
    }

    public double calcularDistancia(Punto puntoOrigen, Punto puntoDestino) throws IOException {
        return servicio.calcularDistancia(puntoOrigen, puntoDestino).getValor();
    }

    @Override
    public void agregarSolicitudViajeCompartido(Persona persona, ViajeCompartido nuevoViaje) {
        persona.agregarSolicitudViajeCompartido(nuevoViaje);
    }

    @Override
    public TipoCombustible getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(TipoCombustible tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }
    public Double getConsumoXKm() {
        return consumoXKm;
    }

    public void setConsumoXKm(Double consumoXKm) {
        this.consumoXKm = consumoXKm;
    }
}
