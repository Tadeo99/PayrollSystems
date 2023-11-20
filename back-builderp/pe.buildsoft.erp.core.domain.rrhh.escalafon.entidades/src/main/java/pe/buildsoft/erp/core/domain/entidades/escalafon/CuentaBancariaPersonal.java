package pe.buildsoft.erp.core.domain.entidades.escalafon;

import java.io.Serializable;
import java.time.OffsetDateTime;
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
 * La Class CuentaBancariaPersonal.
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
@Table(name = "CuentaBancariaPersonal", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class CuentaBancariaPersonal extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id cuenta bancaria personal. */
    @Id
    @Column(name = "idCuentaBancariaPersonal" , length = 32)
    private String idCuentaBancariaPersonal;
   
    /** El personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
    private Personal personal;
   
    /** El item by banco. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idBanco", referencedColumnName = "idItem")
    private Item itemByBanco;*/
    @Column(name = "idBanco" , precision = 18 , scale = 0)
    private Long idItemByBanco;
    
    /** El nro cuenta. */
    @Column(name = "nroCuenta" , length = 100)
    private String nroCuenta;
   
    /** El nro c c i. */
    @Column(name = "nroCCI" , length = 100)
    private String nroCCI;
   
    /** El fecha apertura. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechaApertura")
    private OffsetDateTime fechaApertura;
   
    /** El item by moneda. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMoneda", referencedColumnName = "idItem")
    private Item itemByMoneda;*/
    @Column(name = "idMoneda" , precision = 18 , scale = 0)
    private Long idItemByMoneda;
   
    /** El item by tipo cuenta. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoCuenta", referencedColumnName = "idItem")
    private Item itemByTipoCuenta;*/
    @Column(name = "idTipoCuenta" , precision = 18 , scale = 0)
    private Long idItemByTipoCuenta;
   
    /** El modulo. */
    @Column(name = "modulo" , length = 100)
    private String modulo;
   
    /** El sucursal. */
    @Column(name = "sucursal" , length = 100)
    private String sucursal;
   
    /** El sub cuenta. */
    @Column(name = "subCuenta" , length = 100)
    private String subCuenta;
    
    /** El sub cuenta. */
    @Column(name = "esCts" , length = 1)
    private String esCts;
   
    /** El item by tipo deposito cuenta.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoDepositoCuenta", referencedColumnName = "idItem")
    private Item itemByTipoDepositoCuenta;*/
    @Column(name = "idTipoDepositoCuenta" , precision = 18 , scale = 0)
    private Long idItemByTipoDepositoCuenta;
    
    @Transient
    private List<String> listaIdPersonal = new ArrayList<String>();
   
    /**
     * Instancia un nuevo cuenta bancaria personal.
     */
    public CuentaBancariaPersonal() {
		super();
    }
   
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idCuentaBancariaPersonal == null) ? 0 : idCuentaBancariaPersonal.hashCode());
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
        CuentaBancariaPersonal other = (CuentaBancariaPersonal) obj;
        if (idCuentaBancariaPersonal == null) {
            if (other.idCuentaBancariaPersonal != null) {
                return false;
            }
        } else if (!idCuentaBancariaPersonal.equals(other.idCuentaBancariaPersonal)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CuentaBancariaPersonal [idCuentaBancariaPersonal=" + idCuentaBancariaPersonal + "]";
    }
   
}