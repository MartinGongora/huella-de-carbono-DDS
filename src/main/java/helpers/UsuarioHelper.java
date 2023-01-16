package helpers;

import db.EntityManagerHelper;
import domain.model.usuarios.Usuario;
import domain.repositories.RepositorioUsuarios;
import spark.Request;

public class UsuarioHelper {

    public static Usuario usuarioLogueado(Request request) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Usuario.class, request.session().attribute("id"));
    }

}
