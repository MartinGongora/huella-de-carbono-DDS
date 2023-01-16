package domain.model.Entidades.HuellaCarbono;

import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.AgentesSectoriales.SectorTerritorial;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class ReporteSectorPorDivision {
  HashMap<DivisionTerritorial, Double>hcPorDivision = new HashMap<DivisionTerritorial, Double>();
}
