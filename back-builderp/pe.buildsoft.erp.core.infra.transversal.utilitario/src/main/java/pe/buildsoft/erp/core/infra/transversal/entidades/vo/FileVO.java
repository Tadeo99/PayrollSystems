package pe.buildsoft.erp.core.infra.transversal.entidades.vo;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import pe.buildsoft.erp.core.infra.transversal.type.TipoReporteGenerarType;


@Getter
@Setter
public class FileVO implements Serializable {

    /** La Constante serialVersionUID. */
	private static final long serialVersionUID = -3854043104169839788L;

	/** El name. */
    private String name;
    
    /** El mime. */
    private String mime;
    
    /** El length. */
    private long length;
    
    /** La data. */
    private byte[] data;
    
   /** La reporte generado map. */
   private Map<String,Object> reporteGeneradoMap;
    
   private String DataBig;
   
   private String userName;
   
   /** La codigo. */
   private String codigo;
   
   private TipoReporteGenerarType tipoReporteGenerarType;
   
   private boolean esCopiaCorreo;
   
   /** El mime. */
   private String mimePresentar;
   private String extension;
   private String ruta="";
   
   private long lastModified = 0;
   private boolean eliminarArchivoTemp = true;
   
   private boolean nombreOriginal = true;
	/**
     * Establece el name.
     *
     * @param name el new name
     */
    public void setName(String name) {
        this.name = name;
        int extDot = name.lastIndexOf('.');
        if (extDot > 0) {
            String extension = name.substring(extDot + 1);
            if ("bmp".equals(extension)) {
                mime = "image/bmp";
            } else if ("jpg".equals(extension)) {
                mime = "image/jpeg";
            } else if ("gif".equals(extension)) {
                mime = "image/gif";
            } else if ("png".equals(extension)) {
                mime = "image/png";
            } else if ("xls".equals(extension)) {
                mime = "application/vnd.ms-excel";
            } else if ("xlsx".equals(extension)) {
                mime = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            } else {
                mime = "image/unknown";
            }
        }
    }
}