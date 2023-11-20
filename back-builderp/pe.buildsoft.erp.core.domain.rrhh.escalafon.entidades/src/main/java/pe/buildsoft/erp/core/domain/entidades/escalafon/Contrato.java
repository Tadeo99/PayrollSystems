package pe.buildsoft.erp.core.domain.entidades.escalafon;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Contrato.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Sun May 02 02:32:48 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Contrato", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class Contrato extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id contrato. */
    @Id
    @Column(name = "idContrato" , length = 32)
    private String idContrato;
   
    /** El personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
    private Personal personal;
   
    /** El item by tipo contrato. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoContrato", referencedColumnName = "idItem")
    private Item itemByTipoContrato;*/
    @Column(name = "idTipoContrato" , precision = 18 , scale = 0)
    private Long idItemByTipoContrato;
   
    /** El fecha inicio. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaInicio")
    private OffsetDateTime fechaInicio;
   
    /** El fecha final. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaFinal")
    private OffsetDateTime fechaFinal;
   
    /** El nrodoc. */
    @Column(name = "nrodoc" , length = 10)
    private String nrodoc;
   
    /** El estado. */
    @Column(name = "estao" , length = 1)
    private String estado;
   
    /** El contrato detalle contrado list. */
    @OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY)
    private List<DetalleContrado> contratoDetalleContradoList = new ArrayList<>();
    
    /**
     * Instancia un nuevo contrato.
     */
    public Contrato() {
		super();
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idContrato == null) ? 0 : idContrato.hashCode());
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
        Contrato other = (Contrato) obj;
        if (idContrato == null) {
            if (other.idContrato != null) {
                return false;
            }
        } else if (!idContrato.equals(other.idContrato)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Contrato [idContrato=" + idContrato + "]";
    }
   
}