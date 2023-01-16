package domain.model.GeoReferencia;

import domain.model.Entidades.EntidadPersistente;
import domain.model.Entidades.EstadoSolicitud;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "viajeCompartido")
@Getter
@Setter
public class ViajeCompartido extends EntidadPersistente {
  @ManyToOne
  @JoinColumn(name = "tramo_id")
  private Tramo tramoACompartir;
  @ManyToOne
  @JoinColumn(name = "organizacion_id")
  private Organizacion organizacion;
  @ManyToOne
  @JoinColumn(name = "persona_id")
  private Persona personaQueComparte;

  @ManyToOne
  @JoinColumn(name = "persona_compartir_id")
  private Persona personaACompartir;

  @Enumerated(value = EnumType.STRING)
  private EstadoSolicitud estado;

  public ViajeCompartido(Tramo tramoACompartir, Organizacion organizacion, Persona personaQueComparte, Persona personaACompartir) {
    this.tramoACompartir = tramoACompartir;
    this.organizacion = organizacion;
    this.personaQueComparte = personaQueComparte;
    this.personaACompartir = personaACompartir;
    this.estado = EstadoSolicitud.PENDIENTE;
  }

  public ViajeCompartido() {

  }

  public Organizacion getOrganizacion() {
    return organizacion;
  }

  public Persona getPersonaQueComparte() {
    return personaQueComparte;
  }

  public Tramo getTramoACompartir() {
    return tramoACompartir;
  }

  public void aprobarViaje(){
    this.estado = EstadoSolicitud.APROBADA;
  }
}
