package domain.model.Entidades.HuellaCarbono;

import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Sector;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ReporteOrganizacion {
  Organizacion organizacion;
  Double huellaDeMediciones; //no se usa, ver si es necesario
  HashMap<Sector, Double> hcPorSector = new HashMap<Sector, Double>();
}
