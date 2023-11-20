package pe.buildsoft.erp.core.application.servicios.rrhh.hora.reporte;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.ListaItemsDTO;
import pe.buildsoft.erp.core.infra.transversal.cache.AtributosEntityCacheUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.AtributoEntityVO;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.TransferDataOperUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.exel.ExcelUtil;

/**
 * <ul>
 * <li>Copyright 2017 ndavilal. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class Test.
 *
 * @author ndavilal
 * @version 1.0 , 07/04/2015
 * @since SIAA-CORE 2.1
 */
public class MigrarTablaTablaCommon {

	/**
	 * El metodo principal.
	 *
	 * @param args los argumentos
	 */
	public static void main(String[] args) {
		try {
			// hoja2
			System.out.println("Inici0 listaitem Procesando exel " + FechaUtil.obtenerFechaActual());
			File rutaArchivo = new File("D:\\elim\\t_registro\\Anexo2_PDT_PLAME_18MAR.xlsx");
			XSSFWorkbook hSSFWorkbook = ExcelUtil.leerExcelXlsx(rutaArchivo);
			Map<String, Integer> campoMappingExcelListaItemMap = new HashMap<>();
			campoMappingExcelListaItemMap.put("descripcion", 0);
			campoMappingExcelListaItemMap.put("idListaItems", 2);

			// N� DESCRIPCI�N ABREVIADA Observacion listaItemObservacion
			Map<String, Integer> campoMappingExcelItemDataMap = new HashMap<>();
			campoMappingExcelItemDataMap.put("codigoExterno", 0);
			campoMappingExcelItemDataMap.put("nombre", 1);
			campoMappingExcelItemDataMap.put("abreviatura", 2);
			campoMappingExcelItemDataMap.put("observacion", 3);
			campoMappingExcelItemDataMap.put("descripcion", 4);

			// Forma 1
			List<ListaItemsDTO> listaListaItems = new ArrayList<>();
			int contadorItem = 0;
			for (int i = 2; i <= (32 + 8); i++) {
				List<ListaItemsDTO> listaItemTemp = transferObjetoEntityExcelXlsxDTO(hSSFWorkbook, i, 1,
						campoMappingExcelListaItemMap, ListaItemsDTO.class, 1);
				for (var objListaItem : listaItemTemp) {
					StringBuilder observacion = new StringBuilder("");
					List<ItemDTO> listaDataItem = transferObjetoEntityExcelXlsxDTO(hSSFWorkbook, i, 4,
							campoMappingExcelItemDataMap, ItemDTO.class, null);
					for (var itemDTO : listaDataItem) {
						if (itemDTO.getNombre() != null) {
							itemDTO.setNombre(itemDTO.getNombre().replaceAll("\"\"", ""));
							itemDTO.setNombre(itemDTO.getNombre().replaceAll("'", ""));
						}
						if (StringUtil.isNotNullOrBlank(itemDTO.getDescripcion())) {
							observacion.append(itemDTO.getDescripcion());
							observacion.append("\n");
						}
						if (!StringUtil.isNotNullOrBlank(itemDTO.getAbreviatura())) {
							itemDTO.setAbreviatura(itemDTO.getNombre());
						}
						if (itemDTO.getAbreviatura() != null) {
							itemDTO.setAbreviatura(itemDTO.getAbreviatura().replaceAll("\"\"", ""));
							itemDTO.setAbreviatura(itemDTO.getAbreviatura().replaceAll("'", ""));
						}
					}
					objListaItem.setDescripcion(objListaItem.getDescripcion().replaceAll("\"\"", ""));
					objListaItem.setDescripcion(objListaItem.getDescripcion().replaceAll("'", ""));
					objListaItem.setObservacion(observacion.toString().replaceAll("\"\"", "").replaceAll("'", ""));
					contadorItem = contadorItem + listaDataItem.size();
					objListaItem.setListaItemsItemList(listaDataItem);
				}
				listaListaItems.addAll(listaItemTemp);

			}
			long generarCodigo = 1;
			System.out.println("lista Obtenida del XLSX " + listaListaItems.size());
			StringBuilder sql = new StringBuilder();
			sql.append("delete from commo.listaitems;\n");
			for (var objData : listaListaItems) {
				if (objData.getObservacion() == null) {
					objData.setObservacion("");
				}
				// objData.setIdListaItems(generarCodigo);
				sql.append("insert into commo.listaitems (idlistaitems,descripcion,observacion,estado) values("
						+ objData.getIdListaItems() + ", '" + objData.getDescripcion() + "','"
						+ objData.getObservacion() + "','A');\n");
				generarCodigo++;
			}
			crearArchivo("commo.listaItem", sql);
			System.out.println("fin commo.listaItem.sql " + FechaUtil.obtenerFechaActual());
			sql = null;
			System.out.println("Inici0 item Procesando exel " + FechaUtil.obtenerFechaActual());
			generarCodigo = 1;
			System.out.println("lista Obtenida del XLSX " + contadorItem);
			sql = new StringBuilder();
			sql.append("delete from commo.item;\n");
			for (var objData : listaListaItems) {
				int codigo = 1;
				generarCodigo = objData.getIdListaItems() * 10000;
				/*
				 * if (objData.getIdListaItems() == 30) { generarCodigo =
				 * objData.getIdListaItems() * 10000; }
				 */

				sql.append("/*INICIO DATOS DE  " + objData.getDescripcion() + " */\n");
				for (var dataItem : objData.getListaItemsItemList()) {
					if (!StringUtil.isNotNullOrBlank(dataItem.getObservacion())) {
						dataItem.setObservacion("");
					}
					if (dataItem.getAbreviatura() == null) {
						dataItem.setAbreviatura("");
					}
					sql.append(
							"insert into commo.item (iditem,idlistaitems,descripcion,nombre,abreviatura,codigo,codigoexterno,estado,observacion) values("
									+ generarCodigo + ", " + objData.getIdListaItems() + ",'" + objData.getDescripcion()
									+ "','" + dataItem.getNombre() + "','" + dataItem.getAbreviatura() + "'," + codigo
									+ ",'" + dataItem.getCodigoExterno() + "','A','" + dataItem.getObservacion()
									+ "');\n");
					generarCodigo++;
					codigo++;
				}
				sql.append("/*FIN DATOS DE  " + objData.getDescripcion() + " */\n");
			}
			crearArchivo("commo.item", sql);
			System.out.println("fin commo.item.sql " + FechaUtil.obtenerFechaActual());
			sql = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static <T> List<T> transferObjetoEntityExcelXlsxDTO(XSSFWorkbook workBook, int hoja, int filaData,
			Map<String, Integer> campoMappingExcelMap, Class<T> entityClassDTO, Integer cantidaData) {
		List<T> resultado = new ArrayList<T>();
		try {
			if (workBook == null) {
				return null;
			}
			List<AtributoEntityVO> listaAtributos = AtributosEntityCacheUtil.getInstance()
					.obtenerListaAtributos(entityClassDTO);
			XSSFSheet hssfSheet = workBook.getSheetAt(hoja - 1);
			int contador = 0;
			int cantidadNulos = 0;
			int cantidaDataProcesar = 0;
			while (cantidadNulos <= 2) {
				XSSFRow hssfRow = hssfSheet.getRow(contador);
				if (hssfRow == null) {
					cantidadNulos++;
				}
				contador++;
				if (contador >= filaData) {
					cantidaDataProcesar++;
					if (hssfRow != null) {
						T resultadoTemp = entityClassDTO.getDeclaredConstructor().newInstance();
						for (var objAtr : listaAtributos) {
							if (campoMappingExcelMap.containsKey(objAtr.getNombreAtributo())) {
								Field f = resultadoTemp.getClass().getDeclaredField(objAtr.getNombreAtributo());
								f.setAccessible(true);
								Object value = TransferDataOperUtil.obtenerValorXlsx(hssfRow, null,
										campoMappingExcelMap.get(objAtr.getNombreAtributo()), objAtr);
								if (value != null) {
									f.set(resultadoTemp, value);
								}
							}
						}
						resultado.add(resultadoTemp);
					}

					if (cantidaData != null) {
						if (cantidaDataProcesar == cantidaData) {
							break;
						}
					}
				}

			}
			if (workBook != null) {
				workBook.close();
			}
		} catch (Exception e) {
			System.err.println(
					"Error TransferDataObjectUtil.transferObjetoEntityExcelXlsxDTO(Object ressul,Class<T> entityClassDTO) al parsear "
							+ entityClassDTO.getName() + "  " + e.getMessage());
		}
		return resultado;
	}

	private static void crearArchivo(String nombre, StringBuilder data) {
		try {
			String ruta = "d:/elim/t_registro/" + nombre + ".sql";
			File archivo = new File(ruta);
			BufferedWriter bw;
			archivo.delete();
			if (archivo.exists()) {
				archivo.delete();
			}
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(data.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
