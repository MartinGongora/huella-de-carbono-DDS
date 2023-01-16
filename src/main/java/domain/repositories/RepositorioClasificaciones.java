package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Entidades.ClasificacionOrganizacion;

import java.util.List;

public class RepositorioClasificaciones {
  public List<ClasificacionOrganizacion> buscarTodos() {
    return EntityManagerHelper
        .getEntityManager()
        .createQuery("from " + ClasificacionOrganizacion.class.getName())
        .getResultList();
  }

  public ClasificacionOrganizacion buscar(Integer id) {
    return EntityManagerHelper
        .getEntityManager()
        .find(ClasificacionOrganizacion.class, id);
  }

  public void guardar(ClasificacionOrganizacion clasificacionOrganizacion) {
    EntityManagerHelper.beginTransaction();
    EntityManagerHelper
        .getEntityManager()
        .persist(clasificacionOrganizacion);
    EntityManagerHelper.commit();
  }

  public void guardarClasificaciones(ClasificacionOrganizacion ... clasificaciones) {
    EntityManagerHelper.beginTransaction();
    for (ClasificacionOrganizacion c: clasificaciones){
      EntityManagerHelper
              .getEntityManager()
              .persist(c);
    }
    EntityManagerHelper.commit();
  }


  public ClasificacionOrganizacion buscarPorNombre(String nombre) {
    ClasificacionOrganizacion clasificacionOrganizacion = (ClasificacionOrganizacion) EntityManagerHelper
        .createQuery("from " + ClasificacionOrganizacion.class.getName() + " where nombre = '" + nombre + "'")
        .getSingleResult();
    return clasificacionOrganizacion;
  }
}
