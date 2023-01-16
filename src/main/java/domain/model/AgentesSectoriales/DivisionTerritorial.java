package domain.model.AgentesSectoriales;

import domain.model.Entidades.EntidadPersistente;
import domain.model.Entidades.Organizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "division_territorial")
@DiscriminatorColumn(name = "division")
@Getter
@Setter
public abstract class DivisionTerritorial extends EntidadPersistente {

     @Column(name = "nombre")
     private String nombre;

     @ManyToOne(cascade = CascadeType.ALL)
     @JoinColumn(name = "sector_territorial_id", referencedColumnName = "id")
     private SectorTerritorial sectorTerritorial; //TODO: HABRIA QUE BORRAR ESTO

     public abstract List<Organizacion> getOrganizaciones();

     public String getNombre() {
          return nombre;
     }

     public void setNombre(String nombre) {
          this.nombre = nombre;
     }

     public SectorTerritorial getSectorTerritorial() {
          return sectorTerritorial;
     }

     public void setSectorTerritorial(SectorTerritorial sectorTerritorial) {
          this.sectorTerritorial = sectorTerritorial;
     }
}
