package Server;

import Spark.utils.*;
import db.EntityManagerHelper;
import domain.controllers.*;
import domain.model.usuarios.Permiso;
import domain.model.usuarios.Usuario;
import helpers.PermisoHelper;
import helpers.UsuarioHelper;
import middlewares.AuthMiddleware;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("esAdmin", EsAdmin.esAdmin)
                .build();

        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("esMiembro", EsMiembro.esMiembro)
                .build();

        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("esOrg", EsOrg.esOrg)
                .build();

        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() throws IOException {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() throws IOException {
        InitController initController = new InitController();
        MiembroController miembroController = new MiembroController();
        LoginController loginController = new LoginController();
        TrayectoController trayectoController = new TrayectoController();
        ApiRestController apiRestController = new ApiRestController();
        AdminController adminController = new AdminController();
        OrganizacionController organizacionController = new OrganizacionController();
        UsuarioHelper usurarioLogueado = new UsuarioHelper();
        ReporteController reporteController =  new ReporteController();

        Spark.path("/", ()->{
            Spark.get("", initController::pantallaHome, engine);

            Spark.get("/api/usuarios", apiRestController::mostrarUsuarios);
            Spark.get("/api/organizaciones/:razonSocial", apiRestController::mostrarSectores);
            Spark.get("/api/provincias/:id", apiRestController::mostrarMunicipios);
            Spark.get("/api/:tipoTransportePublico", apiRestController::mostrarMedios);
            Spark.get("/api/:tipoTransportePublico/:linea", apiRestController::mostrarEstaciones);
        });

        Spark.path("/login", ()->{
            /*Miembros*/
            Spark.get("/miembro", loginController::loginMiembro, engine);
            Spark.post("/miembro", loginController::login);
            Spark.get("/miembro/logout", loginController::logout);

            /*Organizacion*/
            Spark.get("/organizacion",loginController::loginOrganizacion,engine);
            Spark.post("/organizacion",loginController::loginOrga);
            Spark.get("/organizacion/logout", loginController::logout);

            /*Admin*/
            Spark.get("/admin/logout", loginController::logout);

            /*Agentes*/
            Spark.get("/agente/logout", loginController::logout);

        });


        Spark.path("/admin", ()->{

            Spark.before("", AuthMiddleware::verificarSesion);
            Spark.before("/*", AuthMiddleware::verificarSesion);
            Spark.before("/*", ((request, response) -> {
                String nombreUsuario = request.params("nombreDeUsuario");
                Usuario usuarioLogeado = UsuarioHelper.usuarioLogueado(request);
                List<Permiso> permisosMiembro = new ArrayList<>();

                if(!PermisoHelper.usuarioTienePermisos(request, 1 ,permisosMiembro)){
                    if(usuarioLogeado.getRol().getId() == 2) {
                        response.redirect("/miembros/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    } else if (usuarioLogeado.getRol().getId() == 3) {
                        response.redirect("/organizaciones/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    }
                }
            }));

            Spark.before("/abm/org", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/abm/fe", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/abm/miembro", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/abm/agentes", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/organizaciones/:nombreDeUsuario", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/:nombreDeUsuario", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.get("/abm/org",adminController::mostrarOrg,engine);
            //Spark.get("/abm/provincia",adminController::mostrarProv,engine);
            Spark.get("/abm/fe",adminController::mostrarFe,engine);
            Spark.post("/abm/fe", adminController::modificarFactores);
            Spark.get("/abm/miembro",adminController::mostrarMiembros,engine);
            Spark.get("/abm/agentes",adminController::mostrarAgentes,engine);
            Spark.get("/organizaciones/:nombreDeUsuario", organizacionController::mostrarEditar); //TODO no esta funcionando
            Spark.delete("/:nombreDeUsuario", adminController::borrar); //TODO las amb usan este
            //Spark.before("", AuthMiddleware::verificarSesion);

        });


        Spark.path("/registrar", ()->{
            Spark.get("/miembro", miembroController::registrar, engine);
            Spark.get("/organizacion", organizacionController::registrar, engine);
            Spark.post("/exitoMiembro", miembroController::crear, engine);
            Spark.post("/exitoOrg", organizacionController::agregar, engine);
            //Spark.post("/exitoOrg", loginController::exitoOrganizacion ,engine);
        });

        Spark.path("/miembros", ()->{

            Spark.before("/:nombreDeUsuario", AuthMiddleware::verificarSesion);
            Spark.before("/*", AuthMiddleware::verificarSesion);

            Spark.before("/:nombreDeUsuario", ((request, response) -> {
                String nombreUsuario = request.params("nombreDeUsuario");
                Usuario usuarioLogeado = UsuarioHelper.usuarioLogueado(request);
                List<Permiso> permisosMiembro = new ArrayList<>();


                if(!PermisoHelper.usuarioTienePermisos(request, 2 ,permisosMiembro)) {
                    if (usuarioLogeado.getRol().getId() == 3) {
                        response.redirect("/organizaciones/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    } else {
                        response.redirect("/agentes/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    }
                }
                if (usuarioLogeado.getRol().getId() != 1) {
                    if (!nombreUsuario.equals(usuarioLogeado.getNombreDeUsuario())) {
                        response.redirect("/miembros/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    }
                }
            }));

            Spark.before("/:nombreDeUsuario", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/:nombreDeUsuario/huellaCarbono", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/:nombreDeUsuario/vincular", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/:nombreDeUsuario/trayectos", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/:nombreDeUsuario/viaje", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/:nombreDeUsuario/org/:razonSocial", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            //Spark.get("/exito", miembroController::exitoMiembro ,engine);
            Spark.get("/:nombreDeUsuario", miembroController::mostrar, engine);

            Spark.get("/:nombreDeUsuario/recomendacion", initController::recomendacion, engine);
            Spark.get("/:nombreDeUsuario/huellaCarbono", miembroController::huella, engine);
            Spark.post("/:nombreDeUsuario/huellaCarbono", miembroController::calcularHuella, engine);

            Spark.post("/:nombreDeUsuario", miembroController::modificar);
            Spark.post("/:nombreDeUsuario/vincular", miembroController::vincular);

            Spark.get("/:nombreDeUsuario/trayectos", trayectoController::mostrar, engine);
            Spark.post("/:nombreDeUsuario/trayectos", trayectoController::crear);

            Spark.delete("/:nombreDeUsuario", adminController::borrar);
            Spark.post("/:nombreDeUsuario/viaje", miembroController::viajar);

            Spark.get("/:nombreDeUsuario/org/:razonSocial", organizacionController::mostrar, engine);

        });

        Spark.path("/organizaciones", ()->{
            Spark.before("/:nombreDeUsuario", AuthMiddleware::verificarSesion);
            Spark.before("/*", AuthMiddleware::verificarSesion);


            Spark.before("/:nombreDeUsuario", ((request, response) -> {
                String nombreUsuario = request.params("nombreDeUsuario");
                Usuario usuarioLogeado = UsuarioHelper.usuarioLogueado(request);
                List<Permiso> permisosMiembro = new ArrayList<>(); //esta demas

                if(!PermisoHelper.usuarioTienePermisos(request, 3 ,permisosMiembro)) {
                    if (usuarioLogeado.getRol().getId() == 2) {
                        response.redirect("/miembros/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    } else {
                        response.redirect("/agentes/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    }
                }
                if (usuarioLogeado.getRol().getId() != 1) {
                    if (!nombreUsuario.equals(usuarioLogeado.getNombreDeUsuario())) {
                        response.redirect("/organizaciones/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    }
                }
            }));

            Spark.before("/:id-organizacion/solicitudes", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/:nombreDeUsuario/mediciones", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/:nombreDeUsuario/huellaCarbono", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));

            Spark.before("/:nombreDeUsuario", ((request, response) -> {
                EntityManagerHelper.closeEntityManager();
            }));


            Spark.post("/:id-organizacion/solicitudes/:id-solicitud/aceptada", organizacionController::aceptarSolicitud);
            Spark.post("/:id-organizacion/solicitudes/:id-solicitud/rechazada", organizacionController::rechazarSolicitud);
            Spark.get("/:id-organizacion/solicitudes", organizacionController::mostrarVincular, engine);
            Spark.post("/:nombreDeUsuario/editar/:nombre-sector", organizacionController::agregarSector);

            Spark.get("/:nombreDeUsuario/mediciones", organizacionController::mostrarCargarArchivo, engine);
            Spark.post("/:nombreDeUsuario/mediciones", organizacionController::cargarArchivo);

            //Spark.get("/:nombreDeUsuario/reportes", reporteController::mostrarReportesOrg, engine);

            Spark.get("/:nombreDeUsuario/recomendacion", initController::recomendacion, engine);
            Spark.get("/:nombreDeUsuario/huellaCarbono", organizacionController::huella, engine);
            Spark.post("/:nombreDeUsuario/huellaCarbono", organizacionController::calcularHuella, engine);
            //Spark.get("/:nombreDeUsuario/editar", organizacionController::mostrarEditar, engine);
            Spark.post("/:nombreDeUsuario/editar", organizacionController::editar);
            Spark.get("/:nombreDeUsuario",organizacionController::mostrarEditar,engine);
            //Spark.post("", organizacionController::agregar, engine);
        });

        Spark.path("/agentes",()->{
            Spark.before("/:nombreDeUsuario", AuthMiddleware::verificarSesion);
            Spark.before("/*", AuthMiddleware::verificarSesion);

            Spark.before("/:nombreDeUsuario", ((request, response) -> {
                String nombreUsuario = request.params("nombreDeUsuario");
                Usuario usuarioLogeado = UsuarioHelper.usuarioLogueado(request);
                List<Permiso> permisosMiembro = new ArrayList<>();


                if(!PermisoHelper.usuarioTienePermisos(request, 4 ,permisosMiembro)) {
                    if (usuarioLogeado.getRol().getId() == 3) {
                        response.redirect("/organizaciones/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    } else {
                        response.redirect("/miembros/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    }
                }
                if (usuarioLogeado.getRol().getId() != 4) {
                    if (!nombreUsuario.equals(usuarioLogeado.getNombreDeUsuario())) {
                        response.redirect("/agentes/" + usuarioLogeado.getNombreDeUsuario());
                        Spark.halt();
                    }
                }
            }));

            Spark.get("/:nombreDeUsuario", reporteController::mostrar,engine);
        });

    }
}
