package pe.buildsoft.erp.core.domain.entidades.planilla.vo;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Renta5taVO {
	Map<String,Double> ingresosProyectadoMap = new HashMap<>();
	Map<String,Double> impuestoRentaMap = new HashMap<>();
}
