/**
 * Control que facilita el acceso a archivos de recursos y propiedades, y la creación de mensajes a partir de propiedades y parámetros.
 *
 * @author ndavilal.
 * @version 1.0 , 25/03/2012
 * @since SIAA 2.0
 */
export class ControlRecursosWeb {
	/**
	 * Cargar mensaje.
	 *
	 * @param cadena el cadena
	 * @param parametros el parametros
	 * @return the string
	 */
	public static cargarMensajeParametro(cadena: string, parametros: string[]): string {
		return this.asignarParametrosMensaje(cadena, parametros);
	}

	/**
	 * Reemplaza los parametros indicados en un mensaje leido de un bundle.
	 *
	 * @param mensaje El mensaje parametrizado
	 * @param parametros La lista de parámetros
	 * @return String El mensaje modificado
	 */
	private static asignarParametrosMensaje(mensaje: string, parametros: string[]): string {
		console.log("asignarParametrosMensaje==> " + parametros.length);
		if (parametros == null) {
			return mensaje;//this.removerParametros(mensaje);
		}

		let sbMensaje: string = mensaje;
		for (let p = 0; p < parametros.length; p++) {
			const token = "{" + p + "}";
			console.log("asignarParametrosMensaje==> " + token);
			//let inicioToken : number = sbMensaje.indexOf(token);
			if (parametros[p] != null) {
				console.log("asignarParametrosMensaje==> " + parametros[p]);
				//sbMensaje.replace(inicioToken, inicioToken + token.length, parametros[p]);
				sbMensaje = sbMensaje.replace(token, parametros[p]);
			} else {
				console.log("asignarParametrosMensaje.bb==> ");
				//sbMensaje.replace(inicioToken, inicioToken + token.length(), "");
				sbMensaje = sbMensaje.replace(token, "");
			}
		}
		return sbMensaje;//this.removerParametros(sbMensaje);
	}

	/**
	 * Elimina de un mensaje los identificadores de los valores parametrizados.
	 *
	 * @param mensaje El mensaje parametrizado
	 * @return String El mensaje sin los parámetros
	 */
	private static removerParametros(mensaje: string): string {
		if (mensaje == null) {
			return "";
		}
		const sbMensaje = mensaje;
		for (let i = sbMensaje.indexOf("{"), f = sbMensaje.indexOf("}") + 1; (i != -1) && (f > i);) {
			const token = "{" + i + "}";
			sbMensaje.replace(token, "");
			i = sbMensaje.indexOf("{");
			f = sbMensaje.indexOf("}") + 1;
		}
		return sbMensaje.toString();
	}
}
