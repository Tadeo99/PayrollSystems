package pe.buildsoft.erp.core.domain.entidades.pago;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Empresa.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 21:45:31 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "Empresa", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_PAGO)
@Getter
@Setter
public class Empresa extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id empresa. */
    @Id
    @Column(name = "idEmpresa" , precision = 18 , scale = 0)
    private Long idEmpresa;
   
    /** El item by tipo via.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoVia", referencedColumnName = "idItem")
    private Item itemByTipoVia; */
    @Column(name = "idTipoVia" , precision = 18 , scale = 0)
    private Long idItemByTipoVia;
   
    /** El item by zona. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idZona", referencedColumnName = "idItem")
    private Item itemByZona;*/
    @Column(name = "idZona" , precision = 18 , scale = 0)
    private Long idItemByZona;
    
    /** El nombre zona. */
    @Column(name = "nombreZona" , length = 200)
    private String nombreZona;
   
    /** El nombre tipo via. */
    @Column(name = "nombreTipoVia" , length = 200)
    private String nombreTipoVia;
   
    /** El ruc. */
    @Column(name = "ruc" , length = 20)
    private String ruc;
   
    /** El razon social. */
    @Column(name = "razonSocial" , length = 200)
    private String razonSocial;
   
    /** El direccion. */
    @Column(name = "direccion" , length = 150)
    private String direccion;
   
    /** El telefono. */
    @Column(name = "telefono" , length = 50)
    private String telefono;
   
    /** El email. */
    @Column(name = "email" , length = 50)
    private String email;
   
    /** El web. */
    @Column(name = "web" , length = 150)
    private String web;
   
    /** El fecha creacion. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaCreacion")
    private OffsetDateTime fechaCreacion;
   
    /** El usuario creacion. */
    @Column(name = "usuarioCreacion" , length = 50)
    private String usuarioCreacion;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    
    /**
     * Instancia un nuevo empresa.
     */
    public Empresa() {
		super();
    }
   

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
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
        Empresa other = (Empresa) obj;
        if (idEmpresa == null) {
            if (other.idEmpresa != null) {
                return false;
            }
        } else if (!idEmpresa.equals(other.idEmpresa)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Empresa [idEmpresa=" + idEmpresa + "]";
    }
   
}