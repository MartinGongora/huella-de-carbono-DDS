package domain.model.Entidades.Mediciones;

import domain.model.Entidades.Organizacion;
import domain.repositories.RepositorioOrganizaciones;

import java.io.IOException;

public class CargarMediciones extends Thread{
  private Organizacion organizacion;
  private GeneradorMediciones generadorMediciones;

  private RepositorioOrganizaciones repositorioOrganizaciones;

  public CargarMediciones(Organizacion organizacion, GeneradorMediciones generadorMediciones) {
    this.organizacion = organizacion;
    this.generadorMediciones = generadorMediciones;
    this.repositorioOrganizaciones = new RepositorioOrganizaciones();
  }

  public void run(){
    try {
      generadorMediciones.procesarExcel(organizacion);
      repositorioOrganizaciones.modificar(organizacion);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
