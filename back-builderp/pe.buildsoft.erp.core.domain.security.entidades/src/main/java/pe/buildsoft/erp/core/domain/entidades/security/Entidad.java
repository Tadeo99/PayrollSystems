package pe.buildsoft.erp.core.domain.entidades.security;

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
 * La Class Entidad.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Wed Dec 13 22:04:25 COT 2017
 * @since SIAA-CORE 2.1
 */
@Entity
@Table(name = "Entidad", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_SECURIDAD)
@Getter
@Setter
public class Entidad extends BaseEntidad implements Serializable {
	 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id entidad. */
    @Id
    @Column(name = "idEntidad" , length = 32)
    private String idEntidad;
   
    /** El item by tipo via. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoVia", referencedColumnName = "idItem")
    private Item itemByTipoVia;*/
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
   
    /** El codigo. */
    @Column(name = "codigo" , length = 50)
    private String codigo;
   
    /** El codigo externo. */
    @Column(name = "codigoExterno" , length = 50)
    private String codigoExterno;
   
    /** El codigo referencia. */
    @Column(name = "codigoReferencia" , length = 50)
    private String codigoReferencia;
   
    /** El nombre. */
    @Column(name = "nombre" , length = 200)
    private String nombre;
   
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
   
    /**
     * Instancia un nuevo entidad.
     */
    public Entidad() {
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
                + ((idEntidad == null) ? 0 : idEntidad.hashCode());
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
        Entidad other = (Entidad) obj;
        if (idEntidad == null) {
            if (other.idEntidad != null) {
                return false;
            }
        } else if (!idEntidad.equals(other.idEntidad)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Entidad [idEntidad=" + idEntidad + "]";
    }
   
}