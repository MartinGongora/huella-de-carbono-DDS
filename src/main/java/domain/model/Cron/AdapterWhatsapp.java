package domain.model.Cron;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AdapterWhatsapp implements WhatsappNotificaciones {

    private final static String propertiesFile = "src/main/java/domain/model/Cron/whatsapp.properties";
    private String accountSid;
    private String token;
    private String numeroApp;
    private Properties propiedades;
    private InputStream entrada;

    public AdapterWhatsapp() {
        this.propiedades = new Properties();
        this.LeerPropiedades(propertiesFile);

    }

    public void enviarMensaje(String numero, String mensaje) {
        Twilio.init(this.accountSid, this.token);
        Message message = Message.creator(

                        new com.twilio.type.PhoneNumber("whatsapp:+" + numero),
                        new com.twilio.type.PhoneNumber(this.numeroApp),mensaje).create();
    }

    public void LeerPropiedades(String archivo) {
        try {
            this.entrada = new FileInputStream(archivo);
            this.propiedades.load(entrada);
            this.accountSid = propiedades.getProperty("ACCOUNT_SID");
            this.token = propiedades.getProperty("AUTH_TOKEN");
            this.numeroApp = propiedades.getProperty("WHATSAPP");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
