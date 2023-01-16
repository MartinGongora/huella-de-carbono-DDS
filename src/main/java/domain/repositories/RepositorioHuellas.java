package domain.repositories;

import db.EntityManagerHelper;
import domain.model.Entidades.HuellaCarbono.HuellaDeCarbono;
import domain.model.Entidades.Mediciones.FactorTraslado;
import org.omg.CORBA.INTERNAL;

import java.util.List;

public class RepositorioHuellas {


    public List<HuellaDeCarbono> buscarTodos() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + HuellaDeCarbono.class.getName())
                .getResultList();
    }


    public List<HuellaDeCarbono> buscarPorAnio(Integer anio, Integer id_division_territorial) {

        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + HuellaDeCarbono.class.getName() + " where anio = '" + anio +"' and periodo = 'ANUAL' and division_territorial = '"+id_division_territorial+ "'")
                .getResultList();
    }

    public HuellaDeCarbono buscarPorAnioYOrga(Integer anio, Integer id_orga) {

        return (HuellaDeCarbono) EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + HuellaDeCarbono.class.getName() + " where anio = '" + anio +"' and periodo = 'ANUAL' and organizacion = '"+ id_orga + "'")
                .getSingleResult();
    }

    public List<HuellaDeCarbono> BuscarTodosLosAnios(int id_sector){
        return EntityManagerHelper.getEntityManager().createQuery("from" + HuellaDeCarbono.class.getName() +
                " where division_territorial = '"+ id_sector +"' and  periodo = 'ANUAL'").getResultList();
    }

}
