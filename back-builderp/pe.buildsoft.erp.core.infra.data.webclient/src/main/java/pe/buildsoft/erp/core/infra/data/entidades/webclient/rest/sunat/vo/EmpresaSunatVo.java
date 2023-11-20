package pe.buildsoft.erp.core.infra.data.entidades.webclient.rest.sunat.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaSunatVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ruc;
	private String razonSocial;
	private String nombreComercial;
	private List<String> telefonos;
	private String tipo;
	private String estado;
	private String condicion;
	private String direccion;
	private String departamento;
	private String provincia;
	private String distrito;
	private String fechaInscripcion;
	private String sistEmsion;
	private String sistContabilidad;
	private String actExterior;
	private List<String> actEconomicas;
	private List<String> cpPago;
	private List<String> sistElectronica;
	private String fechaEmisorFe;
	private List<String> cpeElectronico;
	private String fechaPle;
	private List<String> padrones;
	private String fechaBaja;
	private String profesion;
	private String ubigeo;
	private String capital;

}
