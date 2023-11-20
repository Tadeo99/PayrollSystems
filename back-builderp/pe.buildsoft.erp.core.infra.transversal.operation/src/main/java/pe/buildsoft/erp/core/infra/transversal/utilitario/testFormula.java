package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

//https://blog.gitnux.com/code/java-javascript/
//https://www.tabnine.com/code/java/classes/javax.script.ScriptEngineManager
//https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form

///
//https://docs.spring.io/spring-framework/reference/core/expressions.html
//https://beanshell.github.io/home.html
//https://github.com/ezylang/EvalEx
//https://mathparser.org/

//https://golb.hplar.ch/2020/04/java-javascript-engine.html ok

//https://kodejava.org/tag/scriptenginemanager/ OKOK
//https://www.baeldung.com/java-nashorn
//https://www.tabnine.com/code/java/classes/javax.script.ScriptEngineManager

//https://golb.hplar.ch/2020/04/java-javascript-engine.html

public class testFormula {

	public static void main2(String[] args) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByExtension("js");
		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.put("polyglot.js.allowHostAccess", true);
		bindings.put("polyglot.js.allowHostClassLookup", (Predicate<String>) s -> true);
		bindings.put("engine.WarnInterpreterOnly", false);
		try {
			engine.eval(getScript());
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	private static String getScript() {
		return """
				let Date = Java.type("java.util.Date");
				var BigDecimal = Java.type('java.math.BigDecimal');
				let today = new Date();
				var number = BigDecimal.ONE;
				print(`number is ${number}`);
				print(`Today is ${today.toString()}`);
				print(`today is ${today.getClass().getName()}`);
				""";
	}

	public static void mainx(String[] args) {
		Engine engine = Engine.newBuilder().option("engine.WarnInterpreterOnly", "false").build();
		Context ctx = Context.newBuilder("js").engine(engine).build();

		String script = "6 * 7";

		Object eval = ctx.eval("js", script);
		System.out.println(eval);
	}

	public static void main(String... args) throws IOException, ScriptException {
		/*
		 * main2(args); ScriptEngine graalEngine = new
		 * ScriptEngineManager().getEngineByName("js");
		 * graalEngine.eval("print('Hello World!');");
		 */
		// jsEvalWithVariable();

		Engine manager = Engine.newBuilder().option("engine.WarnInterpreterOnly", "false").build();
		Context interprete = Context.newBuilder("js").allowAllAccess(true).engine(manager).build();
		/*
		 * context.getBindings("js").putMember("a", "1");
		 * context.getBindings("js").putMember("b", "2"); context.eval("js","a*b");
		 */

		// ScriptEngineManager manager = new ScriptEngineManager();
		// ScriptEngine interprete = manager.getEngineByExtension("js");
		Value bindings = interprete.getBindings("js");
		//bindings.putMember("polyglot.js.allowHostAccess", true);
		//bindings.putMember("polyglot.js.allowHostClassLookup", (Predicate<String>) s -> true);
		
		String funciones = """
				var BigDecimal = Java.type('java.math.BigDecimal');
				var RoundingMode = Java.type('java.math.RoundingMode');
				function REDONDEAR(numeroTmp,digito) {
				var numero = new BigDecimal(numeroTmp);
				           return  numero.setScale(digito, RoundingMode.HALF_UP);
				          }

				var r = 0;
				""";
		try {

			interprete.eval("js", funciones.toString());
			String formula = " (remuneracionBasico+asigFaminilar+remunVacacional\r\n"
					+ " +ingRemunSubsidio+ing1roMayo+ingHe35Porc+ingHe100Porc\r\n"
					+ " +ingLiqVacacion-descDominicalProporcional\r\n"
					+ " -conceptoTardanza-descFalta+dominicalFeriados)";
			String formulaBase = "\n r = " + formula + " ;\n r";
			bindings.putMember("remuneracionBasico", 5750.00);
			bindings.putMember("asigFaminilar", 0);
			bindings.putMember("remunVacacional", 0);
			bindings.putMember("ingRemunSubsidio", 0);
			bindings.putMember("ing1roMayo", 0);
			bindings.putMember("ingHe35Porc", 0);
			bindings.putMember("ingHe100Porc", 0);
			bindings.putMember("ingLiqVacacion", 0);
			bindings.putMember("conceptoTardanza", 0);
			bindings.putMember("descFalta", 0);
			bindings.putMember("dominicalFeriados", 1750.00);
			bindings.putMember("esComisionMixta", "S");
			bindings.putMember("descDominicalProporcional", 0);
			try {
				System.out.println("Resultado = " + interprete.eval("js", formulaBase.toString()));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			interprete = Context.newBuilder("js").allowAllAccess(true).engine(manager).build();
			bindings = interprete.getBindings("js");
			System.out.println("Resultado = " + interprete.eval("js", formulaBase.toString()));
		} catch (Exception se) {
			se.printStackTrace();
		}

	}

	public void testScriptableMBean() throws Exception {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("js");
		// ScriptableMBeanServerConnection mbsc = new
		// ScriptableMBeanServerConnection(ManagementFactory.getPlatformMBeanServer());
		// engine.put("mbsc", mbsc);
		String script = "var logging = mbsc.getMBean('java.util.logging:type=Logging');" + "print(logging);"
				+ "var loggers = logging.loggerNames;" + "for (var i = 0 ; i < loggers.length; i++) {"
				+ "  print(loggers[i] + ' ' + logging.getLoggerLevel(loggers[i]));"
				+ "  logging.setLoggerLevel(loggers[i], \"FINEST\");"
				+ "  print(loggers[i] + ' ' + logging.getLoggerLevel(loggers[i]));" + "}";
		engine.eval(script);
		// engine.eval("runtime.foo()");
	}

	private static void jsEvalWithVariable() {
		List<String> namesList = new ArrayList<String>();
		namesList.add("Jill");
		namesList.add("Bob");
		namesList.add("Laureen");
		namesList.add("Ed");

		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine jsEngine = mgr.getEngineByName("graal.js");

		jsEngine.put("namesListKey", namesList);
		System.out.println("Executing in script environment...");
		try {
			jsEngine.eval("var x;" + "var names = namesListKey;" + "for(x in names) {" + "  println(names[x]);" + "}"
					+ "namesListKey.add(\"Dana\");");
		} catch (ScriptException ex) {
			ex.printStackTrace();
		}
	}
}
