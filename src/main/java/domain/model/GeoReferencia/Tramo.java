package domain.model.GeoReferencia;

import domain.model.Entidades.EntidadPersistente;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;
import domain.model.Medios.MedioDeTransporte;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tramo")
@Getter
@Setter
@NoArgsConstructor
public class Tramo extends EntidadPersistente {
    @ManyToOne(cascade = CascadeType.ALL)
    private Punto puntoOrigen;
    @ManyToOne(cascade = CascadeType.ALL)
    private Punto puntoDestino;

    @OneToOne(cascade = CascadeType.ALL)
    private MedioDeTransporte medioDeTransporte;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Persona> pasajeros = new ArrayList<>();
    @Column(name = "totalTramo")
    private Double totalTramo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trayecto_id", referencedColumnName = "id")
    private Trayecto trayecto;

    public Tramo(Punto puntoOrigen, Punto puntoDestino, MedioDeTransporte medioDeTransporte, Persona persona) {
        this.puntoOrigen = puntoOrigen;
        this.puntoDestino = puntoDestino;
        this.medioDeTransporte = medioDeTransporte;
        this.pasajeros.add(persona);
    }

    public Punto getPuntoOrigen() {
        return puntoOrigen;
    }

    public void setPuntoOrigen(Punto puntoOrigen) {
        this.puntoOrigen = puntoOrigen;
    }

    public Punto getPuntoDestino() {
        return puntoDestino;
    }

    public void setPuntoDestino(Punto puntoDestino) {
        this.puntoDestino = puntoDestino;
    }

    public List<Persona> getPasajeros() { return pasajeros; }

    public void setPasajeros(List<Persona> pasajeros) { this.pasajeros = pasajeros; }

    public MedioDeTransporte getMedioDeTransporte() {
        return medioDeTransporte;
    }

    public void setMedioDeTransporte(MedioDeTransporte medioDeTransporte) { this.medioDeTransporte = medioDeTransporte; }

    public List<ViajeCompartido> compartirViaje(Organizacion organizacion, Persona ... personas){
        List<ViajeCompartido> viajes = new ArrayList<>();
        for (Persona pasajero: personas) {
            ViajeCompartido viajeCompartido = new ViajeCompartido(this, organizacion, this.pasajeros.get(0), pasajero);
            pasajero.agregarSolicitudViajeCompartido(viajeCompartido);
            viajes.add(viajeCompartido);
        }
        return viajes;
    }

    public ViajeCompartido compartirUnViaje(Organizacion organizacion, Persona persona){
        ViajeCompartido viajeCompartido = new ViajeCompartido(this, organizacion, this.pasajeros.get(0), persona);
        persona.agregarSolicitudViajeCompartido(viajeCompartido);
        return viajeCompartido;
    }

    public void agregarPasajero(Persona persona){
        pasajeros.add(persona);
    }

    public double calcularDistancia() throws IOException {
        this.totalTramo = medioDeTransporte.calcularDistancia(puntoOrigen ,puntoDestino);
        return this.totalTramo;
    }

    public Double getTotalTramo() {
        return totalTramo;
    }

}
