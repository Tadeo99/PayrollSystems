package pe.buildsoft.erp.core.domain.drools;

public class TesDroolsError {
	private static long cantidadDemorado = 0;

	public static final void main(String[] args) {

		try {
			for (int i = 0; i < 1; i++) {
				procesarRegla(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		double segundero = 1000;
		System.err.println("TesDrools.total --> " + cantidadDemorado + " en segundos " + ((cantidadDemorado / segundero)));
	}

	public static void procesarRegla(int numero) throws Exception {
	
		String dslr = "admina0c35322dabe415aa85be99b7c09ec33.dsrl";
		String dsl = "say_something.dsl";

		long inicioMilisegundo = System.currentTimeMillis();
		double segundero = 1000;
		System.out.println("TesDrools.inicio." + numero + "  --> " + inicioMilisegundo);
//		ProcesarReglaUtil.leerReglaDrlCompiler(dsl, dslr, 5000);
		long finMilisegundo = System.currentTimeMillis() - inicioMilisegundo;
		if (numero > 0) {
			cantidadDemorado = cantidadDemorado + finMilisegundo;
		}
		System.out.println("TesDrools.fin." + numero + "  " + finMilisegundo + " en segundos " + ((finMilisegundo / segundero)));
	}
}
