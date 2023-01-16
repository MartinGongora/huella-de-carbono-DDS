package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Entidades.Solicitud;

import java.util.List;

public class RepositorioSolicitud {

    public List<Solicitud> buscarSolicitudDe(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Solicitud.class.getName() + " where persona_id = '" + id + "'")
                .getResultList();
    }
    public List<Solicitud> buscarSolicitudPendienteDe(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Solicitud.class.getName() + " where persona_id = '" + id + "' and estado='PENDIENTE'")
                .getResultList();
    }
    public Solicitud buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Solicitud.class, id);
    }

    public void agregar(Solicitud solicitud) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(solicitud);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void eliminar(Solicitud solicitud) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(solicitud);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}
