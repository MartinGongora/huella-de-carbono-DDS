package domain.model.Entidades.Mediciones;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Logistica")
public class ConsumoLogistica extends TipoDeConsumo{
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "medio_de_transporte")
    private String medioDeTransporte;
    @Column(name = "distancia_recorrida")
    private double distanciaMediaRecorrida;
    @Column(name = "peso_total_transportado")
    private double pesoTotalTransportado;


    public ConsumoLogistica(String categoria, String medioDeTransporte, double distanciaMediaRecorrida, double pesoTotalTransportado) {
        this.categoria = categoria;
        this.medioDeTransporte = medioDeTransporte;
        this.distanciaMediaRecorrida = distanciaMediaRecorrida;
        this.pesoTotalTransportado = pesoTotalTransportado;
        super.setTipo(Tipo.LOGISTICA);
        super.setUnidad(Unidad.KMxKG);
    }

    public ConsumoLogistica() {

    }

    @Override
    public double getValor() {
        return distanciaMediaRecorrida * pesoTotalTransportado;
    }

    public double getDistanciaMediaRecorrida() {
        return distanciaMediaRecorrida;
    }

    public double getPesoTotalTransportado() {
        return pesoTotalTransportado;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setMedioDeTransporte(String medioDeTransporte) {
        this.medioDeTransporte = medioDeTransporte;
    }

    public void setDistanciaMediaRecorrida(double distanciaMediaRecorrida) {
        this.distanciaMediaRecorrida = distanciaMediaRecorrida;
    }

    public void setPesoTotalTransportado(double pesoTotalTransportado) {
        this.pesoTotalTransportado = pesoTotalTransportado;
    }
}
