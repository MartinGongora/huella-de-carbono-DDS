package domain.model.GeoReferencia;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domain.model.Entidades.EntidadPersistente;
import domain.model.Entidades.Persona;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "trayecto")
@Getter
@Setter
public class Trayecto extends EntidadPersistente {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trayecto", cascade = CascadeType.ALL)
    private List<Tramo> tramos;
    @Column(name = "totalTrayecto")
    private Double totalTrayecto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Persona persona;

    public Trayecto() {
        this.tramos = new ArrayList<>();
    }

    public void agregarTramo(Tramo... tramos){
        Collections.addAll(this.tramos, tramos);
        for (Tramo t: tramos) {
            t.setTrayecto(this);
        }
    }

    public List<Tramo> getTramos() {
        return tramos;
    }

    public Double calcularDistancia() {
        this.totalTrayecto = this.getTramos().stream().mapToDouble(t-> {
            try {
                return t.calcularDistancia();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).sum();

        return totalTrayecto;
    }

    public void calcularTramos(){
        this.totalTrayecto = this.getTramos().stream().mapToDouble(t-> t.getTotalTramo()).sum();
    }
    public Double getTotalTrayecto() {
        return totalTrayecto;
    }
}
