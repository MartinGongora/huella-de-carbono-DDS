import domain.model.Entidades.Persona;
import domain.model.Entidades.TipoDocumento;
import domain.model.usuarios.Usuario;

import db.EntityManagerHelper;

public class MainTest {
    public static void main(String[] args){
        Usuario usuario = EntityManagerHelper
                .getEntityManager()
                .find(Usuario.class, 1);

        Usuario usuario1 = (Usuario) EntityManagerHelper
                .createQuery("from " + Usuario.class.getName() + " where nombreDeUsuario = 'palonso'")
                        .getSingleResult();

        System.out.println(usuario);
        System.out.println(usuario1);
        EntityManagerHelper.closeEntityManager();
    }
}
