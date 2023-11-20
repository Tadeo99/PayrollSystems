package pe.buildsoft.erp.core.domain.entidades.generador;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConfigParametro.
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
@Table(name = "ConfigParametro", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_GENERADOR)
@Getter
@Setter
public class ConfigParametro extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id config arquetipo. */
    @Id
    @Column(name = "idConfigParametro" , length = 32)
    private String idConfigParametro;
   
    /** El grupoNegocio. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrupoNegocio", referencedColumnName = "idGrupoNegocio")
    private GrupoNegocio grupoNegocio;*/
    
    @Column(name = "idGrupoNegocio" , length = 32)
    private String idGrupoNegocio;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 150)
    private String descripcion;
   
    /** El codigo. */
    @Column(name = "codigo" , length = 150)
    private String codigo;
   
    /** El valor. */
    @Column(name = "valor" , length = 150)
    private String valor;
   
      
    /**
     * Instancia un nuevo config arquetipo.
     */
    public ConfigParametro() {
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
                + ((idConfigParametro == null) ? 0 : idConfigParametro.hashCode());
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
        ConfigParametro other = (ConfigParametro) obj;
        if (idConfigParametro == null) {
            if (other.idConfigParametro != null) {
                return false;
            }
        } else if (!idConfigParametro.equals(other.idConfigParametro)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConfigParametro [idConfigParametro=" + idConfigParametro + "]";
    }
   
}