package domain.model.Entidades.HuellaCarbono;

import domain.model.AgentesSectoriales.SectorTerritorial;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ReporteEvolucionSectorTerritorial {
  HashMap<Integer, Double> hcPorAnio = new HashMap<Integer, Double>();

  SectorTerritorial sector;

  public void setSectorTerritorial(SectorTerritorial sector) {
    this.sector = sector;
  }
}
