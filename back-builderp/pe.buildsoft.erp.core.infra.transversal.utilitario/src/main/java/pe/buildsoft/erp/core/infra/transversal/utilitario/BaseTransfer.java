package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.util.List;
import java.util.Map;

import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ScriptSqlResulJDBCVO;

public class BaseTransfer {
	protected <T> T to(Object ressul, Class<T> entityClassEntity, boolean isFiltro, String... entityClasess) {
		return TransferDataUtil.to(ressul, entityClassEntity, isFiltro, entityClasess);
	}

	protected <T> T to(Object ressul, Class<T> entityClassEntity, String... entityClasess) {
		return TransferDataUtil.to(ressul, entityClassEntity, false, entityClasess);
	}

	protected <T> T toDTO(Object ressul, Class<T> entityClassEntity, String... entityClasess) {
		return TransferDataUtil.toDTO(ressul, entityClassEntity, entityClasess);
	}

	protected <T, E> List<T> toList(List<E> ressul, Class<T> entityClassEntity, String... entityClasess) {
		return TransferDataUtil.toList(ressul, entityClassEntity, entityClasess);
	}

	protected <T, E> List<T> toListEntity(List<E> ressulList, Class<T> entityClassEntity, boolean isFiltro,
			String... entityClasess) {
		return TransferDataUtil.toListEntity(ressulList, entityClassEntity, isFiltro, entityClasess);
	}

	protected <T, E> List<T> toListEntity(List<E> ressulList, Class<T> entityClassEntity, String... entityClasess) {
		return TransferDataUtil.toListEntity(ressulList, entityClassEntity, false, entityClasess);
	}

	protected Map<String, Object> toVOMap(Object ressul) {
		return TransferDataUtil.toVOMap(ressul);
	}

	protected Map<String, Object> toMap(Object ressul, boolean isNative, boolean isCargarManyToOne) {
		return TransferDataUtil.toMap(ressul, isNative, isCargarManyToOne);
	}

	protected Map<String, Object> toEntityMap(Object entity) {
		return TransferDataUtil.toEntityMap(entity);
	}

	protected <T> T toEntityVO(ScriptSqlResulJDBCVO scriptSqlResulJDBCVO, Class<T> entityClassEntity) {
		return TransferDataUtil.toEntityVO(scriptSqlResulJDBCVO, entityClassEntity);
	}

	protected static <T> List<T> toEntityListVO(ScriptSqlResulJDBCVO scriptSqlResulJDBCVO, Class<T> entityClassEntity) {
		return TransferDataUtil.toEntityListVO(scriptSqlResulJDBCVO, entityClassEntity);
	}

	protected <T> List<T> toEntityListVO(ScriptSqlResulJDBCVO scriptSqlResulJDBCVO, Class<T> entityClassEntity,
			Map<String, String> formatoFechaMap) {
		return TransferDataUtil.toEntityListVO(scriptSqlResulJDBCVO, entityClassEntity, formatoFechaMap);
	}

	protected Map<String, Map<String, Object>> toFiltroMap(Object ressul, Map<String, List<String>> listaObjeto,
			List<String> listaAtributo, boolean isExcluir, String nombreClase, Map<String, String> fechaFormatoMap) {
		return TransferDataUtil.toFiltroMap(ressul, listaObjeto, listaAtributo, isExcluir, nombreClase,
				fechaFormatoMap);
	}

	protected <T> T toPojo(Object ressul, Class<T> entityClassVO) {
		return TransferDataUtil.toPojo(ressul, entityClassVO);
	}

	public static Object getFieldValue(Object ressul,String nombreAtributo) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		return TransferDataUtil.getFieldValue(ressul, nombreAtributo);
	}
	public static void setField(Object ressul, Object object,String nombreAtributo) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		TransferDataUtil.setField(ressul, object, nombreAtributo);
	}

}
