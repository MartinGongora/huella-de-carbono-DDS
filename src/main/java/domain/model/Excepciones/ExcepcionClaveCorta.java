package domain.model.Excepciones;

public class ExcepcionClaveCorta extends RuntimeException {
    public ExcepcionClaveCorta(){
        super("La password ingresada es muy corta");
    }
}
