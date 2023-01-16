package domain.model.Medios.TransportePublico;

import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.GeoReferencia.Punto;
import domain.model.GeoReferencia.Ubicacion;
import domain.model.Medios.MedioDeTransporte;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Estacion")
@Getter
@Setter
@NoArgsConstructor
public class Estacion extends Punto {
    @Column(name = "nombre")
    private String nombre;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estacion_anterior_id")
    private Estacion estacionAnterior;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estacion_siguiente_id")
    private Estacion estacionSiguiente;
    @Column(name = "distanciaAlOrigen")
    private Double distanciaAlOrigen;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "linea_de_transporte")
    private MedioDeTransporte lineaDeTransporte;

    public Estacion(String nombre, Ubicacion ubicacion, Estacion estacionAnterior, Estacion estacionSiguiente, Double distanciaAlOrigen) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.estacionAnterior = estacionAnterior;
        this.estacionSiguiente = estacionSiguiente;
        this.distanciaAlOrigen = distanciaAlOrigen;
    }

    public String mostrarDetalle(){
        return "Estacion: "+nombre;
    }

    public double calcularDistanciaAEstacionSiguiente() {
       return this.calcularDistancia(this.estacionSiguiente);
    }

    public double calcularDistanciaAEstacionAnterior() { return this.calcularDistancia(this.estacionAnterior); }

    public Double calcularDistancia(Punto unaEstacion) {
        return Math.abs(this.distanciaAlOrigen - unaEstacion.getDistanciaAlOrigen());
    }

    @Override
    public Double getDistanciaAlOrigen() {
        return distanciaAlOrigen;
    }

    @Override
    public Municipio getMunicipio() {
        return this.ubicacion.getMunicipio();
    }

    @Override
    public String getDireccion() {
        return this.ubicacion.getDireccion();
    }

    @Override
    public String getAltura() {
        return this.ubicacion.getAltura();
    }

    @Override
    public Provincia getProvincia() {
        return this.ubicacion.getProvincia();
    }

    @Override
    public void setDireccion(String direccion) {
        this.ubicacion.setDireccion(direccion);
    }

    @Override
    public void setAltura(String altura) {
        this.ubicacion.setAltura(altura);
    }

    @Override
    public void setProvincia(Provincia provincia) {
        this.ubicacion.setProvincia(provincia);
    }

    @Override
    public void setMunicipio(Municipio municipio) {
        this.ubicacion.setMunicipio(municipio);
    }

}
