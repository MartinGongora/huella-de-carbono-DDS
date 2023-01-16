package domain.model.Entidades.HuellaCarbono;

import domain.model.AgentesSectoriales.SectorTerritorial;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ReportePorSector {
  HashMap<SectorTerritorial, Double> hcPorSector = new HashMap<SectorTerritorial, Double>();
  
}
