package domain.model.Entidades.HuellaCarbono;

import domain.model.Entidades.Organizacion;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class ReporteEvolucionOrganizacion {
  Organizacion organizacion;
  HashMap<Integer, Double> hcPorAnio = new HashMap<Integer, Double>();
}
