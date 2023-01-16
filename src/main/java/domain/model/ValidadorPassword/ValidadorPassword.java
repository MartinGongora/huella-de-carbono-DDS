package domain.model.ValidadorPassword;

import java.util.ArrayList;
import java.util.List;

public class ValidadorPassword {
        public List<CondicionDeValidacion> validadores = new ArrayList<>();

        public ValidadorPassword(){
        }

        public void agregarCondicionDeValidacion(CondicionDeValidacion condicion){
            validadores.add(condicion);
        }

        public void validarClave(String clave){
            validadores.forEach(condicionDeValidacion -> condicionDeValidacion.validarPassword(clave));
        }
    }
