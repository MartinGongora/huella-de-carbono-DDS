package domain.model.Entidades;


import java.util.HashMap;
import java.util.Map;

public enum TipoOrganizacion {
    ONG,
    GUBERNAMENTAL,
    EMPRESA,
    INSTITUCION;

    private static Map<String, TipoOrganizacion> tipoOrganizacionMap = new HashMap<String, TipoOrganizacion>(2);

    static {
        tipoOrganizacionMap.put("ONG", ONG);
        tipoOrganizacionMap.put("GUBERNAMENTAL", GUBERNAMENTAL);
        tipoOrganizacionMap.put("EMPRESA", EMPRESA);
        tipoOrganizacionMap.put("INSTITUCION", INSTITUCION);
    }

    public static TipoOrganizacion forValue(String value){
        return tipoOrganizacionMap.get(value.toUpperCase());
    }
}
