/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul> 
 * 
 * La Class StringUtil.
 *
 * @author ndavilal
 * @version 1.0 , 06/04/2015
 * @since SIAA 2.0
 */
export class StringUtils {


	public static obtenerValorMap(valueMap: object, valueDefault: object): object {
		let resultado: object;
		if (!this.isNullOrEmpty(valueMap)) {
			resultado = valueMap;
		} else {
			resultado = valueDefault;
		}
		return resultado;
	}

	/**
	 * Comprueba si es not null or blank.
	 *
	 * @param obj el obj
	 * @return true, si es not null or blank
	 */
	public static isNotNullOrBlank(obj: any): boolean {
		let resultado = false;
		if (obj != null && obj != "" && obj.length > 0) {
			resultado = true;
		}
		return resultado;
	}
	/**
	 * Checks if is null or empty.
	 *
	 * @param object the object
	 * @return true, if is null or empty
	 */
	public static isNullOrEmpty(object: any): boolean {
		if (object != null && !(object == "")) {
			return false;
		}
		return true;
	}

	/**
	 * Comprueba si es null or empty numeric.
	 *
	 * @param object el object
	 * @return true, si es null or empty numeric
	 */
	public static isNullOrEmptyNumeric(object: any): boolean {
		if (object != null && !(object == "")) {
			try {
				const numero = Number.parseFloat(object + '');
				if (numero > 0) {
					return false;
				}
			} catch (e) {
				return true;
			}
			return true;
		}

		return true;
	}

	public static isNullOrEmptyNumericMenosCero(object: any): boolean {
		if (object != null && !(object == "")) {
			try {
				const numero = Number.parseFloat(object + '');
				if (numero != 0) {
					return false;
				}
			} catch (e) {
				return true;
			}
			return true;
		}

		return true;
	}


	public static isNullOrEmptyNumeriCero(object: any): boolean {
		if (object != null && !(object == "")) {
			try {
				const numero = Number.parseFloat(object + '');
				return false;
			} catch (e) {
				return true;
			}
		}

		return true;
	}
}
