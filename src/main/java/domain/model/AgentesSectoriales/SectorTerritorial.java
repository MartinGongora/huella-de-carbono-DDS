package domain.model.AgentesSectoriales;

import domain.model.Entidades.EntidadPersistente;
import domain.model.Entidades.HuellaCarbono.HuellaDeCarbono;
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
@Table(name = "sector_territorial")
@Getter
@Setter
@NoArgsConstructor
public class SectorTerritorial extends EntidadPersistente {
    @Column(name="municipio_o_provincia")
    private String nombre;

    @OneToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "agente_sectorial_id")
    private AgenteSectorial agenteSectorial; //TODO: HABRIA QUE BORRAR ESTO

    //Son las huellas que se guardan de las organizaxiones o de los sectores para no volver a calcularlas
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL}, mappedBy = "sector")
    private List<HuellaDeCarbono> huellas;
    
    @OneToMany(mappedBy = "sectorTerritorial", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<DivisionTerritorial> divisionesTerritoriales;


    public SectorTerritorial(String nombre) {
        this.nombre = nombre;
        this.divisionesTerritoriales = new ArrayList<>();
        this.huellas = new ArrayList<>();
        //this.agenteSectorial.setSectorTerritorial(this);
    }

    public List<DivisionTerritorial> getDivisionesTerritoriales() {
        return divisionesTerritoriales;
    }
    public List<Organizacion> getOrganizaciones(){
        return  this.divisionesTerritoriales.stream().map(divisionTerritorial -> divisionTerritorial.getOrganizaciones())
                .flatMap(divisionesTerritoriales -> divisionesTerritoriales.stream()).collect(Collectors.toList());
    }
    public List<HuellaDeCarbono> getHuellas() {
        for (DivisionTerritorial d: this.divisionesTerritoriales){
            //List<Organizacion> organizaciones
            //d.getOrganizaciones()
        }
        return huellas;
    }

    public void agregarHuella(HuellaDeCarbono... huella) {
        Collections.addAll(this.huellas, huella);
    }

    public void agregarDivisionTerritorial(DivisionTerritorial ... divisionTerritorial) {
        Collections.addAll(this.divisionesTerritoriales,divisionTerritorial);

        for(DivisionTerritorial d: divisionTerritorial){
            d.setSectorTerritorial(this);
        }
    }

    public List<HuellaDeCarbono> buscarHuellaPorAnio(int anio){
        List<HuellaDeCarbono> huellasDeUnAnio =  this.huellas.stream().filter(h -> h.getAnio() == anio).collect(Collectors.toList());
        if(huellasDeUnAnio.size() != 0){
            return huellasDeUnAnio;
        }else{
            throw new RuntimeException("No se encontraron huellas de carbono de ese año");
        }
    }

    public HuellaDeCarbono buscarHuellaPorMesYAnio(int mes,int anio){
        HuellaDeCarbono huellaDeterminada =  this.buscarHuellaPorAnio(anio).stream().filter(h->h.getMes() == mes).collect(Collectors.toList()).get(0);

        if(huellaDeterminada != null){
            return huellaDeterminada;
        }else{
            throw new RuntimeException("No se encontraron huellas de carbono de ese mes y año");
        }
    }
}
