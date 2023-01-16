package domain.repositories;

import db.EntityManagerHelper;
import domain.model.GeoReferencia.Tramo;
import domain.model.GeoReferencia.Trayecto;

public class RepositorioTramos {

    public Tramo buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Tramo.class, id);
    }

    public void guardar(Tramo tramo) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(tramo);
        EntityManagerHelper.commit();
    }

    public void modificar(Tramo tramo) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(tramo);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}
