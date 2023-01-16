package domain.repositories;

import db.EntityManagerHelper;
import domain.model.GeoReferencia.Trayecto;
import domain.model.usuarios.Usuario;

public class RepositorioTrayectos {

    public Trayecto buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Trayecto.class, id);
    }

    public void guardar(Trayecto trayecto) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(trayecto);
        EntityManagerHelper.commit();
    }

    public void modificar(Trayecto trayecto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(trayecto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}
