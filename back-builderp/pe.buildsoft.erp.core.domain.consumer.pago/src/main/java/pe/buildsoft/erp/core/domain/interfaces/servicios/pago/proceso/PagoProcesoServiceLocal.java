package pe.buildsoft.erp.core.domain.interfaces.servicios.pago.proceso;

import java.util.List;

import jakarta.ejb.Local;

import pe.buildsoft.erp.core.domain.entidades.pago.ControlPago;
import pe.buildsoft.erp.core.domain.entidades.pago.vo.SunatDatosVO;

/**
 * La Class PagoServiceLocal.
 * <ul>
 * <li>Copyright 2020 ndavilal - ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:13 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Local
public interface PagoProcesoServiceLocal {

	List<SunatDatosVO> generarExtracionTXTControlPago();

	List<ControlPago> listaControlPagoExtracionF();

	List<SunatDatosVO> generarComprobante(SunatDatosVO sfs);

	List<SunatDatosVO> enviarComprobante(SunatDatosVO sfs);

	List<SunatDatosVO> eliminarBandeja();

	List<SunatDatosVO> actualizarBandeja();

}