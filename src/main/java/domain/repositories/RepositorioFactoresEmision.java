package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Entidades.Mediciones.FactorEmision;
import domain.model.Entidades.Mediciones.FactorTraslado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioFactoresEmision {

    public List<FactorTraslado> buscarTodosTraslado() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + FactorTraslado.class.getName())
                .getResultList();
    }

    public List<FactorEmision> buscarTodosEmision() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + FactorEmision.class.getName())
                .getResultList();
    }

    public void guardar(FactorEmision factorEmision) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(factorEmision);
        EntityManagerHelper.commit();
    }

    public void agregar(FactorEmision factorEmision) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(factorEmision);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void agregarFactoresEmision(FactorEmision ... factoresEmision) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        for(FactorEmision f: factoresEmision) {
            EntityManagerHelper.getEntityManager().persist(f);
        }
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void modificarEmision(FactorEmision factorEmision) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(factorEmision);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void modificarTraslado(FactorTraslado factorTraslado) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(factorTraslado);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void eliminar(FactorEmision factorEmision) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(factorEmision);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void agregarTraslado(FactorTraslado factorTraslado) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(factorTraslado);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void agregarFactoresTraslado(FactorTraslado ... factoresTraslado) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        for(FactorTraslado f: factoresTraslado) {
            EntityManagerHelper.getEntityManager().persist(f);
        }
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void agregarTodosEmision(FactorEmision... factorEmisions){
        List<FactorEmision> factores = new ArrayList<>();
        Collections.addAll(factores,factorEmisions);
        for(FactorEmision f: factores){
            this.agregar(f);
        }
    }

    public void agregarTodosTraslado(FactorTraslado... factorTraslados){
        List<FactorTraslado> factores = new ArrayList<>();
        Collections.addAll(factores,factorTraslados);
        for(FactorTraslado f: factores){
            this.agregarTraslado(f);
        }
    }
}
