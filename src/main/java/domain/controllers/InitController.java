package domain.controllers;

import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.Entidades.Organizacion;
import domain.model.usuarios.Usuario;
import domain.repositories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class InitController {

    RepositorioUsuarios repositorioUsuarios;
    RepositorioOrganizaciones repositorioOrganizaciones;

    public InitController() {
        this.repositorioUsuarios = new RepositorioUsuarios();
        this.repositorioOrganizaciones = new RepositorioOrganizaciones();
    }

    public ModelAndView pantallaHome(Request request, Response response){
        return new ModelAndView(null,"index.hbs");
    }

    public ModelAndView recomendacion(Request request, Response response) {
        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuarioBuscado = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", usuarioBuscado);
        }},"guia_recomendaciones.hbs");
    }
}
