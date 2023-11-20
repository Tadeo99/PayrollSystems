package pe.buildsoft.erp.core.domain.entidades.hora;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

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

/**
 * La Class DetalleRegistroCabecera.
 * <ul>
 * <li>Copyright 2021 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:44 COT 2022
 * @since BuildErp 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "registrohoradet", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
public class RegistroHoraDet  implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id detalle registro cabecera. */
    @Id
    @Column(name = "idregistrohoradet" , length = 32)
    private String idRegistroHoraDet;
   
    /** El id registro cabecera. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idregistrohora", referencedColumnName = "idregistrohora")
    private RegistroHora registroHora;
   
    /** El id requerimiento. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idrequerimiento", referencedColumnName = "idrequerimiento")
    private Requerimiento requerimiento;
   
    /** El horas imputadas. */
    @Column(name = "horasimputadas" , precision = 18 , scale = 2)
    private BigDecimal horasImputadas;
   
    /** El fecha imputacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaimputacion")
    private OffsetDateTime fechaImputacion;
   
    /** El num semana. */
    @Column(name = "numsemana" , precision = 18 , scale = 0)
    private Long numSemana;
    
    /**
     * Instancia un nuevo detalle registro cabecera.
     */
    public RegistroHoraDet() {
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
                + ((idRegistroHoraDet == null) ? 0 : idRegistroHoraDet.hashCode());
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
        RegistroHoraDet other = (RegistroHoraDet) obj;
        if (idRegistroHoraDet == null) {
            if (other.idRegistroHoraDet != null) {
                return false;
            }
        } else if (!idRegistroHoraDet.equals(other.idRegistroHoraDet)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RegistroHoraDet [idRegistroHoraDet=" + idRegistroHoraDet + "]";
    }
   
}