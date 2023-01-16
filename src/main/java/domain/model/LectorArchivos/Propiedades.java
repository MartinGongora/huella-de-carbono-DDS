package domain.model.LectorArchivos;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Propiedades {
    private Properties propiedades = new Properties();
    private InputStream entrada = null;
    private String token;


    public String LeerPropiedades(String archivo){
        try{
            this.entrada = new FileInputStream(archivo);
            propiedades.load(entrada);
            this.token = propiedades.getProperty("verificador") + " " + propiedades.getProperty("token");
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return this.token;
    }
}
