package Organizacion;

public class OrganizacionTest {/*
    Organizacion utnOrg, subteOrg;
    Punto utnDireccion, pacoCasa, betoCasa, subteDireccion, puestoBicis, silviaCasa;
    Sector unSector, otroSector, otroSector2;
    ClasificacionOrganizacion unaClase, otraClase;
    Persona paco, beto, silvia;
    APulmon aPie, enBici;
    Tramo tramo1, tramo2, tramo3, tramo4, tramo5;
    Trayecto trayecto1, trayecto2, trayecto3;

    //pruebas
    VehiculoParticular autoSilvia;
    VehiculoParticular motoPaco;
    VehiculoParticular camionetaBeto;
    TransportePublico lineaB;
    TransportePublico linea105;
    TransportePublico lineaMitre;
    Estacion carlosGardel;
    Ubicacion carlosGardelUbicacion;
    private ServicioDistancia servicioDistancia;

    @Before
    public void init(){
        //Transportes prueba (despues borrar esto de aca y hacer otro test)
        linea105 = new TransportePublico( "105", TipoTransportePublico.COLECTIVO, "Golf");

        lineaB = new TransportePublico("B",TipoTransportePublico.SUBTE);

        this.servicioDistancia = ServicioDistancia.getInstancia();
        this.servicioDistancia.setAdapter(new AdapterServicio());
        //ServicioDistancia servicio = new ServicioDistancia(ServicioDistancia.getInstancia());

        lineaMitre = new TransportePublico("Mitre",TipoTransportePublico.TREN);
        carlosGardelUbicacion = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"),"Av. Corrientes", "3300"); //lo de los nulls hay que chequearlo
        carlosGardel = new Estacion("Carlos Gardel", carlosGardelUbicacion, null, null, 3300.0);
        aPie = new APulmon("A pata");
        enBici = new APulmon("En bici");
        aPie.setServicio(servicioDistancia);
        enBici.setServicio(servicioDistancia);

        //Organizacion 1
        utnOrg = new Organizacion("UTN");
        utnDireccion = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"), "Mozart", "1412");
        utnOrg.setTipo(TipoOrganizacion.INSTITUCION);
        utnOrg.setUbicacionGeografica(utnDireccion);
        unSector = new Sector("RRHH", utnOrg);
        otroSector = new Sector("Admin", utnOrg);
        unaClase = new ClasificacionOrganizacion("Universidad");
        utnOrg.setClasificacionOrg(unaClase);
        utnOrg.agregarSector(new Sector[]{unSector, otroSector});

        //Organizacion 2
        subteOrg = new Organizacion("Subte");
        subteDireccion = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"), "Medrano", "1234");
        subteOrg.setTipo(TipoOrganizacion.GUBERNAMENTAL);
        subteOrg.setUbicacionGeografica(subteDireccion);
        otroSector2 = new Sector("Sistemas", subteOrg);
        otraClase = new ClasificacionOrganizacion("Transporte Publico");
        subteOrg.setClasificacionOrg(otraClase);
        subteOrg.agregarSector(otroSector2);

        //Persona 1
        pacoCasa = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"),"Quesada", "876");
        paco = new Persona("Paco","Paco", TipoDocumento.DNI, "12345567", pacoCasa);
        paco.solicitarVinculacion(utnOrg, unSector);
        paco.solicitarVinculacion(subteOrg, otroSector2);

        // Persona 2
        betoCasa = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"),"Cabildo", "923");
        beto = new Persona("Beto","Perro", TipoDocumento.DNI, "12345567", betoCasa);
        beto.solicitarVinculacion(utnOrg, otroSector);

        // Persona 3
        silviaCasa = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"),"Rivadavia", "458");
        silvia = new Persona("Silvia","Ne", TipoDocumento.DNI, "45123456", silviaCasa);
        silvia.solicitarVinculacion(utnOrg, otroSector);

        puestoBicis = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"), "Pichincha","456");
        trayecto1 = new Trayecto();
        tramo1 = new Tramo(pacoCasa, puestoBicis, aPie, paco);
        tramo2 = new Tramo(puestoBicis, utnDireccion, enBici, paco);
        trayecto1.agregarTramo(new Tramo[]{tramo1, tramo2});


        trayecto2 = new Trayecto();
        tramo3 = new Tramo(utnDireccion, subteDireccion, enBici, paco);
        trayecto2.agregarTramo(tramo3);


        trayecto3 = new Trayecto();
        tramo4 = new Tramo(subteDireccion, puestoBicis, enBici, paco);
        tramo5 = new Tramo(puestoBicis, pacoCasa, aPie, paco);
        trayecto3.agregarTramo(new Tramo[]{tramo4, tramo5});


        paco.agregarTrayecto(new Trayecto[]{trayecto1, trayecto2, trayecto3});
    }


    @Test
    public void utnQuedaEnMozart(){
        Assert.assertEquals("Mozart 1412", utnOrg.getUbicacionGeografica().mostrarDetalle());
    }

    @Test
    public void utnEsInstitucion(){
        Assert.assertEquals(TipoOrganizacion.INSTITUCION, utnOrg.getTipo());
    }

    @Test
    public void utnEsUnaUniversidad(){
        Assert.assertEquals("Universidad", utnOrg.getClasificacionOrg().getNombre());
    }

    @Test
    public void utnTieneTresSolicitud(){
       Assert.assertEquals(3, utnOrg.getSolicitudes().size());
    }

    @Test
    public void rrhhTiene1Empleado(){
        utnOrg.confirmarEmpleados();
        List<String> personas = unSector.getMiembros().stream().map(m -> m.getNombre()).collect(Collectors.toList());
        Assert.assertEquals(1, unSector.getMiembros().size());
        Assert.assertEquals("Paco", unSector.getMiembros().get(0).getNombre());
    }

    @Test
    public void rrhhNoTieneEmpleados(){
        utnOrg.rechazarEmpleados();
        Assert.assertEquals(0, unSector.getMiembros().size());
        Assert.assertEquals(0, utnOrg.getSolicitudes().size());
    }

    @Test
    public void pacoTieneDosTrabajos() {
        utnOrg.confirmarEmpleados();
        subteOrg.confirmarEmpleados();
        Assert.assertEquals(2, paco.getTrabajos().size());
    }

    @Test
    public void pacoTiene5Tramos()  throws IOException {
        utnOrg.confirmarEmpleados();
        subteOrg.confirmarEmpleados();
        List<Trayecto> trayectos = paco.getTrayectos();
        List<Tramo> tramos = trayectos.stream().flatMap(t-> t.getTramos().stream()).collect(Collectors.toList());
        Assert.assertEquals(5, tramos.size());
        Double distancia = paco.distanciaTotalDeTrayectos();
        System.out.println("Distancia Total: " + distancia);
        Assert.assertNotEquals(0.0, distancia);
    }*/
}
