package domain.model.ValidadorPassword;

import domain.model.LectorArchivos.LectorArchivos;

import java.io.FileNotFoundException;

public class CondicionDePeores implements CondicionDeValidacion {
  private static final String LISTA_TOP10K_CLAVES= "10K-Contrasena.txt";
  private LectorArchivos lector;

    public CondicionDePeores() throws FileNotFoundException {
      //lector = new LectorArchivos(LISTA_TOP10K_CLAVES);   //TODO
    }

  @Override
  public void validarPassword(String clave) {

      /* //TODO
      try {
        if (lector.existeEnArchivo(clave))
        {
          throw new ExcepcionContraseniaDebil();
        }
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
      */
  }
}
