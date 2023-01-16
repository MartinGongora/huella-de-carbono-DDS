package Organizacion;

import domain.model.AgentesSectoriales.*;
import domain.model.Entidades.*;
import domain.model.Entidades.*;
import domain.model.Entidades.HuellaCarbono.CalculadoraHC;
import domain.model.Entidades.HuellaCarbono.HuellaDeCarbono;
import domain.model.Entidades.Mediciones.*;
import domain.model.GeoReferencia.Punto;
import domain.model.GeoReferencia.Tramo;
import domain.model.GeoReferencia.Trayecto;
import domain.model.GeoReferencia.Ubicacion;
import domain.model.Medios.APulmon;
import domain.model.Medios.TransportePublico.Estacion;
import domain.model.Medios.TransportePublico.TipoTransportePublico;
import domain.model.Medios.TransportePublico.TransportePublico;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoCombustible;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoVehiculo;
import domain.model.Medios.VehiculoParticular.VehiculoParticular;
import domain.model.Servicios.Distancia.Adapters.ServicioDistanciaAdapter;
import domain.model.Servicios.Distancia.Entidades.DistanciaEntreDirecciones;
import domain.model.Servicios.Distancia.ServicioDistancia;
import domain.model.AgentesSectoriales.AgenteSectorial;
import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.AgentesSectoriales.SectorTerritorial;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HuellaTest {
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
        laMatanza = new Municipio("mata",1);
        otras = new Municipio("teto",1);


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
    public void utnAumentoSuHCDirectasEn2022(){
        Double huella21 = calculadora.calcularMediciones(utnOrg, Periodicidad.ANUAL, 4, 2021);
        Double huella22 = calculadora.calcularMediciones(utnOrg, Periodicidad.ANUAL, 4, 2022);
        System.out.println("HC Anual 2021: " + huella21 + " kgEqCO2");
        System.out.println("HC Anual 2022: " + huella22 + " kgEqCO2");
        Assert.assertTrue(huella21 < huella22);
    }

    @Test
    public void utnTuvoHCDirectasEnMayo2022(){
        Double huellaMayo22 = calculadora.calcularMediciones(utnOrg, Periodicidad.MENSUAL, 5, 2022);
        System.out.println("HC Mayo 2022: " + huellaMayo22 + " kgEqCO2");
        Assert.assertEquals(6046.58, huellaMayo22, 0.0);
    }

    @Test
    public void laElectricidadContaminaMas(){
        Double electricidadAntes = calculadora.calcularMediciones(utnOrg, Periodicidad.MENSUAL, 3, 2022);
        calculadora.setFactoresEmision(1.0, Tipo.ELECTRICIDAD);
        Double electricidadDespues = calculadora.calcularMediciones(utnOrg, Periodicidad.MENSUAL, 3, 2022);
        System.out.println("La HC aumento luego de aumentar el nuevo FE: ");
        System.out.println(electricidadAntes + " < " + electricidadDespues + " kgEqCO2");
        Assert.assertTrue(electricidadAntes < electricidadDespues);
    }

    @Test
    public void seCompartieron2Tramos(){
        System.out.println("Tramo 3 : " + tramo3.getPasajeros().stream().map(p -> p.getNombre()).collect(Collectors.toList()));
        System.out.println("Tramo 4 : " + tramo4.getPasajeros().stream().map(p -> p.getNombre()).collect(Collectors.toList()));
        System.out.println("Tramo 5 : " + tramo5.getPasajeros().stream().map(p -> p.getNombre()).collect(Collectors.toList()));
        Assert.assertEquals(2, tramo3.getPasajeros().size());
        Assert.assertEquals(3, tramo4.getPasajeros().size());
        Assert.assertEquals(1, tramo5.getPasajeros().size());
    }

    @Test
    public void DistanciaTotalPersonas()  throws IOException {
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

        calculadora.calcularTraslado(paco, Periodicidad.ANUAL, LocalDate.now().getMonthValue(), LocalDate.now().getYear());

        System.out.println("Distancia Total Paco: " + paco.getTrayectos().stream().mapToDouble(t -> t.getTotalTrayecto()).sum());
        System.out.println("Distancia Total Beto: " + beto.getTrayectos().stream().mapToDouble(t -> t.getTotalTrayecto()).sum());
        System.out.println("Distancia Total Silvia: " + silvia.getTrayectos().stream().mapToDouble(t -> t.getTotalTrayecto()).sum());
        Assert.assertEquals(1.3, tramo1.getTotalTramo(), 0.01);
        Assert.assertEquals(2.1, tramo2.getTotalTramo(), 0.01);
        Assertions.assertEquals(6.0, paco.getTrayectos().stream().mapToDouble(t -> t.getTotalTrayecto()).sum(),0.1);
        Assertions.assertEquals(2.6, beto.getTrayectos().stream().mapToDouble(t -> t.getTotalTrayecto()).sum(),0.1);
        Assertions.assertEquals(2.6, silvia.getTrayectos().stream().mapToDouble(t -> t.getTotalTrayecto()).sum(),0.1);
    }

    @Test
    public void SilviaGeneraMuchoMasHC()  throws IOException {
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

        Double pacoHC = calculadora.calcularTraslado(paco, Periodicidad.ANUAL, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        Double betoHC = calculadora.calcularTraslado(beto, Periodicidad.ANUAL, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        Double silviaHC = calculadora.calcularTraslado(silvia, Periodicidad.ANUAL, LocalDate.now().getMonthValue(), LocalDate.now().getYear());

        System.out.println("Paco HC Anual: " + pacoHC + " kgCO2eq");
        System.out.println("Beto HC Anual: " + betoHC + " kgCO2eq");
        System.out.println("Silvia HC Anual: " + silviaHC + " kgCO2eq");

        Assertions.assertTrue(pacoHC > betoHC && pacoHC > silviaHC);
    }

    @Test
    public void UtnTieneUnaHCAnualYNoSeModificaAlTenerloYaCalculado()  throws IOException {
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

        Double huellaUtn2022 = calculadora.organizacionHC(utnOrg, Periodicidad.ANUAL, 0, 2022);
        Double utnHCDirectas = calculadora.calcularMediciones(utnOrg, Periodicidad.ANUAL, 0, 2022);
        Double utnUnSectorHC = calculadora.sectoresHC(Arrays.asList(new Sector[]{unSector}), Periodicidad.ANUAL);
        Double utnOtroSectorHC = calculadora.sectoresHC(Arrays.asList(new Sector[]{otroSector}), Periodicidad.ANUAL);

        System.out.println("UTN HC Anual:");
        System.out.println("UTN HC Anual: " + huellaUtn2022 + " kgCO2eq");
        System.out.println("UTN HC Directas Anual: " + utnHCDirectas + " kgCO2eq");
        System.out.println("UTN HC RRHH Anual: " + utnUnSectorHC + " kgCO2eq");
        System.out.println("UTN HC Admin Anual: " + utnOtroSectorHC + " kgCO2eq");

        Double huellaUtnMayo = calculadora.organizacionHC(utnOrg, Periodicidad.MENSUAL, 5, 2022);
        Double utnHCDirectasMayo = calculadora.calcularMediciones(utnOrg, Periodicidad.MENSUAL, 5, 2022);
        Double utnUnSectorHCMayo = calculadora.sectoresHC(Arrays.asList(new Sector[]{unSector}), Periodicidad.MENSUAL);
        Double utnOtroSectorHCMayo = calculadora.sectoresHC(Arrays.asList(new Sector[]{otroSector}), Periodicidad.MENSUAL);

        System.out.println("\nUTN HC Mensual:");
        System.out.println("UTN HC Mayo 2022: " + huellaUtnMayo + " kgCO2eq");
        System.out.println("UTN HC Directas Mayo 2022: " + utnHCDirectasMayo + " kgCO2eq");
        System.out.println("UTN HC RRHH Mayo 2022: " + utnUnSectorHCMayo + " kgCO2eq");
        System.out.println("UTN HC Admin Mayo 2022: " + utnOtroSectorHCMayo + " kgCO2eq");

        Double huellaUtn2021 = calculadora.organizacionHC(utnOrg, Periodicidad.ANUAL, 0, 2021);

        List<HuellaDeCarbono> huellas = utnOrg.getHuellas().stream().filter(h -> h.getPeriodo() == Periodicidad.ANUAL).collect(Collectors.toList());
        Double huella2021 = huellas.stream().filter(h -> h.getAnio() == 2021).findFirst().get().getValor();

        System.out.println("\nUTN HC Año 2021:");
        System.out.println("UTN HC 2021: " + huella2021 + " kgCO2eq");

        Assertions.assertEquals(16642.6, huellaUtn2022, 0.1);
        Assertions.assertEquals(6062.7, huellaUtnMayo, 0.1);
        Assertions.assertEquals(12431.7, huella2021, 0.1);

        //agrego una persona con un nuevo trayecto
        Persona martin = new Persona("Martin","Ne", TipoDocumento.DNI, "45123456", silviaCasa);
        martin.solicitarVinculacion(utnOrg, otroSector);

        utnOrg.confirmarEmpleados();

        Trayecto trayecto18 = new Trayecto();
        trayecto18.agregarTramo(tramo5);
        martin.agregarTrayecto(trayecto18);

        //y chequeo que de los mismos valores de huella, ya que no debe recalcular en este caso
        Double huellaUtn2022v2 = calculadora.organizacionHC(utnOrg, Periodicidad.ANUAL, 0, 2022);
        Assertions.assertEquals(16642.6, huellaUtn2022v2, 0.1);

        //y en este caso si debe recalcular
        Double huellaUtn2022v3 = calculadora.organizacionHC(utnOrg, Periodicidad.ANUAL, 0, 2023);
        assertNotEquals(huellaUtn2022v2, huellaUtn2022v3);
    }

    @Test
    public void PacoImpactaEnLaUtn()  throws IOException {
        double distancia = this.distanciaMock();
        DistanciaEntreDirecciones distanciaMock = mock(DistanciaEntreDirecciones.class);

        when(distanciaMock.getValor()).thenReturn(distancia);
        when(this.adapterMock.calcularDistancia(pacoCasa, estCongreso)).thenReturn(distanciaMock);
        when(this.adapterMock.calcularDistancia(estLacroze, silviaCasa)).thenReturn(distanciaMock);
        when(this.adapterMock.calcularDistancia(silviaCasa, utnDireccion)).thenReturn(distanciaMock);
        when(this.adapterMock.calcularDistancia(utnDireccion, estCongreso)).thenReturn(distanciaMock);

        paco.distanciaTotalDeTrayectos();
        beto.distanciaTotalDeTrayectos();
        silvia.distanciaTotalDeTrayectos();

        DecimalFormat formatoNumero = new DecimalFormat("#.00");

        Double miImpacto = calculadora.impactoPersonalHC(utnOrg, paco, Periodicidad.ANUAL, 5, 2022);
        System.out.println("Paco impacto en el 2022: " + formatoNumero.format(miImpacto) + " %");

        Double miImpactoMarzo = calculadora.impactoPersonalHC(utnOrg, paco, Periodicidad.MENSUAL, 3, 2022);
        System.out.println("Paco impacto en marzo: " + formatoNumero.format(miImpactoMarzo) + " %");

        Assertions.assertEquals(0.5, miImpacto, 0.1);
        Assertions.assertEquals(1.5, miImpactoMarzo, 0.1);
    }

    @Test
    public void IndicadoresDeHC()  throws IOException {
        double distancia = this.distanciaMock();
        DistanciaEntreDirecciones distanciaMock = mock(DistanciaEntreDirecciones.class);

        when(distanciaMock.getValor()).thenReturn(distancia);
        when(this.adapterMock.calcularDistancia(pacoCasa, estCongreso)).thenReturn(distanciaMock);
        when(this.adapterMock.calcularDistancia(estLacroze, silviaCasa)).thenReturn(distanciaMock);
        when(this.adapterMock.calcularDistancia(silviaCasa, utnDireccion)).thenReturn(distanciaMock);
        when(this.adapterMock.calcularDistancia(utnDireccion, estCongreso)).thenReturn(distanciaMock);

        paco.distanciaTotalDeTrayectos();
        beto.distanciaTotalDeTrayectos();
        silvia.distanciaTotalDeTrayectos();

        DecimalFormat formatoNumero = new DecimalFormat("#.00");

        Double indicadorRRHH = calculadora.indicadorSector(unSector, Periodicidad.ANUAL);
        System.out.println("RRHH consume por miembro: " + formatoNumero.format(indicadorRRHH) + " kgCO2Eq");

        Double indicadorAdmin = calculadora.indicadorSector(otroSector, Periodicidad.ANUAL);
        System.out.println("Admin consume por miembro: " + formatoNumero.format(indicadorAdmin) + " kgCO2Eq");

        Assertions.assertEquals(83.4, indicadorRRHH, 0.1);
        Assertions.assertEquals(53.8, indicadorAdmin, 0.1);
    }

    @Test
    public void agenteDeCabaTieneHuella() throws IOException{
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

        DecimalFormat formatoNumero = new DecimalFormat("#.00");

        Double huella22 = calculadora.sectorTerritorialHC(sectorTerritorialCaba, Periodicidad.ANUAL, 5, 2022);
        List<HuellaDeCarbono> huellas = sectorTerritorialCaba.getHuellas().stream().filter(h -> h.getPeriodo() == Periodicidad.ANUAL).collect(Collectors.toList());

        Double registro22 = huellas.stream().filter(h -> h.getAnio() == 2022).findFirst().get().getValor();
        System.out.println("HC Anual 2022: " + formatoNumero.format(registro22) + " kgEqCO2");
        Assert.assertEquals(16642.6, registro22, 0.1);
    }

    private double distanciaMock(){
        double valor = 1.3;
        return valor;
    }

}
