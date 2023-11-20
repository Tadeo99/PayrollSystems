package pe.buildsoft.erp.core.domain.interfaces.repositories;


/**
 * La Interface GenericDAOLocal.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 * 
 * @param <K> el tipo de clave
 * @param <T> el tipo generico
 * @author BuildSoft.
 * @version 1.0, Fri Apr 25 17:57:10 COT 2014
 * @since Rep v1..0
 */
public interface GenericDAOLocal<K,T> {

	
	T saveNative(T entity);
	
    /**
     * Save.
     *
     * @param entity el entity
     * @return the t
     */
    T save(T entity);

    /**
     * Update.
     *
     * @param entity el entity
     * @return the t
     */
    T update(T entity);

    /**
     * Delete.
     *
     * @param entity el entity
     * @return the t
     */
    T delete(T entity);

    
    /**
     * Find.
     *
     * @param classs el classs
     * @param id el id
     * @return the t
     */
    T find(Class<T> classs, K id);
}
