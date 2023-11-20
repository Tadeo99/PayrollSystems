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
 * La Class ConfigTablaClassDet.
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
@Table(name = "Atributo", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_GENERADOR)
@Getter
@Setter
public class Atributo extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id config tabla clasess det. */
    @Id
    @Column(name = "idAtributo" , length = 32)
    private String idAtributo;
   
    /** El config tabla class. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idModelo", referencedColumnName = "idModelo")
    private Modelo modelo;
   
    /** El nombre columna. */
    @Column(name = "columna" , length = 150)
    private String columna;
   
    /** El nombre atributo. */
    @Column(name = "atributo" , length = 150)
    private String atributo;
   
    /** El es null. */
    @Column(name = "esNull" , length = 1)
    private String esNull;
   
    /** El type. */
    @Column(name = "type" , length = 100)
    private String type;
   
    /** El length. */
    @Column(name = "length" , precision = 18 , scale = 2)
    private Integer length;
   
    /** El comentario columna. */
    @Column(name = "comentario" , length = 150)
    private String comentario;
   
    /** El tipo l lave. */
    @Column(name = "tipoLLave" , length = 2)
    private String tipoLLave;
   
    /** El nombre tabla referencia. */
    @Column(name = "idModeloRef" , length = 32)
    private String idModeloRef;
   
    /** El mostrar grilla. */
    @Column(name = "mostrarGrilla" , length = 1)
    private String mostrarGrilla;
   
    /** El requiered filtro. */
    @Column(name = "requieredFiltro" , length = 1)
    private String requieredFiltro;
   
    /** El requiered frm. */
    @Column(name = "requieredFrm" , length = 1)
    private String requieredFrm;
   
    /** El tipo componente. */
    @Column(name = "tipoComponente" , precision = 18 , scale = 0)
    private Long tipoComponente;
   
    /** El indice. */
    @Column(name = "indice" , length = 1)
    private String indice;
   
    /** El indice grupo. */
    @Column(name = "indiceGrupo" , length = 250)
    private String indiceGrupo;
   
    /** El alter add column. */
    @Column(name = "alterAdd" , length = 1)
    private String alterAdd;
    
    /** El alter mod column. */
    @Column(name = "alterMod" , length = 1)
    private String alterMod;
   
    /**
     * Instancia un nuevo config tabla class det.
     */
    public Atributo() {
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
                + ((idAtributo == null) ? 0 : idAtributo.hashCode());
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
        Atributo other = (Atributo) obj;
        if (idAtributo == null) {
            if (other.idAtributo != null) {
                return false;
            }
        } else if (!idAtributo.equals(other.idAtributo)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Atributo [idAtributo=" + idAtributo + "]";
    }
   
}