package pe.buildsoft.erp.core.domain.entidades.common;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;

/**
 * La Class ListaValores.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Tue Apr 18 09:57:00 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "ListaValores", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class ListaValores implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id lista valores. */
    @Id
    @Column(name = "idListaValores" , precision = 18 , scale = 0)
    private Long idListaValores;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 250)
    private String nombre;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 500)
    private String descripcion;
   
    /** El sentencia sql. */
    @Column(name = "sentenciaSql" , length = 500)
    private String sentenciaSql;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /**
     * Instancia un nuevo lista valores.
     */
    public ListaValores() {
    	super();
    }
   
   
    /**
     * Instancia un nuevo lista valores.
     *
     * @param idListaValores el id lista valores
     * @param nombre el nombre
     * @param descripcion el descripcion
     * @param sentenciaSql el sentencia sql
     * @param estado el estado
     */
    public ListaValores(Long idListaValores, String nombre, String descripcion, String sentenciaSql, String estado ) {
        super();
        this.idListaValores = idListaValores;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sentenciaSql = sentenciaSql;
        this.estado = estado;
    }
   
   
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idListaValores == null) ? 0 : idListaValores.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ListaValores other = (ListaValores) obj;
        if (idListaValores == null) {
            if (other.idListaValores != null) {
                return false;
            }
        } else if (!idListaValores.equals(other.idListaValores)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ListaValores [idListaValores=" + idListaValores + "]";
    }
   
}