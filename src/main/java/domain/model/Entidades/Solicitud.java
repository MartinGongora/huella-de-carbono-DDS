package domain.model.Entidades;

import db.Converters.LocalDateAttributeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "solicitud")
@Getter
@Setter
@NoArgsConstructor
public class Solicitud extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    /*
    @Column(columnDefinition = "DATE")
    private LocalDate fecha;
     */
    @Column(name = "fecha")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fecha;
    @Enumerated(value = EnumType.STRING)
    private EstadoSolicitud estado;
    public Solicitud(Persona unaPersona, Sector unSector, Organizacion unaOrganizacion){
        this.organizacion = unaOrganizacion;
        this.persona = unaPersona;
        this.sector = unSector;
        this.fecha = LocalDate.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public Persona getPersona() {
        return persona;
    }

    public Sector getSector() {
        return sector;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void aprobarSolicitud(){
        this.estado = EstadoSolicitud.APROBADA;
    }
}
