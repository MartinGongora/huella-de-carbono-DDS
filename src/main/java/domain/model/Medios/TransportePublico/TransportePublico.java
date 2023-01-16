package domain.model.Medios.TransportePublico;

import domain.model.Entidades.Persona;
import domain.model.GeoReferencia.ViajeCompartido;
import domain.model.GeoReferencia.Punto;
import domain.model.Medios.MedioDeTransporte;
import domain.model.Medios.VehiculoParticular.TipoCombustible.TipoCombustible;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
@Entity
@DiscriminatorValue("transporte_publico")
@Getter
@Setter
@NoArgsConstructor
public  class TransportePublico extends MedioDeTransporte{

  @Column(name = "linea")
  private String linea;

  @Column(name = "ramal")
  private String ramal;
  @Enumerated(EnumType.STRING)
  private TipoTransportePublico tipoTransportePublico;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "lineaDeTransporte")
  private List<Estacion> estaciones;
  @Enumerated(EnumType.STRING)
  private TipoCombustible tipoCombustible;
  @Column
  private Double consumoXKm;

  public TransportePublico(String linea,TipoTransportePublico tipo) {
    this.linea = linea;
    this.tipoTransportePublico = tipo;
    this.estaciones = new ArrayList<>();
  }

  public TransportePublico(String linea,TipoTransportePublico tipo, String ramal){
    this.linea = linea;
    this.tipoTransportePublico = tipo;
    this.estaciones = new ArrayList<>();
    this.ramal = ramal;
  }

  public List<Estacion> getEstaciones() {
    return estaciones;
  }

  public void setEstaciones(List<Estacion> estaciones) {
    this.estaciones = estaciones;
  }

  @Override
  public double calcularDistancia(Punto puntoOrigen, Punto puntoDestino) {
    return Math.abs(puntoOrigen.getDistanciaAlOrigen() - puntoDestino.getDistanciaAlOrigen());
  }

  public void agregarEstacion(Estacion ... unaEstacion) {
    Collections.addAll(this.estaciones, unaEstacion);
  }

  @Override
  public void agregarSolicitudViajeCompartido(Persona persona, ViajeCompartido viajeCompartido){
  }

  public TipoCombustible getTipoCombustible() {
    return tipoCombustible;
  }

  public void setTipoCombustible(TipoCombustible tipoCombustible) {
    this.tipoCombustible = tipoCombustible;
  }

  public Double getConsumoXKm() {
    return consumoXKm;
  }

  public void setConsumoXKm(Double consumoXKm) {
    this.consumoXKm = consumoXKm;
  }
}
