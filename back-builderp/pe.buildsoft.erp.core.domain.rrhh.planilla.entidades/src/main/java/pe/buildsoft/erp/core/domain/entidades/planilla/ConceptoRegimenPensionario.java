package pe.buildsoft.erp.core.domain.entidades.planilla;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.domain.util.ConfiguracionEntityManagerUtil;
import pe.buildsoft.erp.core.infra.transversal.entidades.BaseEntidad;

/**
 * La Class ConceptoRegimenPensionario.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 2.1, Thu Apr 22 23:10:02 COT 2021
 * @since BUILDERP-CORE 2.1
 */
@Entity
@Table(name = "ConceptoRegimenPensionario", schema = ConfiguracionEntityManagerUtil.ESQUEMA_CORE_RRHH_PLANILLA)
@Getter
@Setter
public class ConceptoRegimenPensionario extends BaseEntidad implements Serializable {
 
    /** La Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** El id concepto regimen pensionario. */
    @Id
    @Column(name = "idConceptoRegimenPensionario" , length = 32)
    private String idConceptoRegimenPensionario;
   
    /** El item by regimen pensionario.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRegimenPensionario", referencedColumnName = "idItem")
    private Item itemByRegimenPensionario; */
    @Column(name = "idRegimenPensionario", precision = 18, scale = 0)
	private Long idItemByRegimenPensionario;
   
    /** El periodo laboral personal. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPeriodoLaboraPersonal", referencedColumnName = "idPeriodoLaboraPersonal")
    private PeriodoLaboraPersonal periodoLaboraPersonal
    @Column(name = "idPeriodoLaboraPersonal" , length = 32)
    private String idPeriodoLaboraPersonal;*/
    
   
    /** El id mes by devengue. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMesDevengue", referencedColumnName = "idItem")
    private Item itemByMesByDevengue;*/
    @Column(name = "idMesDevengue", precision = 18, scale = 0)
   	private Long idItemByMesByDevengue;
   
    /** El comision fija. */
    @Column(name = "comisionFija" , precision = 18 , scale = 2)
    private BigDecimal comisionFija;
   
    /** El comision sobre flujo. */
    @Column(name = "comisionSobreFlujo" , precision = 18 , scale = 2)
    private BigDecimal comisionSobreFlujo;
   
    /** El comision sobre flujo mixto. */
    @Column(name = "comisionSobreFlujoMixto" , precision = 18 , scale = 2)
    private BigDecimal comisionSobreFlujoMixto;
   
    /** El comision anual sobre saldo mixto. */
    @Column(name = "comisionAnualSobreSaldoMixto" , precision = 18 , scale = 2)
    private BigDecimal comisionAnualSobreSaldoMixto;
   
    /** El prima seguros. */
    @Column(name = "primaSeguros" , precision = 18 , scale = 2)
    private BigDecimal primaSeguros;
   
    /** El aporte obligatorio. */
    @Column(name = "aporteObligatorio" , precision = 18 , scale = 2)
    private BigDecimal aporteObligatorio;
   
    /** El remuneracion maxima asegurable. */
    @Column(name = "remuneracionMaximaAsegurable" , precision = 18 , scale = 2)
    private BigDecimal remuneracionMaximaAsegurable;
   
    /** El anhio. 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAnhio", referencedColumnName = "idAnhio")    
    private Anhio anhio;*/
    @Column(name = "idAnhio", precision = 18, scale = 0)
   	private Long idAnhio;
    
    @Transient
	private boolean esEliminado = false;
    
    /**
     * Instancia un nuevo concepto regimen pensionario.
     */
    public ConceptoRegimenPensionario() {
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
                + ((idConceptoRegimenPensionario == null) ? 0 : idConceptoRegimenPensionario.hashCode());
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
        ConceptoRegimenPensionario other = (ConceptoRegimenPensionario) obj;
        if (idConceptoRegimenPensionario == null) {
            if (other.idConceptoRegimenPensionario != null) {
                return false;
            }
        } else if (!idConceptoRegimenPensionario.equals(other.idConceptoRegimenPensionario)) {
            return false;
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ConceptoRegimenPensionario [idConceptoRegimenPensionario=" + idConceptoRegimenPensionario + "]";
    }
   
}