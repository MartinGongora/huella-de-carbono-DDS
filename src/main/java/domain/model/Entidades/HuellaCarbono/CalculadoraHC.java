package domain.model.Entidades.HuellaCarbono;

import domain.model.AgentesSectoriales.SectorTerritorial;
import domain.model.Entidades.Mediciones.*;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;
import domain.model.Entidades.Sector;
import domain.model.GeoReferencia.Tramo;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoCombustible;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CalculadoraHC {
    private List<FactorEmision> factoresEmision;
    private List<FactorTraslado> factoresTraslado;
    private Double constante = 1.5;

    public CalculadoraHC() {
        this.factoresEmision = new ArrayList<>();
        this.factoresTraslado = new ArrayList<>();
    }
    public Double organizacionHC(Organizacion organizacion, Periodicidad periodo, int mes, int anio){
        List<HuellaDeCarbono> huellasDeOrganizacion = organizacion.getHuellas();
        HuellaDeCarbono huellaBuscada = this.estaHuellaExiste(huellasDeOrganizacion, periodo, mes, anio);
        if(huellaBuscada != null){
            return huellaBuscada.getValor();
        }else {
            Double totalOrganizacion = this.calcularMediciones(organizacion, periodo, mes, anio);
            totalOrganizacion += this.sectoresHC(organizacion.getSectores(), periodo);
            HuellaDeCarbono nuevaHuella = new HuellaDeCarbono(mes, anio, periodo, totalOrganizacion);
            nuevaHuella.setDivisionesTerritoriales(organizacion.getDivisionTerritorial());
            organizacion.agregarHuella(nuevaHuella);
            //organizacion.getDivisionTerritorial()
            return totalOrganizacion;
        }
    }

    public Double organizacionHCDirectas(Organizacion organizacion, Periodicidad periodo, int mes, int anio){
        List<HuellaDeCarbono> huellasDeOrganizacion = organizacion.getHuellas();
        HuellaDeCarbono huellaBuscada = this.estaHuellaExiste(huellasDeOrganizacion, periodo, mes, anio);
        if(huellaBuscada != null){
            return huellaBuscada.getValor();
        }else {
            Double totalOrganizacion = this.calcularMediciones(organizacion, periodo, mes, anio);
            organizacion.agregarHuella(new HuellaDeCarbono(mes, anio, periodo, totalOrganizacion));
            return totalOrganizacion;
        }
    }
//hacer que retorne directamente el valor
    private HuellaDeCarbono estaHuellaExiste(List<HuellaDeCarbono> huellas, Periodicidad periodo, int mes, int anio) {
        List<HuellaDeCarbono> huellasDeEsaPeriocidad = huellas.stream().filter(unaHuella -> unaHuella.getPeriodo().equals(periodo)).collect(Collectors.toList());
        if (periodo.equals(Periodicidad.ANUAL)){
            List<HuellaDeCarbono> huellasRetornables = huellasDeEsaPeriocidad.stream().filter(unaHuella -> unaHuella.getPeriodo().equals(periodo) && unaHuella.getAnio() == anio).collect(Collectors.toList());
            if (huellasRetornables.size()>0){
                return huellasRetornables.get(0);
            }
            return null;
        }else{
            List<HuellaDeCarbono> huellasRetornables = huellasDeEsaPeriocidad.stream().filter(unaHuella -> unaHuella.getPeriodo().equals(periodo) && unaHuella.getAnio() == anio && unaHuella.getMes() == mes).collect(Collectors.toList());
            if (huellasRetornables.size()>0){
                return huellasRetornables.get(0);
            }
            return null;
        }
    }

    public Double sectoresHC(List<Sector> sectores, Periodicidad periodo){
        Double totalSectores = 0.0;
        Double totalUnSector;
        for (Sector sector: sectores) {
            totalUnSector = 0.0;
            List<Persona> personas = sector.getMiembros();
            totalUnSector = personas.stream().mapToDouble(p -> this.calcularTraslado(p, periodo,LocalDate.now().getMonthValue(), LocalDate.now().getYear())).sum();
            sector.agregarHuella(new HuellaDeCarbono(LocalDate.now().getMonthValue(), LocalDate.now().getYear(), periodo, totalUnSector));
            totalSectores+=totalUnSector;
        }
        return totalSectores;
    }

    public Double sectorTerritorialHC(SectorTerritorial sectorTerritorial, Periodicidad periodo, int mes, int anio){
        List<Organizacion> organizacionesTerritorio = sectorTerritorial.getOrganizaciones();
        Double hcSectorTerritorial = organizacionesTerritorio.stream().mapToDouble(organizacion -> this.organizacionHC(organizacion, periodo, mes, anio)).sum();
        sectorTerritorial.agregarHuella(new HuellaDeCarbono(mes, anio, periodo,hcSectorTerritorial));
        return hcSectorTerritorial;
    }

    public Double indicadorSector(Sector sector, Periodicidad periodo){
        Double huellaSector = this.sectoresHC(Arrays.asList(new Sector[]{sector}), periodo);
        return huellaSector / sector.getMiembros().size();
    }

    public Double impactoPersonalHC(Organizacion organizacion, Persona persona, Periodicidad periodo, int mes, int anio){
        Double totalOrganizacion = this.organizacionHC(organizacion, periodo, mes, anio);
        Double miHuella = this.calcularTraslado(persona, periodo, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        return miHuella * 100 / totalOrganizacion;
    }

    public Double calcularMediciones(Organizacion organizacion, Periodicidad periodo, int mes, int anio){
        List<Medicion> mediciones = organizacion.getMediciones().stream().filter(m -> m.getAnio() == anio).collect(Collectors.toList());
        if (periodo == Periodicidad.MENSUAL){
            mediciones = mediciones.stream().filter(m -> m.getMes() == mes).collect(Collectors.toList());
        }
        Double medicionesHc = mediciones.stream().mapToDouble(m -> hacerCalculo(m)).sum();
        return medicionesHc;
    }

    public Double calcularTraslado(Persona persona, Periodicidad periodo, int mes, int anio){
        List<HuellaDeCarbono> huellasDeOrganizacion = persona.getHuellas();

        HuellaDeCarbono huellaBuscada = this.estaHuellaExiste(huellasDeOrganizacion, periodo, mes, anio);
        if(huellaBuscada != null){
            return huellaBuscada.getValor();
        }else {
            Double personaHC = this.hacerCalculoTraslado(persona);
            if (periodo == Periodicidad.MENSUAL) {
                personaHC = personaHC * 22;
            } else {
                personaHC = personaHC * 5 * 52;
            }
            HuellaDeCarbono huellaNueva = new HuellaDeCarbono(LocalDate.now().getMonthValue(), LocalDate.now().getYear(), periodo, personaHC);
            //huellaNueva.setDivisionesTerritoriales(persona.getDomicilio().getMunicipio().);
            persona.agregarHuella(huellaNueva);
            huellaNueva.setPersona(persona);
            return personaHC;
        }
    }
    
    private Double hacerCalculoTraslado(Persona persona){
        List<Tramo> tramos = persona.getTrayectos().stream().flatMap(t -> t.getTramos().stream()).collect(Collectors.toList());
        Double trasladoHC;
        trasladoHC = tramos.stream().mapToDouble(t->(t.getTotalTramo()/t.getPasajeros().size()) *
            t.getMedioDeTransporte().getConsumoXKm() *
            this.factoresTraslado.stream()
                .filter(factorTraslado -> factorTraslado.esFactor(t.getMedioDeTransporte().getTipoCombustible()))
                .findFirst().orElse(null)
                .getValorFT())
            .sum();
        return trasladoHC;
    }

    private Double hacerCalculo(Medicion medicion){
        Double calculo = 0.0;
        calculo = medicion.getValor() *
            this.factoresEmision.stream()
                .filter(factorEmision -> factorEmision.esFactor(medicion.getTipoDeConsumo().getTipo()))
                .findFirst().orElse(null).getValorFE();
        if(medicion.getActividad() == Actividad.LOGISTICA){
            calculo *= constante;
        }
        return calculo;
    }

    public List<FactorEmision> getFactoresEmision() {
        return factoresEmision;
    }

    public void setFactoresEmision(Double valor, Tipo tipo) {
        FactorEmision factorEmision = this.factoresEmision.stream().filter(factorEmision1 -> factorEmision1.esFactor(tipo)).findFirst().get();
        factorEmision.setValorFE(valor);
    }
    public void agregarFactores(FactorEmision ... factores){
        Collections.addAll(this.factoresEmision, factores);
    }

    public void setFactoresTraslado(Double valor, TipoCombustible tipo) {
        FactorTraslado factorTraslado = this.factoresTraslado.stream().filter(factorTraslado1 -> factorTraslado1.esFactor(tipo)).findFirst().get();
        factorTraslado.setValorFT(valor);
    }
    public void agregarFactoresTraslado(FactorTraslado ... factores){
        Collections.addAll(this.factoresTraslado, factores);
    }

    public void agregarFactoresEmision(FactorEmision ... factores){
        Collections.addAll(this.factoresEmision, factores);
    }
    public Double getConstante() {
        return constante;
    }

    public void setConstante(Double constante) {
        this.constante = constante;
    }
}
