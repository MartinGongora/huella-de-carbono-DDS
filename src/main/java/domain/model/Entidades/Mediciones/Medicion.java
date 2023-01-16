package domain.model.Entidades.Mediciones;

import domain.model.Entidades.EntidadPersistente;
import domain.model.Entidades.Organizacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "medicion")
@Getter
@Setter
public class Medicion extends EntidadPersistente {
    @Enumerated
    private Actividad actividad;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_de_consumo_id", referencedColumnName = "id")
    private TipoDeConsumo tipoDeConsumo;

    @Enumerated
    private Periodicidad periodicidad;

    @Column(name = "mes")
    private int mes;

    @Column(name = "anio")
    private int anio;

    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    public Actividad getActividad() {
        return actividad;
    }

    public TipoDeConsumo getTipoDeConsumo() {
        return tipoDeConsumo;
    }

    public double getValor() {
        return this.tipoDeConsumo.getValor();
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public void setTipoDeConsumo(TipoDeConsumo tipoDeConsumo) {
        this.tipoDeConsumo = tipoDeConsumo;
    }

    public Periodicidad getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Periodicidad periodicidad) {
        this.periodicidad = periodicidad;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
}
