package domain.model.Excepciones;

public class ExcepcionContraseniaDebil  extends RuntimeException {

    public ExcepcionContraseniaDebil() {
        super("La clave ingresada es debil");
    }

}
