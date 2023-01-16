package ValidadorDeClaves;
import domain.model.ValidadorPassword.CondicionDeLargo;
import domain.model.ValidadorPassword.ValidadorPassword;
import domain.model.ValidadorPassword.CondicionDeContenido;
import domain.model.ValidadorPassword.CondicionDePeores;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class Validador {
    ValidadorPassword validador;
    @Before
    public void init() throws FileNotFoundException {
        validador = new ValidadorPassword();
        validador.agregarCondicionDeValidacion(new CondicionDeLargo());
        validador.agregarCondicionDeValidacion(new CondicionDePeores());
        validador.agregarCondicionDeValidacion(new CondicionDeContenido());
    }

    @Test
    public void claveCorta() {
        try {
            validador.validarClave("Clave1");
            fail("No se lanzo excepcion");
        } catch (Exception ex) {
            assertEquals("La password ingresada es muy corta", ex.getMessage());
        }
    }

    @Test
    public void claveSinMinusculas(){
        try {
            validador.validarClave("RFRT23999");
            fail("No se lanzo excepcion");
        } catch (Exception ex) {
            assertEquals("La clave ingresada carece de numero, miniuscula o mayuscula", ex.getMessage());
        }
    }

    @Test
    public void claveSinMayusculas(){
        try {
            validador.validarClave("abcde1234");
            fail("No se lanzo excepcion");
        } catch (Exception ex) {
            assertEquals("La clave ingresada carece de numero, miniuscula o mayuscula", ex.getMessage());
        }
    }

    @Test
    public void claveSinNumero(){
        try {
            validador.validarClave("abcdeABCDE");
            fail("No se lanzo excepcion");
        } catch (Exception ex) {
            assertEquals("La clave ingresada carece de numero, miniuscula o mayuscula", ex.getMessage());
        }
    }

    @Test
    public void claveDelArchivo(){
        try {
            validador.validarClave("12345678");//Contraseña perteneciente al archivo de las 10k peores
            fail("No se lanzo excepcion");
        } catch (Exception ex) {
            assertEquals("La clave ingresada es debil", ex.getMessage());
        }
    }
}
