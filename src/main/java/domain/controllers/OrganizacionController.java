package domain.controllers;

import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.Entidades.*;
import domain.model.Entidades.HuellaCarbono.CalculadoraHC;
import domain.model.Entidades.Mediciones.*;
import domain.model.GeoReferencia.Punto;
import domain.model.GeoReferencia.Ubicacion;
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

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizacionController {
  private RepositorioClasificaciones repositorioClasificaciones;
  private RepositorioOrganizaciones repositorioOrganizaciones;

  private RepositorioRoles repositorioRoles;

  private RepositorioUsuarios repositorioUsuarios;

  private RepositorioDivisionTerritorial repositorioDivisionTerritorial;

  private RepositorioSolicitud repositorioSolicitud;
  private RepositorioDirecciones repoDirecciones;
  private RepositorioSectores repositorioSectores;

  private RepositorioFactoresEmision repoFactoresEmision;

  private RepositorioSectoresTerritoriales repositorioSectoresTerritoriales;

  private ValidadorPassword validador;

  public OrganizacionController() throws FileNotFoundException {
    this.repositorioClasificaciones = new RepositorioClasificaciones();
    this.repositorioOrganizaciones = new RepositorioOrganizaciones();
    this.repositorioRoles = new RepositorioRoles();
    this.repositorioUsuarios = new RepositorioUsuarios();
    this.repositorioDivisionTerritorial = new RepositorioDivisionTerritorial();
    this.repositorioSolicitud = new RepositorioSolicitud();
    this.repositorioSectores = new RepositorioSectores();
    this.repoFactoresEmision = new RepositorioFactoresEmision();
    this.validador = new ValidadorPassword();
    this.validador.agregarCondicionDeValidacion(new CondicionDeLargo());
    this.validador.agregarCondicionDeValidacion(new CondicionDePeores());
    this.validador.agregarCondicionDeValidacion(new CondicionDeContenido());
    this.repositorioSectoresTerritoriales = new RepositorioSectoresTerritoriales();
  }

  public ModelAndView registrar(Request request, Response response){
    List<ClasificacionOrganizacion> todasLasclasificaciones = this.repositorioClasificaciones.buscarTodos();
    List<DivisionTerritorial> provincias = this.repositorioDivisionTerritorial.buscarProvincias();

    return new ModelAndView(new HashMap<String, Object>(){{
      put("clasificaciones", todasLasclasificaciones);
      put("provincia", provincias);
    }}, "registrar_organizacion.hbs");
  }

  public ModelAndView agregar(Request request, Response response){
    Rol rolPersist = this.repositorioRoles.buscar(3);

    Usuario usuarioNuevo = new Usuario();
    usuarioNuevo.setRol(rolPersist);
    usuarioNuevo.setNombreDeUsuario(request.queryParams("usuario"));
    this.validador.validarClave(request.queryParams("contrasenia"));
    usuarioNuevo.setContrasenia(request.queryParams("contrasenia"));

    Organizacion nuevaOrganizacion = new Organizacion();

    nuevaOrganizacion.setRazonSocial(request.queryParams("nombre"));

    nuevaOrganizacion.setTipo(TipoOrganizacion.forValue(request.queryParams("tipo-organizacion")));

    ClasificacionOrganizacion clasificacionOrganizacion = repositorioClasificaciones.buscarPorNombre(request.queryParams("clasificacion-organizacion"));
    nuevaOrganizacion.setClasificacionOrg(clasificacionOrganizacion);

    List<Sector> listadoSectores = new ArrayList<>();
    String[] sectoresString = request.queryParams("sectores").split(",");
    List<Sector> sectoresOrganizacion = new ArrayList<>();
    Arrays.stream(sectoresString).forEach(sector->sectoresOrganizacion.add(new Sector(sector.trim().toUpperCase(), nuevaOrganizacion)));
    nuevaOrganizacion.setSectores(sectoresOrganizacion);


    Municipio municipio = (Municipio) repositorioDivisionTerritorial.buscarMunicipio(request.queryParams("municipio"));
    Provincia provincia = (Provincia) repositorioDivisionTerritorial.buscarProvincia(request.queryParams("provincia"));
    Punto ubicacion = new Ubicacion(municipio, provincia, request.queryParams("direccion"), request.queryParams("altura"));
    nuevaOrganizacion.setUbicacionGeografica(ubicacion);
    nuevaOrganizacion.setDivisionTerritorial((DivisionTerritorial) municipio);
    municipio.setOrganizaciones(nuevaOrganizacion);


    repositorioOrganizaciones.guardar(nuevaOrganizacion);

    usuarioNuevo.setOrganizacion(nuevaOrganizacion);
    municipio.setOrganizaciones(nuevaOrganizacion);
    repositorioDivisionTerritorial.modificarMunicipio(municipio);
    repositorioUsuarios.guardar(usuarioNuevo);

    return new ModelAndView(new HashMap<String, Object>(){{}},"org_exito.hbs");
  }


  public ModelAndView mostrarVincular(Request request, Response response) {
    //String razonSocial = request.params("nombreDeUsuario");
    //Organizacion organizacionBuscada = repositorioOrganizaciones.buscarNombre(razonSocial);

    String idBuscado = request.params("id-organizacion");
    Organizacion organizacionBuscada = repositorioOrganizaciones.buscar(new Integer(idBuscado));
    Usuario usuario = repositorioUsuarios.buscarPorOrganizacion(new Integer(idBuscado));

    return new ModelAndView(new HashMap<String, Object>(){{
      put("organizacion", organizacionBuscada);
      put("usuario", usuario);
    }}, "vincular_miembro.hbs");
  }

  public Response aceptarSolicitud(Request request, Response response) {
    String idOrganizacion = request.params("id-organizacion");
    String idSolicitud = request.params("id-solicitud");
    //Organizacion organizacion = repositorioOrganizaciones.buscarNombre(idOrganizacion);
    Organizacion organizacion = repositorioOrganizaciones.buscar(new Integer(idOrganizacion));
    Solicitud solicitudAAceptar = organizacion.getSolicitudes().stream().filter(solicitud -> solicitud.getId().equals(new Integer(idSolicitud))).collect(Collectors.toList()).get(0);
    organizacion.aceptarVinculacion(solicitudAAceptar);

    repositorioOrganizaciones.modificar(organizacion);
    repositorioSolicitud.eliminar(solicitudAAceptar);
    return response;
  }

  public Object rechazarSolicitud(Request request, Response response) {
    String idOrganizacion = request.params("id-organizacion");
    String idSolicitud = request.params("id-solicitud");
    //Organizacion organizacion = repositorioOrganizaciones.buscarNombre(idOrganizacion);
    Organizacion organizacion = repositorioOrganizaciones.buscar(new Integer(idOrganizacion));
    Solicitud solicitudARechazar = organizacion.getSolicitudes().stream().filter(solicitud -> solicitud.getId().equals(new Integer(idSolicitud))).collect(Collectors.toList()).get(0);
    organizacion.rechazarVinculacion(solicitudARechazar);
    repositorioOrganizaciones.modificar(organizacion);
    repositorioSolicitud.eliminar(solicitudARechazar);
    return response;
  }

  public ModelAndView mostrar(Request request, Response response) {
    String nombreUsuario = request.params("nombreDeUsuario");
    String nombreOrganizacion = request.params("razonSocial");
    Usuario usuario = repositorioUsuarios.buscarNombre(nombreUsuario);
    Organizacion organizacion = this.buscarOrganizacion(nombreOrganizacion);

    /* List<Organizacion> organizaciones = this.repositorioOrganizaciones.buscarTodos();
    List<DivisionTerritorial> provincias = this.repositorioDivisionTerritorial.buscarProvincias();
    Usuario usuarioBuscado = this.repositorioUsuarios.buscarNombre(nombreUsuario);
    List<Solicitud> solicitudes = this.repoSolicitud.buscarSolicitudDe(new Integer(usuarioBuscado.getPersona().getId()));

*/
    System.out.println(organizacion.getSectores());
    return new ModelAndView(new HashMap<String, Object>(){{
      put("organizacion", organizacion);
      put("usuario", usuario);
    }},"miembros_org.hbs");
  }

  private Organizacion buscarOrganizacion(String nombreDeusuario){
    Usuario usuario = repositorioUsuarios.buscarNombre(nombreDeusuario);
   // Organizacion organizacion = repositorioOrganizaciones.buscarPorId(usuario)

    return usuario.getOrganizacion();
  }

  public ModelAndView mostrarEditar(Request request, Response response) {
    String nombreUsuario = request.params("nombreDeUsuario");
    Usuario usuarioLogueado = UsuarioHelper.usuarioLogueado(request);
    Usuario usuario = repositorioUsuarios.buscarNombre(nombreUsuario);
    Organizacion organizacion = this.buscarOrganizacion(nombreUsuario);
    List<ClasificacionOrganizacion> todasLasclasificaciones = this.repositorioClasificaciones.buscarTodos();
    List<DivisionTerritorial> provincias = this.repositorioDivisionTerritorial.buscarProvincias();

    return new ModelAndView(new HashMap<String, Object>(){{
      put("usuarioLogueado", usuarioLogueado);
      put("organizacion", organizacion);
      put("usuario", usuario);
      put("provincias", provincias);
      put("clasificaciones", todasLasclasificaciones);
    }},"datos_organizacion.hbs");
  }

  public Response editar(Request request, Response response){
    String nombreUsuario = request.params("nombreDeUsuario");
    Usuario usuarioAModificar = this.repositorioUsuarios.buscarNombre(nombreUsuario);
    Organizacion organizacionAModificar = usuarioAModificar.getOrganizacion();
    Punto puntoAModificar = usuarioAModificar.getOrganizacion().getUbicacionGeografica();
    editarOrganizacion(organizacionAModificar, puntoAModificar, request);

    this.repositorioOrganizaciones.modificar(organizacionAModificar);

    response.redirect("/organizaciones/" + organizacionAModificar.getRazonSocial());
    return response;
  }
  public void editarOrganizacion(Organizacion organizacion, Punto punto, Request request){

    if (request.queryParams("razonSocial") != null){
      organizacion.setRazonSocial(request.queryParams("razonSocial"));
    }

    if (request.queryParams("tipoOrgSel") != null){
      organizacion.setTipo(TipoOrganizacion.valueOf(request.queryParams("tipoOrgSel")));
    }
    if (request.queryParams("clasificacionSel") != null){
      ClasificacionOrganizacion clasificacion = this.repositorioClasificaciones.buscarPorNombre(request.queryParams("clasificacionSel"));
      organizacion.setClasificacionOrg(clasificacion);
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

  public Response editarMartin(Request request, Response response) {
    String nombreUsuario = request.params("nombreDeUsuario");
    Usuario usuario = repositorioUsuarios.buscarNombre(nombreUsuario);
    Organizacion organizacion = this.buscarOrganizacion(nombreUsuario);
    //razon social
    organizacion.setRazonSocial(request.params("nombre"));
    //tipo
    organizacion.setTipo(TipoOrganizacion.forValue(request.queryParams("tipo")));
    //clasificacion
    ClasificacionOrganizacion clasificacionOrganizacion = repositorioClasificaciones.buscarPorNombre("clasificacion");
    organizacion.setClasificacionOrg(clasificacionOrganizacion);
    //direccion
    Municipio municipio = (Municipio) repositorioDivisionTerritorial.buscarMunicipio(request.queryParams("municipio"));
    Provincia provincia = (Provincia) repositorioDivisionTerritorial.buscarProvincia(request.queryParams("provincia"));
    Punto ubicacion = new Ubicacion(municipio, provincia, request.queryParams("direccion"), request.queryParams("altura"));
    organizacion.setUbicacionGeografica(ubicacion);
    //sectores
    /*
    List<Sector> listadoSectores = new ArrayList<>();
    String[] sectoresString = request.queryParams("sectores").split(",");
    List<Sector> sectoresOrganizacion = organizacion.getSectores();
    Arrays.stream(sectoresString).forEach(sector->sectoresOrganizacion.add(new Sector(sector.trim().toUpperCase(), organizacion)));
    organizacion.setSectores(sectoresOrganizacion);
    */
    repositorioOrganizaciones.modificar(organizacion);
    response.redirect("/" + nombreUsuario);

    return response;
  }

  public Response eliminarSector(Request request, Response response) {
    String nombreDeUsuario = request.params("nombreDeUsuario");
    String idSector = request.params("id-sector");
    Organizacion organizacion = buscarOrganizacion(nombreDeUsuario);
    Sector sectorAEliminar = organizacion.getSectores().stream().filter(sector -> sector.getId().equals(new Integer(idSector))).collect(Collectors.toList()).get(0);
    organizacion.eliminarSector(sectorAEliminar);
    repositorioOrganizaciones.modificar(organizacion);
    repositorioSectores.eliminar(sectorAEliminar);
    return response;
  }

  public Response agregarSector(Request request, Response response) {
    String nombreDeUsuario = request.params("nombreDeUsuario");
    String nombreSector = request.params("nombre-sector");
    Organizacion organizacion = buscarOrganizacion(nombreDeUsuario);
    Sector sectorAAgregar = new Sector(nombreSector.toUpperCase().trim(), organizacion);
    List<Sector> sectores = organizacion.getSectores();
    sectores.add(sectorAAgregar);
    organizacion.setSectores(sectores);

    repositorioOrganizaciones.modificar(organizacion);
    return response;
  }

  public ModelAndView mostrarCargarArchivo(Request request, Response response) {
    Usuario usuario = repositorioUsuarios.buscarNombre(request.params("nombreDeUsuario"));
    Organizacion organizacion = usuario.getOrganizacion();
    return new ModelAndView(new HashMap<String, Object>(){{
      put("organizacion", organizacion);
      put("usuario", usuario);
    }}, "registrar_mediciones.hbs");
  }


  public Response cargarArchivo(Request request, Response response) throws IOException, ServletException {

    File uploadDir = new File("upload");
    uploadDir.mkdir(); // create the upload directory if it doesn't exist

    Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".xlsx");

    request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

    try (InputStream input = request.raw().getPart("myFile").getInputStream()) { // getPart needs to use same "name" as input field in form
      Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
    }
    Usuario usuario = repositorioUsuarios.buscarNombre(request.params("nombreDeUsuario"));
    Organizacion organizacion = usuario.getOrganizacion();
    GeneradorMediciones generadorMediciones = new GeneradorMediciones("upload/" + tempFile.getFileName().toString());
    CargarMediciones cargarMediciones = new CargarMediciones(organizacion, generadorMediciones);
    cargarMediciones.start();


    response.redirect("/organizaciones/" + request.params("nombreDeUsuario") + "/mediciones");

    return response;
  }

  public ModelAndView calcularHuella(Request request, Response response) {
    String nombreUsuario = request.params("nombreDeUsuario");
    Usuario usuarioBuscado = this.repositorioUsuarios.buscarNombre(nombreUsuario);
    Organizacion organizacion = usuarioBuscado.getOrganizacion();
    CalculadoraHC calculadoraHC = new CalculadoraHC();

    List<FactorTraslado> factores = this.repoFactoresEmision.buscarTodosTraslado();
    FactorTraslado[] factoresTraslado = new FactorTraslado[factores.size()];
    factoresTraslado = factores.toArray(factoresTraslado);
    calculadoraHC.agregarFactoresTraslado(factoresTraslado);

    List<FactorEmision> factoresE = this.repoFactoresEmision.buscarTodosEmision();
    FactorEmision[] factoresEmision = new FactorEmision[factoresE.size()];
    factoresEmision = factoresE.toArray(factoresEmision);
    calculadoraHC.agregarFactoresEmision(factoresEmision);

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

      huellaValor = calculadoraHC.organizacionHC(organizacion,
          Periodicidad.valueOf(request.queryParams("periodoSel")),
          new Integer(request.queryParams("mesSel")),
          new Integer(request.queryParams("anioSel")));
    }

    DecimalFormat formatoNumero = new DecimalFormat("####.##");

    //this.repositorioSectoresTerritoriales.modificar(organizacion.getDivisionTerritorial().getSectorTerritorial());
    this.repositorioOrganizaciones.modificar(organizacion);
    huellaStr += formatoNumero.format(huellaValor).toString() + " kgEqCO2";
    String finalHuellaStr = huellaStr;
    return new ModelAndView(new HashMap<String, Object>() {{
      put("usuario", usuarioBuscado);
      put("huellaStr", finalHuellaStr);
    }}, "calculadora_hc_org.hbs");
  }


  public ModelAndView huella(Request request, Response response) {
    String nombreUsuario = request.params("nombreDeUsuario");
    Usuario usuarioBuscado = this.repositorioUsuarios.buscarNombre(nombreUsuario);
    return new ModelAndView(new HashMap<String, Object>() {{
      put("usuario", usuarioBuscado);
    }}, "calculadora_hc_org.hbs");
  }
}
