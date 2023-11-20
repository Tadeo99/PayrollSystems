package pe.buildsoft.erp.core.infra.transversal.utilitario;

public class testHiberante {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String className = "pe.buildsoft.erp.core.domain.entidades.common.Ubigeo$HibernateProxy$fPTp9nmt";
		String handlerHibernate = "$HibernateProxy";
		if (className.contains(handlerHibernate)) {
			int indexOf = className.indexOf(handlerHibernate);
			className = className.substring(0, indexOf);
		}
		System.out.println(className);
	}

}
