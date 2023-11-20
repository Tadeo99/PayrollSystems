package pe.buildsoft.erp.core.domain.entidades.escalafon;

import java.io.Serializable;
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
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Beneficiarios.
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
@Table(name = "Beneficiarios", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class Beneficiarios extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id beneficiario. */
    @Id
    @Column(name = "idBeneficiario" , length = 32)
    private String idBeneficiario;
   
    /** El personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
    private Personal personal;
   
    /** El apellido paterno. */
    @Column(name = "apellidoPaterno" , length = 100)
    private String apellidoPaterno;
   
    /** El apellido materno. */
    @Column(name = "apellidoMaterno" , length = 100)
    private String apellidoMaterno;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 100)
    private String nombre;
   
    /** El item by vinculo. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVinculo", referencedColumnName = "idItem")
    private Item itemByVinculo;*/
    @Column(name = "idVinculo" , precision = 18 , scale = 0)
    private Long idItemByVinculo;
   
    /** El fecha nacimiento. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaNacimiento")
    private OffsetDateTime fechaNacimiento;
   
    /** El situacion. */
    @Column(name = "situacion" , length = 100)
    private String situacion;
   
    /** El fecha activacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaActivacion")
    private OffsetDateTime fechaActivacion;
   
    /**
     * Instancia un nuevo beneficiarios.
     */
    public Beneficiarios() {
		super();
    }
   
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idBeneficiario == null) ? 0 : idBeneficiario.hashCode());
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
        Beneficiarios other = (Beneficiarios) obj;
        if (idBeneficiario == null) {
            if (other.idBeneficiario != null) {
                return false;
            }
        } else if (!idBeneficiario.equals(other.idBeneficiario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Beneficiarios [idBeneficiario=" + idBeneficiario + "]";
    }
   
}