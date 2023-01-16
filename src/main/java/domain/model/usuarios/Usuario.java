package domain.model.usuarios;

import domain.model.AgentesSectoriales.AgenteSectorial;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;
import lombok.Getter;
import lombok.Setter;
import domain.model.Entidades.EntidadPersistente;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;

import static com.google.common.hash.Hashing.sha256;

@Entity
@Table(name = "usuario")
@Setter
@Getter
public class Usuario extends EntidadPersistente {

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String nombreDeUsuario;

    @Column
    private String contrasenia;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    @OneToOne(cascade = CascadeType.ALL)
    private Persona persona;

    @OneToOne(cascade = CascadeType.ALL)
    private Organizacion organizacion;

    @OneToOne(cascade = CascadeType.ALL)
    private AgenteSectorial agenteSectorial;

    @Override
    public String toString(){
        return "Persona{" + "nombre='" + nombreDeUsuario + '\'' + '}';
    }

    private String encryptPassword(String password) {
        return sha256()
            .hashString(password, StandardCharsets.UTF_8)
            .toString();
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = this.encryptPassword(contrasenia);
    }
}
