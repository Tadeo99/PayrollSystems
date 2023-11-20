package pe.buildsoft.erp.core.domain.entidades.generador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Tecnologia.
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
@Table(name = "Tecnologia", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_GENERADOR)
@Getter
@Setter
public class Tecnologia extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id tecnologia. */
    @Id
    @Column(name = "idTecnologia" , length = 32)
    private String idTecnologia;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 150)
    private String nombre;
   
    /** El tipo. */
    @Column(name = "tipo" , length = 7)
    private String tipo;
   
    /** El tecnologia config tecnologia list. */
//    @OneToMany(mappedBy = "tecnologia", fetch = FetchType.LAZY)
    @Transient
    private List<Plantilla> tecnologiaConfigTecnologiaList = new ArrayList<>();
    
    /** El tecnologia by base datos proyecto list. */
//    @OneToMany(mappedBy = "tecnologiaByBaseDatos", fetch = FetchType.LAZY)
    @Transient
    private List<Proyecto> tecnologiaByBaseDatosProyectoList = new ArrayList<>();
    
    /** El tecnologia by back end proyecto list. */
//    @OneToMany(mappedBy = "tecnologiaByBackEnd", fetch = FetchType.LAZY)
    @Transient
    private List<Proyecto> tecnologiaByBackEndProyectoList = new ArrayList<>();
    
    /** El tecnologia by front end proyecto list. */
//    @OneToMany(mappedBy = "tecnologiaByFrontEnd", fetch = FetchType.LAZY)
    @Transient
    private List<Proyecto> tecnologiaByFrontEndProyectoList = new ArrayList<>();
    
    /** El tecnologia config arquetipo list. */
//    @OneToMany(mappedBy = "tecnologia", fetch = FetchType.LAZY)
    @Transient
    private List<ConfigParametro> tecnologiaConfigArquetipoList = new ArrayList<>();
    
    /** El tecnologia by base datos config type equivalencia list. */
//    @OneToMany(mappedBy = "tecnologiaByBaseDatos", fetch = FetchType.LAZY)
    @Transient
    private List<EquivalenciaType> tecnologiaByBaseDatosConfigTypeEquivalenciaList = new ArrayList<>();
    
    /** El tecnologia equivalente config type equivalencia list. */
//    @OneToMany(mappedBy = "tecnologiaEquivalente", fetch = FetchType.LAZY)
    @Transient
    private List<EquivalenciaType> tecnologiaEquivalenteConfigTypeEquivalenciaList = new ArrayList<>();
    
    /**
     * Instancia un nuevo tecnologia.
     */
    public Tecnologia() {
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
                + ((idTecnologia == null) ? 0 : idTecnologia.hashCode());
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
        Tecnologia other = (Tecnologia) obj;
        if (idTecnologia == null) {
            if (other.idTecnologia != null) {
                return false;
            }
        } else if (!idTecnologia.equals(other.idTecnologia)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Tecnologia [idTecnologia=" + idTecnologia + "]";
    }
   
}