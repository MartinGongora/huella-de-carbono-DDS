package domain.repositories;

import db.EntityManagerHelper;
import domain.model.AgentesSectoriales.AgenteSectorial;
import domain.model.Entidades.Solicitud;

import java.util.List;

public class RepositoriosAgentes {

    public List<AgenteSectorial> traerAgentes() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + AgenteSectorial.class.getName())
                .getResultList();
    }

    public AgenteSectorial buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(AgenteSectorial.class, id);
    }

    public void guardar(AgenteSectorial agente) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
            .getEntityManager()
            .persist(agente);
        EntityManagerHelper.commit();
    }

    public void modificar(AgenteSectorial agente) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(agente);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }


}
