package domain.repositories;

import db.EntityManagerHelper;
import domain.model.usuarios.Usuario;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.google.common.hash.Hashing.sha256;

public class RepositorioUsuarios {

    public List<Usuario> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Usuario.class.getName())
                .getResultList();
    }

    public Usuario buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Usuario.class, id);
    }

    public Usuario buscarNombre(String nombre) {
        String query = "FROM " + Usuario.class.getName() + " WHERE nombreDeUsuario = '" + nombre + "'";
        Usuario usuario = (Usuario) EntityManagerHelper.getEntityManager()
                .createQuery(query)
                .getSingleResult();
        return usuario;
    }

    public Usuario inicioSesion(String nombreUsuario, String pass,Integer idRol) {
        System.out.println(pass);
        String query = "from "
                + Usuario.class.getName()
                +" WHERE nombreDeUsuario='"
                + nombreUsuario
                +"' AND contrasenia='"
                + this.encryptPassword(pass)
                +"' AND rol_id=" + idRol;
        Usuario usuario = (Usuario) EntityManagerHelper
                .getEntityManager()
                .createQuery(query)
                .getSingleResult();
        return usuario;
    }

    public Usuario inicioSesionMiembro(String nombreUsuario, String pass) {
        String query = "from "
                + Usuario.class.getName()
                +" WHERE nombreDeUsuario='"
                + nombreUsuario
                +"' AND contrasenia='"
                + this.encryptPassword(pass)
                +"'";
        Usuario usuario = (Usuario) EntityManagerHelper
                .getEntityManager()
                .createQuery(query)
                .getSingleResult();
        return usuario;
    }

    public void guardar(Usuario usuario) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(usuario);
        EntityManagerHelper.commit();
    }

    public void agregar(Usuario usuario) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(usuario);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }


    public void modificar(Usuario usuario) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(usuario);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }


    public void eliminar(Usuario usuario) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(usuario);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public Usuario buscarPorOrganizacion(Integer idOrganizacion){
        String query = "FROM " + Usuario.class.getName() + " WHERE organizacion_id = '" + idOrganizacion + "'";
        Usuario usuario = (Usuario) EntityManagerHelper.getEntityManager()
            .createQuery(query)
            .getSingleResult();
        return usuario;
    }

    public List<Usuario> buscarTodasLasOrganizaciones() {
        return EntityManagerHelper
            .getEntityManager()
            .createQuery("from " + Usuario.class.getName() + " WHERE organizacion_id IS NOT NULL and rol_id = 3 ")
            .getResultList();
    }

    public List<Usuario> buscarTodosLosMiembros() {
        return EntityManagerHelper
            .getEntityManager()
            .createQuery("from " + Usuario.class.getName() + " WHERE persona_id is not null and rol_id = 2")
            .getResultList();
    }

    private String encryptPassword(String password) {
        return sha256()
            .hashString(password, StandardCharsets.UTF_8)
            .toString();
    }
}
