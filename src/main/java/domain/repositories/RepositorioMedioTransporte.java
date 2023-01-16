package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Medios.MedioDeTransporte;

import java.util.List;

public class RepositorioMedioTransporte {

    public List<MedioDeTransporte> buscarMedios(String tipo) {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + MedioDeTransporte.class.getName()+ " where tipoTransportePublico = '" + tipo + "'")
                .getResultList();
    }

    public MedioDeTransporte buscarLinea(String tipo, String linea) {
        return (MedioDeTransporte) EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + MedioDeTransporte.class.getName() + " where tipoTransportePublico = '" + tipo + "' and linea='" + linea + "'")
                .getSingleResult();
    }

    public void agregar(MedioDeTransporte transporte) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(transporte);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

}
