package domain.model.Excepciones;

public class ExcepcionFaltaMayusculaONumero extends RuntimeException {

    public ExcepcionFaltaMayusculaONumero() {
        super("La clave ingresada carece de numero, miniuscula o mayuscula");
    }
}
