package pe.buildsoft.erp.core.domain.entidades.cola;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;


/**
 * La Class Cola.
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
@Table(name = "Cola", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_COMMON)
@Getter
@Setter
public class Cola extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id cola. */
    @Id
    @Column(name = "idCola" , precision = 18 , scale = 0)
    private Long idCola;
   
    /** El nombre cola. */
    @Column(name = "nombreCola" , length = 100)
    private String nombreCola;
   
    /** El nivel cola. */
    @Column(name = "nivelCola" , precision = 18 , scale = 0)
    private Long nivelCola;
   
    /** El fecha actualizacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaActualizacion")
    private OffsetDateTime fechaActualizacion;
   
    /** El codigo usuario. */
    @Column(name = "codigoUsuario" , length = 10)
    private String codigoUsuario;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El cola configuracion filtro cola list. */
    @OneToMany(mappedBy = "cola", fetch = FetchType.LAZY)
    private List<ConfiguracionFiltroCola> colaConfiguracionFiltroColaList = new ArrayList<ConfiguracionFiltroCola>();
    
     
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idCola == null) ? 0 : idCola.hashCode());
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
        Cola other = (Cola) obj;
        if (idCola == null) {
            if (other.idCola != null) {
                return false;
            }
        } else if (!idCola.equals(other.idCola)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Cola [idCola=" + idCola + "]";
    }
   
}