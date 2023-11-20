package pe.buildsoft.erp.core.domain.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ColaNoctura.
 * <ul>
 * <li>Copyright 2015 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 2.1, Thu Dec 21 12:16:30 COT 2017
 * @since BuildErp 1.0
 */
@Entity
@Table(name = "ColaNoctura", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class ColaNoctura extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id cola nocturna. */
    @Id
    @Column(name = "idColaNocturna" , length = 32)
    private String idColaNocturna;
   
    /** El objecto json. */
    @Column(name = "objectoJson")
    private String objectoJson;
   
    /** El fecha actualizacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaActualizacion")
    private OffsetDateTime fechaActualizacion;
   
    /** El codigo usuario. */
    @Column(name = "codigoUsuario" , length = 10)
    private String codigoUsuario;
   
    /** El estado proceso. */
    @Column(name = "estadoProceso" , length = 1)
    private String estadoProceso;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
    
    /** El id solicitud reporte. */
    @Column(name = "idSolicitudReporte" , length = 32)
    private String idSolicitudReporte;
   
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idColaNocturna == null) ? 0 : idColaNocturna.hashCode());
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
        ColaNoctura other = (ColaNoctura) obj;
        if (idColaNocturna == null) {
            if (other.idColaNocturna != null) {
                return false;
            }
        } else if (!idColaNocturna.equals(other.idColaNocturna)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ColaNoctura [idColaNocturna=" + idColaNocturna + "]";
    }
   
}