package pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

@Getter
@Setter
public class RegistroHoraVO extends BaseEntidad implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	 
	 @Transient
	 private String id;
	 @Transient
	 private String idCentroCosto;
	 @Transient
	 private String nombre;
	 @Transient
	 private BigDecimal total;
	 @Transient
	 private List <RegistroHoraVO> listaDetalle = null;
}
