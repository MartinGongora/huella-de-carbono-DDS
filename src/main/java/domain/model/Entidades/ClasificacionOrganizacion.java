package domain.model.Entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clasificacionOrganizacion")
public class ClasificacionOrganizacion extends EntidadPersistente{
    @Column(name = "nombre")
    private String nombre;

    public ClasificacionOrganizacion(String nombre){
        this.nombre = nombre;
    }

    public ClasificacionOrganizacion() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
