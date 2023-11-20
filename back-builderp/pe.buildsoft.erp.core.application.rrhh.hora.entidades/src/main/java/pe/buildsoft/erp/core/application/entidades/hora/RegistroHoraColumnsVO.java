package pe.buildsoft.erp.core.application.entidades.hora;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroHoraColumnsVO implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	   
	 /** El id detalle registro cabecera. */
	 private String key;
	 
	 private String label;
	 
	 private String fecha;
	 
	 private boolean editable;

}
