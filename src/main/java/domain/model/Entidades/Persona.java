package domain.model.Entidades;

import domain.model.Entidades.HuellaCarbono.HuellaDeCarbono;
import domain.model.Excepciones.ExcepcionNoPerteneceALaOrganizacion;
import domain.model.GeoReferencia.Punto;
import domain.model.GeoReferencia.Tramo;
import domain.model.GeoReferencia.Trayecto;
import domain.model.GeoReferencia.ViajeCompartido;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "persona")
@Getter
@Setter
public class Persona extends EntidadPersistente{
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Organizacion> organizacionesContacto;

    /*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //TODO MODIFICADO
    //@JoinColumn(name = "tramo_id", referencedColumnName = "id")
    private List<Tramo> tramos;*/

    @ManyToMany(cascade = CascadeType.ALL) //TODO MODIFICADO
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tramo> tramos;

    @Enumerated(value = EnumType.STRING)
    private TipoDocumento tipoDocumento;
    @Column(name = "documento")
    private String documento;

    //OnetoOne
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    //@OneToOne(cascade = CascadeType.ALL)
    private Punto domicilio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persona")
    private List<Trayecto> trayectos;

    /*@ManyToMany(fetch = FetchType.EAGER, mappedBy = "miembros") //TODO MODIFICADO
    private List<Sector> sectores;*/

    @ManyToMany(mappedBy = "miembros", cascade = CascadeType.ALL)  //TODO MODIFICADO
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sector> sectores;

    /*@ManyToMany(fetch = FetchType.EAGER) //TODO MODIFICADO
    private List<ViajeCompartido> solicitudesViajesCompartidos;*/

    @ManyToMany(cascade = CascadeType.ALL) //TODO MODIFICADO
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ViajeCompartido> solicitudesViajesCompartidos;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "mail")
    private String mail;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<HuellaDeCarbono> huellas;

    public Persona(String nombre, String apellido, TipoDocumento tipoDocumento, String documento, Punto domicilio){
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.domicilio = domicilio;
        this.trayectos = new ArrayList<>();
        this.sectores = new ArrayList<>();
        this.solicitudesViajesCompartidos = new ArrayList<>();
        this.huellas = new ArrayList<>();
        this.tramos = new ArrayList<>();
    }

    public Persona() {

    }

    public Solicitud solicitarVinculacion(Organizacion unaOrganizacion, Sector unSector){
        Solicitud unaSolicitud = new Solicitud(this, unSector, unaOrganizacion);
        unaOrganizacion.agregarSolicitud(unaSolicitud);
        return unaSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Punto getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Punto domicilio) {
        this.domicilio = domicilio;
    }

    public void setTrabajos(Sector sector){
        this.sectores.add(sector);
    }

    public List<Sector> getTrabajos() {
        return sectores;
    }

    public List<Trayecto> getTrayectos() {
        return trayectos;
    }

    public void agregarTrayecto(Trayecto ... trayectos){
        Collections.addAll(this.trayectos, trayectos);
        for (Trayecto t: trayectos) {
            t.setPersona(this);
        }
    }

    public void agregarTamo(Tramo ... tramos){
        Collections.addAll(this.tramos, tramos);
    }

    public Organizacion getOrganizacion(Sector unSector){
        return unSector.getOrganizacion();
    }

    public Double distanciaTotalDeTrayectos(){
        return this.getTrayectos().stream().mapToDouble(s->s.calcularDistancia()).sum();
    }

    public List<Organizacion> getOrganizaciones(){
        List<Organizacion> organizaciones = new ArrayList<>();
        this.sectores.forEach(trabajo -> organizaciones.add(trabajo.getOrganizacion()));
        return organizaciones;
    }

    public void agregarSolicitudViajeCompartido(ViajeCompartido nuevoViaje){
        this.solicitudesViajesCompartidos.add(nuevoViaje);
    }

    public void confirmarViaje() {
        for (ViajeCompartido solicitud : solicitudesViajesCompartidos) {
            if (this.getOrganizaciones().stream().anyMatch(organizacion -> organizacion.equals(solicitud.getOrganizacion()))) {
                solicitud.getTramoACompartir().agregarPasajero(this);
                Trayecto trayecto = new Trayecto();
                this.agregarTrayecto(trayecto);
                trayecto.agregarTramo(solicitud.getTramoACompartir());
                solicitud.aprobarViaje();
            } else {
                throw new ExcepcionNoPerteneceALaOrganizacion();
            }
        }
        solicitudesViajesCompartidos.clear();
    }

    public void confirmarElViaje(ViajeCompartido unViaje) {
        unViaje.getTramoACompartir().agregarPasajero(this);
        Trayecto trayecto = new Trayecto();
        this.agregarTrayecto(trayecto);
        trayecto.agregarTramo(unViaje.getTramoACompartir());
        unViaje.aprobarViaje();

        solicitudesViajesCompartidos.remove(unViaje);
    }

    public void confirmarViajes() {
        for (ViajeCompartido solicitud : solicitudesViajesCompartidos) {
            solicitud.getTramoACompartir().agregarPasajero(this);
            Trayecto trayecto = new Trayecto();
            this.agregarTrayecto(trayecto);
            trayecto.agregarTramo(solicitud.getTramoACompartir());
            solicitud.aprobarViaje();
        }
        solicitudesViajesCompartidos.clear();
    }

    public List<ViajeCompartido> getSolicitudesViajesCompartidos() {
        return solicitudesViajesCompartidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public List<HuellaDeCarbono> getHuellas() {
        return huellas;
    }
    
    public void agregarHuella(HuellaDeCarbono ... huellas){
        Collections.addAll(this.huellas, huellas);
    }
}

