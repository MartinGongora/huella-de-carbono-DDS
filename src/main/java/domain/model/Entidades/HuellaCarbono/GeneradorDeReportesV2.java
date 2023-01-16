package domain.model.Entidades.HuellaCarbono;

import domain.model.AgentesSectoriales.DivisionTerritorial;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.AgentesSectoriales.SectorTerritorial;
import domain.model.Entidades.ClasificacionOrganizacion;
import domain.model.Entidades.Mediciones.Periodicidad;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Sector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GeneradorDeReportesV2 {

  public ReportePorSector reporteSectorTerritorial(List<SectorTerritorial> sectores, int anio){
    ReportePorSector reporte = new ReportePorSector();
    HashMap<SectorTerritorial, Double> hcPorSector = new HashMap<SectorTerritorial, Double>();
    sectores.forEach(sectorTerritorial -> hcPorSector.put(sectorTerritorial, recuperarHuella(anio, sectorTerritorial.getHuellas()).getValor()));
    reporte.setHcPorSector(hcPorSector);
    return reporte;
  }

  public Double hcDeTipo(List<SectorTerritorial> sectoresTerritoriales, int anio, ClasificacionOrganizacion clasificacionOrganizacion){
    List<Organizacion> organizaciones = sectoresTerritoriales.stream().map(sectorTerritorial -> sectorTerritorial.getOrganizaciones())
        .flatMap(sectores -> sectores.stream()).collect(Collectors.toList());
    List<Organizacion> organizacionesFiltradas = organizaciones.stream().filter(organizacion -> organizacion.getClasificacionOrg().equals(clasificacionOrganizacion)).collect(Collectors.toList());
    Double valorHc = 0.0;
    for (Organizacion org : organizacionesFiltradas){
      HuellaDeCarbono hc = recuperarHuella(anio, org.getHuellas());
      valorHc = valorHc + hc.getValor();
    }
    return valorHc;
  }

  public ReportePorTipo reportePorTipo(List<SectorTerritorial> sectoresTerritoriales, int anio, List<ClasificacionOrganizacion> clasificacionesOrganizacion){
    ReportePorTipo reporte = new ReportePorTipo();
    HashMap<ClasificacionOrganizacion, Double> hcPorTipo = new HashMap<ClasificacionOrganizacion, Double>();
    clasificacionesOrganizacion.forEach(clasificacionOrganizacion -> hcPorTipo.put(clasificacionOrganizacion,hcDeTipo(sectoresTerritoriales, anio, clasificacionOrganizacion)));
    reporte.setHcPorTipoOrganizacion(hcPorTipo);
    return reporte;
  }

  private HuellaDeCarbono recuperarHuella(int anio, List<HuellaDeCarbono> huellas) {
    HuellaDeCarbono huellaBuscada;
    List<HuellaDeCarbono> huellasFiltradas = huellas.stream().filter(huellaDeCarbono -> huellaDeCarbono.getPeriodo().equals(Periodicidad.ANUAL)).filter(huellaDeCarbono -> huellaDeCarbono.getAnio()==anio).collect(Collectors.toList());
    return huellasFiltradas.get(0);
  }

  public ReporteSectorPorDivision reporteSectorPorDivision(SectorTerritorial sector, int anio){
    ReporteSectorPorDivision reporte = new ReporteSectorPorDivision();
    HashMap<DivisionTerritorial, Double> hcPorDivision = new HashMap<DivisionTerritorial, Double>();
    List<DivisionTerritorial> divisionesTerritoriales = sector.getDivisionesTerritoriales();
    for (DivisionTerritorial division : divisionesTerritoriales){
      List<Organizacion> organizacions = division.getOrganizaciones();
      List<HuellaDeCarbono> huellas = new ArrayList<>();
      organizacions.forEach(organizacion -> huellas.add(recuperarHuella(anio, organizacion.getHuellas())));
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
