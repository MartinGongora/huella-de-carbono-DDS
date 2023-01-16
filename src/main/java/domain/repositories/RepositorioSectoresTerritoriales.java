package domain.repositories;

import db.EntityManagerHelper;
import domain.model.AgentesSectoriales.AgenteSectorial;
import domain.model.AgentesSectoriales.SectorTerritorial;
import domain.model.usuarios.Usuario;

import java.util.List;

public class RepositorioSectoresTerritoriales {

    public List<SectorTerritorial> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + SectorTerritorial.class.getName())
                .getResultList();
    }

    public SectorTerritorial buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(SectorTerritorial.class, id);
    }

    public void modificar(SectorTerritorial sector) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(sector);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}
