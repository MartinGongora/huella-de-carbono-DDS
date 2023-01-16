package domain.model.Excepciones;

public class ExcepcionNoPerteneceALaOrganizacion extends RuntimeException{
  public ExcepcionNoPerteneceALaOrganizacion() {
    super("El pasajero que se quiere agregar al tramo no pertenece a la organizacion");
  }
}
