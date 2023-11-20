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
 * La Class Anhio.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 00:26:55 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Anhio", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class Anhio implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id anhio. */
    @Id
    @Column(name = "idAnhio" , precision = 18 , scale = 0)
    private Long idAnhio;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 500)
    private String nombre;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    
    /**
     * Instancia un nuevo anhio.
     */
    public Anhio() {
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
                + ((idAnhio == null) ? 0 : idAnhio.hashCode());
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
        Anhio other = (Anhio) obj;
        if (idAnhio == null) {
            if (other.idAnhio != null) {
                return false;
            }
        } else if (!idAnhio.equals(other.idAnhio)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Anhio [idAnhio=" + idAnhio + "]";
    }
   
}