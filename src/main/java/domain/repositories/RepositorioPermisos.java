package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Medios.MedioDeTransporte;
import domain.model.usuarios.Permiso;

import java.util.List;

public class RepositorioPermisos {

    public List<Permiso> permisosRol(String tipo){
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Permiso.class.getName()+ " where rol_id = '" + tipo + "'")
                .getResultList();
    }

}
