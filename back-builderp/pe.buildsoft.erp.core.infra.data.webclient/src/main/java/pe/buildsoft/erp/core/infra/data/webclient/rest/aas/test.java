package pe.buildsoft.erp.core.infra.data.webclient.rest.aas;

import java.util.ArrayList;
import java.util.List;

public class test {

	public static void main(String[] args) throws InterruptedException {

		List<CobranzaVo> listaDAta = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			CobranzaVo objData = new CobranzaVo(); 
			// NUM_POLIZA_GRUPO COD_CIA NUM_UNICO_ITEM COD_MON IMP_COBRADO TIP_SITUACION
			// NUM_RECIBO NUM_POLIZA COD_PRODUCTO ANIOMES
			objData.setNombres("3013012");
			objData.setCodigoUnico( "1");
			objData.setEstado("999404");
			objData.setApellidoMaterno("1");
			objData.setApellidoPaterno("150");
			objData.setIdItemByEstadoCivil(1L);
			objData.setTelefono1("9999999999999");
			objData.setTelefono2("9999999999999");
			objData.setCelular("13");
			objData.setNroTarjeta("202212");

			listaDAta.add(objData);
		}
		System.out.println(listaDAta.size());
		 Thread.sleep(100000);
	}
}
