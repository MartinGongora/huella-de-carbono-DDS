package domain.controllers;

import domain.model.AgentesSectoriales.AgenteSectorial;
import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.AgentesSectoriales.SectorTerritorial;
import domain.model.Entidades.ClasificacionOrganizacion;
import domain.model.Entidades.HuellaCarbono.*;
import domain.model.Entidades.Mediciones.Periodicidad;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Sector;
import domain.model.usuarios.Usuario;
import domain.repositories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class ReporteController {
    private GeneradorDeReportes generadorDeReportes;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioSectoresTerritoriales repositorioSectoresTerritoriales;
    private RepositorioDivisionTerritorial repoProvincias;
    private RepositorioOrganizaciones repositorioOrganizaciones;
    private CalculadoraHC calculadora;

    public ReporteController() throws IOException {
        this.generadorDeReportes = new GeneradorDeReportes();
        this.repositorioUsuarios = new RepositorioUsuarios();
        this.repositorioSectoresTerritoriales = new RepositorioSectoresTerritoriales();
        this.repoProvincias = new RepositorioDivisionTerritorial();
        this.repositorioOrganizaciones = new RepositorioOrganizaciones();
        this.calculadora = new CalculadoraHC();
    }

    public ModelAndView mostrarReportesOrg(Request request, Response response) {
        String nombreOrganizacion = request.params("razonSocial");
        Organizacion organizacion = this.repositorioOrganizaciones.buscarNombre(nombreOrganizacion);

        //VISTA ORGANIZACION
        ReporteEvolucionOrganizacion reporteEvolucionOrganizacion = generadorDeReportes.reporteEvolucionOrganizacion(organizacion);
        ReporteOrganizacion reporteOrganizacion = generadorDeReportes.reporteOrganizacion(organizacion, 2022);

        return new ModelAndView(new HashMap<String, Object>(){{
            put("reporteEvolucionOrganizacion", reporteEvolucionOrganizacion);
            put("reporteOrganizacion", reporteOrganizacion);
        }},"reportes_org.hbs");
    }

    public ModelAndView mostrar(Request request, Response response) {

        String nombreUsuario = request.params("nombreDeUsuario");
        Usuario usuario = this.repositorioUsuarios.buscarNombre(nombreUsuario);
        AgenteSectorial agenteSectorial = usuario.getAgenteSectorial();
        SectorTerritorial sectorTerritorial = agenteSectorial.getSectorTerritorial();
        List<Organizacion> organizaciones = sectorTerritorial.getOrganizaciones();

        List<SectorTerritorial> sectoresTerritoriales = this.repositorioSectoresTerritoriales.buscarTodos();
        //List<Provincia> provincias = this.repoProvincias.buscarProv();

        //VISTA AGENTE

        System.out.println("AAAAAAA");
        System.out.println(agenteSectorial.getNombre());
        System.out.println(sectorTerritorial.getNombre());

        //List<SectorTerritorial> sectoresTerritoriales = new ArrayList<>();
        //sectoresTerritoriales.add(sectorTerritorial);

        Double huella21 = calculadora.sectorTerritorialHC(sectorTerritorial, Periodicidad.ANUAL, 1, 2022);
        Double huella22 = calculadora.sectorTerritorialHC(sectorTerritorial, Periodicidad.ANUAL, 1, 2021);


        ReportePorSector reporteSectorTerritorial = generadorDeReportes.reporteSectorTerritorial(sectorTerritorial, Year.now().getValue());
        ReportePorSector reporteSectorTerritorialAnterior = generadorDeReportes.reporteSectorTerritorial(sectorTerritorial, Year.now().getValue()-1);
        ReportePorTipo reporteClasificacionesOrganizaciones = generadorDeReportes.reporteClasificacionOrganizacion(sectorTerritorial, Year.now().getValue());
        ReporteSectorPorDivision reporteDivisionesTerritoriales = generadorDeReportes.ReportePorDivisionTerritorial(sectorTerritorial, Year.now().getValue());

        HashMap<SectorTerritorial, Double> reporteSectoresTerritoriales = new HashMap<>();
        for (SectorTerritorial s: sectoresTerritoriales){
            reporteSectoresTerritoriales.putAll(generadorDeReportes.reporteSectorTerritorial(s, Year.now().getValue()).getHcPorSector());
        }

        ReporteEvolucionSectorTerritorial reporteEvolucionSectorTerritorial = generadorDeReportes.reporteEvolucionSectorTerritorial(sectorTerritorial);



        //ReportePorSector reporteSectoresTerritoriales = generadorDeReportes.reportePorSectorTerritorial(sectoresTerritoriales, Year.now().getValue());
        /*ReportePaisPorProvincia reporteProvincias = generadorDeReportes.reportePaisPorProvincia(provincias, 2022);
        ReportePorSector reporteSectorTerritorial = generadorDeReportes.reporteSectorTerritorial(sectorTerritorial, 2022);
        ReporteSectorPorDivision reporteDivisionesTerritoriales = generadorDeReportes.ReportePorDivisionTerritorial(sectorTerritorial, 2022);

        ReporteEvolucionSectorTerritorial reporteEvolucionSectorTerritorial = generadorDeReportes.reporteEvolucionSectorTerritorial(sectorTerritorial);
         */
        Double resultado = 0.0;

        System.out.println("BBBBBBB");
        System.out.println(huella21);
        //System.out.println(huella22);
        //System.out.println(reporteSectoresTerritoriales.getHcPorSector().get(sectorTerritorial));


        return new ModelAndView(new HashMap<String, Object>(){{
            put("usuario", usuario);
            put("reporteSectoresTerritoriales", reporteSectoresTerritoriales);
            //put("reporteProvincias", reporteProvincias);
            put("reporteSectorTerritorial", reporteSectorTerritorial.getHcPorSector());
            put("reporteDivisionesTerritoriales", reporteDivisionesTerritoriales.getHcPorDivision());
            put("reporteClasificacionesOrganizaciones", reporteClasificacionesOrganizaciones.getHcPorTipoOrganizacion());
            put("reporteEvolucionSectorTerritorial", reporteEvolucionSectorTerritorial.getHcPorAnio());
        }},"reportes.hbs");
    }
}
