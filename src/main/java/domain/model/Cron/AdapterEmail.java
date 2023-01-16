package domain.model.Cron;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AdapterEmail implements EmailNotificaciones {
    private final static String propertiesFile = "src/main/java/domain/model/Cron/Email.properties";
    private String SERVIDOR_SMTP;
    private int PORTA_SERVIDOR_SMTP;
    private String CONTA_PADRAO;
    private String SENHA_CONTA_PADRAO;
    private String sender;
    private String emailsubject;
    private Properties propiedades;
    private InputStream entrada;

    public AdapterEmail() {
        this.propiedades = new Properties();
        this.LeerPropiedades(propertiesFile);
    }

    public void enviarEmail(String destino, String msg) {
        final Session newsession = Session.getInstance(this.Eprop(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
            }
        });
        try {
            final Message newmes = new MimeMessage(newsession);
            newmes.setRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            newmes.setFrom(new InternetAddress(sender));
            newmes.setSubject(emailsubject);
            newmes.setText(msg);
            newmes.setSentDate(new Date());
            Transport.send(newmes);
            System.out.println("Email sent!");
        } catch (final MessagingException ex) {
            System.out.println("Segui participando");
        }
    }

    public Properties Eprop() {
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", SERVIDOR_SMTP);
        config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);
        return config;
    }

    public void LeerPropiedades(String archivo) {
        try {
            this.entrada = new FileInputStream(archivo);
            this.propiedades.load(entrada);
            this.SERVIDOR_SMTP = propiedades.getProperty("SERVIDOR_SMTP");
            this.PORTA_SERVIDOR_SMTP = Integer.parseInt(propiedades.getProperty("PORTA_SERVIDOR_SMTP"));
            this.CONTA_PADRAO = propiedades.getProperty("CONTA_PADRAO");
            this.SENHA_CONTA_PADRAO = propiedades.getProperty("SENHA_CONTA_PADRAO");
            this.sender =  propiedades.getProperty("SENDER");
            this.emailsubject =  propiedades.getProperty("EMAIL_SUBJECT");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
