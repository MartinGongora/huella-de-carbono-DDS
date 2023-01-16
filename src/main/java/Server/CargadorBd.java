package Server;

import db.EntityManagerHelper;
import domain.model.AgentesSectoriales.AgenteSectorial;
import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.AgentesSectoriales.SectorTerritorial;
import domain.model.Entidades.ClasificacionOrganizacion;
import domain.model.Entidades.Mediciones.FactorEmision;
import domain.model.Entidades.Mediciones.FactorTraslado;
import domain.model.Entidades.Mediciones.Tipo;
import domain.model.Entidades.Persona;
import domain.model.Entidades.TipoDocumento;
import domain.model.GeoReferencia.Punto;
import domain.model.GeoReferencia.Ubicacion;
import domain.model.Medios.TransportePublico.Estacion;
import domain.model.Medios.TransportePublico.TipoTransportePublico;
import domain.model.Medios.TransportePublico.TransportePublico;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoCombustible;
import domain.model.usuarios.Permiso;
import domain.model.usuarios.Rol;
import domain.model.usuarios.Usuario;
import domain.repositories.*;

public class CargadorBd {
  public static void main(String[] args) {
    new CargadorBd().run();
  }

  public void run() {
    RepositorioDivisionTerritorial repositorioDivisionTerritorial = new RepositorioDivisionTerritorial();
    RepositorioClasificaciones repositorioClasificaciones = new RepositorioClasificaciones();
    RepositorioRoles repositorioRoles = new RepositorioRoles();
    RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
    RepositorioPersonas repositorioPersonas = new RepositorioPersonas();
    RepositorioMedioTransporte repositorioMedioTransporte = new RepositorioMedioTransporte();
    RepositorioFactoresEmision repositorioFactoresEmision = new RepositorioFactoresEmision();
    RepositoriosAgentes repoAgentes = new RepositoriosAgentes();

    try {
      repositorioDivisionTerritorial.buscarProvincia("Buenos Aires");
    }
    catch (Exception ex) {

      System.out.println(ex.getMessage());

      //Provincias y Municipios
      Provincia buenosAires = new Provincia("Buenos Aires");
      Provincia CABA = new Provincia("Ciudad Autonoma de Buenos Aires");
      Provincia catamarca = new Provincia("Catamarca");
      Provincia chaco = new Provincia("Chaco");
      Provincia chubut = new Provincia("Chubut");
      Provincia cordoba = new Provincia("Cordoba");
      Provincia corrientes = new Provincia("Corrientes");
      Provincia entreRios = new Provincia("Entre Rios");
      Provincia formosa = new Provincia("Formosa");
      Provincia jujuy = new Provincia("Jujuy");
      Provincia laPampa = new Provincia("La Pampa");
      Provincia laRioja = new Provincia("La Rioja");
      Provincia mendoza = new Provincia("Mendoza");
      Provincia misiones = new Provincia("Misiones");
      Provincia neuquen = new Provincia("Neuquen");
      Provincia rioNegro = new Provincia("Rio Negro");
      Provincia salta = new Provincia("Salta");
      Provincia sanJuan = new Provincia("San Juan");
      Provincia sanLuis = new Provincia("San Luis");
      Provincia santaCruz = new Provincia("Santa Cruz");
      Provincia santaFe = new Provincia("Santa Fe");
      Provincia santiagoDelEstero = new Provincia("Santiago del Estero");
      Provincia tierraDelFuego = new Provincia("Tierra del Fuego");
      Provincia tucuman = new Provincia("Tucuman");

      SectorTerritorial sectorBsAs = new SectorTerritorial("Provincia Buenos Aires");
      buenosAires.setSectorTerritorial(sectorBsAs);

      repositorioDivisionTerritorial.agregarProvincias(new Provincia[]{buenosAires,CABA,catamarca, chaco, chubut, cordoba, corrientes, entreRios,
              formosa,jujuy,laPampa,laRioja,mendoza,misiones, neuquen,rioNegro,salta,sanJuan,
              sanLuis,santaCruz,santaFe,santiagoDelEstero,tierraDelFuego,tucuman});

      AgenteSectorial agenteBsAs = new AgenteSectorial("Provincia Buenos Aires", sectorBsAs);
      sectorBsAs.setAgenteSectorial(agenteBsAs);
      repoAgentes.guardar(agenteBsAs);

      Municipio moron = new Municipio("Moron", 1);
      moron.setProvincia(buenosAires);
      Municipio moreno = new Municipio("Moreno", 2);
      moreno.setProvincia(buenosAires);
      Municipio lujan = new Municipio("Lujan", 3);
      lujan.setProvincia(buenosAires);
      buenosAires.setMunicipios(moron, moreno, lujan);

      Municipio belgrano = new Municipio("Belgrano", 1);
      belgrano.setProvincia(CABA);
      Municipio agronomia = new Municipio("Agronomia", 2);
      agronomia.setProvincia(CABA);
      Municipio chacarita = new Municipio("Chacarita", 3);
      chacarita.setProvincia(CABA);
      CABA.setMunicipios(belgrano, agronomia, chacarita);

      Municipio sanfer = new Municipio("San Fernando", 1);
      sanfer.setProvincia(catamarca);
      catamarca.setMunicipios(sanfer);

      Municipio resistencia = new Municipio("Resistencia", 1);
      resistencia.setProvincia(chaco);
      chaco.setMunicipios(resistencia);

      Municipio rawson = new Municipio("Rawson", 1);
      rawson.setProvincia(chubut);
      chubut.setMunicipios(rawson);

      Municipio cordobaMuni = new Municipio("Cordoba", 1);
      cordobaMuni.setProvincia(cordoba);
      cordoba.setMunicipios(cordobaMuni);

      Municipio corrientesMuni = new Municipio("Corrientes", 1);
      corrientesMuni.setProvincia(corrientes);
      corrientes.setMunicipios(corrientesMuni);

      Municipio parana = new Municipio("Parana", 1);
      parana.setProvincia(entreRios);
      entreRios.setMunicipios(parana);

      Municipio formosaMuni = new Municipio("Formosa", 1);
      formosaMuni.setProvincia(formosa);
      formosa.setMunicipios(formosaMuni);

      Municipio sanSalvador = new Municipio("San Salvador", 1);
      sanSalvador.setProvincia(jujuy);
      jujuy.setMunicipios(sanSalvador);

      Municipio santaRosa = new Municipio("Santa Rosa", 1);
      santaRosa.setProvincia(laPampa);
      laPampa.setMunicipios(santaRosa);

      Municipio laRiojaMuni = new Municipio("La Rioja", 1);
      laRiojaMuni.setProvincia(laRioja);
      laRioja.setMunicipios(laRiojaMuni);

      Municipio mendozaMuni = new Municipio("Mendoza", 1);
      mendozaMuni.setProvincia(mendoza);
      mendoza.setMunicipios(mendozaMuni);

      Municipio posadas = new Municipio("Posadas", 1);
      posadas.setProvincia(misiones);
      misiones.setMunicipios(posadas);

      Municipio neuquenMuni = new Municipio("Neuquen", 1);
      neuquenMuni.setProvincia(neuquen);
      neuquen.setMunicipios(neuquenMuni);

      Municipio viedma = new Municipio("Viedma", 1);
      viedma.setProvincia(rioNegro);
      rioNegro.setMunicipios(viedma);

      Municipio saltaMuni = new Municipio("Salta", 1);
      saltaMuni.setProvincia(salta);
      salta.setMunicipios(saltaMuni);

      Municipio sanJuanMuni = new Municipio("San Juan", 1);
      sanJuanMuni.setProvincia(sanJuan);
      sanJuan.setMunicipios(sanJuanMuni);

      Municipio sanLuisMuni = new Municipio("San Luis", 1);
      sanLuisMuni.setProvincia(sanLuis);
      sanLuis.setMunicipios(sanLuisMuni);

      Municipio rioGallegos = new Municipio("Rio Gallegos", 1);
      rioGallegos.setProvincia(santaCruz);
      santaCruz.setMunicipios(rioGallegos);

      Municipio staFE = new Municipio("Santa Fe", 1);
      staFE.setProvincia(santaFe);
      santaFe.setMunicipios(staFE);

      Municipio santiago = new Municipio("Santiago", 1);
      santiago.setProvincia(santiagoDelEstero);
      santiagoDelEstero.setMunicipios(santiago);

      Municipio ushuaia = new Municipio("Ushuaia", 1);
      ushuaia.setProvincia(tierraDelFuego);
      tierraDelFuego.setMunicipios(ushuaia);

      Municipio sanMaikol = new Municipio("San Maikol", 1);
      sanMaikol.setProvincia(tucuman);
      tucuman.setMunicipios(sanMaikol);



      repositorioDivisionTerritorial.modificarProvincias(new Provincia[]{buenosAires,CABA,catamarca, chaco, chubut, cordoba, corrientes, entreRios,
              formosa,jujuy,laPampa,laRioja,mendoza,misiones, neuquen,rioNegro,salta,sanJuan,
              sanLuis,santaCruz,santaFe,santiagoDelEstero,tierraDelFuego,tucuman});

      SectorTerritorial sectorMoron = new SectorTerritorial("Municipio Moron");
      moron.setSectorTerritorial(sectorMoron);

      AgenteSectorial agenteMoron = new AgenteSectorial("Municipio Moron", sectorMoron);
      sectorMoron.setAgenteSectorial(agenteMoron);
      //repoAgentes.guardar(agenteMoron);

      //clasificaciones
      ClasificacionOrganizacion universidad = new ClasificacionOrganizacion("Universidad");
      ClasificacionOrganizacion transporte = new ClasificacionOrganizacion("Transporte");
      repositorioClasificaciones.guardarClasificaciones(new ClasificacionOrganizacion[]{universidad,transporte});


      //roles
      Rol admin = new Rol();
      admin.setNombre("Admin");
      admin.setId(1);
      admin.agregarPermiso(Permiso.CREAR_ORGANIZACION);
      admin.agregarPermiso(Permiso.EDITAR_ORGANIZACION);
      admin.agregarPermiso(Permiso.VER_ORGANIZACION);
      admin.agregarPermiso(Permiso.ELIMIAR_ORGANIZACION);
      admin.agregarPermiso(Permiso.CREAR_MIEMBRO);
      admin.agregarPermiso(Permiso.EDITAR_MIEMBRO);
      admin.agregarPermiso(Permiso.VER_MIEMBRO);
      admin.agregarPermiso(Permiso.ELIMIAR_MIEMBRO);
      admin.agregarPermiso(Permiso.VER_AGENTE);
      admin.agregarPermiso(Permiso.EDITAR_AGENTE);
      Rol miembro = new Rol();
      miembro.setId(2);
      miembro.setNombre("Miembro");
      miembro.agregarPermiso(Permiso.CREAR_MIEMBRO);
      miembro.agregarPermiso(Permiso.EDITAR_MIEMBRO);
      miembro.agregarPermiso(Permiso.VER_MIEMBRO);
      Rol organizacion = new Rol();
      organizacion.setId(3);
      organizacion.agregarPermiso(Permiso.CREAR_ORGANIZACION);
      organizacion.agregarPermiso(Permiso.EDITAR_ORGANIZACION);
      organizacion.agregarPermiso(Permiso.VER_ORGANIZACION);
      organizacion.setNombre("Organizacion");
      Rol agente = new Rol();
      agente.setId(4);
      agente.setNombre("Agente");
      agente.agregarPermiso(Permiso.EDITAR_AGENTE);
      agente.agregarPermiso(Permiso.VER_AGENTE);

      repositorioRoles.agregarRoles(new Rol[]{admin, miembro, organizacion, agente});

      //ubicacion
      Municipio haedo = new Municipio("Haedo",4);
      haedo.setProvincia(buenosAires);
      buenosAires.setMunicipios(haedo);

      Ubicacion ubicacion = new Ubicacion(haedo, buenosAires, "Cabildo", "2345");
      //repositorioDivisionTerritorial.agregar(ubicacion);

      //personaAdmin
      Persona personaAdmin = new Persona("Antico", "Mariano", TipoDocumento.DNI, "29123343", ubicacion);
      //repositorioPersonas.agregar(personaAdmin);

      //admin
      Usuario adminMantico = new Usuario();
      adminMantico.setPersona(personaAdmin);
      adminMantico.setNombreDeUsuario("mantico");
      adminMantico.setContrasenia("Manti1212");
      adminMantico.setNombre("Mariano");
      adminMantico.setApellido("Antico");
      adminMantico.setRol(repositorioRoles.buscar(1));

      repositorioUsuarios.guardar(adminMantico);

      //Medios de transporte
      TransportePublico subteA = new TransportePublico("A", TipoTransportePublico.SUBTE, "");
      subteA.setConsumoXKm(0.54);
      subteA.setTipoCombustible(TipoCombustible.ELECTRICO);
      TransportePublico linea15 = new TransportePublico("15", TipoTransportePublico.COLECTIVO, "Pacheco");
      linea15.setConsumoXKm(0.082);
      linea15.setTipoCombustible(TipoCombustible.NAFTA);

      repositorioMedioTransporte.agregar(subteA);
      repositorioMedioTransporte.agregar(linea15);

      //ubicaciones
      Municipio balbanera = new Municipio("Balbanera",4);
      balbanera.setProvincia(CABA);
      CABA.setMunicipios(balbanera);
      Ubicacion ubicacion1 = new Ubicacion(balbanera, CABA, "Libertador", "100");
      Ubicacion ubicacion2 = new Ubicacion(balbanera, CABA, "San Martin", "345");
      Ubicacion ubicacion3 = new Ubicacion(balbanera, CABA, "Lavalle", "4545");
      Ubicacion ubicacion4 = new Ubicacion(balbanera, CABA, "Diagonal Norte", "450");
      Ubicacion ubicacion5 = new Ubicacion(balbanera, CABA, "Avenida de Mayo", "1200");
      Ubicacion ubicacion6 = new Ubicacion(balbanera, CABA, "Cabildo", "2500");
      Ubicacion ubicacion7 = new Ubicacion(balbanera, CABA, "Cabildo", "3456");
      Ubicacion ubicacion8 = new Ubicacion(balbanera, CABA, "Cabildo", "2345");
      Ubicacion ubicacion9 = new Ubicacion(balbanera, CABA, "Cabildo", "1234");
      repositorioDivisionTerritorial.agregarPuntos(new Punto[]{ubicacion1, ubicacion2, ubicacion3, ubicacion4,
                                                    ubicacion5, ubicacion6, ubicacion7, ubicacion8, ubicacion9});

      //estaciones
      Estacion retiro = new Estacion("Retiro", ubicacion1, null, null, 0.0);
      retiro.setLineaDeTransporte(subteA);
      Estacion sanMartin = new Estacion("San Martin", ubicacion2, null, null, 400.0);
      sanMartin.setLineaDeTransporte(subteA);
      Estacion lavalle = new Estacion("Lavalle", ubicacion3, null, null, 800.0);
      lavalle.setLineaDeTransporte(subteA);
      Estacion diagonalNorte = new Estacion("Diagonal Norte", ubicacion4, null, null, 1200.0);
      diagonalNorte.setLineaDeTransporte(subteA);
      Estacion avenidaDeMayo = new Estacion("Avenida De Mayo", ubicacion5, null, null, 1500.0);
      avenidaDeMayo.setLineaDeTransporte(subteA);
      Estacion moreno2 = new Estacion("Moreno", ubicacion6, null, null, 1900.0);
      moreno2.setLineaDeTransporte(subteA);
      Estacion independencia = new Estacion("Independencia", ubicacion7, null, null, 2300.0);
      independencia.setLineaDeTransporte(subteA);
      Estacion sanjuan = new Estacion("San Juan", ubicacion8, null, null, 2800.0);
      sanjuan.setLineaDeTransporte(subteA);
      Estacion constitucion = new Estacion("Constitucion", ubicacion9, null, null, 3200.0);
      constitucion.setLineaDeTransporte(subteA);

      retiro.setEstacionSiguiente(sanMartin);
      sanMartin.setEstacionSiguiente(lavalle);
      lavalle.setEstacionSiguiente(diagonalNorte);
      diagonalNorte.setEstacionSiguiente(avenidaDeMayo);
      avenidaDeMayo.setEstacionSiguiente(moreno2);
      moreno2.setEstacionSiguiente(independencia);
      independencia.setEstacionSiguiente(sanjuan);
      sanjuan.setEstacionSiguiente(constitucion);

      sanMartin.setEstacionAnterior(retiro);
      lavalle.setEstacionAnterior(sanMartin);
      diagonalNorte.setEstacionAnterior(lavalle);
      avenidaDeMayo.setEstacionAnterior(diagonalNorte);
      moreno2.setEstacionAnterior(avenidaDeMayo);
      independencia.setEstacionAnterior(moreno2);
      sanjuan.setEstacionAnterior(independencia);
      constitucion.setEstacionAnterior(sanjuan);

      repositorioDivisionTerritorial.agregarPuntos(new Punto[]{retiro, sanMartin, lavalle, diagonalNorte, avenidaDeMayo,
              moreno2, independencia, sanjuan, constitucion});
      //repositorioDivisionTerritorial.updateTodos(retiro, sanMartin, lavalle, diagonalNorte, avenidaDeMayo,
      //    moreno2, independencia, sanjuan, constitucion);

      //Factores Emision
      FactorEmision gasNatural = new FactorEmision(Tipo.GAS_NATURAL, 1.95);
      FactorEmision dieselGasoil = new FactorEmision(Tipo.DIESEL_GASOIL, 2.77);
      FactorEmision kerosene = new FactorEmision(Tipo.KEROSENE, 2.64);
      FactorEmision fuelOil = new FactorEmision(Tipo.FUEL_OIL, 2.81);
      FactorEmision nafta = new FactorEmision(Tipo.NAFTA, 2.37);
      FactorEmision carbon = new FactorEmision(Tipo.CARBON, 2.45);
      FactorEmision carbonLenia = new FactorEmision(Tipo.CARBON_DE_LENA, 2.23);
      FactorEmision lenia = new FactorEmision(Tipo.LENA, 1.84);
      FactorEmision combustibleGasoil = new FactorEmision(Tipo.COMBUSTIBLE_CONSUMIDO_GASOIL, 2.77);
      FactorEmision combustibleGNC = new FactorEmision(Tipo.COMBUSTIBLE_CONSUMIDO_GNC, 1.86);
      FactorEmision combustibleNafta = new FactorEmision(Tipo.COMBUSTIBLE_CONSUMIDO_NAFTA, 2.37);
      FactorEmision electricidad = new FactorEmision(Tipo.ELECTRICIDAD, 0.5);
      FactorEmision logistica = new FactorEmision(Tipo.LOGISTICA, 0.062);

      FactorTraslado sangre = new FactorTraslado(TipoCombustible.SANGRE, 0.0);
      FactorTraslado electrico = new FactorTraslado(TipoCombustible.ELECTRICO, 0.5);
      FactorTraslado gasoil = new FactorTraslado(TipoCombustible.GASOIL, 2.77);
      FactorTraslado gnc = new FactorTraslado(TipoCombustible.GNC, 1.86);
      FactorTraslado naftaFT = new FactorTraslado(TipoCombustible.NAFTA, 2.37);

      repositorioFactoresEmision.agregarFactoresEmision(new FactorEmision[]{gasNatural, dieselGasoil, kerosene, fuelOil, nafta, carbon, carbonLenia,
          lenia, combustibleGasoil, combustibleGNC, combustibleNafta, electricidad, logistica});

      repositorioFactoresEmision.agregarFactoresTraslado(new FactorTraslado[]{sangre, electrico, gasoil, gnc, naftaFT});

      //Persona agente y agente
      Municipio merlo = new Municipio("Merlo",5);
      merlo.setProvincia(buenosAires);
      buenosAires.setMunicipios(merlo);
      Ubicacion ubicacionAgente = new Ubicacion(merlo, buenosAires, "Moncho", "345");
      //repositorioDivisionTerritorial.agregar(ubicacionAgente);

      //Persona personaAgente = new Persona("Marlboro","Man", TipoDocumento.DNI, "344556", ubicacionAgente);
      //personaAgente.setTelefono("455656");
      //personaAgente.setMail("mm@asas");
      //repositorioPersonas.agregar(personaAgente);

      Usuario usuarioAgente = new Usuario();
      usuarioAgente.setAgenteSectorial(agenteBsAs);
      //usuarioAgente.setPersona(personaAgente);
      usuarioAgente.setNombreDeUsuario("AgenteBSAS");
      usuarioAgente.setContrasenia("Agente123");
      usuarioAgente.setNombre("Agente");
      usuarioAgente.setApellido("Buenos Aires");
      usuarioAgente.setRol(repositorioRoles.buscar(4));

      repositorioUsuarios.guardar(usuarioAgente);

      Usuario usuarioAgente2 = new Usuario();
      usuarioAgente2.setAgenteSectorial(agenteMoron);
      //usuarioAgente.setPersona(personaAgente);
      usuarioAgente2.setNombreDeUsuario("AgenteMoron");
      usuarioAgente2.setContrasenia("Agente123");
      usuarioAgente2.setNombre("Agente");
      usuarioAgente2.setApellido("Moron");
      usuarioAgente2.setRol(repositorioRoles.buscar(4));

      repositorioUsuarios.guardar(usuarioAgente2);

      EntityManagerHelper.closeEntityManager();
    }
  }

/*
  public void insertProvincia(Provincia provincia){
    EntityManager em = EntityManagerHelper.getEntityManager();
    em.getTransaction().begin();
    Query query = em.createNativeQuery("INSERT INTO division_territorial (division, nombre) VALUES(:division,:nombre)");
    query.setParameter("name", name);
    query.setParameter("email", email);
    query.executeUpdate();
    em.getTransaction().commit();
  }*/
}
