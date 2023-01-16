package domain.model.Entidades.HuellaCarbono;

import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.AgentesSectoriales.SectorTerritorial;
import domain.model.Entidades.ClasificacionOrganizacion;
import domain.model.Entidades.Mediciones.Periodicidad;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Sector;
import domain.model.Entidades.TipoOrganizacion;
import domain.repositories.RepositorioDivisionTerritorial;
import domain.repositories.RepositorioHuellas;
import domain.repositories.RepositorioOrganizaciones;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GeneradorDeReportes {

    public ReportePorSector reportePorSectorTerritorial(List<SectorTerritorial> sectores, int anio){
        ReportePorSector reporte = new ReportePorSector();
        HashMap<SectorTerritorial, Double> hcPorSector = new HashMap<SectorTerritorial, Double>();



        for (SectorTerritorial s: sectores) {
            List<DivisionTerritorial> divisiones = s.getDivisionesTerritoriales();
            for (DivisionTerritorial d: divisiones){
                List<Organizacion> organizaciones = d.getOrganizaciones();
                for(Organizacion o: organizaciones){
                    sectores.forEach(sectorTerritorial -> hcPorSector.put(sectorTerritorial, recuperarHuella(anio, o.getHuellas()).getValor()));
                }
            }
        }

        reporte.setHcPorSector(hcPorSector);
        return reporte;
    }

    public ReportePorSector reporteSectorTerritorial(SectorTerritorial sectorTerritorial, int anio) {
      ReportePorSector reporte = new ReportePorSector();
      HashMap<SectorTerritorial, Double> hcSector = new HashMap<SectorTerritorial, Double>();
      Double total = 0.0;
      List <DivisionTerritorial> divisionesTerritoriales = sectorTerritorial.getDivisionesTerritoriales();

      for (DivisionTerritorial division : divisionesTerritoriales){
        List<Organizacion> organizaciones = division.getOrganizaciones();
        List<HuellaDeCarbono> huellas = new ArrayList<>();
        organizaciones.forEach(organizacion -> huellas.add(recuperarHuella(anio, organizacion.getHuellas())));
        Double valor = 0.0;
        for (HuellaDeCarbono huellaDeCarbono : huellas){
         valor = valor + huellaDeCarbono.getValor();
        }
        total = total + valor;
      }
      hcSector.put(sectorTerritorial, total);
      reporte.setHcPorSector(hcSector);
      return reporte;
    }

    public Double hcDeTipo(List<Organizacion> organizaciones, int anio, ClasificacionOrganizacion clasificacionOrganizacion){
      List<Organizacion> organizacionesFiltradas = organizaciones.stream().filter(organizacion -> organizacion.getClasificacionOrg().equals(clasificacionOrganizacion)).collect(Collectors.toList());
      Double valorHc = 0.0;
      for (Organizacion org : organizacionesFiltradas){
        HuellaDeCarbono hc = recuperarHuella(anio, org.getHuellas());
        valorHc = valorHc + hc.getValor();
      }
      return valorHc;
    }

    public ReportePorTipo reporteClasificacionOrganizacion(SectorTerritorial sectorTerritorial, int anio) {
        ReportePorTipo reporte = new ReportePorTipo();
        List<Organizacion> organizaciones = sectorTerritorial.getOrganizaciones();
        List<ClasificacionOrganizacion> clasificacionesOrganizaciones = organizaciones.stream().map(o -> o.getClasificacionOrg()).distinct().collect(Collectors.toList());
        HashMap<ClasificacionOrganizacion, Double> hcPorTipo = new HashMap<ClasificacionOrganizacion, Double>();
        clasificacionesOrganizaciones.forEach(c -> hcPorTipo.put(c, hcDeTipo(organizaciones, anio, c)));
        reporte.setHcPorTipoOrganizacion(hcPorTipo);
        return reporte;
    }

    private HuellaDeCarbono recuperarHuella(int anio, List<HuellaDeCarbono> huellas) {
      HuellaDeCarbono huellaBuscada;
      List<HuellaDeCarbono> huellasFiltradas = huellas.stream().filter(huellaDeCarbono -> huellaDeCarbono.getPeriodo().equals(Periodicidad.ANUAL)).filter(huellaDeCarbono -> huellaDeCarbono.getAnio()==anio).collect(Collectors.toList());
      return huellasFiltradas.get(0);
    }

    public ReporteSectorPorDivision ReportePorDivisionTerritorial(SectorTerritorial sector, int anio){
      ReporteSectorPorDivision reporte = new ReporteSectorPorDivision();
      List<DivisionTerritorial> divisionesTerritoriales = sector.getDivisionesTerritoriales();
      HashMap<DivisionTerritorial, Double> hcPorDivision = new HashMap<DivisionTerritorial, Double>();
        for (DivisionTerritorial division : divisionesTerritoriales){
        List<Organizacion> organizaciones = division.getOrganizaciones();
        List<HuellaDeCarbono> huellas = new ArrayList<>();
        organizaciones.forEach(organizacion -> huellas.add(recuperarHuella(anio, organizacion.getHuellas())));
        Double valor = 0.0;
        for (HuellaDeCarbono huellaDeCarbono : huellas){
          valor = valor + huellaDeCarbono.getValor();
        }
        hcPorDivision.put(division, valor);
      }
      reporte.setHcPorDivision(hcPorDivision);
      return reporte;
    }

    public ReportePaisPorProvincia reportePaisPorProvincia(List<Provincia> provincias, int anio){
      ReportePaisPorProvincia reporte = new ReportePaisPorProvincia();
      HashMap<Provincia, Double> hcPorProvincia = new HashMap<Provincia, Double>();
      for (Provincia provincia : provincias){
        List<Organizacion> organizacions = provincia.getOrganizaciones();
        List<HuellaDeCarbono> huellas = new ArrayList<>();
        organizacions.forEach(organizacion -> huellas.add(recuperarHuella(anio, organizacion.getHuellas())));
        Double valor = 0.0;
        for (HuellaDeCarbono huellaDeCarbono : huellas){
          valor = valor + huellaDeCarbono.getValor();
        }
        hcPorProvincia.put(provincia, valor);
      }
      reporte.setHcPorProvincia(hcPorProvincia);
      return reporte;
    }

    public ReporteOrganizacion reporteOrganizacion(Organizacion organizacion, int anio){
      ReporteOrganizacion reporte = new ReporteOrganizacion();
      reporte.setOrganizacion(organizacion);
      HashMap<Sector, Double> hcPorSector = new HashMap<Sector, Double>();
      organizacion.getSectores().forEach(sector -> hcPorSector.put(sector, recuperarHuella(anio, sector.getHuellas()).getValor()));
      reporte.setHcPorSector(hcPorSector);
      return reporte;
    }

    public ReporteEvolucionOrganizacion reporteEvolucionOrganizacion(Organizacion organizacion){
      ReporteEvolucionOrganizacion reporte = new ReporteEvolucionOrganizacion();
      reporte.setOrganizacion(organizacion);
      HashMap<Integer, Double> hcPorAnio = new HashMap<Integer, Double>();
      List<HuellaDeCarbono> huellasFiltradas = organizacion.getHuellas().stream().filter(huellaDeCarbono -> huellaDeCarbono.getPeriodo().equals(Periodicidad.ANUAL)).collect(Collectors.toList());
      huellasFiltradas.forEach(huellaDeCarbono -> hcPorAnio.put(huellaDeCarbono.getAnio(), huellaDeCarbono.getValor()));
      reporte.setHcPorAnio(hcPorAnio);
      return reporte;
    }

    public ReporteEvolucionSectorTerritorial reporteEvolucionSectorTerritorial(SectorTerritorial sector){
      ReporteEvolucionSectorTerritorial reporte = new ReporteEvolucionSectorTerritorial();
      reporte.setSectorTerritorial(sector);
      HashMap<Integer, Double> hcPorAnio = new HashMap<Integer , Double>();
      List<HuellaDeCarbono> huellasFiltradas = sector.getHuellas().stream().filter(huellaDeCarbono -> huellaDeCarbono.getPeriodo().equals(Periodicidad.ANUAL)).collect(Collectors.toList());
      huellasFiltradas.forEach(huellaDeCarbono -> hcPorAnio.put((huellaDeCarbono.getAnio()), huellaDeCarbono.getValor()));
      reporte.setHcPorAnio(hcPorAnio);
      return reporte;
    }
  }