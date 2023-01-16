package domain.model.usuarios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "rol")
@Setter
@Getter
public class Rol {


    @Column
    private String nombre;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "rol_permiso", joinColumns = @JoinColumn(name = "rol_id"))
    @Enumerated(EnumType.STRING)
    private Set<Permiso> permisos = new HashSet<>();
    @Id
    private Integer id;

    public Rol() {

    }

    public void agregarPermiso(Permiso permiso) {
        this.permisos.add(permiso);
    }

    public Boolean tenesPermiso(Permiso permiso) {
        return this.permisos.contains(permiso);
    }

    public Boolean tenesTodosLosPermisos(Permiso ... permisos) {
        return Arrays.stream(permisos).allMatch(p -> this.permisos.contains(p));
    }

    public Boolean tenesPermiso(int rolId, List<Permiso> permisos) {
        Set<Permiso> permisosRol = new HashSet<>();
        if (rolId == 1){
            permisosRol.add(Permiso.EDITAR_ORGANIZACION);
            permisosRol.add(Permiso.ELIMIAR_ORGANIZACION);
            permisosRol.add(Permiso.CREAR_ORGANIZACION);
            permisosRol.add(Permiso.VER_ORGANIZACION);
            permisosRol.add(Permiso.EDITAR_MIEMBRO);
            permisosRol.add(Permiso.VER_MIEMBRO);
            permisosRol.add(Permiso.ELIMIAR_MIEMBRO);
            permisosRol.add(Permiso.CREAR_MIEMBRO);
            permisosRol.add(Permiso.VER_AGENTE);
            permisosRol.add(Permiso.EDITAR_AGENTE);
        } else if (rolId == 2){
            permisosRol.add(Permiso.EDITAR_MIEMBRO);
            permisosRol.add(Permiso.VER_MIEMBRO);
            permisosRol.add(Permiso.CREAR_MIEMBRO);
        } else if (rolId == 3){
            permisosRol.add(Permiso.EDITAR_ORGANIZACION);
            permisosRol.add(Permiso.CREAR_ORGANIZACION);
            permisosRol.add(Permiso.VER_ORGANIZACION);
        } else {
            permisosRol.add(Permiso.VER_AGENTE);
            permisosRol.add(Permiso.EDITAR_AGENTE);
        }
        return permisosRol.stream().allMatch(p -> this.permisos.contains(p));
        //return Arrays.stream(permisosRol).allMatch(p -> this.permisos.contains(p));
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
