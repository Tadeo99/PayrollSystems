package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.IOException;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

//https://memorynotfound.com/apache-httpclient-http-post-request-method-example/
public class testPOst {
	public static void main(String... args) throws IOException {
		ScriptEngineManager manager = new ScriptEngineManager();
		
		List<ScriptEngineFactory> lista = manager.getEngineFactories();
		
		System.out.println("lista = " + lista.size());
		
		ScriptEngine interprete = manager.getEngineByName("graal.js");
		try {
			String formula = "X*Y/100";
			interprete.put("X", 5);
			interprete.put("Y", 80);
			System.out.println("Resultado = " + interprete.eval(formula));
		} catch (ScriptException se) {
			se.printStackTrace();
		}

	}
}
