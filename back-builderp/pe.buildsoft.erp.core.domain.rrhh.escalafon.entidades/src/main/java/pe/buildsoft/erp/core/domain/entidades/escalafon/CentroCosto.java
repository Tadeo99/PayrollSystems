package pe.buildsoft.erp.core.domain.entidades.escalafon;

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
 * La Class CentroCosto.
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
@Table(name = "CentroCosto", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class CentroCosto extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id centro costo. */
    @Id
    @Column(name = "idCentroCosto" , length = 32)
    private String idCentroCosto;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 150)
    private String nombre;
   
    /** El codigo. */
    @Column(name = "codigo" , length = 10)
    private String codigo;
   
    /** El descripcion. */
    @Column(name = "descripcion" , length = 150)
    private String descripcion;
   
    /** El fecha creacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion")
    private OffsetDateTime fechaCreacion;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El centro costo asociar centro costo list. */
    @OneToMany(mappedBy = "centroCosto", fetch = FetchType.LAZY)
    private List<AsociarCentroCosto> centroCostoAsociarCentroCostoList = new ArrayList<>();
    
    /**
     * Instancia un nuevo centro costo.
     */
    public CentroCosto() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idCentroCosto == null) ? 0 : idCentroCosto.hashCode());
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
        CentroCosto other = (CentroCosto) obj;
        if (idCentroCosto == null) {
            if (other.idCentroCosto != null) {
                return false;
            }
        } else if (!idCentroCosto.equals(other.idCentroCosto)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CentroCosto [idCentroCosto=" + idCentroCosto + "]";
    }
   
}