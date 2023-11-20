package pe.buildsoft.erp.core.application.entidades.escalafon;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.application.entidades.common.ItemDTO;
import pe.buildsoft.erp.core.application.entidades.common.UbigeoDTO;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class DireccionPersonalDTO.
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
public class DireccionPersonalDTO extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id direccion personal. */
    private String idDireccionPersonal;
   
    /** El personal. */
    private PersonalDTO personal;
   
    /** El domiciliado. */
    private String domiciliado;
   
    /** El direcion centro asistencial essalud. */
    private String direcionCentroAsistencialEssalud;
   
    /** El item by tipo via. */
    private Long idItemByTipoVia;
    private ItemDTO itemByTipoVia;
   
    /** El nombre tipo via. */
    private String nombreTipoVia;
   
    /** El item by zona. */
    private Long idItemByZona;
    private ItemDTO itemByZona;
   
    /** El nombre zona. */
    private String nombreZona;
   
    /** El numero. */
    private Long numero;
   
    /** El interior. */
    private Long interior;
   
    /** El departamento. */
    private Long departamento;
   
    /** El block. */
    private Long block;
   
    /** El mazana. */
    private String mazana;
   
    /** El lote. */
    private Long lote;
   
    /** El etapa. */
    private String etapa;
   
    /** El kilometro. */
    private BigDecimal kilometro;
   
    /** El referencia. */
    private String referencia;
   
    /** El item by procedencia direccion. */
    private Long idItemByProcedenciaDireccion;
    private ItemDTO itemByProcedenciaDireccion;
   
    /** El ubigeo. */
    private String idUbigeo;
    private UbigeoDTO ubigeo;
    
    private List<String> listaIdPersonal = new ArrayList<String>();
   
    /**
     * Instancia un nuevo direccion personalDTO.
     */
    public DireccionPersonalDTO() {
		super();
    }
   

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((idDireccionPersonal == null) ? 0 : idDireccionPersonal.hashCode());
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
        DireccionPersonalDTO other = (DireccionPersonalDTO) obj;
        if (idDireccionPersonal == null) {
            if (other.idDireccionPersonal != null) {
                return false;
            }
        } else if (!idDireccionPersonal.equals(other.idDireccionPersonal)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DireccionPersonalDTO [idDireccionPersonal=" + idDireccionPersonal + "]";
    }
   
}