package Organizacion;

public class CompartirTramosTest {/*
  Persona marge = new Persona("Marge", "Simpson", TipoDocumento.DNI, "123654789", null);
  Persona homero = new Persona("Homero Jimeno", "Simpson", TipoDocumento.DNI, "10564205", null);
  Persona bart = new Persona("Bartolomeo", "Simpson", TipoDocumento.DNI, "10564205", null);
  Persona lisa = new Persona("Lisa", "Simpson", TipoDocumento.DNI, "10564205", null);

  Punto casaSimpson = new Ubicacion(new Municipio("Budge", 1),new Provincia("BSAS"), "avenida siempre viva", "1042");
  Punto plantaNuclear = new Ubicacion(new Municipio("Palermo", 2),new Provincia("BSAS"), "calle falsa", "123");
  VehiculoParticular auto = new VehiculoParticular(TipoVehiculo.AUTO);
  Trayecto trayecto1 = new Trayecto();
  Tramo tramo1 = new Tramo(casaSimpson, plantaNuclear, auto, homero);
  Organizacion empresaBurns = new Organizacion("Monopolio energetico Burns");
  Sector sector7G = new Sector("7-G",empresaBurns);

  @Before
  public void init(){
    //Homero tiene un trayecto en auto
    trayecto1.agregarTramo(tramo1);
    empresaBurns.agregarSector(sector7G);
    homero.solicitarVinculacion(empresaBurns,sector7G);
    marge.solicitarVinculacion(empresaBurns, sector7G);
    empresaBurns.confirmarEmpleados();
    homero.agregarTrayecto(trayecto1);
    tramo1.compartirViaje(empresaBurns, new Persona[]{marge, lisa, bart});
  }

  @Test
  public void margeBartLisaTienenUnTramoAConfirmar(){
    Assert.assertEquals(1, marge.getSolicitudesViajesCompartidos().size());
    Assert.assertEquals(1, bart.getSolicitudesViajesCompartidos().size());
    Assert.assertEquals(1, lisa.getSolicitudesViajesCompartidos().size());
  }

  @Test
  public void margeTrabajaEnLaPlantaYViajaConHomero(){
    marge.confirmarViaje();
    assertEquals(marge, tramo1.getPasajeros().get(1));
  }

  @Test
  public void BartNoViajaConHomeroXqNoTrabajaEnLaPlanta(){
    try {
      bart.confirmarViaje();
      fail("No se lanzo excepcion");
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      assertEquals("El pasajero que se quiere agregar al tramo no pertenece a la organizacion", ex.getMessage());
    }
  }*/
}
