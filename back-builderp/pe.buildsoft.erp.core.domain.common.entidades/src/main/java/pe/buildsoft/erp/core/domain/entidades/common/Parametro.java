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
 * La Class Parametro.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Dec 14 00:27:42 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "Parametro", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class Parametro implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id parametro. */
    @Id
    @Column(name = "idParametro" , length = 32)
    private String idParametro;
   
    /** El id entidad. */
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")*/
    @Column(name = "idEntidad" , length = 32)
    private String entidad;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 100)
    private String descripcion;
   
    /** El codigo. */
    @Column(name = "codigo" , length = 20)
    private String codigo;
   
    /** El valor. */
    @Column(name = "valor" , length = 32)
    private String valor;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /**
     * Instancia un nuevo parametro.
     */
    public Parametro() {
    	super();
    }
   
   
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idParametro == null) ? 0 : idParametro.hashCode());
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
        Parametro other = (Parametro) obj;
        if (idParametro == null) {
            if (other.idParametro != null) {
                return false;
            }
        } else if (!idParametro.equals(other.idParametro)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Parametro [idParametro=" + idParametro + "]";
    }
   
}