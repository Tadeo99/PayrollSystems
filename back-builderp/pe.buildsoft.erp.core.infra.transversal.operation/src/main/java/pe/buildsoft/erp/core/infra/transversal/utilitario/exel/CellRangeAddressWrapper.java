package pe.buildsoft.erp.core.infra.transversal.utilitario.exel;

import org.apache.poi.ss.util.CellRangeAddress;

/**
 * <ul>
 * <li>Copyright 2014 MAPFRE. Todos los derechos reservados.</li>
 * </ul>
 * 
 * La Class CellRangeAddressWrapper.
 *
 * @author BuildSoft.
 * @version 1.0 , 29/04/2016
 * @since BuildErp 1.0
 */
public class CellRangeAddressWrapper implements Comparable<CellRangeAddressWrapper> {

	/** La range. */
	public CellRangeAddress range;

	/**
	 * Instancia un nuevo cell range address wrapper.
	 *
	 * @param theRange el the range
	 */
	public CellRangeAddressWrapper(CellRangeAddress theRange) {
		this.range = theRange;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CellRangeAddressWrapper o) {
		if (range.getFirstColumn() < o.range.getFirstColumn() && range.getFirstRow() < o.range.getFirstRow()) {
			return -1;
		} else if (range.getFirstColumn() == o.range.getFirstColumn() && range.getFirstRow() == o.range.getFirstRow()) {
			return 0;
		} else {
			return 1;
		}
	}
}