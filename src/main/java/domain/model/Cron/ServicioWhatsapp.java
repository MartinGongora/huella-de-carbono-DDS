package domain.model.Cron;

public class ServicioWhatsapp {

    private WhatsappNotificaciones adapter;


    public void enviarMensaje(String numero,String mensaje){
        adapter.enviarMensaje(numero,mensaje);
    }

    public void setAdapter(WhatsappNotificaciones adapter) {
        this.adapter = adapter;
    }
}
