package Persistencia;

public class PersistenciaTest {/*
    Organizacion utnOrg;
    Punto utnDireccion, pacoCasa, betoCasa, silviaCasa;
    Ubicacion estCongresoUbicacion, estLacrozeUbicacion;
    Sector unSector, otroSector;
    ClasificacionOrganizacion unaClase;
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
    SectorTerritorial sectorTerritorialCaba;

    Provincia buenosAires,laPampa;
    Municipio caba,laMatanza,otras;

    @Before
    public void init() throws IOException {

        // Distancia
        //servicioDistancia = ServicioDistancia.getInstancia();
        //servicioDistancia.setAdapter(new AdapterServicio());
        adapterMock = mock(ServicioDistanciaAdapter.class);
        servicioDistancia = ServicioDistancia.getInstancia();
        servicioDistancia.setAdapter(this.adapterMock);

        buenosAires = new Provincia("Buenos Aires");

        //Organizacion
        utnOrg = new Organizacion("UTN");
        utnDireccion = new Ubicacion(new Municipio("Soldati", 1),buenosAires, "Mozart", "1412");
        utnOrg.setTipo(TipoOrganizacion.INSTITUCION);
        utnOrg.setUbicacionGeografica(utnDireccion);
        unSector = new Sector("RRHH", utnOrg);
        otroSector = new Sector("ADMIN", utnOrg);
        unaClase = new ClasificacionOrganizacion("Universidad");
        utnOrg.setClasificacionOrg(unaClase);
        utnOrg.agregarSector(new Sector[]{unSector, otroSector});
        mediciones = new GeneradorMediciones("UTNDatos.xlsx");
        mediciones.procesarExcel(utnOrg);
        sectorTerritorialCaba = new SectorTerritorial("Argentina");


        laPampa = new Provincia("La Pampa");
        caba = new Municipio("caba",1);
        laMatanza = new Municipio("matanza",1);
        otras = new Municipio("perro",1);

        buenosAires.setMunicipios(caba,laMatanza);
        laPampa.setMunicipios(otras);

        laMatanza.setOrganizaciones(utnOrg);
        utnOrg.setDivisionTerritorial(laMatanza);

        sectorTerritorialCaba.agregarDivisionTerritorial(buenosAires,laPampa, caba);

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

        //Persona 1
        pacoCasa = new Ubicacion(new Municipio("Nunez", 1),buenosAires,"Quesada", "876");
        paco = new Persona("Paco","Paco", TipoDocumento.DNI, "12345567", pacoCasa);
        paco.solicitarVinculacion(utnOrg, unSector);

        // Persona 2
        betoCasa = new Ubicacion(new Municipio("Belgrano", 1),buenosAires,"Cabildo", "825");
        beto = new Persona("Beto","Perro", TipoDocumento.DNI, "12345567", betoCasa);
        beto.solicitarVinculacion(utnOrg, otroSector);

        // Persona 3
        silviaCasa = new Ubicacion(new Municipio("Balbanera", 1),buenosAires,"Rivadavia", "458");
        silvia = new Persona("Silvia","Ne", TipoDocumento.DNI, "45123456", silviaCasa);
        silvia.solicitarVinculacion(utnOrg, otroSector);

        utnOrg.confirmarEmpleados();

        // Subte
        subteD = new TransportePublico("D", TipoTransportePublico.SUBTE);
        subteD.setTipoCombustible(TipoCombustible.ELECTRICO);
        subteD.setConsumoXKm(15.4/100);
        estCongresoUbicacion = new Ubicacion(new Municipio("Saveedra", 1),buenosAires,"Av. Cabildo", "2900");
        estCongreso = new Estacion("Congreso", estCongresoUbicacion, null, null, 2.9);
        estLacrozeUbicacion = new Ubicacion(new Municipio("Palermo", 1),buenosAires,"Av. Cabildo", "800");
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
    public void persistir1OrganizacionTest(){
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(utnOrg);
        EntityManagerHelper.getEntityManager().persist(trayecto1);
        EntityManagerHelper.getEntityManager().persist(silvia);
        EntityManagerHelper.commit();
    }

    private double distanciaMock(){
        double valor = 1.3;
        return valor;
    }*/

}
