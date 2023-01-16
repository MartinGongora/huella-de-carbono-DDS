package domain.model.GeoReferencia;

import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Entity
@DiscriminatorValue("Ubicacion")
@Setter
@Getter
@NoArgsConstructor
public class Ubicacion extends Punto {

    @Column(name = "direccion")
    private String direccion;
    @Column(name = "altura")
    private String altura;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "municipio_id")
    private Municipio municipio;

    public Ubicacion(Municipio municipio, Provincia provincia, String direccion, String altura){
        this.municipio = municipio;
        this.provincia = provincia;
        this.direccion = direccion;
        this.altura = altura;
    }
    @Override
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    @Override
    public void setAltura(String altura) {
        this.altura = altura;
    }

    @Override
    public void setProvincia(Provincia provincia){
        this.provincia = provincia;
    }

    @Override
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String mostrarDetalle() {
        return direccion+" "+altura;
    }

    @Override
    public Double getDistanciaAlOrigen() {
        return null;
    }

    @Override
    public Municipio getMunicipio() {
        return this.municipio;
    }
    @Override
    public String getDireccion() {
        return direccion;
    }
    @Override
    public String getAltura() {
        return altura;
    }

}
