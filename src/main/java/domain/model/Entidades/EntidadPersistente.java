package domain.model.Entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Setter
@Getter
public abstract class EntidadPersistente {
    @Id
    @GeneratedValue
    private Integer id;
}
