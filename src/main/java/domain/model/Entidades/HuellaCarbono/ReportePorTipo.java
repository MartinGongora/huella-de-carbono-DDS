package domain.model.Entidades.HuellaCarbono;

import domain.model.Entidades.ClasificacionOrganizacion;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ReportePorTipo {
  HashMap<ClasificacionOrganizacion, Double> hcPorTipoOrganizacion = new HashMap<ClasificacionOrganizacion, Double>();
}
