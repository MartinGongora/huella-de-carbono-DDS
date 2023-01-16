package domain.model.AgentesSectoriales;

import domain.model.Entidades.Organizacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Entity
@DiscriminatorValue("Municipio")
@Getter
@Setter
@NoArgsConstructor
public class Municipio extends DivisionTerritorial{

    @Column
    private int localidadId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "divisionTerritorial", cascade = CascadeType.ALL)
    private List<Organizacion> organizaciones;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provincia_id")
    private Provincia provincia; 

    public Municipio(String nombre, int localidadId){
        super.setNombre(nombre);
        this.localidadId = localidadId;
        this.organizaciones = new ArrayList<>();
    }

    public List<Organizacion> getOrganizaciones() {
        return organizaciones;
    }

    public void setOrganizaciones(Organizacion... organizaciones) {
        Collections.addAll(this.organizaciones,organizaciones);
    }
}
