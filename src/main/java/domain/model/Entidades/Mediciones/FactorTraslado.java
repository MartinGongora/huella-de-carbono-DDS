package domain.model.Entidades.Mediciones;

import domain.model.Entidades.EntidadPersistente;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoCombustible;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "factores_Emision_Traslado")
@Getter
@Setter
@NoArgsConstructor
public class FactorTraslado extends EntidadPersistente {
  @Enumerated(value = EnumType.STRING)
  private TipoCombustible tipoCombustible;

  @Column(name = "valor_FactorEmision")
  private Double valorFT;

  public FactorTraslado(TipoCombustible tipoCombustible, Double valorFT) {
    this.tipoCombustible = tipoCombustible;
    this.valorFT = valorFT;
  }

  public TipoCombustible getTipoCombustible() {
    return tipoCombustible;
  }

  public Double getValorFT() {
    return valorFT;
  }

  public boolean esFactor(TipoCombustible unTipo){
    return unTipo.equals(this.tipoCombustible);
  }

  public void setValorFT(Double valorFT) {
    this.valorFT = valorFT;
  }
}
