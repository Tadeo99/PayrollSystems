package pe.buildsoft.erp.core.domain.entidades.hora;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.entidades.escalafon.CentroCosto;
import pe.buildsoft.erp.core.domain.entidades.escalafon.Personal;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class Requerimiento.
 * <ul>
 * <li>Copyright 2021 MAPFRE -
 * MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author BuildSoft
 * @version 1.0, Thu Feb 24 15:16:43 COT 2022
 * @since BuildErp 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "requerimiento", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
public class Requerimiento extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id requerimiento. */
    @Id
    @Column(name = "idrequerimiento" , length = 32)
    private String idRequerimiento;
   
    /** El area. */
    @Column(name = "area" , length = 100)
    private String area;
   
    /** El codigo. */
    @Column(name = "codigo" , length = 15)
    private String codigo;
    
    /** El nombre. */
    @Column(name = "nombre" , length = 200)
    private String nombre;
   
    /** El horas. */
    @Column(name = "horas" , precision = 18 , scale = 2)
    private BigDecimal horas;
   
    /** El id personal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonal", referencedColumnName = "idpersonal")
    private Personal personal;
   
    /** El tipo. */
    @Column(name = "tipo" , length = 20)
    private String tipo;
   
    /** El fecha inicio. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechainicio")
    private OffsetDateTime fechaInicio;
   
    /** El fecha fin. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechafin")
    private OffsetDateTime fechaFin;
   
    /** El fecha termino. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fechatermino")
    private OffsetDateTime fechaTermino;
    
    /** El fecha termino. */
    //@Temporal( TemporalType.TIMESTAMP)
    @Column(name = "fecharegistra")
    private OffsetDateTime fechaRegistra;
   
    /** El estado. */
    @Column(name = "estado" , length = 1)
    private String estado;
   
    /** El observaciones. */
    @Column(name = "observaciones" , length = 200)
    private String observaciones;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcentrocosto", referencedColumnName = "idcentrocosto")
    private CentroCosto centroCosto;
    
	/** El item by estado tipogobierno.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipogobierno", referencedColumnName = "idItem")
	private Item itemByTipoGobierno; */
	@Column(name = "idtipogobierno" , precision = 18 , scale = 0)
    private Long idItemByTipoGobierno;
   
    /** El id requerimiento detalle registro cabecera list. */
    @OneToMany(mappedBy = "requerimiento", fetch = FetchType.LAZY)
    private List<RegistroHoraDet> requerimientoDetalleRegistroHoraList = new ArrayList<>();
    
    /** El id requerimiento requerimiento personal list. */
    @OneToMany(mappedBy = "requerimiento", fetch = FetchType.LAZY)
    private List<RequerimientoPersonal> requerimientoRequerimientoPersonalList = new ArrayList<>();
    
    
    /**
     * Instancia un nuevo requerimiento.
     */
    public Requerimiento() {
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
                + ((idRequerimiento == null) ? 0 : idRequerimiento.hashCode());
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
        Requerimiento other = (Requerimiento) obj;
        if (idRequerimiento == null) {
            if (other.idRequerimiento != null) {
                return false;
            }
        } else if (!idRequerimiento.equals(other.idRequerimiento)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Requerimiento [idRequerimiento=" + idRequerimiento + "]";
    }
   
}