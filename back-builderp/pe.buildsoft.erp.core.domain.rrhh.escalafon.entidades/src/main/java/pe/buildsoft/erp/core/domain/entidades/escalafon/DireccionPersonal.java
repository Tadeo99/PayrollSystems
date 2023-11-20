package pe.buildsoft.erp.core.domain.entidades.escalafon;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * La Class DireccionPersonal.
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
@Table(name = "DireccionPersonal", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_ESCAL)
@Getter
@Setter
public class DireccionPersonal extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id direccion personal. */
    @Id
    @Column(name = "idDireccionPersonal" , length = 32)
    private String idDireccionPersonal;
   
    /** El personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonal", referencedColumnName = "idPersonal")
    private Personal personal;
   
    /** El domiciliado. */
    @Column(name = "domiciliado" , length = 1)
    private String domiciliado;
   
    /** El direcion centro asistencial essalud. */
    @Column(name = "direcionCentroAsistencialEssalud" , length = 1)
    private String direcionCentroAsistencialEssalud;
   
    /** El item by tipo via. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoVia", referencedColumnName = "idItem")
    private Item itemByTipoVia; */
    @Column(name = "idTipoVia" , precision = 18 , scale = 0)
    private Long idItemByTipoVia;
   
    /** El nombre tipo via. */
    @Column(name = "nombreTipoVia" , length = 200)
    private String nombreTipoVia;
   
    /** El item by zona. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idZona", referencedColumnName = "idItem")
    private Item itemByZona;*/
    @Column(name = "idZona" , precision = 18 , scale = 0)
    private Long idItemByZona;
   
    /** El nombre zona. */
    @Column(name = "nombreZona" , length = 200)
    private String nombreZona;
   
    /** El numero. */
    @Column(name = "numero" , precision = 18 , scale = 0)
    private Long numero;
   
    /** El interior. */
    @Column(name = "interior" , precision = 18 , scale = 0)
    private Long interior;
   
    /** El departamento. */
    @Column(name = "departamento" , precision = 18 , scale = 0)
    private Long departamento;
   
    /** El block. */
    @Column(name = "block" , precision = 18 , scale = 0)
    private Long block;
   
    /** El mazana. */
    @Column(name = "mazana" , length = 200)
    private String mazana;
   
    /** El lote. */
    @Column(name = "lote" , precision = 18 , scale = 0)
    private Long lote;
   
    /** El etapa. */
    @Column(name = "etapa" , length = 200)
    private String etapa;
   
    /** El kilometro. */
    @Column(name = "kilometro" , precision = 18 , scale = 2)
    private BigDecimal kilometro;
   
    /** El referencia. */
    @Column(name = "referencia" , length = 200)
    private String referencia;
   
    /** El item by procedencia direccion. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProcedenciaDireccion", referencedColumnName = "idItem")
    private Item itemByProcedenciaDireccion;*/
    @Column(name = "idProcedenciaDireccion" , precision = 18 , scale = 0)
    private Long idItemByProcedenciaDireccion;
   
    /** El ubigeo.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUbigeo", referencedColumnName = "idUbigeo")
    private Ubigeo ubigeo; */
    @Column(name = "idUbigeo", length = 6)
	private String idUbigeo;
   
    @Transient
    private List<String> listaIdPersonal = new ArrayList<>();
    
    /**
     * Instancia un nuevo direccion personal.
     */
    public DireccionPersonal() {
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
        DireccionPersonal other = (DireccionPersonal) obj;
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
        return "DireccionPersonal [idDireccionPersonal=" + idDireccionPersonal + "]";
    }
   
}