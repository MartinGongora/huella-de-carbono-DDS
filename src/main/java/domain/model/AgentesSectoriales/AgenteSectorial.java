package domain.model.AgentesSectoriales;

import domain.model.Entidades.EntidadPersistente;
import domain.model.Entidades.HuellaCarbono.HuellaDeCarbono;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "agente_sectorial")
@NoArgsConstructor
@Getter
@Setter
public class AgenteSectorial extends EntidadPersistente {
    @Column(name = "nombre")
    private String nombre;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "sector_territorial_id")
    private SectorTerritorial sectorTerritorial;

    public AgenteSectorial(String nombre, SectorTerritorial sectorTerritorial) {
        this.nombre = nombre;
        this.sectorTerritorial = sectorTerritorial;
        this.sectorTerritorial.setAgenteSectorial(this);
    }
    
    public String getNombre() { return nombre; }

    public SectorTerritorial getSectorTerritorial() {
        return sectorTerritorial;
    }

    public List<HuellaDeCarbono> buscarHuellaSegunAnio(int anio){

        return  this.sectorTerritorial.buscarHuellaPorAnio(anio);
    }

    public HuellaDeCarbono buscarHuellaPorMesYAnio(int mes,int anio){
        return this.sectorTerritorial.buscarHuellaPorMesYAnio(mes,anio);
    }
}
