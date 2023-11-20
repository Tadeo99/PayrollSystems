package pe.buildsoft.erp.core.domain.entidades.generador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Modelo.
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
@Table(name = "Modelo", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_GENERADOR)
@Getter
@Setter
public class Modelo extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id config tabla class. */
    @Id
    @Column(name = "idModelo" , length = 32)
    private String idModelo;
   
    /** El config grupo servicio. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idGrupoNegocio", referencedColumnName = "idGrupoNegocio")
    private GrupoNegocio grupoNegocio;
   
   
    /** El sequence tabla. */
    @Column(name = "sequence" , length = 150)
    private String sequence;
   
    /** El nombre tabla. */
    @Column(name = "nombreTabla" , length = 150)
    private String nombreTabla;
   
    /** El nombre clase. */
    @Column(name = "nombreClase" , length = 150)
    private String nombreClase;
   
    /** El codigo. */
    @Column(name = "codigo" , length = 100)
    private String codigo;
   
    /** El comentario tabla. */
    @Column(name = "comentario" , length = 150)
    private String comentario;
   
    /** El notas. */
    @Column(name = "notas" , length = 150)
    private String notas;
   
 
   
    /** El config tabla class config tabla class det list. */
//    @OneToMany(mappedBy = "configTablaClass", fetch = FetchType.LAZY)
    @Transient
    private List<Atributo> listaAtributo = new ArrayList<>();
    
    /**
     * Instancia un nuevo config tabla class.
     */
    public Modelo() {
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
                + ((idModelo == null) ? 0 : idModelo.hashCode());
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
        Modelo other = (Modelo) obj;
        if (idModelo == null) {
            if (other.idModelo != null) {
                return false;
            }
        } else if (!idModelo.equals(other.idModelo)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Modelo [idModelo=" + idModelo + "]";
    }
   
}