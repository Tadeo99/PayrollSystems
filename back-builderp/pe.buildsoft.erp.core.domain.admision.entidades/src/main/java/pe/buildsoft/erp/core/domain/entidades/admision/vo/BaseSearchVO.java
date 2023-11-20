package pe.buildsoft.erp.core.domain.entidades.admision.vo;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseSearch;

@Getter
@Setter
public class BaseSearchVO extends BaseSearch {

	@Transient
	private String nroDoc;
}
