package Organizacion;

public class MedicionesTest {/*
    Organizacion utnOrg;
    Punto utnDireccion;
    Sector unSector;
    ClasificacionOrganizacion unaClase;
    GeneradorMediciones mediciones;
    CalculadoraHC calculadora;
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

    @Before
    public void init() throws IOException{
        //Organizacion 1
        utnOrg = new Organizacion("UTN");
        utnDireccion = new Ubicacion(new Municipio("Budge", 2),new Provincia("BSAS"), "Mozart", "1412");
        utnOrg.setTipo(TipoOrganizacion.INSTITUCION);
        utnOrg.setUbicacionGeografica(utnDireccion);
        unSector = new Sector("RRHH", utnOrg);
        unaClase = new ClasificacionOrganizacion("Universidad");
        utnOrg.setClasificacionOrg(unaClase);
        utnOrg.agregarSector(new Sector[]{unSector});
        mediciones = new GeneradorMediciones("UTNDatos.xlsx");
        mediciones.procesarExcel(utnOrg);

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
        electricidad = new FactorEmision(Tipo.ELECTRICIDAD, 0.486); //0.486
        logistica = new FactorEmision(Tipo.LOGISTICA, 0.062);

        calculadora.agregarFactores(gas_natural, diesel_gasoil, kerosene, fuel_oil, nafta,carbon,carbon_lena, lena, combustible_consumido_gasoil, combustible_consumido_gnc,
            combustible_consumido_nafta, electricidad, logistica);
        calculadora.setConstante(2.00);
    }

    @Test
    public void utnTiene29Mediciones()  {
        for (Medicion m: utnOrg.getMediciones()) {
            System.out.println(m.getActividad());
            System.out.println(m.getTipoDeConsumo().getTipo());
            System.out.println(m.getValor() + " " + m.getTipoDeConsumo().getUnidad());
            System.out.println(m.getPeriodicidad());
            System.out.println(m.getMes());
            System.out.println(m.getAnio());
            System.out.println();
        }

        Assert.assertEquals(29, utnOrg.getMediciones().size());
    }

    @Test
    public void utnGasto1234PesosEnCarbon() {
        Assert.assertEquals(1234.0, utnOrg.getMediciones().get(6).getValor(), 0.1);
    }

    @Test
    public void utnTieneHuellaMayorEn2022(){
        Double huella19 = calculadora.calcularMediciones(utnOrg, Periodicidad.ANUAL, 4, 2019);
        Double huella22 = calculadora.calcularMediciones(utnOrg, Periodicidad.ANUAL, 4, 2022);
        Assert.assertTrue(huella19 < huella22);
    }

*/
}
