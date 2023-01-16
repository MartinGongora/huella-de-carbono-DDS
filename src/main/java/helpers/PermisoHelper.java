package helpers;

import db.EntityManagerHelper;
import domain.model.usuarios.Permiso;
import domain.model.usuarios.Rol;
import domain.model.usuarios.Usuario;
import spark.Request;

import java.util.List;

public class PermisoHelper {

    public static Boolean usuarioTienePermisos(Request request, int rolId, List<Permiso> permisos) {
        return UsuarioHelper
                .usuarioLogueado(request)
                .getRol()
                .tenesPermiso(rolId, permisos);
    }
}
