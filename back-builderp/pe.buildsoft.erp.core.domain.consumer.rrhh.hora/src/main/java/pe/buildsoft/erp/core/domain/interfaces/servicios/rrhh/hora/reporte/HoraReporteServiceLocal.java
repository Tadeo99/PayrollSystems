package pe.buildsoft.erp.core.domain.interfaces.servicios.rrhh.hora.reporte;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.rrhh.hora.vo.RegistroHoraReporteVO;

@Local
public interface HoraReporteServiceLocal {

	String procesarReporteRegistroHora(RegistroHoraReporteVO objFiltro);

}
