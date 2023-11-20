package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;
 

/**
 * La Class CuentaBancariaPersonalDTO.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Apr 22 23:29:57 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Getter
@Setter
public class CuentaBancariaPersonalDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id cuenta bancaria personal. */
    private String idCuentaBancariaPersonal;
   
    /** El personal. */
    private PersonalDTO personal;
   
    /** El item by banco. */
    private Long idItemByBanco;
    private ItemDTO itemByBanco;
   
    /** El nro cuenta. */
    private String nroCuenta;
   
    /** El nro c c i. */
    private String nroCCI;
   
    /** El fecha apertura. */
    private OffsetDateTime fechaApertura;
   
    /** El item by moneda. */
    private Long idItemByMoneda;
    private ItemDTO itemByMoneda;
   
    /** El item tipo cuenta. */
    private Long idItemByTipoCuenta;
    private ItemDTO itemByTipoCuenta;
   
    /** El modulo. */
    private String modulo;
   
    /** El sucursal. */
    private String sucursal;
   
    /** El sub cuenta. */
    private String subCuenta;
   
    /** El item by tipo deposito cuenta. */
    private Long idItemByTipoDepositoCuenta;
    private ItemDTO itemByTipoDepositoCuenta;
   
    private String esCts;
    
    private List<String> listaIdPersonal = new ArrayList<String>();
    /**
     * Instancia un nuevo cuenta bancaria personalDTO.
     */
    public CuentaBancariaPersonalDTO() {
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
        CuentaBancariaPersonalDTO other = (CuentaBancariaPersonalDTO) obj;
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
        return "CuentaBancariaPersonalDTO [idCuentaBancariaPersonal=" + idCuentaBancariaPersonal + "]";
    }
   
}