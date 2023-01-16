package domain.model.Entidades.Mediciones;

import domain.model.Entidades.EntidadPersistente;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tipo_de_Consumo")
@DiscriminatorColumn(name = "discriminador")

@Setter
public abstract class TipoDeConsumo extends EntidadPersistente {
    @Enumerated(value = EnumType.STRING)
    private Unidad unidad;

    @Enumerated(value = EnumType.STRING)
    private Tipo tipo;

    public Tipo getTipo() {
        return tipo;
    }

    public Unidad getUnidad(){
            return unidad;
    };

    public abstract double getValor();

}
