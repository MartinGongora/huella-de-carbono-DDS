package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Entidades.Organizacion;
import domain.model.usuarios.Usuario;

import java.util.List;

public class RepositorioOrganizaciones {
  public List<Organizacion> buscarTodos() {
    return EntityManagerHelper
        .getEntityManager()
        .createQuery("from " + Organizacion.class.getName())
        .getResultList();
  }

  public Organizacion buscar(Integer id) {
    return EntityManagerHelper
        .getEntityManager()
        .find(Organizacion.class, id);
  }

  public void guardar(Organizacion organizacion) {
    EntityManagerHelper.beginTransaction();
    EntityManagerHelper
        .getEntityManager()
        .persist(organizacion);
    EntityManagerHelper.commit();
  }

  public Organizacion buscarNombre(String nombre) {
    Organizacion organizacion = (Organizacion) EntityManagerHelper
        .createQuery("from " + Organizacion.class.getName() + " where razonSocial = '" + nombre + "'")
        .getSingleResult();
    return organizacion;
  }
  public List<Organizacion> buscarPorDivision(int id_division) {
    List<Organizacion> organizacion = EntityManagerHelper
            .createQuery("from " + Organizacion.class.getName() + " where division_Territorial_id = '" + id_division + "'")
            .getResultList();
    return organizacion;
  }
  public void modificar(Organizacion organizacion) {
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    EntityManagerHelper.getEntityManager().merge(organizacion);
    EntityManagerHelper.getEntityManager().getTransaction().commit();
  }




}
