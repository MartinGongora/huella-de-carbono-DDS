package domain.repositories;

import db.EntityManagerHelper;
import domain.model.GeoReferencia.Punto;
import domain.model.GeoReferencia.Ubicacion;
import domain.model.Medios.TransportePublico.Estacion;
import domain.model.usuarios.Usuario;

import java.util.List;

public class RepositorioDirecciones {

    public List<Punto> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Punto.class.getName())
                .getResultList();
    }

    public Punto buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Punto.class, id);
    }

    public void guardar(Punto punto) {
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper
                .getEntityManager()
                .persist(punto);
        EntityManagerHelper.commit();
    }

    public void agregar(Punto punto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(punto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }


    public void modificar(Punto punto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(punto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }


    public void eliminar(Punto punto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(punto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public List<Punto> buscarEstaciones(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Punto.class.getName()+ " where linea_de_transporte = '" + id + "'")
                .getResultList();
    }

    public Estacion buscarEstacion(Integer id, String nombre) {
        return (Estacion) EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + Estacion.class.getName()+ " where linea_de_transporte = '" + id + "' and nombre ='" + nombre + "'")
                .getSingleResult();
    }

}
