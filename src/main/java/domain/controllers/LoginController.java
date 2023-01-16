package domain.controllers;

import db.EntityManagerHelper;
import domain.model.usuarios.Usuario;
import domain.repositories.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class LoginController {

    private RepositorioUsuarios repositorioUsuarios;

    public LoginController() {
        this.repositorioUsuarios = new RepositorioUsuarios();
    }

    public ModelAndView loginOrganizacion(Request request, Response response){
        return new ModelAndView(null,"login_organizacion.hbs");
    }

    public ModelAndView loginMiembro(Request request, Response response){
        return new ModelAndView(null,"login_miembro.hbs");
    }
    public Response login(Request request, Response response) {
        String usuarioIngresado = request.queryParams("nombre_de_usuario");
        String contraseniaIngresada = request.queryParams("contrasenia");
        if (usuarioIngresado != null && contraseniaIngresada != null) {
            try {
                Usuario usuario = this.repositorioUsuarios.inicioSesionMiembro(usuarioIngresado, contraseniaIngresada);
                if (usuario != null && contraseniaIngresada.equals(request.queryParams("contrasenia"))) {
                    request.session(true);
                    request.session().attribute("id", usuario.getId());
                    System.out.println("existe");

                    if (usuario.getRol().getId() == 1) {
                        request.session(true);
                        request.session().attribute("id", usuario.getId());
                        response.redirect("/admin/abm/miembro");
                    } else if (usuario.getRol().getId() == 2) {
                        request.session(true);
                        request.session().attribute("id", usuario.getId());
                        response.redirect("/miembros/" + usuario.getNombreDeUsuario());
                    } else if (usuario.getRol().getId() == 4) {
                        request.session(true);
                        request.session().attribute("id", usuario.getId());
                        response.redirect("/agentes/" + usuario.getNombreDeUsuario());
                    }
                }


            } catch (Exception exception) {
                //Si cae es un agente sectorial


                response.redirect("/login/miembro");

            }
        }
        return response;
    }

    public Response loginOrga(Request request, Response response) {
        String usuarioIngresado = request.queryParams("nombre_de_usuario");
        String contraseniaIngresada = request.queryParams("contrasenia");
        if (usuarioIngresado != null && contraseniaIngresada != null) {
            try {
                Usuario usuario = this.repositorioUsuarios.inicioSesion(usuarioIngresado, contraseniaIngresada, 3);

                if (usuario != null && contraseniaIngresada.equals(request.queryParams("contrasenia"))) {
                    request.session(true);
                    request.session().attribute("id", usuario.getId());
                    response.redirect("/organizaciones/" + usuario.getNombreDeUsuario());
                } else {
                    response.redirect("/login/organizacion");
                }
            } catch (Exception exception) {
                response.redirect("/login/organizacion");
            }
        }
        return response;
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/");
        return response;
    }

    public Response loginAdmin(Request request, Response response) {

        try {
            Usuario usuario = new RepositorioUsuarios().inicioSesion(request.queryParams("nombre_de_usuario"),request.queryParams("contrasenia"),1);//todo: hashear
            if(usuario != null) {
                request.session(true);
                request.session().attribute("id", usuario.getId());

                response.redirect("/admin/todos");
            }
            else {
                response.redirect("/admin");
            }
        }
        catch (Exception exception) {
            response.redirect("/admin");
        }
        return response;
    }


}
