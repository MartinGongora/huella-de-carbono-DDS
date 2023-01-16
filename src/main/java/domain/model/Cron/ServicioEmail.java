package domain.model.Cron;

public class ServicioEmail {

    private EmailNotificaciones adapter;

    public  void enviarEmail(String email,String mensje){
        adapter.enviarEmail(email,mensje);
    }

    public void setAdapter(EmailNotificaciones adapter) {
        this.adapter = adapter;
    }


}
