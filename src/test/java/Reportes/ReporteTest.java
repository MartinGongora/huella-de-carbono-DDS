package Reportes;

public class ReporteTest {/*
  Organizacion utnOrg, barraca;
  Punto utnDireccion, pacoCasa, betoCasa, silviaCasa;
  Ubicacion estCongresoUbicacion, estLacrozeUbicacion;
  Sector unSector, otroSector;
  ClasificacionOrganizacion unaClase, otraClase;
  GeneradorMediciones mediciones;
  CalculadoraHC calculadora;
  Persona paco, beto, silvia;
  APulmon aPie;
  Tramo tramo1, tramo2, tramo3, tramo4, tramo5;
  Trayecto trayecto1, trayecto2;
  VehiculoParticular autoCompartido;
  TransportePublico subteD;
  Estacion estCongreso, estLacroze;
  ServicioDistancia servicioDistancia;
  ServicioDistanciaAdapter adapterMock;
  FactorEmision gas_natural, //1.95
      diesel_gasoil, //2.77
      kerosene, //2.64
      fuel_oil, //2.81
      nafta, //2.37
      carbon, //2.45
      carbon_lena, //2.23
      lena, //1.84
      combustible_consumido_gasoil, //2.77
      combustible_consumido_gnc, //1.86
      combustible_consumido_nafta, //2.37
      electricidad, //0.486
      logistica; // 0.062
  FactorTraslado sangre, electrico, gasoil, gnc, naftaT;
  AgenteSectorial unAgente;
  SectorTerritorial sectorTerritorialCaba, sectorBuenosAires, sectorLaPampa;

  Provincia buenosAires,laPampa;
  Municipio caba,laMatanza,otras;

  GeneradorDeReportes reportero;



  @Before
  public void init() throws IOException {

    // Distancia
    //servicioDistancia = ServicioDistancia.getInstancia();
    //servicioDistancia.setAdapter(new AdapterServicio());
    adapterMock = mock(ServicioDistanciaAdapter.class);
    servicioDistancia = ServicioDistancia.getInstancia();
    servicioDistancia.setAdapter(this.adapterMock);

    //Organizacion
    utnOrg = new Organizacion("UTN");
    utnDireccion = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"), "Mozart", "1412");
    utnOrg.setTipo(TipoOrganizacion.INSTITUCION);
    utnOrg.setUbicacionGeografica(utnDireccion);
    unSector = new Sector("RRHH", utnOrg);
    otroSector = new Sector("ADMIN", utnOrg);
    unaClase = new ClasificacionOrganizacion("Universidad");
    utnOrg.setClasificacionOrg(unaClase);
    utnOrg.agregarSector(new Sector[]{unSector, otroSector});
    mediciones = new GeneradorMediciones("UTNDatos.xlsx");
    mediciones.procesarExcel(utnOrg);

    barraca = new Organizacion("Barraca");
    otraClase = new ClasificacionOrganizacion("PostProductora");
    barraca.setClasificacionOrg(otraClase);

    sectorTerritorialCaba = new SectorTerritorial("Ciudad de Buenos Aires");
    sectorBuenosAires = new SectorTerritorial("BSAS");
    sectorLaPampa = new SectorTerritorial("LA PAMPA");


    buenosAires = new Provincia("bsas");
    laPampa = new Provincia("lapampa");
    caba = new Municipio("caba",1);
    laMatanza = new Municipio("mata", 1);
    otras = new Municipio("teto", 1);


    buenosAires.setMunicipios(caba,laMatanza);
    laPampa.setMunicipios(otras);

    laMatanza.setOrganizaciones(utnOrg, barraca);
    sectorTerritorialCaba.agregarDivisionTerritorial(buenosAires,laPampa);
    sectorBuenosAires.agregarDivisionTerritorial(buenosAires);
    sectorLaPampa.agregarDivisionTerritorial(laPampa);

    unAgente = new AgenteSectorial("Juan", sectorTerritorialCaba);

    // Calculadora
    calculadora = new CalculadoraHC();
    gas_natural = new FactorEmision(Tipo.GAS_NATURAL, 1.95);
    diesel_gasoil = new FactorEmision(Tipo.DIESEL_GASOIL, 2.77);
    kerosene =  new FactorEmision(Tipo.KEROSENE, 2.64);//2.64
    fuel_oil = new FactorEmision(Tipo.FUEL_OIL, 2.81); //2.81
    nafta = new FactorEmision(Tipo.NAFTA, 2.37); //2.37
    carbon = new FactorEmision(Tipo.CARBON, 2.45); //2.45
    carbon_lena = new FactorEmision(Tipo.CARBON_DE_LENA, 2.23); //2.23
    lena = new FactorEmision(Tipo.LENA, 1.84); //1.84
    combustible_consumido_gasoil = new FactorEmision(Tipo.COMBUSTIBLE_CONSUMIDO_GASOIL, 2.77); //2.77
    combustible_consumido_gnc = new FactorEmision(Tipo.COMBUSTIBLE_CONSUMIDO_GNC, 1.86); //1.86
    combustible_consumido_nafta = new FactorEmision(Tipo.COMBUSTIBLE_CONSUMIDO_NAFTA, 2.37); //2.37
    electricidad = new FactorEmision(Tipo.ELECTRICIDAD, 0.5); //0.486
    logistica = new FactorEmision(Tipo.LOGISTICA, 0.062);

    calculadora.agregarFactores(gas_natural, diesel_gasoil, kerosene, fuel_oil, nafta,carbon,carbon_lena, lena, combustible_consumido_gasoil, combustible_consumido_gnc,
        combustible_consumido_nafta, electricidad, logistica);
    //calculadora.agregarFactoresTraslado(new Double[]{0.0, 0.5, 2.77, 1.86, 2.37});
    sangre = new FactorTraslado(TipoCombustible.SANGRE, 0.0);
    //electrico, gasoil gnc, nafta
    electrico = new FactorTraslado(TipoCombustible.ELECTRICO, 0.5);
    gasoil = new FactorTraslado(TipoCombustible.GASOIL, 2.77);
    gnc = new FactorTraslado(TipoCombustible.GNC, 1.86);
    naftaT = new FactorTraslado(TipoCombustible.NAFTA, 2.37);
    calculadora.agregarFactoresTraslado(sangre, electrico, gasoil, gnc, naftaT);
    calculadora.setConstante(2.00);

    reportero = new GeneradorDeReportes();

    //Persona 1
    pacoCasa = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"),"Quesada", "876");
    paco = new Persona("Paco","Paco", TipoDocumento.DNI, "12345567", pacoCasa);
    paco.solicitarVinculacion(utnOrg, unSector);

    // Persona 2
    betoCasa = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"),"Cabildo", "825");
    beto = new Persona("Beto","Perro", TipoDocumento.DNI, "12345567", betoCasa);
    beto.solicitarVinculacion(utnOrg, otroSector);

    // Persona 3
    silviaCasa = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"),"Rivadavia", "458");
    silvia = new Persona("Silvia","Ne", TipoDocumento.DNI, "45123456", silviaCasa);
    silvia.solicitarVinculacion(utnOrg, otroSector);

    utnOrg.confirmarEmpleados();

    // Subte
    subteD = new TransportePublico("D", TipoTransportePublico.SUBTE);
    subteD.setTipoCombustible(TipoCombustible.ELECTRICO);
    subteD.setConsumoXKm(15.4/100);
    estCongresoUbicacion = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"),"Av. Cabildo", "2900");
    estCongreso = new Estacion("Congreso", estCongresoUbicacion, null, null, 2.9);
    estLacrozeUbicacion = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"),"Av. Cabildo", "800");
    estLacroze = new Estacion("Lacroze", estLacrozeUbicacion, null, null, .8);
    subteD.agregarEstacion(estCongreso);
    subteD.agregarEstacion(estLacroze);

    // Trayectos
    autoCompartido = new VehiculoParticular(TipoVehiculo.AUTO);
    autoCompartido.setConsumoXKm(6.2/100);
    autoCompartido.setServicio(servicioDistancia);
    autoCompartido.setTipoCombustible(TipoCombustible.NAFTA);
    aPie = new APulmon("A pata");
    aPie.setServicio(servicioDistancia);
    trayecto1 = new Trayecto();
    paco.agregarTrayecto(trayecto1);
    tramo1 = new Tramo(pacoCasa, estCongreso, aPie, paco);
    tramo2 = new Tramo(estCongreso, estLacroze, subteD, paco);
    tramo3 = new Tramo(estLacroze, silviaCasa, autoCompartido, paco);
    tramo3.compartirViaje(utnOrg,beto);
    tramo4 = new Tramo(silviaCasa, utnOrg.getUbicacionGeografica(), autoCompartido, paco);
    tramo4.compartirViaje(utnOrg,new Persona[]{beto, silvia});

    trayecto1.agregarTramo(new Tramo[]{tramo1, tramo2, tramo3,tramo4});
    beto.confirmarViaje();
    silvia.confirmarViaje();

    trayecto2 = new Trayecto();
    tramo5 = new Tramo(utnDireccion, estCongreso, autoCompartido, silvia);
    trayecto2.agregarTramo(tramo5);
    silvia.agregarTrayecto(trayecto2);


  }

  @Test
  public void reporteSectorTerritorialCaba() throws IOException{
    double distancia = this.distanciaMock();
    DistanciaEntreDirecciones distanciaMock = mock(DistanciaEntreDirecciones.class);

    when(distanciaMock.getValor()).thenReturn(distancia);
    when(this.adapterMock.calcularDistancia(pacoCasa,estCongreso)).thenReturn(distanciaMock);
    when(this.adapterMock.calcularDistancia(estLacroze,silviaCasa)).thenReturn(distanciaMock);
    when(this.adapterMock.calcularDistancia(silviaCasa,utnDireccion)).thenReturn(distanciaMock);
    when(this.adapterMock.calcularDistancia(utnDireccion,estCongreso)).thenReturn(distanciaMock);

    paco.distanciaTotalDeTrayectos();
    beto.distanciaTotalDeTrayectos();
    silvia.distanciaTotalDeTrayectos();

    Double huella21 = calculadora.sectorTerritorialHC(sectorTerritorialCaba, Periodicidad.ANUAL, 5, 2021);
   // System.out.println(huella21);
    Double huella22 = calculadora.sectorTerritorialHC(sectorTerritorialCaba, Periodicidad.ANUAL, 5, 2022);
  //  System.out.println(huella22);

    List<SectorTerritorial> sectores = new ArrayList<>();
    sectores.add(sectorTerritorialCaba);

    //Reporte por sector
//    ReportePorSector reportePorSector = reportero.reporteSectorTerritorial(sectores, 2022);
  //  Assert.assertEquals(1, reportePorSector.getHcPorSector().size());
   // Assert.assertEquals(huella22, reportePorSector.getHcPorSector().get(sectorTerritorialCaba));

    //Reporte por tipo
    /*
    List<ClasificacionOrganizacion> tipos = new ArrayList<>();
    tipos.add(unaClase);
    tipos.add(otraClase);
    ReportePorTipo reportePorTipo = reportero.reportePorTipo(sectores, 2022, tipos);
    Assert.assertEquals(2, reportePorTipo.getHcPorTipoOrganizacion().size());
    Assert.assertEquals(huella22, reportePorTipo.getHcPorTipoOrganizacion().get(unaClase));
*/
    //Reporte sector por division NO ENTIENDO COMO FUNCIONA ESTE TEST
    /*
    ReporteSectorPorDivision reporteSectorPorDivision = reportero.reporteSectorPorDivision(sectorTerritorialCaba, 2022);
    System.out.println(reporteSectorPorDivision.getHcPorDivision());
    Assert.assertEquals(2, reporteSectorPorDivision.getHcPorDivision().size());
    Assert.assertEquals(huella22, reportePorSector.getHcPorSector().get(sectorTerritorialCaba));  //aca deberia aceptar buenosAires pero rompe
    */

    //Reporte pais por provincia
    /*List<Provincia> provincias = new ArrayList<>();
    provincias.add(buenosAires);
    provincias.add(laPampa);
    ReportePaisPorProvincia reportePaisPorProvincia = reportero.reportePaisPorProvincia(provincias, 2022);
    Assert.assertEquals(2, reportePaisPorProvincia.getHcPorProvincia().size());
    Assert.assertEquals(huella22, reportePaisPorProvincia.getHcPorProvincia().get(buenosAires));
*/
    //Reporte organizacion
    //
    /*
    ReporteOrganizacion reporteOrganizacion = reportero.reporteOrganizacion(utnOrg, 2022);
   // System.out.println(reporteOrganizacion.getHcPorSector());
    Assert.assertEquals(2, reporteOrganizacion.getHcPorSector().size());
    Assert.assertEquals(utnOrg, reporteOrganizacion.getOrganizacion());
    //falta chequear un valor

    //Reporte evolucion organizacion
    ReporteEvolucionOrganizacion reporteEvolucionOrganizacion = reportero.reporteEvolucionOrganizacion(utnOrg);
    Assert.assertEquals(2, reporteEvolucionOrganizacion.getHcPorAnio().size());
    Assert.assertEquals(utnOrg, reporteEvolucionOrganizacion.getOrganizacion());
    Assert.assertEquals(huella22, reporteEvolucionOrganizacion.getHcPorAnio().get(2022));
    Assert.assertEquals(huella21, reporteEvolucionOrganizacion.getHcPorAnio().get(2021));

    //Reporte evolucion sector territorial
    //ReporteEvolucionSectorTerritorial reporteEvolucionSectorTerritorial = reportero.reporteEvolucionSectorTerritorial(sectorTerritorialCaba);
    Assert.assertEquals(2, reporteEvolucionSectorTerritorial.getHcPorAnio().size());
 //   Assert.assertEquals(sectorTerritorialCaba, reporteEvolucionSectorTerritorial.getHcPorAnio());
    Assert.assertEquals(huella22, reporteEvolucionSectorTerritorial.getHcPorAnio().get(2022));
    Assert.assertEquals(huella21, reporteEvolucionSectorTerritorial.getHcPorAnio().get(2021));
  }

  private double distanciaMock(){
    double valor = 1.3;
    return valor;
  }*/
}
