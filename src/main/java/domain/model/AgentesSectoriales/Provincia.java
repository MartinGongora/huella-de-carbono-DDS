package domain.model.AgentesSectoriales;

import domain.model.Entidades.Organizacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Entity
@DiscriminatorValue("Provincia")
@Getter
@Setter
@NoArgsConstructor
public class Provincia extends DivisionTerritorial {

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "provincia", cascade = CascadeType.ALL)
    private List<Municipio> municipios;

    public Provincia(String nombre, Integer id){
        super.setNombre(nombre);
        super.setId(id);
        this.municipios = new ArrayList<>();
    }

    public List<Organizacion> getOrganizaciones(){
        return  this.municipios.stream().map(m -> m.getOrganizaciones()).flatMap(municipio -> municipio.stream()).collect(Collectors.toList());
    }

    public void setMunicipios(Municipio ... municipios){
        Collections.addAll(this.municipios,municipios);
        for(Municipio m: municipios){
            m.setProvincia(this);
        }
    }

}
