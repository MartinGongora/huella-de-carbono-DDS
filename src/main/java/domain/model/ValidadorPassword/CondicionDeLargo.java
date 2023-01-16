package domain.model.ValidadorPassword;

import domain.model.Excepciones.ExcepcionClaveCorta;

public class CondicionDeLargo implements CondicionDeValidacion {

    private static final int LARGO_CLAVE_MINIMO = 8;

    @Override
    public void validarPassword( String clave) throws ExcepcionClaveCorta
    {
        if (clave.length() < LARGO_CLAVE_MINIMO)
        {
            throw new ExcepcionClaveCorta();
        }
    }

}
