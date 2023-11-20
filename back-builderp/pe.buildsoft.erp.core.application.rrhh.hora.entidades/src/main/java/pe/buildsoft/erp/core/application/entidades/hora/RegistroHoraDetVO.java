package pe.buildsoft.erp.core.application.entidades.hora;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroHoraDetVO implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	   
	 private String id;
	 
	 private String req;
	 
	 private double dom;
	 
	 private double lu;
	 
	 private double ma;
	 
	 private double mi;
	 
	 private double ju;
	 
	 private double vi;
	 
	 private double sa;

}
