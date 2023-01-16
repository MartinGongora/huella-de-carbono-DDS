package domain.controllers;

import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.Entidades.HuellaCarbono.CalculadoraHC;
import domain.model.Entidades.Mediciones.FactorTraslado;
import domain.model.Entidades.Mediciones.Periodicidad;
import domain.model.Entidades.*;
import domain.model.GeoReferencia.*;
import domain.model.ValidadorPassword.CondicionDeContenido;
import domain.model.ValidadorPassword.CondicionDeLargo;
import domain.model.ValidadorPassword.CondicionDePeores;
import domain.model.ValidadorPassword.ValidadorPassword;
import domain.model.usuarios.Rol;
import domain.model.usuarios.Usuario;
import domain.repositories.*;
import helpers.UsuarioHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MiembroController {
    RepositorioUsuarios repositorioUsuarios;
    RepositorioPersonas repositorioPersonas;
    RepositorioDirecciones repositorioDirecciones;
    RepositorioDivisionTerritorial repositorioDivisionTerritorial;
    RepositorioRoles repositorioRoles;
    ValidadorPassword validador;
    RepositorioSolicitud repoSolicitud;
    RepositorioOrganizaciones repositorioOrganizaciones;
    RepositorioViajesCompartidos repoViajesCompartidos;
    RepositorioTramos repoTramos;
    RepositorioTrayectos repoTrayectos;
    RepositorioFactoresEmision repoFactoresEmision;

    public MiembroController() throws IOException {
        this.repositorioUsuarios = new RepositorioUsuarios();
        this.repositorioPersonas = new RepositorioPersonas();
        this.repositorioDirecciones = new RepositorioDirecciones();
        this.repositorioDivisionTerritorial = new RepositorioDivisionTerritorial();
        this.repositorioRoles = new RepositorioRoles();
        this.validador = new ValidadorPassword();
        this.validador.agregarCondicionDeValidacion(new CondicionDeLargo());
        this.validador.agregarCondicionDeValidacion(new CondicionDePeores());
        this.validador.agregarCondicionDeValidacion(new CondicionDeContenido());
        this.repositorioOrganizaciones = new RepositorioOrganizaciones();
        this.repoSolicitud = new RepositorioSolicitud();
        this.repoViajesCompartidos = new RepositorioViajesCompartidos();
        this.repoTramos = new RepositorioTramos();
        this.repoTrayectos = new RepositorioTrayectos();
        this.repoFactoresEmision = new RepositorioFactoresEmision();
    }

    public ModelAndView registrar(Request request, Response response){
        List<DivisionTerritorial> provincias = this.repositorioDivisionTerritorial.buscarProvincias();
        List<Usuario> usuarios = this.repositorioUsuarios.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("provincia", provincias);
            put("usuarios", usuarios);
        }},"registrar_miembro.hbs");
    }


    public ModelAndView mostrar(Request request, Response response){
        Usuario usuarioLogeado = UsuarioHelper.usuarioLogueado(request);
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioBuscado = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        HashSet<Organizacion> misOrganizaciones = new HashSet<>();
        misOrganizaciones.addAll(usuarioBuscado.getPersona().getOrganizaciones());
        List<ViajeCompartido> viajesPendientes = this.repoViajesCompartidos.buscarViajePendienteDe(new Integer(usuarioBuscado.getPersona().getId()));
        List<Solicitud> solicitudesPendientes = this.repoSolicitud.buscarSolicitudPendienteDe(new Integer(usuarioBuscado.getPersona().getId()));
        List<Organizacion> organizaciones = this.repositorioOrganizaciones.buscarTodos();
        List<DivisionTerritorial> provincias = this.repositorioDivisionTerritorial.buscarProvincias();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuarioLogueado", usuarioLogeado);
            put("usuario", usuarioBuscado);
            put("viaje", viajesPendientes);
            put("solicitud", solicitudesPendientes);
            put("misOrganizaciones", misOrganizaciones);
            put("organizacion", organizaciones);
            put("provincia", provincias);
        }},"datos_miembro.hbs");
    }

    public void verificarDatos(Usuario usuario, Persona persona, Punto punto, Request request){

        if (request.queryParams("nombre") != null && request.queryParams("nombre") != usuario.getNombre()){
            usuario.setNombre(request.queryParams("nombre"));
            persona.setNombre(request.queryParams("nombre"));
        }

        if (request.queryParams("apellido") != null && request.queryParams("apellido") != usuario.getNombre()){
            usuario.setApellido(request.queryParams("apellido"));
            persona.setApellido(request.queryParams("apellido"));
        }

        System.out.println(request.queryParams("tipoDocumento"));
        if (request.queryParams("tipoDocumento") != null && TipoDocumento.valueOf(request.queryParams("tipoDocumento")) != persona.getTipoDocumento()){
            persona.setTipoDocumento(TipoDocumento.valueOf(request.queryParams("tipoDocumento")));
        }

        if (request.queryParams("nroDocumento") != null && request.queryParams("nroDocumento") != persona.getDocumento()){
            persona.setDocumento(request.queryParams("nroDocumento"));
        }

        if (request.queryParams("telefono") != null && request.queryParams("telefono") != persona.getTelefono()){
            persona.setTelefono(request.queryParams("telefono"));
        }

        if (request.queryParams("email") != null && request.queryParams("email") != persona.getMail()){
            persona.setMail(request.queryParams("email"));
        }

        if(request.queryParams("calle")!= null && request.queryParams("calle") != punto.getDireccion()){
            punto.setDireccion(request.queryParams("calle"));
        }

        Provincia provinciaBuscada = (Provincia) this.repositorioDivisionTerritorial.buscarProvincia(request.queryParams("provincia"));
        punto.setProvincia(provinciaBuscada);

        Municipio municipio = (Municipio) this.repositorioDivisionTerritorial.buscarMunicipio(request.queryParams("municipio"));
        punto.setMunicipio(municipio);
    }


    public Response modificar(Request request, Response response) {
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioAModificar = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        Persona personaAModificar = usuarioAModificar.getPersona();
        Punto puntoAModificar = personaAModificar.getDomicilio();


        verificarDatos(usuarioAModificar, personaAModificar, puntoAModificar, request);

        this.repositorioDirecciones.modificar(puntoAModificar);
        this.repositorioPersonas.modificar(personaAModificar);
        this.repositorioUsuarios.modificar(usuarioAModificar);

        response.redirect("/miembros/" + usuarioAModificar.getNombreDeUsuario());
        return response;
    }

    public Response vincular(Request request, Response response) {
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioAModificar = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        Organizacion orgBuscada = this.repositorioOrganizaciones.buscarNombre(request.queryParams("orgVincular"));
        List<Sector> sector = orgBuscada.getSectores().stream().filter(s -> s.getNombre().equals(request.queryParams("sectorVincular"))).collect(Collectors.toList());
        Persona personaAModificar = usuarioAModificar.getPersona();

        Solicitud nuevaSolicitud = personaAModificar.solicitarVinculacion(orgBuscada, sector.get(0));

        this.repoSolicitud.agregar(nuevaSolicitud);

        response.redirect("/miembros/" + usuarioAModificar.getNombreDeUsuario());
        return response;
    }
    public void agregarDatos(Usuario usuario, Persona persona, Punto punto, Request request){
        List<Usuario> todosUsuarios = this.repositorioUsuarios.buscarTodos();
        List<Usuario> usuarios = todosUsuarios.stream().filter(u -> u.getNombreDeUsuario() == request.queryParams("usuario")).collect(Collectors.toList());

        if (request.queryParams("usuario") != null && usuarios.size() == 0){
            usuario.setNombreDeUsuario(request.queryParams("usuario"));
        }

        if (request.queryParams("contrasenia") != null){
            this.validador.validarClave(request.queryParams("contrasenia"));
            usuario.setContrasenia(request.queryParams("contrasenia"));
        }

        if (request.queryParams("nombre") != null){
            usuario.setNombre(request.queryParams("nombre"));
            persona.setNombre(request.queryParams("nombre"));
        }

        if (request.queryParams("apellido") != null){
            usuario.setApellido(request.queryParams("apellido"));
            persona.setApellido(request.queryParams("apellido"));
        }

        persona.setTipoDocumento(TipoDocumento.valueOf(request.queryParams("tipoDocumento")));

        if (request.queryParams("nroDocumento") != null){
            persona.setDocumento(request.queryParams("nroDocumento"));
        }

        if (request.queryParams("telefono") != null){
            persona.setTelefono(request.queryParams("telefono"));
        }

        if (request.queryParams("email") != null){
            persona.setMail(request.queryParams("email"));
        }

        if(request.queryParams("calle")!= null){
            punto.setDireccion(request.queryParams("calle"));
        }

        if(request.queryParams("altura")!= null){
            punto.setAltura(request.queryParams("altura"));
        }

        Provincia provinciaBuscada = (Provincia) this.repositorioDivisionTerritorial.buscarProvincia(request.queryParams("provincia"));
        punto.setProvincia(provinciaBuscada);

        Municipio municipio = (Municipio) this.repositorioDivisionTerritorial.buscarMunicipio(request.queryParams("municipio"));
        punto.setMunicipio(municipio);
    }

    public ModelAndView crear(Request request, Response response) {
        Rol rolPersist = this.repositorioRoles.buscar(2);
        //System.out.println(rolPersist.getNombre());

        Usuario usuarioNuevo = new Usuario();
        Persona personaNueva = new Persona();
        Punto nuevoPunto = new Ubicacion();

        usuarioNuevo.setRol(rolPersist);
        usuarioNuevo.setPersona(personaNueva);
        personaNueva.setDomicilio(nuevoPunto);

        agregarDatos(usuarioNuevo, personaNueva, nuevoPunto, request);

        this.repositorioDirecciones.agregar(nuevoPunto);
        this.repositorioPersonas.agregar(personaNueva);
        this.repositorioUsuarios.guardar(usuarioNuevo);

        //response.redirect("/miembros/"+usuarioNuevo.getNombreDeUsuario());
        //response.redirect("/miembros/exito");
        //return response;
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", usuarioNuevo);
        }},"miembro_exito.hbs");
    }

    public ModelAndView exitoMiembro(Request request, Response response){
        return new ModelAndView(new HashMap<String, Object>(){{
        }},"miembro_exito.hbs");
    }

    public ModelAndView mostrarTodos(Request request, Response response) {
        List<Usuario> usuarios = this.repositorioUsuarios.buscarTodos();
        //List<Estacion> estaciones = this.repositorioUsuarios.buscarTodos();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuarios", usuarios);
        }},"admin_miembros.hbs");
    }

   public Response borrar(Request request, Response response) {
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioABorrar = this.repositorioUsuarios.buscarNombre(nombreUsuario);

        this.repositorioUsuarios.eliminar(usuarioABorrar);
        return response;
    }

    public Response viajar(Request request, Response response){
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuario = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        Persona persona = usuario.getPersona();
        List<ViajeCompartido> viajesPendientes = this.repoViajesCompartidos.buscarViajePendienteDe(new Integer(usuario.getPersona().getId()));

        System.out.println(viajesPendientes);

        Trayecto trayecto = new Trayecto();
        trayecto.setPersona(persona);
        persona.confirmarViajes();

        for(ViajeCompartido v: viajesPendientes){
            Tramo tramo = v.getTramoACompartir();
            trayecto.agregarTramo(tramo);
        }

        trayecto.calcularTramos();

        this.repositorioPersonas.modificar(persona);
        response.redirect("/miembros/" + usuario.getNombreDeUsuario());
        return response;
    }

    public ModelAndView huella(Request request, Response response) {
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioBuscado = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        return new ModelAndView(new HashMap<String, Object>() {{
            put("usuario", usuarioBuscado);
        }}, "calculadora_hc_miembro.hbs");
    }

    public ModelAndView calcularHuella(Request request, Response response) {
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioBuscado = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        Persona persona = usuarioBuscado.getPersona();
        CalculadoraHC calculadoraHC = new CalculadoraHC();

        List<FactorTraslado> factores = this.repoFactoresEmision.buscarTodosTraslado();
        FactorTraslado[] factoresTraslado = new FactorTraslado[factores.size()];
        factoresTraslado = factores.toArray(factoresTraslado);
        calculadoraHC.agregarFactoresTraslado(factoresTraslado);

        String huellaStr = "";
        Double huellaValor = 0.0;

        if (request.queryParams("periodoSel") != null && request.queryParams("mesSel") != null && request.queryParams("anioSel") != null){
            huellaStr = "Su huella del ";
            if (request.queryParams("periodoSel").equals("MENSUAL")){
                huellaStr += "mes " + request.queryParams("mesSel") + " de " + request.queryParams("anioSel");
            } else {
                huellaStr += "año " + request.queryParams("anioSel");
            }
            huellaStr += " es: ";

            huellaValor = calculadoraHC.calcularTraslado(persona,
                    Periodicidad.valueOf(request.queryParams("periodoSel")),
                    new Integer(request.queryParams("mesSel")),
                    new Integer(request.queryParams("anioSel")));

        }

        DecimalFormat formatoNumero = new DecimalFormat("#.##");

        this.repositorioPersonas.modificar(persona);
        huellaStr += formatoNumero.format(huellaValor).toString() + " kgEqCO2";
        String finalHuellaStr = huellaStr;
        return new ModelAndView(new HashMap<String, Object>() {{
            put("usuario", usuarioBuscado);
            put("huellaStr", finalHuellaStr);
        }}, "calculadora_hc_miembro.hbs");
    }
}
