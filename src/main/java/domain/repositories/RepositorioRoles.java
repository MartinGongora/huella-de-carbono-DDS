package domain.repositories;

import db.EntityManagerHelper;
import domain.model.usuarios.Rol;

public class RepositorioRoles {

    public Rol buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Rol.class, id);
    }

    public void agregar(Rol rol){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
            .getEntityManager()
            .persist(rol);
        EntityManagerHelper.commit();
    }

    public void agregarRoles(Rol ... roles){
        EntityManagerHelper.beginTransaction();
        for (Rol r: roles) {
            EntityManagerHelper
                    .getEntityManager()
                    .persist(r);
        }
        EntityManagerHelper.commit();
    }

}
