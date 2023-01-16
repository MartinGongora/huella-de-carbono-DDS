package domain.model.Cron;

import domain.model.Entidades.Organizacion;
import domain.model.Entidades.Persona;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

public class ServicioNotificaciones extends TimerTask {

    private List<Organizacion> organizaciones;
    private ServicioEmail mailService;
    private ServicioWhatsapp whatsappService;
    private String mensaje;

    public ServicioNotificaciones() {
        this.whatsappService = new ServicioWhatsapp();
        this.mailService = new ServicioEmail();
        this.organizaciones = new ArrayList<>();
        this.whatsappService.setAdapter(new AdapterWhatsapp());
        this.mailService.setAdapter(new AdapterEmail());
    }

    @Override
    public void run() {

        for (Organizacion organizacion : organizaciones) {

            List<Persona> contactosDeOrganizacion = organizacion.getContactos();

            for (Persona contacto : contactosDeOrganizacion) {

                whatsappService.enviarMensaje(contacto.getTelefono(), this.mensaje);

                mailService.enviarEmail(contacto.getMail(), this.mensaje);

            }

        }
    }

    public void setOrganizaciones(Organizacion... organizaciones) {
        Collections.addAll(this.organizaciones, organizaciones);
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    private void setearAdapters(){

    }
}
