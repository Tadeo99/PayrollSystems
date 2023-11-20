package pe.buildsoft.erp.core.domain.entidades.generador;

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
 * La Class ConfigTypeEquivalencia.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Mon Sep 26 11:35:26 PET 2022
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "EquivalenciaType", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_GENERADOR)
@Getter
@Setter
public class EquivalenciaType extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id config type equivalencia. */
    @Id
    @Column(name = "idEquivalenciaType" , length = 32)
    private String idEquivalenciaType;
   
    /** El tecnologia by base datos. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTecnologia", referencedColumnName = "idTecnologia")
    private Tecnologia tecnologia;
   
    /** El tecnologia equivalente. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTecnologiaEquivalente", referencedColumnName = "idTecnologia")
    private Tecnologia tecnologiaEquivalente;
   
    /** El type. */
    @Column(name = "type" , length = 100)
    private String type;
   
    /** El type atribute. */
    @Column(name = "typeAtribute" , length = 100)
    private String typeAtribute;
   
    /** El es decimal. */
    @Column(name = "esDecimal" , length = 1)
    private String esDecimal;
   
    /**
     * Instancia un nuevo config type equivalencia.
     */
    public EquivalenciaType() {
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
                + ((idEquivalenciaType == null) ? 0 : idEquivalenciaType.hashCode());
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
        EquivalenciaType other = (EquivalenciaType) obj;
        if (idEquivalenciaType == null) {
            if (other.idEquivalenciaType != null) {
                return false;
            }
        } else if (!idEquivalenciaType.equals(other.idEquivalenciaType)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "EquivalenciaType [idEquivalenciaType=" + idEquivalenciaType + "]";
    }
   
}