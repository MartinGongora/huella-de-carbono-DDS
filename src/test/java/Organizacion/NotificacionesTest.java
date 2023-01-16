package Organizacion;

import domain.model.Cron.ServicioNotificaciones;
import domain.model.Entidades.*;
import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;
import domain.model.Entidades.TipoDocumento;
import domain.model.Entidades.TipoOrganizacion;
import org.junit.Before;
import org.junit.Test;
import java.util.Timer;

public class NotificacionesTest {/*

    Organizacion utnOrg;
    Persona paco;
    Timer t;

    @Before
    public void init(){
        paco = new Persona("Paco","Paco", TipoDocumento.DNI, "12345567", null);
        paco.setMail("matiasbp7@hotmail.com");
        paco.setTelefono("5491161021739");
        utnOrg = new Organizacion("UTN");
        utnOrg.setTipo(TipoOrganizacion.INSTITUCION);
        utnOrg.setContactos(paco);


    }
    @Test
    public void prueba(){
        t= new Timer();
        ServicioNotificaciones servicioNotificaciones= new ServicioNotificaciones();
        servicioNotificaciones.setOrganizaciones(utnOrg);
        servicioNotificaciones.setMensaje("Este es un mensaje de prueba");
  //      t.scheduleAtFixedRate(servicioNotificaciones, 0, 100000);
        servicioNotificaciones.run();
    }*/
}
