package pe.buildsoft.erp.core.application.entidades.aas.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadgeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private String classes;
}
