package pe.buildsoft.erp.core.domain.drools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.buildsoft.erp.core.domain.type.LimpiarVaciosType;
import pe.buildsoft.erp.core.domain.type.RedondearType;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ReglaVO;
import pe.buildsoft.erp.core.infra.transversal.entidades.vo.ValueDataVO;
import pe.buildsoft.erp.core.infra.transversal.type.TipoCampoType;
import pe.buildsoft.erp.core.infra.transversal.utilitario.FechaUtil;
import pe.buildsoft.erp.core.infra.transversal.utilitario.StringUtil;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class UtilitarioReglaUtil.
 *
 * @author ndavilal
 * @version 1.0, Thu Jul 31 10:21:30 COT 2017
 * @since SIAA-CORE 2.1
 */
public class R {

	private static final String ERROR = "${ERROR}";
	private static final String ERROR_ERROR_AL_EXTRAER = "${ERROR} ERROR AL EXTRAER ";
	private static final String ERROR_ERROR_AL_EXTRAER_NO_ES_UN_DATO_DEL_TIPO_CADENA = "${ERROR} ERROR AL EXTRAER, NO ES UN DATO DEL TIPO CADENA ";
	private static final String ERROR_NO_ES_UN_TIPO_VALIDO_PARA_LIMPIAR_VACIOS = "${ERROR} NO ES UN TIPO VALIDO PARA LIMPIAR VACIOS ";
	private static final String VALOR_DE_LA_FECHA_ES_NULO_O_NO_ES_EL_CORRECTO = " VALOR DE LA FECHA ES NULO O NO ES EL CORRECTO ";
	private static final String ERROR_EL_FORMATO_NO_ES_CORRECTO = "${ERROR} EL FORMATO NO ES CORRECTO ";
	private static final String ERROR_NO_SE_PUEDE_REDONDEAR_UN_CAMPO_QUE_NO_ES_NUMERICO = "${ERROR} NO SE PUEDE REDONDEAR UN CAMPO QUE NO ES NUMERICO ";
	private static final String ERROR_HUBO_UN_ERROR_AL_DIVIR_NO_ES_UN_NUMERO_VALIDO = "${ERROR} HUBO UN ERROR AL DIVIR NO ES UN NUMERO VALIDO ";
	private static final String ERROR_HUBO_UN_ERROR_AL_MULTIPLICAR_NO_ES_UN_NUMERO_VALIDO = "${ERROR} HUBO UN ERROR AL multiplicar NO ES UN NUMERO VALIDO ";
	/** La log. */
	private static Logger log = LoggerFactory.getLogger(R.class);
	/** La Constante ES. */
	private static final Locale ES = new Locale("es", "PE");

	/** La Constante LTRIM. */
	private final static Pattern LTRIM = Pattern.compile("^\\s+");

	/** La Constante RTRIM. */
	private final static Pattern RTRIM = Pattern.compile("\\s+$");

	/**
	 * Devolver campo.
	 *
	 * @param cnf el cnf
	 * @param key el key
	 * @return the value data vo
	 */
	public static ValueDataVO devolverCampo(ReglaVO cnf, String key) {
		ValueDataVO resultado = new ValueDataVO();
		if (cnf.getDataTupaMap().containsKey(key)) {
			resultado = new ValueDataVO(cnf.getDataTupaMap().get(key));
		} else {
			resultado.setData("${ERROR} NO EXISTE  CAMPO " + key);
		}
		return resultado;
	}

	/**
	 * Devolver campo.
	 *
	 * @param cnf el cnf
	 * @param key el key
	 * @return the value data vo
	 */
	public static ValueDataVO devolverCampo(ReglaVO cnf, String key, String formato) {
		ValueDataVO resultado = new ValueDataVO();
		try {
			if (cnf.getDataTupaMap().containsKey(key)) {
				resultado = new ValueDataVO(cnf.getDataTupaMap().get(key));
				resultado = formatearFecha(resultado, formato);
			} else {
				resultado.setData("${ERROR} NO EXISTE  CAMPO " + key);
			}
		} catch (Exception e) {
			resultado.setData("${ERROR} EL CAMPO NO ES DEL TIPO FECHA O ES NULO " + key);
		}
		return resultado;
	}

	/**
	 * Sumar.
	 *
	 * @param objects el objects
	 * @return the value data vo
	 */
	public static ValueDataVO sumar(Object... objects) {
		ValueDataVO resultado = new ValueDataVO();
		BigDecimal suma = BigDecimal.ZERO;
		try {
			for (var object : objects) {
				if (object instanceof Number) {
					suma = suma.add(new BigDecimal(object.toString()));
				} else if (object instanceof ValueDataVO) {
					ValueDataVO valueDataTemp = (ValueDataVO) object;
					BigDecimal bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
					suma = suma.add(bigDecimal);
				} else {
					resultado.setData("${ERROR} HUBO UN ERROR AL SUMAR NO ES UN NUMERO VALIDO ");
					return resultado;
				}
			}
		} catch (Exception e) {
			resultado.setData("${ERROR} HUBO UN ERROR AL SUMAR NO ES UN NUMERO VALIDO ");
			return resultado;
		}
		resultado.setData(suma);
		return resultado;
	}

	public static ValueDataVO sumar(ReglaVO cnf, Object... objects) {
		ValueDataVO resultado = new ValueDataVO();
		BigDecimal suma = BigDecimal.ZERO;
		try {
			for (var object : objects) {
				if (object instanceof Number) {
					suma = suma.add(new BigDecimal(object.toString()));
				} else if (object instanceof String) {
					ValueDataVO valueDataTemp = cnf.getDataTupaMap().get(object);
					if (valueDataTemp != null) {
						BigDecimal bigDecimal = null;
						if (valueDataTemp.getData() instanceof BigDecimal dataValue) {
							bigDecimal = dataValue;
						} else {
							bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
						}
						suma = suma.add(bigDecimal);
					}
				} else if (object instanceof ValueDataVO valueDataTemp) {
					BigDecimal bigDecimal = null;
					if (valueDataTemp.getData() instanceof BigDecimal dataValue) {
						bigDecimal = dataValue;
					} else {
						bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
					}
					suma = suma.add(bigDecimal);
				} else {
					resultado.setData("${ERROR} HUBO UN ERROR AL SUMAR NO ES UN NUMERO VALIDO " + object);
					return resultado;
				}
			}
		} catch (Exception e) {
			resultado.setData("${ERROR} HUBO UN ERROR AL SUMAR NO ES UN NUMERO VALIDO ");
			return resultado;
		}
		resultado.setData(suma);
		return resultado;
	}

	public static ValueDataVO porcentaje(ReglaVO cnf, String porcentaje, Object... objects) {
		ValueDataVO resultado = new ValueDataVO();
		BigDecimal suma = BigDecimal.ZERO;
		try {
			for (var object : objects) {
				if (object instanceof Number) {
					suma = suma.add(new BigDecimal(object.toString()));
				} else if (object instanceof String) {
					ValueDataVO valueDataTemp = cnf.getDataTupaMap().get(object);
					if (valueDataTemp != null) {
						BigDecimal bigDecimal = null;
						if (valueDataTemp.getData() instanceof BigDecimal dataValue) {
							bigDecimal = dataValue;
						} else {
							bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
						}
						suma = suma.add(bigDecimal);
					}
				} else if (object instanceof ValueDataVO valueDataTemp) {
					if (valueDataTemp != null) {
						BigDecimal bigDecimal = null;
						if (valueDataTemp.getData() instanceof BigDecimal dataValue) {
							bigDecimal = dataValue;
						} else {
							bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
						}
						suma = suma.add(bigDecimal);
					}
				} else {
					resultado.setData("${ERROR} HUBO UN ERROR AL PORCENTAJE NO ES UN NUMERO VALIDO " + object);
					return resultado;
				}
			}
			BigDecimal porcentajeCal = (new BigDecimal(porcentaje)).divide(new BigDecimal("100"), 2,
					RoundingMode.HALF_DOWN);
			suma = suma.divide(porcentajeCal, 2, RoundingMode.HALF_DOWN);
		} catch (Exception e) {
			resultado.setData("${ERROR} HUBO UN ERROR AL PORCENTAJE NO ES UN NUMERO VALIDO " + porcentaje);
			return resultado;
		}
		resultado.setData(suma);
		return resultado;
	}

	public static ValueDataVO rangoUIT(ReglaVO cnf, Object valor) {
		ValueDataVO resultado = new ValueDataVO();
		ValueDataVO uit = cnf.getDataTupaMap().get("UIT");
		List<BigDecimal> tramaos = new ArrayList<>();
		Map<BigDecimal, BigDecimal> rangoInicioMap = new HashMap<>();
		Map<BigDecimal, BigDecimal> rangoFinMap = new HashMap<>();

		BigDecimal keyPorcentaje = new BigDecimal("0.08");
		tramaos.add(keyPorcentaje);
		rangoInicioMap.put(keyPorcentaje, BigDecimal.ZERO);
		rangoFinMap.put(keyPorcentaje, new BigDecimal("5"));

		keyPorcentaje = new BigDecimal("0.14");
		tramaos.add(keyPorcentaje);
		rangoInicioMap.put(keyPorcentaje, new BigDecimal("5"));
		rangoFinMap.put(keyPorcentaje, new BigDecimal("20"));

		keyPorcentaje = new BigDecimal("0.17");
		tramaos.add(keyPorcentaje);
		rangoInicioMap.put(keyPorcentaje, new BigDecimal("20"));
		rangoFinMap.put(keyPorcentaje, new BigDecimal("35"));

		keyPorcentaje = new BigDecimal("0.20");
		tramaos.add(keyPorcentaje);
		rangoInicioMap.put(keyPorcentaje, new BigDecimal("35"));
		rangoFinMap.put(keyPorcentaje, new BigDecimal("45"));

		keyPorcentaje = new BigDecimal("0.30");
		tramaos.add(keyPorcentaje);
		rangoInicioMap.put(keyPorcentaje, new BigDecimal("45"));

		if (uit != null && uit.getData() != null) {
			BigDecimal valorUIT = new BigDecimal(uit.getData() + "");
			BigDecimal rentaBurtaTrabajo = new BigDecimal(valor + "");
			if (valor instanceof ValueDataVO dataValue) {
				resultado = new ValueDataVO(dataValue);
			} else {
				resultado.setData(valor);
			}
			if (valorUIT != null && rentaBurtaTrabajo != null) {
				BigDecimal impuesto = BigDecimal.ZERO;
				for (var porcentaje : tramaos) {
					BigDecimal rangoInicio = BigDecimal.ZERO;
					BigDecimal rangoFin = BigDecimal.ZERO;
					if (rangoInicioMap.containsKey(porcentaje) && rangoFinMap.containsKey(porcentaje)) {
						rangoInicio = rangoInicioMap.get(porcentaje).multiply(valorUIT);
						rangoFin = rangoFinMap.get(porcentaje).multiply(valorUIT);
						if (!(rentaBurtaTrabajo.compareTo(rangoInicio) > 0
								&& rangoFin.compareTo(rentaBurtaTrabajo) <= 0)) {
							impuesto = impuesto.add((rangoFin.subtract(rangoInicio)).multiply(porcentaje));
						} else {
							impuesto = impuesto.add((rentaBurtaTrabajo.subtract(rangoInicio)).multiply(porcentaje));
							break;
						}
					} else {
						if (rangoInicioMap.containsKey(porcentaje)) {
							rangoInicio = rangoInicioMap.get(porcentaje).multiply(valorUIT);
							if (rentaBurtaTrabajo.compareTo(rangoInicio) > 0) {
								impuesto = impuesto.add((rentaBurtaTrabajo.subtract(rangoInicio)).multiply(porcentaje));
							}
						}
					}
				}
				resultado.setData(impuesto);
			}
		}

		return resultado;
	}

	/**
	 * Restar.
	 *
	 * @param objects el objects
	 * @return the value data vo
	 */
	public static ValueDataVO restar(Object... objects) {
		ValueDataVO resultado = new ValueDataVO();
		BigDecimal resta = BigDecimal.ZERO;
		Integer contador = 0;
		try {
			for (var object : objects) {
				if (object instanceof Number) {
					if (contador > 0) {
						resta = resta.subtract(new BigDecimal(object.toString()));
					} else {
						resta = new BigDecimal(object.toString());
					}
				} else if (object instanceof ValueDataVO valueDataTemp) {
					BigDecimal bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
					if (contador > 0) {
						resta = resta.subtract(bigDecimal);
					} else {
						resta = bigDecimal;
					}
				} else {
					resultado.setData("${ERROR} HUBO UN ERROR AL RESTAR NO ES UN NUMERO VALIDO ");
					return resultado;
				}
				contador++;
			}
		} catch (Exception e) {
			log.error("restar", e);
			resultado.setData("${ERROR} HUBO UN ERROR AL RESTAR NO ES UN NUMERO VALIDO ");
			return resultado;
		}
		resultado.setData(resta);
		return resultado;
	}

	public static ValueDataVO restar(ReglaVO cnf, Object... objects) {
		ValueDataVO resultado = new ValueDataVO();
		BigDecimal resta = BigDecimal.ZERO;
		try {
			int index = 0;
			for (var object : objects) {
				index++;
				if (object instanceof Number) {
					if (index == 1) {
						resta = new BigDecimal(object.toString());
					} else {
						resta = resta.subtract(new BigDecimal(object.toString()));
					}

				} else if (object instanceof String) {
					ValueDataVO valueDataTemp = cnf.getDataTupaMap().get(object);
					if (valueDataTemp != null) {
						BigDecimal bigDecimal = null;
						if (valueDataTemp.getData() instanceof BigDecimal dataValue) {
							bigDecimal = dataValue;
						} else {
							bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
						}
						if (index == 1) {
							resta = bigDecimal;
						} else {
							resta = resta.subtract(bigDecimal);
						}

					}
				} else if (object instanceof ValueDataVO valueDataTemp) {
					BigDecimal bigDecimal = null;
					if (valueDataTemp.getData() instanceof BigDecimal valueData) {
						bigDecimal = valueData;
					} else {
						bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
					}
					if (index == 1) {
						resta = bigDecimal;
					} else {
						resta = resta.subtract(bigDecimal);
					}
				} else {
					resultado.setData("${ERROR} HUBO UN ERROR AL restar NO ES UN NUMERO VALIDO " + object);
					return resultado;
				}
			}
		} catch (Exception e) {
			resultado.setData("${ERROR} HUBO UN ERROR AL restar NO ES UN NUMERO VALIDO ");
			return resultado;
		}
		resultado.setData(resta);
		return resultado;
	}

	/**
	 * Multiplicar.
	 *
	 * @param objects el objects
	 * @return the value data vo
	 */
	public static ValueDataVO multiplicar(Object... objects) {
		BigDecimal numTemp = BigDecimal.ONE;
		ValueDataVO resultado = new ValueDataVO();
		try {
			for (var object : objects) {
				if (object instanceof Number) {
					numTemp = numTemp.multiply(new BigDecimal(object.toString()));
				} else if (object instanceof ValueDataVO valueData) {
					ValueDataVO valueDataTemp = valueData;
					BigDecimal bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
					numTemp = numTemp.multiply(bigDecimal);

				} else {
					resultado.setData("${ERROR} HUBO UN ERROR AL MULTIPLICAR NO ES UN NUMERO VALIDO ");
					return resultado;
				}
			}
		} catch (Exception e) {
			log.error("multiplicar", e);
			resultado.setData("${ERROR} HUBO UN ERROR AL MULTIPLICAR NO ES UN NUMERO VALIDO ");
			return resultado;
		}
		resultado.setData(numTemp);
		return resultado;
	}

	public static ValueDataVO multiplicar(ReglaVO cnf, String multiplo, Object... objects) {
		ValueDataVO resultado = new ValueDataVO();
		BigDecimal suma = BigDecimal.ZERO;
		try {
			for (var object : objects) {
				if (object instanceof Number) {
					suma = suma.add(new BigDecimal(object.toString()));
				} else if (object instanceof String) {
					ValueDataVO valueDataTemp = cnf.getDataTupaMap().get(object);
					if (valueDataTemp != null) {
						BigDecimal bigDecimal = null;
						if (valueDataTemp.getData() instanceof BigDecimal valueData) {
							bigDecimal = valueData;
						} else {
							bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
						}
						suma = suma.add(bigDecimal);
					}
				} else if (object instanceof ValueDataVO valueDataTemp) {
					if (valueDataTemp != null) {
						BigDecimal bigDecimal = null;
						if (valueDataTemp.getData() instanceof BigDecimal valueData) {
							bigDecimal = valueData;
						} else {
							bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
						}
						suma = suma.add(bigDecimal);
					}
				} else {
					resultado.setData(ERROR_HUBO_UN_ERROR_AL_MULTIPLICAR_NO_ES_UN_NUMERO_VALIDO + object);
					return resultado;
				}
			}
			BigDecimal multiploCalc = new BigDecimal(multiplo);
			suma = suma.multiply(multiploCalc);
		} catch (Exception e) {
			resultado.setData(ERROR_HUBO_UN_ERROR_AL_MULTIPLICAR_NO_ES_UN_NUMERO_VALIDO + multiplo);
			return resultado;
		}
		resultado.setData(suma);
		return resultado;
	}

	public static ValueDataVO dividir(ReglaVO cnf, String dividendo, Object... objects) {
		ValueDataVO resultado = new ValueDataVO();
		BigDecimal suma = BigDecimal.ZERO;
		try {
			for (var object : objects) {
				if (object instanceof Number) {
					suma = suma.add(new BigDecimal(object.toString()));
				} else if (object instanceof String) {
					ValueDataVO valueDataTemp = cnf.getDataTupaMap().get(object);
					if (valueDataTemp != null) {
						BigDecimal bigDecimal = null;
						if (valueDataTemp.getData() instanceof BigDecimal valueData) {
							bigDecimal = valueData;
						} else {
							bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
						}
						suma = suma.add(bigDecimal);
					}
				} else if (object instanceof ValueDataVO valueDataTemp) {
					if (valueDataTemp != null) {
						BigDecimal bigDecimal = null;
						if (valueDataTemp.getData() instanceof BigDecimal valueData) {
							bigDecimal = valueData;
						} else {
							bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
						}
						suma = suma.add(bigDecimal);
					}
				} else {
					resultado.setData(ERROR_HUBO_UN_ERROR_AL_MULTIPLICAR_NO_ES_UN_NUMERO_VALIDO + object);
					return resultado;
				}
			}
			BigDecimal dividendoCalc = new BigDecimal(dividendo);
			suma = suma.divide(dividendoCalc, 2, RoundingMode.HALF_UP);
		} catch (Exception e) {
			resultado.setData(ERROR_HUBO_UN_ERROR_AL_MULTIPLICAR_NO_ES_UN_NUMERO_VALIDO + dividendo);
			return resultado;
		}
		resultado.setData(suma);
		return resultado;
	}

	/**
	 * Dividir.
	 *
	 * @param objects el objects
	 * @return the value data vo
	 */
	public static ValueDataVO dividir(Object... objects) {
		ValueDataVO resultado = new ValueDataVO();
		BigDecimal division = BigDecimal.ZERO;
		Integer contador = 0;
		try {
			for (var object : objects) {
				if (object instanceof Number) {
					if (contador > 0) {
						division = division.divide(new BigDecimal(object.toString()), 2, RoundingMode.HALF_UP);
					} else {
						division = new BigDecimal(object.toString());
					}
				} else if (object instanceof ValueDataVO valueDataTemp) {
					BigDecimal bigDecimal = new BigDecimal(valueDataTemp.getData().toString());
					if (contador > 0) {
						division = division.divide(bigDecimal, 2, RoundingMode.HALF_UP);
					} else {
						division = bigDecimal;
					}
				} else {
					resultado.setData(ERROR_HUBO_UN_ERROR_AL_DIVIR_NO_ES_UN_NUMERO_VALIDO);
					return resultado;
				}
				contador++;
			}
		} catch (Exception e) {
			log.error("dividir", e);
			resultado.setData(ERROR_HUBO_UN_ERROR_AL_DIVIR_NO_ES_UN_NUMERO_VALIDO);
			return resultado;
		}
		resultado.setData(division);
		return resultado;
	}

	/**
	 * Redondear.
	 *
	 * @param object          el object
	 * @param numeroDecimales el numero decimales
	 * @param tipoRedondeo    el tipo redondeo
	 * @return the value data vo
	 */
	public static ValueDataVO redondear(Object object, Integer numeroDecimales, String tipoRedondeo) {
		String key = tipoRedondeo.trim().toUpperCase();
		RedondearType redondear = RedondearType.getLooKupMap().get(key);
		ValueDataVO respuesta = (ValueDataVO) object;
		if (!StringUtil.isNullOrEmptyNumeriCero(respuesta.getData())) {
			BigDecimal numeroTemp = new BigDecimal(respuesta.getData().toString());
			respuesta.setData(numeroTemp.setScale(numeroDecimales, redondear.getValue()));
		} else {
			respuesta.setData(ERROR_NO_SE_PUEDE_REDONDEAR_UN_CAMPO_QUE_NO_ES_NUMERICO);
		}
		return respuesta;
	}

	/**
	 * Fecha actual.
	 *
	 * @return the value data vo
	 */
	public static ValueDataVO fechaActual() {
		ValueDataVO resultado = new ValueDataVO();
		resultado.setData(FechaUtil.obtenerFechaActual());
		return resultado;
	}

	/**
	 * Formatear fecha.
	 *
	 * @param campo   el campo
	 * @param formato el formato
	 * @return the value data vo
	 */
	public static ValueDataVO formatearFecha(Object campo, String formato) {
		StringBuilder concatenar = new StringBuilder();
		ValueDataVO resultado = (ValueDataVO) campo;
		OffsetDateTime fecha = null;
		try {
			if (!StringUtil.isNullOrEmpty(resultado.getData())) {
				fecha = (OffsetDateTime) resultado.getData();
				SimpleDateFormat dateFormat = new SimpleDateFormat(formato, ES);
				dateFormat.setLenient(Boolean.FALSE);
				String fechaParseada = dateFormat.format(fecha);
				concatenar.append(fechaParseada);
				concatenar.append(";");
				concatenar.append(formato);
				resultado.setData(concatenar.toString());
			} else {
				resultado.setData(ERROR_EL_FORMATO_NO_ES_CORRECTO + formato
						+ VALOR_DE_LA_FECHA_ES_NULO_O_NO_ES_EL_CORRECTO + fecha);
			}
		} catch (Exception e) {
			log.error("formatearFecha", e);
			resultado.setData(
					ERROR_EL_FORMATO_NO_ES_CORRECTO + formato + VALOR_DE_LA_FECHA_ES_NULO_O_NO_ES_EL_CORRECTO + fecha);
			return resultado;
		}
		return resultado;
	}

	/**
	 * Concatenar.
	 *
	 * @param objects el objects
	 * @return the value data vo
	 */
	public static ValueDataVO concatenar(Object... objects) {
		StringBuilder concatenar = new StringBuilder();
		ValueDataVO resultado = new ValueDataVO();
		for (var object : objects) {
			if (object instanceof ValueDataVO valueData) {
				concatenar.append(valueData.getData());
			} else {
				concatenar.append(object);
			}
		}
		resultado.setData(concatenar.toString());
		return resultado;
	}

	/**
	 * Limpiar vacios.
	 *
	 * @param campo el campo
	 * @param tipo  el tipo
	 * @return the value data vo
	 */
	public static ValueDataVO limpiarVacios(Object campo, String tipo) {
		ValueDataVO respuesta = new ValueDataVO();
		String cadena = "";
		try {
			if (campo instanceof ValueDataVO valueData) {
				respuesta = valueData;
				cadena = respuesta.getData().toString();
			} else {
				cadena = campo.toString();
			}
			tipo = tipo.toUpperCase();
			LimpiarVaciosType limpiarVaciosType = LimpiarVaciosType.getLooKupMap().get(tipo);
			switch (limpiarVaciosType) {
			case TRIM:
				cadena = cadena.trim();
				respuesta.setData(cadena);
				break;
			case TRIM_DERECHA:
				cadena = limpiarDerecha(cadena);
				respuesta.setData(cadena);
				break;
			case TRIM_IZQUIERDA:
				cadena = limpiarIzquierda(cadena);
				respuesta.setData(cadena);
				break;
			default:
				respuesta.setData(ERROR_NO_ES_UN_TIPO_VALIDO_PARA_LIMPIAR_VACIOS);
			}
		} catch (Exception e) {
			log.error("limpiarVacios", e);
			respuesta.setData(ERROR_NO_ES_UN_TIPO_VALIDO_PARA_LIMPIAR_VACIOS);
		}
		return respuesta;
	}

	/**
	 * Extraer.
	 *
	 * @param campo          el campo
	 * @param posicionIncial el posicion incial
	 * @param posicionFinal  el posicion final
	 * @return the value data vo
	 */
	public static ValueDataVO extraer(Object campo, Integer posicionIncial, Integer posicionFinal) {
		ValueDataVO resultado = new ValueDataVO();
		try {
			resultado = (ValueDataVO) campo;
			if (!resultado.getData().toString().contains(ERROR)) {
				Object resultadoTemp = resultado.getData();
				if (!StringUtil.isNullOrEmpty(resultadoTemp)) {
					if (resultadoTemp instanceof String) {
						resultadoTemp = resultadoTemp.toString().substring(posicionIncial - 1, posicionFinal - 1);
						resultado.setData(resultadoTemp);
					} else {
						resultado.setData(ERROR_ERROR_AL_EXTRAER_NO_ES_UN_DATO_DEL_TIPO_CADENA);
					}
				}
			}
		} catch (Exception e) {
			log.error("extraer", e);
			resultado.setData(ERROR_ERROR_AL_EXTRAER + posicionIncial + " " + posicionFinal + " ");
		}
		return resultado;
	}

	/**
	 * Asignar.
	 *
	 * @param regla el regla
	 * @param valor el valor
	 */
	public static void asignar(ReglaVO regla, Object valor) {
		ValueDataVO dataVO = new ValueDataVO();
		if (valor instanceof ValueDataVO valueData) {
			dataVO = new ValueDataVO(valueData);
		} else {
			dataVO.setData(valor);
		}
		regla.setResultado(dataVO);
	}

	/**
	 * Asignar.
	 *
	 * @param regla el regla
	 * @param valor el valor
	 */
	public static void asignar(ReglaVO regla, Object valor, String tipoCampo) {
		ValueDataVO dataVO = new ValueDataVO();
		TipoCampoType tipoCampoType = TipoCampoType.get(tipoCampo);
		if (valor instanceof ValueDataVO valueData) {
			dataVO = new ValueDataVO(valueData);
		} else {
			dataVO.setData(valor);
		}
		dataVO.setTipoCampoType(tipoCampoType);
		regla.setResultado(dataVO);
	}

	/**
	 * Limpiar izquierda.
	 *
	 * @param s el s
	 * @return the string
	 */
	private static String limpiarIzquierda(String s) {
		return LTRIM.matcher(s).replaceAll("");
	}

	/**
	 * Limpiar derecha.
	 *
	 * @param s el s
	 * @return the string
	 */
	private static String limpiarDerecha(String s) {
		return RTRIM.matcher(s).replaceAll("");
	}

	/**
	 * Igual.
	 *
	 * @param campo1 el campo1
	 * @param campo2 el campo2
	 * @return true, en caso de exito
	 */
	public static boolean igual(Object campo1, Object campo2) {
		boolean respuesta = false;
		try {
			String cadena1 = "";
			String cadena2 = "";
			if (campo1 instanceof ValueDataVO valueDataCampo1) {
				cadena1 = valueDataCampo1.getData().toString();
			} else {
				cadena1 = campo1.toString();
			}

			if (campo2 instanceof ValueDataVO valueDataCampo2) {
				cadena2 = valueDataCampo2.getData().toString();
			} else {
				cadena2 = campo2.toString();
			}
			if ((!StringUtil.isNullOrEmpty(cadena1) && !StringUtil.isNullOrEmpty(cadena2))
					&& (cadena1.trim().equals(cadena2.trim()))) {
				respuesta = true;
			}
		} catch (Exception e) {
			log.error("igual", e);
			respuesta = false;
		}
		return respuesta;
	}

	/**
	 * Igual.
	 *
	 * @param campo1 el campo1
	 * @param campo2 el campo2
	 * @return true, en caso de exito
	 */
	public static boolean igualIgnorarCaracterExtranho(Object campo1, Object campo2) {
		boolean respuesta = false;
		try {
			String cadena1 = "";
			String cadena2 = "";
			if (campo1 instanceof ValueDataVO valueDataCampo1) {
				cadena1 = valueDataCampo1.getData().toString();
			} else {
				cadena1 = campo1.toString();
			}
			cadena1 = StringUtil.quitarCaracterExtranio(cadena1, 0);// 0=bloque ==> configuracion.caracter.extranio.0.
			if (campo2 instanceof ValueDataVO valueDataCampo2) {
				cadena2 = valueDataCampo2.getData().toString();
			} else {
				cadena2 = campo2.toString();
			}
			if ((!StringUtil.isNullOrEmpty(cadena1) && !StringUtil.isNullOrEmpty(cadena2))
					&& (cadena1.trim().equals(cadena2.trim()))) {
				respuesta = true;
			}
		} catch (Exception e) {
			log.error("igualIgnorarCaracterExtranho", e);
			respuesta = false;
		}
		return respuesta;
	}

	public static boolean igual(Object campo1, Object campo2, String formatoFecha) {
		boolean respuesta = false;
		try {
			String cadena1 = "";
			String cadena2 = "";
			if (campo1 instanceof ValueDataVO valueDataCampo1) {
				cadena1 = valueDataCampo1.getData().toString();
			} else {
				cadena1 = campo1.toString();
			}

			if (campo2 instanceof ValueDataVO valueDataCampo2) {
				cadena2 = valueDataCampo2.getData().toString();
			} else {
				cadena2 = campo2.toString();
			}
			if ((!StringUtil.isNullOrEmpty(cadena1) && !StringUtil.isNullOrEmpty(cadena2))
					&& cadena1.trim().equals(cadena2.trim())) {
				respuesta = true;
			}
		} catch (Exception e) {
			log.error("igual", e);
			respuesta = false;
		}
		return respuesta;
	}

	/**
	 * Es numero.
	 *
	 * @param campo1 el campo1
	 * @return true, en caso de exito
	 */
	public static boolean esNumero(Object campo1) {
		boolean respuesta = true;
		String numeroCadena = "";
		try {
			if (campo1 instanceof ValueDataVO valueDataCampo1) {
				numeroCadena = valueDataCampo1.getData().toString();
			} else {
				numeroCadena = campo1.toString();
			}
			BigDecimal verificarNumero = new BigDecimal(numeroCadena);
		} catch (NumberFormatException e) {
			log.error("esNumero", e);
			respuesta = false;
		}
		return respuesta;
	}

	/**
	 * Es vacio.
	 *
	 * @param campo el campo
	 * @return true, en caso de exito
	 */
	public static boolean esVacio(Object campo) {
		boolean respuesta = true;
		try {
			ValueDataVO valueDataCampo = (ValueDataVO) campo;
			if (valueDataCampo != null && valueDataCampo.getData() != null
					&& !valueDataCampo.getData().toString().contains("{ERROR}")) {
				if (!valueDataCampo.getData().toString().trim().equals("")) {
					return false;
				}
			}
		} catch (Exception e) {
			log.error("esVacio", e);
			respuesta = true;
		}
		return respuesta;
	}

	public static boolean esVacio(ReglaVO cnf, String campo) {
		boolean respuesta = true;
		try {
			ValueDataVO valueDataCampo = cnf.getDataTupaMap().get(campo);
			if (valueDataCampo != null && valueDataCampo.getData() != null
					&& !valueDataCampo.getData().toString().contains("{ERROR}")) {
				if (!valueDataCampo.getData().toString().trim().equals("")) {
					return false;
				}
			}
		} catch (Exception e) {
			log.error("esVacio", e);
			respuesta = true;
		}
		return respuesta;
	}
}