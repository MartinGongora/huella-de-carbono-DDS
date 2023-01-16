package domain.model.ValidadorPassword;

import domain.model.Excepciones.ExcepcionFaltaMayusculaONumero;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CondicionDeContenido implements CondicionDeValidacion {

    @Override
    public void validarPassword(String clave) {
        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]" );
        Matcher m = p.matcher(clave);
        if (!m.find())
            throw new ExcepcionFaltaMayusculaONumero();
    }
}