package domain.model.ValidadorPassword;

import java.io.FileNotFoundException;
import java.io.IOException;

import domain.model.Excepciones.ExcepcionContraseniaDebil;
import domain.model.LectorArchivos.LectorArchivos;

public class Archivo {

    private static final String LISTA_TOP10K_CLAVES = "10K-Contrasena.txt";
    private LectorArchivos lector = new LectorArchivos(LISTA_TOP10K_CLAVES);

    public Archivo() throws FileNotFoundException {
    }

    public void validarClave(String password) {
        try {
            if (lector.existeEnArchivo(password))
            {
                throw new ExcepcionContraseniaDebil();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
