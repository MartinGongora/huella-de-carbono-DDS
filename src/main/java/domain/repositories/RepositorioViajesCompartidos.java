package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Entidades.Persona;
import domain.model.Entidades.Solicitud;
import domain.model.GeoReferencia.ViajeCompartido;
import domain.model.usuarios.Usuario;

import java.util.List;

public class RepositorioViajesCompartidos {

    public void guardar(ViajeCompartido viajeCompartido) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(viajeCompartido);
        EntityManagerHelper.commit();
    }

    public List<ViajeCompartido> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Persona.class.getName())
                .getResultList();
    }

    public List<ViajeCompartido> buscarViajeCon(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + ViajeCompartido.class.getName() + " where persona_id = '" + id + "'")
                .getResultList();
    }

    public List<ViajeCompartido> buscarViajeDe(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + ViajeCompartido.class.getName() + " where persona_compartir_id = '" + id + "'")
                .getResultList();
    }

    public List<ViajeCompartido> buscarViajePendienteDe(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + ViajeCompartido.class.getName() + " where persona_compartir_id = '" + id + "' and estado = 'PENDIENTE'")
                .getResultList();
    }

    public void modificar(ViajeCompartido viaje) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(viaje);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}
