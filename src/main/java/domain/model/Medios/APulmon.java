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
@DiscriminatorValue("a_pulmon")
@Getter
@Setter
@NoArgsConstructor
public class APulmon extends MedioDeTransporte {
    @Column
    private String nombre;
    @Transient
    private ServicioDistancia servicio;

    public void setServicio(ServicioDistancia servicio) {
        this.servicio = servicio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public APulmon(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public double calcularDistancia(Punto puntoOrigen, Punto puntoDestino) throws IOException {
        return this.servicio.calcularDistancia(puntoOrigen, puntoDestino).getValor();
    }

    @Override
    public void agregarSolicitudViajeCompartido(Persona persona, ViajeCompartido viajeCompartido) {

    }

    @Override
    public TipoCombustible getTipoCombustible() {
        return TipoCombustible.SANGRE;
    }

    public Double getConsumoXKm() {
        return 0.0;
    }

}
