package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Entidades.Persona;

import java.util.List;

public class RepositorioPersonas {

    public List<Persona> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Persona.class.getName())
                .getResultList();
    }

    public Persona buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Persona.class, id);
    }

    public void agregar(Persona persona) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(persona);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }


    public void modificar(Persona persona) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(persona);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }


    public void eliminar(Persona persona) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(persona);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}
