package domain.controllers;

import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;
import domain.model.GeoReferencia.*;
import domain.model.Medios.APulmon;
import domain.model.Medios.MedioDeTransporte;
import domain.model.Medios.ServicioContratado;
import domain.model.Medios.TransportePublico.Estacion;
import domain.model.Medios.TransportePublico.TransportePublico;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoCombustible;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoVehiculo;
import domain.model.Medios.VehiculoParticular.VehiculoParticular;
import domain.model.Servicios.Distancia.Adapters.AdapterServicio;
import domain.model.Servicios.Distancia.ServicioDistancia;
import domain.model.usuarios.Usuario;
import domain.repositories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TrayectoController {
    RepositorioUsuarios repositorioUsuarios;
    RepositorioPersonas repositorioPersonas;
    RepositorioDirecciones repositorioDirecciones;
    RepositorioDivisionTerritorial repositorioDivisionTerritorial;
    RepositorioRoles repositorioRoles;
    RepositorioTramos repoTramos;
    RepositorioTrayectos repoTrayecto;
    RepositorioMedioTransporte repoMedioTransporte;
    RepositorioViajesCompartidos repoViajeCompartido;


    private ServicioDistancia servicioDistancia;

    public TrayectoController() throws IOException {
        this.repositorioUsuarios = new RepositorioUsuarios();
        this.repositorioPersonas = new RepositorioPersonas();
        this.repositorioDirecciones = new RepositorioDirecciones();
        this.repositorioDivisionTerritorial = new RepositorioDivisionTerritorial();
        this.repositorioRoles = new RepositorioRoles();
        this.repoTramos = new RepositorioTramos();
        this.repoTrayecto = new RepositorioTrayectos();
        this.servicioDistancia = ServicioDistancia.getInstancia();
        this.servicioDistancia.setAdapter(new AdapterServicio());
        this.repoMedioTransporte = new RepositorioMedioTransporte();
        this.repoViajeCompartido = new RepositorioViajesCompartidos();
    }

    public ModelAndView mostrar(Request request, Response response) {
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioAModificar = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        Persona persona = usuarioAModificar.getPersona();
        List<Organizacion> organizaciones = persona.getOrganizaciones();
        Set<Persona> miembros = new HashSet<>();
        for (Organizacion o: organizaciones) {
            miembros.addAll(o.misEmpleados());
        }

        miembros.remove(persona);
        List<DivisionTerritorial> provincias = this.repositorioDivisionTerritorial.buscarProvincias();

        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", usuarioAModificar);
            put("provincia", provincias);
            put("organizacion", organizaciones);
            put("miembros", miembros);
        }},"trayectos.hbs");
    }

    public Punto agregarDatosPulmon(Request request, List<String> queries) {
        String medioSel = request.queryParams(queries.get(0));
        Punto punto;

        if (medioSel.equals("direccion")) {
            punto = new Ubicacion();
            if (request.queryParams(queries.get(1)) != null) {
                punto.setDireccion(request.queryParams(queries.get(1)));
            }
            if (request.queryParams(queries.get(2)) != null) {
                punto.setAltura(request.queryParams(queries.get(2)));
            }

            Provincia provinciaBuscada = (Provincia) this.repositorioDivisionTerritorial.buscarProvincia(request.queryParams(queries.get(3)));
            punto.setProvincia(provinciaBuscada);

            Municipio municipio = (Municipio) this.repositorioDivisionTerritorial.buscarMunicipio(request.queryParams(queries.get(4)));
            punto.setMunicipio(municipio);
        } else {
            String tipoTransporte = request.queryParams(queries.get(5));
            String linea = request.queryParams(queries.get(6));
            String estacionOrigen = request.queryParams(queries.get(7));
            MedioDeTransporte medio = this.repoMedioTransporte.buscarLinea(tipoTransporte, linea);
            punto = this.repositorioDirecciones.buscarEstacion(medio.getId(), estacionOrigen);
        }
        return punto;
    }

    public Response crear(Request request, Response response) throws IOException {
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioAModificar = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        Persona persona = usuarioAModificar.getPersona();
        List<Organizacion> organizaciones = persona.getOrganizaciones();
        Punto puntoOrigen;
        Punto puntoDestino;
        MedioDeTransporte medioDeTransporte;
        List<Persona> pasajeros = new ArrayList<>();

        String medioElegido = request.queryParams("medioOrigen");

        if (medioElegido.equals("a_pulmon")) {
            APulmon apie = new APulmon();
            apie.setServicio(servicioDistancia);
            medioDeTransporte = apie;
            List<String> queries = new ArrayList<>();
            queries.addAll(Arrays.asList("medioOrigenSel", "direccionOrigen", "alturaOrigen", "provinciaOrigenSel", "muniOrigenSel", "transporteSelect_1", "lineaSelect_1", "estacionSelect_1"));
            puntoOrigen = agregarDatosPulmon(request, queries);

            queries.clear();
            queries.addAll(Arrays.asList("medioDestinoSel", "direccionDestino", "alturaDestino", "provinciaDestinoSel", "municipioDestinoSel", "transporteSelect_2", "lineaSelect_2", "estacionSelect_2"));
            puntoDestino = agregarDatosPulmon(request, queries);

        } else if (medioElegido.equals("transporte_publico")) {

            String tipoTransporte = request.queryParams("transporteSelect_1");
            String linea = request.queryParams("lineaSelect_1");
            String estacionOrigen = request.queryParams("estacionSelect_1");
            String estacionDestino = request.queryParams("estacionSelect_2");

            medioDeTransporte = this.repoMedioTransporte.buscarLinea(tipoTransporte, linea);
            puntoOrigen = this.repositorioDirecciones.buscarEstacion(medioDeTransporte.getId(), estacionOrigen);
            puntoDestino = this.repositorioDirecciones.buscarEstacion(medioDeTransporte.getId(), estacionDestino);

        }else if (medioElegido.equals("vehiculo_particular")){

            Set<Persona> miembros = new HashSet<>();
            for (Organizacion o: organizaciones) {
                miembros.addAll(o.misEmpleados());
            }

            List<Persona> listaMiembros = new ArrayList<>(miembros);

            for (int i = 0; i < listaMiembros.size(); i++) {
                String miembroSel = request.queryParams("miembro_" + listaMiembros.get(i).getId());
                if (miembroSel != null){
                    pasajeros.add(listaMiembros.get(i));
                }
            }

            VehiculoParticular vehiculo = new VehiculoParticular();
            vehiculo.setServicio(servicioDistancia);
            medioDeTransporte = vehiculo;

            if (request.queryParams("vehiculoSelect_1").equals("AUTO")){
                vehiculo.setConsumoXKm(6.2/100);
            }else if(request.queryParams("vehiculoSelect_1").equals("MOTO")){
                vehiculo.setConsumoXKm(4.6/100);
            } else {
                vehiculo.setConsumoXKm(9.2/100);
            }

            vehiculo.setTipoCombustible(TipoCombustible.valueOf(request.queryParams("vehiculoTipoSelect_1")));
            vehiculo.setVehiculo(TipoVehiculo.valueOf(request.queryParams("vehiculoSelect_1")));
            List<String> queries = new ArrayList<>();
            queries.addAll(Arrays.asList("medioOrigenSel", "direccionOrigen", "alturaOrigen", "provinciaOrigenSel", "muniOrigenSel", "transporteSelect_1", "lineaSelect_1", "estacionSelect_1"));
            puntoOrigen = agregarDatosPulmon(request, queries);

            queries.clear();
            queries.addAll(Arrays.asList("medioDestinoSel", "direccionDestino", "alturaDestino", "provinciaDestinoSel", "municipioDestinoSel", "transporteSelect_2", "lineaSelect_2", "estacionSelect_2"));
            puntoDestino = agregarDatosPulmon(request, queries);
        }else {

            Set<Persona> miembros = new HashSet<>();
            for (Organizacion o: organizaciones) {
                miembros.addAll(o.misEmpleados());
            }

            List<Persona> listaMiembros = new ArrayList<>(miembros);

            for (int i = 0; i < listaMiembros.size(); i++) {
                String miembroSel = request.queryParams("miembro_" + listaMiembros.get(i).getId());
                if (miembroSel != null){
                    pasajeros.add(listaMiembros.get(i));
                }
            }

            ServicioContratado servicioContratado = new ServicioContratado();
            servicioContratado.setServicio(servicioDistancia);
            servicioContratado.setConsumoXKm(6.2/100);
            servicioContratado.setTipoCombustible(TipoCombustible.NAFTA);
            medioDeTransporte = servicioContratado;
            servicioContratado.setTipoServicioContratado(request.queryParams("servicioSelect_1"));
            List<String> queries = new ArrayList<>();
            queries.addAll(Arrays.asList("medioOrigenSel", "direccionOrigen", "alturaOrigen", "provinciaOrigenSel", "muniOrigenSel", "transporteSelect_1", "lineaSelect_1", "estacionSelect_1"));
            puntoOrigen = agregarDatosPulmon(request, queries);

            queries.clear();
            queries.addAll(Arrays.asList("medioDestinoSel", "direccionDestino", "alturaDestino", "provinciaDestinoSel", "municipioDestinoSel", "transporteSelect_2", "lineaSelect_2", "estacionSelect_2"));
            puntoDestino = agregarDatosPulmon(request, queries);
        }


        Trayecto trayecto = new Trayecto();
        Tramo tramo = new Tramo(puntoOrigen, puntoDestino,medioDeTransporte,persona);
        trayecto.agregarTramo(tramo);
        trayecto.setPersona(persona);
        persona.agregarTamo(tramo);

        Persona[] personasCompartidas = new Persona[pasajeros.size()];
        personasCompartidas = pasajeros.toArray(personasCompartidas);

        if (medioElegido.equals("vehiculo_particular") || medioElegido.equals("servicio_contratado")) {
            //ViajeCompartido viajes = tramo.compartirUnViaje(organizaciones.get(0), personasCompartidas);
            //for (ViajeCompartido v: viajes){
            //    this.repoViajeCompartido.guardar(v);
            //}
            for (Persona p: personasCompartidas){
                ViajeCompartido viaje = tramo.compartirUnViaje(organizaciones.get(0), p);
                this.repoViajeCompartido.guardar(viaje);
                this.repositorioPersonas.modificar(p);
            }
        }

        Double distancia = medioDeTransporte.calcularDistancia(puntoOrigen,puntoDestino);
        trayecto.setTotalTrayecto(distancia);
        tramo.setTotalTramo(distancia);

        this.repositorioPersonas.modificar(persona);

        response.redirect("/miembros/" + nombreUsuario);
        return response;
    }

}
