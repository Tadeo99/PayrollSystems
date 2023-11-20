package pe.buildsoft.erp.core.domain.drools;

import java.util.List;

import jakarta.ejb.Local;

@Local
public interface IProcesarReglaUtil {

	<E> void procesarRegla(List<E> listaMemoria, String regla);

	<E> void procesarRegla(E memoria, String regla);
}
