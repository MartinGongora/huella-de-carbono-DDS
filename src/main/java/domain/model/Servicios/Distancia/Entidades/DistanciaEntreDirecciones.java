package domain.model.Servicios.Distancia.Entidades;

public class DistanciaEntreDirecciones {
    public double valor;
    public String unidad;

    public DistanciaEntreDirecciones(double valor, String unidad) {
        this.valor = valor;
        this.unidad = unidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public double getValor() {
        return valor;
    }
}
