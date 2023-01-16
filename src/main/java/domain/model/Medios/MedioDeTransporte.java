package domain.model.Medios;

import domain.model.Entidades.EntidadPersistente;
import domain.model.Entidades.Persona;
import domain.model.GeoReferencia.ViajeCompartido;
import domain.model.GeoReferencia.Punto;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoCombustible;

import javax.persistence.*;
import java.io.IOException;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "medio_de_transporte")
@DiscriminatorColumn(name = "medio")
public abstract class MedioDeTransporte extends EntidadPersistente {
    public abstract double calcularDistancia(Punto puntoOrigen, Punto puntoDestino) throws IOException;
    public abstract void agregarSolicitudViajeCompartido(Persona persona, ViajeCompartido viajeCompartido);
    public abstract TipoCombustible getTipoCombustible();
    public abstract Double getConsumoXKm();
}
