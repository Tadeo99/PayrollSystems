package pe.buildsoft.erp.core.domain.entidades.security;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class AgrupaEntidad.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 20 00:30:21 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "AgrupaEntidad", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class AgrupaEntidad extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id agrupa entidad. */
    @Id
    @Column(name = "idAgrupaEntidad" , length = 32)
    private String idAgrupaEntidad;
   
    /** El entidad. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntidad", referencedColumnName = "idEntidad")
    private Entidad entidad;
   
    /** El entidad agrupa. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntidadAgrupa", referencedColumnName = "idEntidad")
    private Entidad entidadAgrupa;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /**
     * Instancia un nuevo agrupa entidad.
     */
    public AgrupaEntidad() {
    	super();
    }
   
   
    /**
     * Instancia un nuevo agrupa entidad.
     *
     * @param idAgrupaEntidad el id agrupa entidad
     * @param entidad el entidad
     * @param entidadAgrupa el entidad agrupa
     * @param estado el estado
     */
    public AgrupaEntidad(String idAgrupaEntidad, Entidad entidad,Entidad entidadAgrupa,String estado ) {
        super();
        this.idAgrupaEntidad = idAgrupaEntidad;
        this.entidad = entidad;
        this.entidadAgrupa = entidadAgrupa;
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
                + ((idAgrupaEntidad == null) ? 0 : idAgrupaEntidad.hashCode());
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
        AgrupaEntidad other = (AgrupaEntidad) obj;
        if (idAgrupaEntidad == null) {
            if (other.idAgrupaEntidad != null) {
                return false;
            }
        } else if (!idAgrupaEntidad.equals(other.idAgrupaEntidad)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AgrupaEntidad [idAgrupaEntidad=" + idAgrupaEntidad + "]";
    }
   
}