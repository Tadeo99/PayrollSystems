package pe.buildsoft.erp.core.infra.transversal.entidades;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasePagination {
	int length;
	int size;
	int page;
	int lastPage;
	int startIndex;
	int endIndex;
}
