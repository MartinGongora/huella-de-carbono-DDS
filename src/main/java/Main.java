import Server.Server;
import Server.CargadorBd;
import java.io.IOException;

import static spark.Spark.staticFiles;

public class Main {


/*
     public static void main(String[] args) {
         Organizacion utnOrg;
         Persona paco;

         paco = new Persona("Paco","Paco", TipoDocumento.DNI, "12345567", null);
         paco.setMail("ddstestmatias2@outlook.com");
         paco.setTelefono("5491161021739");
         utnOrg = new Organizacion("UTN");
         utnOrg.setTipo(TipoOrganizacion.INSTITUCION);
         utnOrg.setContactos(paco);

         Timer t= new Timer();
        ServicioNotificaciones servicioNotificaciones= new ServicioNotificaciones();
         servicioNotificaciones.setOrganizaciones(utnOrg);
         servicioNotificaciones.setMensaje("Este es un mensaje de prueba");
        t.scheduleAtFixedRate(servicioNotificaciones, 0, 10000);
    }
    */
  public static void main(String[] args) throws IOException {
    staticFiles.externalLocation("upload");
    new CargadorBd().run();
    new Server().run();
  }
}
