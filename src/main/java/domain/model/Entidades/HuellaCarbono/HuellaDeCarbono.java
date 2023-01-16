package domain.model.Entidades.HuellaCarbono;

import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.Entidades.EntidadPersistente;
import domain.model.Entidades.Mediciones.Periodicidad;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;
import domain.model.Entidades.Sector;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "huellaDeCarbono")
@Getter
@Setter
public class HuellaDeCarbono extends EntidadPersistente {

    @Column(name = "mes")
    private int mes;
    @Column(name = "anio")
    private int anio;
    @Enumerated(value = EnumType.STRING)
    private Periodicidad periodo;
    @Column(name = "valor")
    private Double valor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sector_id",referencedColumnName = "id")
    private Sector sector;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizacion",referencedColumnName = "id")
    private Organizacion organizacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona",referencedColumnName = "id")
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "division_territorial")
    private DivisionTerritorial divisionesTerritoriales;


    public HuellaDeCarbono(int mes, int anio, Periodicidad periodo, Double valor) {
        this.mes = mes;
        this.anio = anio;
        this.periodo = periodo;
        this.valor = valor;
    }

    public HuellaDeCarbono() {

    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Double getValor() {
        return valor;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Periodicidad getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodicidad periodo) {
        this.periodo = periodo;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setDivisionesTerritoriales(DivisionTerritorial divisionesTerritoriales) {
        this.divisionesTerritoriales = divisionesTerritoriales;
    }
}
