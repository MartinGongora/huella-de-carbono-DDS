package domain.model.GeoReferencia;

import domain.model.AgentesSectoriales.Municipio;
import domain.model.AgentesSectoriales.Provincia;
import domain.model.Entidades.EntidadPersistente;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "punto")
@DiscriminatorColumn(name = "discriminador")

public abstract class Punto extends EntidadPersistente {

    public abstract String mostrarDetalle();

    public abstract Double getDistanciaAlOrigen();

    public abstract Municipio getMunicipio();

    public abstract String getDireccion();

    public abstract String getAltura();

    public abstract Provincia getProvincia();

    public abstract void setDireccion(String direccion);

    public abstract void setAltura(String altura);

    public abstract void setProvincia(Provincia provincia);

    public abstract void setMunicipio(Municipio municipio);

}
