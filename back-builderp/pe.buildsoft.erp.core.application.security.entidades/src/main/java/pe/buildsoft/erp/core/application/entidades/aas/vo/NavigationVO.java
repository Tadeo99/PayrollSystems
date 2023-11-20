package pe.buildsoft.erp.core.application.entidades.aas.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NavigationVO implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private List<NavigationItemVO> compact = new ArrayList<>();
	private List<NavigationItemVO> defaults = new ArrayList<>();
	private List<NavigationItemVO> futuristic = new ArrayList<>();
	private List<NavigationItemVO> horizontal = new ArrayList<>();
}
