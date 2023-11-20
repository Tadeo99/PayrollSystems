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
 * La Class ConfigTecnologia.
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
@Table(name = "Plantilla", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_GENERADOR)
@Getter
@Setter
public class Plantilla extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id config tecnologia. */
    @Id
    @Column(name = "idPlantilla" , length = 32)
    private String idPlantilla;
   
    /** El tecnologia. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTecnologia", referencedColumnName = "idTecnologia")
    private Tecnologia tecnologia;
   
    /** El nombre plantilla. */
    @Column(name = "nombre" , length = 150)
    private String nombre;
   
    /** El codigo grupo plantilla. */
    @Column(name = "codigoGrupo" , length = 100)
    private String codigoGrupo;
   
    /** El plantilla. */
    @Column(name = "plantilla")
    private String plantilla;
   
    /** El nombre archivo generar. */
    @Column(name = "nombreArchivoGenerar" , length = 200)
    private String nombreArchivoGenerar;
   
    /** El extension. */
    @Column(name = "extension" , length = 10)
    private String extension;
   
    /** El es collection data. */
    @Column(name = "esCollectionData" , length = 1)
    private String esCollectionData;
   
    /** El alias paquete. */
    @Column(name = "esInclude" , length = 1)
    private String esInclude;
   
    /** El alias nombre. */
    @Column(name = "orden" , precision = 18, scale = 0)
    private Long orden;
   
    /**
     * Instancia un nuevo config tecnologia.
     */
    public Plantilla() {
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
                + ((idPlantilla == null) ? 0 : idPlantilla.hashCode());
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
        Plantilla other = (Plantilla) obj;
        if (idPlantilla == null) {
            if (other.idPlantilla != null) {
                return false;
            }
        } else if (!idPlantilla.equals(other.idPlantilla)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Plantilla [idPlantilla=" + idPlantilla + "]";
    }
   
}