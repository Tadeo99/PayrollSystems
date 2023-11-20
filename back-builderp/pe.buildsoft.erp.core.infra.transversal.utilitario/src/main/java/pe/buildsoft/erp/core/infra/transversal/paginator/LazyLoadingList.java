package pe.buildsoft.erp.core.infra.transversal.paginator;

import java.util.AbstractList;
import java.util.List;

/**
 * La Class LazyLoadingList.
 * <ul>
 * <li>Copyright 2014 MAPFRE-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @param <T> el tipo generico
 * @author BuildSoft.
 * @version 1.0, Tue Apr 29 17:38:17 COT 2014
 * @since Rep v1..0
 */
public class LazyLoadingList<T> extends AbstractList<T> {

    /** El data provider. */
    private IDataProvider<T> dataProvider;
    
    /** La lista firts page data. */
    private List<T> firtsPageData;
    
    /** La lista current page data. */
    private List<T> currentPageData;
    
    /** El current page. */
    private int currentPage = -1;
    
    /** El page size. */
    private int pageSize;

    /**
     * Instancia un nuevo lazy loading list.
     *
     * @param dataProvider el data provider
     * @param pageSize el page size
     */
    public LazyLoadingList(IDataProvider<T> dataProvider, int pageSize) {
        this.dataProvider = dataProvider;
        this.pageSize = pageSize;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#get(int)
     */
    @Override
    public T get(int i) {
    	 if (i < pageSize) {
             if (firtsPageData == null) {
                 firtsPageData = dataProvider.getBufferedData(i, pageSize);
             }
             return firtsPageData.get(i);
         }
         int page = i / pageSize;
         if (page != currentPage) {
             currentPage = page;
             currentPageData = dataProvider.getBufferedData(i, pageSize);
         }
         return currentPageData.get(i % pageSize);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        return dataProvider.getTotalResultsNumber();
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#clear()
     */
    @Override
    public void clear() {
        firtsPageData.clear();
        currentPageData.clear();
    }
}
