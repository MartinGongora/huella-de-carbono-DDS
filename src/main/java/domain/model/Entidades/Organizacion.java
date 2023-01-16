package domain.model.Entidades;

import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.Entidades.HuellaCarbono.HuellaDeCarbono;
import domain.model.Entidades.Mediciones.Medicion;
import domain.model.GeoReferencia.Punto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "organizacion")
@Getter
@Setter
public class Organizacion extends EntidadPersistente {
    @Column(name = "razonSocial")
    private String razonSocial;
    @Enumerated(value = EnumType.STRING)
    private TipoOrganizacion tipo;

    @OneToOne(cascade = CascadeType.ALL)
    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "ubicacion_id")
    private Punto ubicacionGeografica;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizacion", cascade = CascadeType.ALL)
    private List<Sector> sectores;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clasificacion_id")
    private ClasificacionOrganizacion clasificacionOrg;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizacion")
    private List<Solicitud> solicitudes;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizacion", cascade = CascadeType.ALL)
    private List<Medicion> mediciones;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Persona> contactos;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organizacion", cascade = CascadeType.ALL)
    private List<HuellaDeCarbono> huellas;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "division_Territorial_id",referencedColumnName = "id")
    private DivisionTerritorial divisionTerritorial;

    public Organizacion(String razonSocial) {
        this.razonSocial = razonSocial;
        this.sectores = new ArrayList<>();
        this.solicitudes = new ArrayList<>();
        this.mediciones = new ArrayList<>();
        this.contactos= new ArrayList<>();
        this.huellas = new ArrayList<>();
    }

    public Organizacion() {

    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public TipoOrganizacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoOrganizacion tipo) {
        this.tipo = tipo;
    }

    public Punto getUbicacionGeografica() {
        return ubicacionGeografica;
    }

    public void setUbicacionGeografica(Punto ubicacionGeografica) {
        this.ubicacionGeografica = ubicacionGeografica;
    }

    public ClasificacionOrganizacion getClasificacionOrg() {
        return clasificacionOrg;
    }

    public void setClasificacionOrg(ClasificacionOrganizacion clasificacionOrg) { this.clasificacionOrg = clasificacionOrg; }

    public void agregarSector(Sector ... sectores){
        Collections.addAll(this.sectores, sectores);
    }

    public void agregarSolicitud(Solicitud ... solicitudes){
        Collections.addAll(this.solicitudes, solicitudes);
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public List<Sector> getSectores() {
        return sectores;
    }

    public void confirmarEmpleados(){
        for(Solicitud solicitud: solicitudes){
            solicitud.getSector().agregarMiembro(solicitud.getPersona());
            solicitud.getPersona().setTrabajos(solicitud.getSector());
            solicitud.aprobarSolicitud();
        }
        solicitudes.clear();
    }

    public void aceptarVinculacion(Solicitud solicitud){
        solicitud.getSector().agregarMiembro(solicitud.getPersona());
        solicitud.getPersona().setTrabajos(solicitud.getSector());
        solicitudes.removeIf(solicitud1 -> solicitud1.equals(solicitud));
    }

    public void rechazarVinculacion(Solicitud solicitud){
        solicitudes.removeIf(solicitud1 -> solicitud1.equals(solicitud));
    }

    public List<Persona> misEmpleados(){
        List<Sector> sectores = this.sectores;
        List<Persona> personas = new ArrayList<>();
        for(Sector s: sectores){
            personas.addAll(s.getMiembros());
        }
        return personas;
    }
    public void rechazarEmpleados(){
        solicitudes.clear();
    }

    public void setMediciones(Medicion ... mediciones) {
        Collections.addAll(this.mediciones, mediciones);
    }

    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public List<Persona> getContactos() {
        return contactos;
    }

    public void setContactos(Persona ... persona) {
        Collections.addAll(this.contactos, persona);
    }
    
    public List<HuellaDeCarbono> getHuellas() {
        return huellas;
    }
    
    public void agregarHuella(HuellaDeCarbono ... huellas){
        Collections.addAll(this.huellas, huellas);
        for(HuellaDeCarbono huella: huellas){
            huella.setOrganizacion(this);
        }
    }

    public void eliminarSector(Sector sector){
        sectores.removeIf(sector1-> sector1.equals(sector));
    }
}
