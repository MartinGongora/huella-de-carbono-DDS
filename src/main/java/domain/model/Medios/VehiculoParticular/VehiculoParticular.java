package domain.model.Medios.VehiculoParticular;

import domain.model.Entidades.Persona;
import domain.model.GeoReferencia.ViajeCompartido;
import domain.model.GeoReferencia.Punto;
import domain.model.Medios.MedioDeTransporte;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoCombustible;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoVehiculo;
import domain.model.Servicios.Distancia.ServicioDistancia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;

@Entity
@DiscriminatorValue("vehiculo_particular")
@Getter
@Setter
@NoArgsConstructor
public  class VehiculoParticular extends MedioDeTransporte {

    @Enumerated(EnumType.STRING)
    //@Column
    private TipoCombustible tipoCombustible;
    @Transient
    private ServicioDistancia servicio;
    @Enumerated(EnumType.STRING)
   // @Column
    private TipoVehiculo vehiculo;
    @Column(name = "consumo")
    private Double consumoXKm;


    public VehiculoParticular(TipoVehiculo tipoVehiculo){
        this.vehiculo = tipoVehiculo;
    }
    public void setServicio(ServicioDistancia servicio) {
        this.servicio = servicio;
    }

    public double calcularDistancia(Punto puntoOrigen, Punto puntoDestino) throws IOException {
        return servicio.calcularDistancia(puntoOrigen, puntoDestino).getValor();
    }

    @Override
    public void agregarSolicitudViajeCompartido(Persona persona,  ViajeCompartido nuevoViaje) {
        persona.agregarSolicitudViajeCompartido(nuevoViaje);
    }

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

    public void setVehiculo(TipoVehiculo vehiculo){ this.vehiculo = vehiculo;}

}