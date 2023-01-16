package domain.controllers;

import domain.model.AgentesSectoriales.AgenteSectorial;
import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.Entidades.Mediciones.FactorEmision;
import domain.model.Entidades.Mediciones.FactorTraslado;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;
import domain.model.usuarios.Usuario;
import domain.repositories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AdminController {

    RepositorioUsuarios repositorioUsuarios;
    RepositorioPersonas repositorioPersonas;
    RepositorioDirecciones repositorioDirecciones;
    RepositorioDivisionTerritorial repositorioDivisionTerritorial;
    RepositorioRoles repositorioRoles;
    RepositorioSolicitud repoSolicitud;
    RepositorioOrganizaciones repositorioOrganizaciones;
    RepositorioFactoresEmision repositorioFactoresEmision;

    RepositoriosAgentes repositoriosAgentes;
    public AdminController() throws IOException {
        this.repositorioUsuarios = new RepositorioUsuarios();
        this.repositorioPersonas = new RepositorioPersonas();
        this.repositorioDirecciones = new RepositorioDirecciones();
        this.repositorioDivisionTerritorial = new RepositorioDivisionTerritorial();
        this.repositorioRoles = new RepositorioRoles();
        this.repositorioOrganizaciones = new RepositorioOrganizaciones();
        this.repoSolicitud = new RepositorioSolicitud();
        this.repositorioFactoresEmision = new RepositorioFactoresEmision();
        this.repositoriosAgentes = new RepositoriosAgentes();
    }

    public ModelAndView mostrarLogin(Request request,Response response){
        return new ModelAndView(null,"login.hbs");
    }
    public ModelAndView mostrarTodos(Request request, Response response) {
        List<Usuario> usuarios = this.repositorioUsuarios.buscarTodos();
        List<Organizacion> organizaciones = this.repositorioOrganizaciones.buscarTodos();
        List<DivisionTerritorial> provincias = this.repositorioDivisionTerritorial.buscarProvincias();
        List<FactorEmision> factores = this.repositorioFactoresEmision.buscarTodosEmision();

        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuarios", usuarios);
            put("organizacion", organizaciones);
            put("provincia", provincias);
            put("factor", factores);
        }},"admin_miembros.hbs");
    }
    public ModelAndView mostrarMiembros(Request request, Response response) {
        List<Usuario> usuarios = this.repositorioUsuarios.buscarTodosLosMiembros();
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuarios", usuarios);
        }},"admin_miembros.hbs");
    }


    public Response borrar(Request request, Response response) {
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioABorrar = this.repositorioUsuarios.buscarNombre(nombreUsuario);

        this.repositorioUsuarios.eliminar(usuarioABorrar);
        //response.redirect("/admin");
        return response;
    }

    public ModelAndView mostrarOrg(Request request, Response response) {
           // List<Organizacion> organizaciones = this.repositorioOrganizaciones.buscarTodos();
            List<Usuario> usuarios = this.repositorioUsuarios.buscarTodasLasOrganizaciones();
            return new ModelAndView(new HashMap<String, Object>(){{

                put("usuarios", usuarios);

            }},"abm_organizacion.hbs");

    }

    public ModelAndView mostrarProv(Request request, Response response) {
        List<DivisionTerritorial> provincias = this.repositorioDivisionTerritorial.buscarProvincias();

        return new ModelAndView(new HashMap<String, Object>(){{
            put("provincia", provincias);
        }},"abm_provincia.hbs");
    }
    public ModelAndView mostrarAgentes(Request request, Response response){
        List<AgenteSectorial> agenteSectoriales = this.repositoriosAgentes.traerAgentes();

        return new ModelAndView(new HashMap<String,Object>(){{
            put("agetes",agenteSectoriales);
        }},"abm_agentes.hbs");
    }


    public ModelAndView mostrarFe(Request request, Response response) {
        List<FactorEmision> factoresEmision = this.repositorioFactoresEmision.buscarTodosEmision();
        List<FactorTraslado> factoresTraslado = this.repositorioFactoresEmision.buscarTodosTraslado();

        return new ModelAndView(new HashMap<String, Object>(){{
            put("factoresEmision", factoresEmision);
            put("factoresTraslado", factoresTraslado);
        }},"abm_fe.hbs");
    }

    public Response modificarFactores(Request request, Response response) {
        List<FactorEmision> factoresEmision = this.repositorioFactoresEmision.buscarTodosEmision();
        List<FactorTraslado> factoresTraslado = this.repositorioFactoresEmision.buscarTodosTraslado();


        for (FactorEmision fe: factoresEmision){
            System.out.println(fe.getTipo().toString());
            fe.setValorFE(Double.parseDouble(request.queryParams(fe.getTipo().toString())));
            //this.repositorioFactoresEmision.modificarEmision(fe);
        }
        for (FactorTraslado ft: factoresTraslado){
            ft.setValorFT(Double.parseDouble(request.queryParams(ft.getTipoCombustible().toString())));
            //this.repositorioFactoresEmision.modificarTraslado(ft);
        }

        FactorEmision[] factoresE = new FactorEmision[factoresEmision.size()];
        factoresE = factoresEmision.toArray(factoresE);

        FactorTraslado[] factoresT = new FactorTraslado[factoresTraslado.size()];
        factoresT = factoresTraslado.toArray(factoresT);

        this.repositorioFactoresEmision.agregarFactoresEmision(factoresE);
        this.repositorioFactoresEmision.agregarFactoresTraslado(factoresT);
        response.redirect("/admin/abm/fe");
        return response;
    }


}
