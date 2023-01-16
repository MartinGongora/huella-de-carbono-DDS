package domain.model.Entidades.HuellaCarbono;

import domain.model.AgentesSectoriales.Provincia;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class ReportePaisPorProvincia {
  HashMap<Provincia, Double> hcPorProvincia = new HashMap<Provincia, Double>();
}
