package domain.model.Entidades.Mediciones;

import domain.model.Entidades.EntidadPersistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "factor_de_Emision")
@Getter
@Setter
@NoArgsConstructor
public class FactorEmision extends EntidadPersistente {
  @Enumerated(value = EnumType.STRING)
  private Tipo tipo;
  @Column(name = "valor_factorEmision")
  private Double valorFE;

  public FactorEmision(Tipo tipo, Double valorFE) {
    this.tipo = tipo;
    this.valorFE = valorFE;
  }

  public Tipo getTipo() {
    return tipo;
  }

  public Double getValorFE() {
    return valorFE;
  }

  public void setValorFE(Double valorFE) {
    this.valorFE = valorFE;
  }

  public boolean esFactor(Tipo unTipo){
    return unTipo.equals(this.tipo);
  }
}
