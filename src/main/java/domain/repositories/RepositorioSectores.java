package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Entidades.Sector;

public class RepositorioSectores {
  public void eliminar(Sector sector) {
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    EntityManagerHelper.getEntityManager().remove(sector);
    EntityManagerHelper.getEntityManager().getTransaction().commit();
  }
}
