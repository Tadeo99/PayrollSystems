package pe.buildsoft.erp.core.domain.entidades.escalafon;

import java.io.Serializable;
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
 * La Class Institucion.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Institucion", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class Institucion extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id institucion. */
    @Id
    @Column(name = "idInstitucion" , precision = 18 , scale = 0)
    private Long idInstitucion;
   
    /** El item by tipo institucion.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoInstitucion", referencedColumnName = "idItem")
    private Item itemByTipoInstitucion; */
    @Column(name = "idTipoInstitucion" , precision = 18 , scale = 0)
    private Long  idItemByTipoInstitucion;
   
    /** El item by regimen.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRegimen", referencedColumnName = "idItem")
    private Item itemByRegimen; */
    @Column(name = "idRegimen" , precision = 18 , scale = 0)
    private Long  idItemByRegimen;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El institucion carrera list. */
    @OneToMany(mappedBy = "institucion", fetch = FetchType.LAZY)
    private List<Carrera> institucionCarreraList = new ArrayList<>();
    
    /**
     * Instancia un nuevo institucion.
     */
    public Institucion() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idInstitucion == null) ? 0 : idInstitucion.hashCode());
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
        Institucion other = (Institucion) obj;
        if (idInstitucion == null) {
            if (other.idInstitucion != null) {
                return false;
            }
        } else if (!idInstitucion.equals(other.idInstitucion)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Institucion [idInstitucion=" + idInstitucion + "]";
    }
   
}