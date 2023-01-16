package domain.controllers;

import com.google.gson.Gson;
import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;
import domain.model.Entidades.Sector;
import domain.model.Entidades.TipoOrganizacion;
import domain.model.GeoReferencia.Punto;
import domain.model.Medios.MedioDeTransporte;
import domain.model.Medios.TransportePublico.Estacion;
import domain.model.Medios.TransportePublico.TransportePublico;
import domain.model.usuarios.Usuario;
import domain.repositories.*;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApiRestController {

    private RepositorioOrganizaciones repoOrganizaciones;
    private RepositorioDivisionTerritorial repoDivisionTerritorial;
    private RepositorioMedioTransporte repoMedioTransporte;
    private RepositorioDirecciones repoDirecciones;
    private RepositorioClasificaciones repositorioClasificaciones;
    private RepositorioPersonas repoPersonas;
    private RepositorioUsuarios repoUsuarios;



    public ApiRestController() {
        this.repoOrganizaciones = new RepositorioOrganizaciones();
        this.repoDivisionTerritorial = new RepositorioDivisionTerritorial();
        this.repoMedioTransporte = new RepositorioMedioTransporte();
        this.repoDirecciones = new RepositorioDirecciones();
        this.repositorioClasificaciones = new RepositorioClasificaciones();
        this.repoPersonas = new RepositorioPersonas();
        this.repoUsuarios = new RepositorioUsuarios();
    }

    public String mostrarSectores(Request request, Response response){
        String razonSocial = request.params("razonSocial");
        Organizacion organizacionBuscada = this.repoOrganizaciones.buscarNombre(razonSocial);
        List<Sector> sectores = organizacionBuscada.getSectores();
        List<String> sectoresStr = sectores.stream().map(s -> s.getNombre()).collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonSectores = gson.toJson(sectoresStr);

        response.type("application/json");
        return jsonSectores;
    }

    public String mostrarMunicipios(Request request, Response response){
        String provincia = request.params("id");
        Provincia provinciaBuscada = (Provincia) this.repoDivisionTerritorial.buscar(new Integer(provincia));

        System.out.println(provinciaBuscada.getNombre());

        List<Municipio> municipios = provinciaBuscada.getMunicipios();
        List<String> municipiosStr = municipios.stream().map(m -> m.getNombre()).collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonMunicipios = gson.toJson(municipiosStr);

        response.type("application/json");
        return jsonMunicipios;
    }

    public String mostrarEstaciones(Request request, Response response){
        String tipo = request.params("tipoTransportePublico");
        String linea = request.params("linea");
        MedioDeTransporte medioDeTransporte = this.repoMedioTransporte.buscarLinea(tipo, linea);
        List<Punto> puntos = this.repoDirecciones.buscarEstaciones(new Integer(medioDeTransporte.getId()));

        List<Estacion> estaciones = new ArrayList<>();
        for (Punto p: puntos){
            estaciones.add((Estacion) p);
        }

        List<String> estacionesStr = estaciones.stream().map(m -> m.getNombre()).collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonEstaciones = gson.toJson(estacionesStr);

        response.type("application/json");
        return jsonEstaciones;
    }

    public String mostrarMedios(Request request, Response response){
        String medio = request.params("tipoTransportePublico");
        List<MedioDeTransporte> mediosDeTransporte = this.repoMedioTransporte.buscarMedios(medio);
        List<TransportePublico> medios = new ArrayList<>();
        for (MedioDeTransporte m: mediosDeTransporte){
            medios.add((TransportePublico) m);
        }

        List<String> subteStr = medios.stream().map(m -> m.getLinea()).collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonMedios = gson.toJson(subteStr);

        response.type("application/json");
        return jsonMedios;
    }

    public String mostrarUsuarios(Request request, Response response){
        List<Usuario> usuarios = this.repoUsuarios.buscarTodos();

        List<String> usuariosStr = usuarios.stream().map(u -> u.getNombreDeUsuario()).collect(Collectors.toList());

        Gson gson = new Gson();
        String jsonUsuarios = gson.toJson(usuariosStr);

        response.type("application/json");
        return jsonUsuarios;
    }




}
