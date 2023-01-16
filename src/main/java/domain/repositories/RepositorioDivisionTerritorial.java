package domain.repositories;

import db.EntityManagerHelper;
import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.GeoReferencia.Punto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioDivisionTerritorial {

    public List<DivisionTerritorial> buscarProvincias() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + DivisionTerritorial.class.getName() + " where division = 'Provincia'")
                .getResultList();
    }

    public List<Provincia> buscarProv() {
        return EntityManagerHelper
                .getEntityManager()
                .createQuery("from " + DivisionTerritorial.class.getName() + " where division = 'Provincia'")
                .getResultList();
    }

    public DivisionTerritorial buscarProvincia(String nombre) {
        DivisionTerritorial provincia = (DivisionTerritorial) EntityManagerHelper
                .createQuery("from " + DivisionTerritorial.class.getName() + " where division = 'Provincia' and nombre = '" + nombre + "'")
                .getSingleResult();
        return provincia;
    }

    public DivisionTerritorial buscarMunicipio(String nombre) {
        DivisionTerritorial divisionTerritorial = (DivisionTerritorial) EntityManagerHelper
                .createQuery("from " + DivisionTerritorial.class.getName() + " where division = 'Municipio' and nombre = '" + nombre + "'")
                .getSingleResult();
        return divisionTerritorial;
    }

    public DivisionTerritorial buscar(Integer id) {
        return EntityManagerHelper
                .getEntityManager()
                .find(DivisionTerritorial.class, id);
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

    public void agregarPuntos(Punto ... puntos) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        for (Punto p: puntos){
            EntityManagerHelper.getEntityManager().persist(p);
        }
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void agregarProvincia(Provincia punto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(punto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void agregarProvincias(Provincia ... provincias) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        for (Provincia p: provincias) {
            EntityManagerHelper.getEntityManager().persist(p);
        }
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void agregarMunicipio(Municipio punto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(punto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void modificarProvincia(Provincia provincia) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(provincia);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void modificarProvincias(Provincia ... provincias) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        for (Provincia p: provincias) {
            EntityManagerHelper.getEntityManager().merge(p);
        }
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void modificar(Punto punto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(punto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void modificarMunicipio(Municipio municipio){
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(municipio);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void eliminar(Punto punto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(punto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    public void agregarTodos(Punto ... puntosAAgregar){
        List<Punto> puntos = new ArrayList<>();
        Collections.addAll(puntos,puntosAAgregar);
        for(Punto p: puntos){
            this.agregar(p);
        }
    }

    public void updateTodos(Punto ... puntosAAgregar){
        List<Punto> puntos = new ArrayList<>();
        Collections.addAll(puntos,puntosAAgregar);
        for(Punto p: puntos){
            this.modificar(p);
        }
    }

}
