package domain.model.Entidades;

import domain.model.Entidades.HuellaCarbono.HuellaDeCarbono;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "sector")
@Getter
@Setter
@NoArgsConstructor
public class Sector extends EntidadPersistente{

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "sectores_id")
    private List<Persona> miembros;

    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sector")
    private List<HuellaDeCarbono> huellas;

    public Sector(String nombre, Organizacion organizacion){
        this.nombre = nombre;
        this.organizacion = organizacion;
        this.miembros = new ArrayList<>();
        this.huellas = new ArrayList<>();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarMiembro(Persona ... miembros){
        Collections.addAll(this.miembros, miembros);
    }

    public List<Persona> getMiembros() {
        return miembros;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public List<HuellaDeCarbono> getHuellas() {
        return huellas;
    }
    public void agregarHuella(HuellaDeCarbono ... huellas){
        Collections.addAll(this.huellas, huellas);
    }

}
