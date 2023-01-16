package domain.model.Entidades.Mediciones;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Directo")
public class ConsumoDirecto extends TipoDeConsumo{

    @Column(name = "valor")
    private double valor;

    public ConsumoDirecto(Tipo tipo) {
        super.setTipo(tipo);
        this.setUnidad();
    }

    public ConsumoDirecto() {

    }


    private void setUnidad() {
        switch (super.getTipo()){
            case GAS_NATURAL: super.setUnidad(Unidad.M3); break;
            case DIESEL_GASOIL:
            case KEROSENE:
            case FUEL_OIL:
            case NAFTA:
            case COMBUSTIBLE_CONSUMIDO_GASOIL:
            case COMBUSTIBLE_CONSUMIDO_GNC:
            case COMBUSTIBLE_CONSUMIDO_NAFTA: super.setUnidad(Unidad.LTS); break;
            case CARBON:
            case CARBON_DE_LENA:
            case LENA: super.setUnidad(Unidad.KG); break;
            case ELECTRICIDAD: super.setUnidad(Unidad.KWH);; break;
            default: break;
        }
    }

    @Override
    public double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    //public void setTipo(Tipo tipo) {
    //    this.tipo = tipo;
    //}
}
