package pe.buildsoft.erp.core.infra.transversal.utilitario.cryto;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
//import jakarta.persistence.Column;
//import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.Id;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * La Class CryptoUtil.
 * <ul>
 * <li>Copyright 2017 ndavilal-
 * mapfre. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, 01/03/2016
 * @since PWR v1.0
 */
public class CryptoUtil {

	private static final String CIPHER_DE_SEDE_ECB_PKCS5_PADDING = "DESede/ECB/PKCS5Padding";

	/** La Constante CLAVE_CIFRADO. */
	private static final String CLAVE_CIFRADO = "94704173533428418a466c6f772925";

	
	private static Logger log = LoggerFactory.getLogger(CryptoUtil.class);
	
	private CryptoUtil() {
	    throw new IllegalStateException("Utility CryptoUtil class");
	}


	
	/**
	 * Encriptar cadena.
	 *
	 * @param s el s
	 * @return the string
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws ShortBufferException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception the exception
	 */
	public static String encriptarCadena(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException  {
		    SecretKeySpec clave = crearClaveEncriptacion();
		    
			byte[] input = s.getBytes("utf-8");
		    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, clave);
			
			byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
			int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
			cipher.doFinal(cipherText, ctLength);
			return Base64.encodeBase64String(cipherText);
			
	}

	/**
	 * Desencriptar cadena.
	 *
	 * @param s el s
	 * @return the string
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception the exception
	 */
	public static String desencriptarCadena(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException  {
		if (s == null) {
			return null;
		}
		 SecretKeySpec clave = crearClaveEncriptacion();
		 Cipher cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
		 cifrador.init(Cipher.DECRYPT_MODE, clave);
		 Base64 base64 = new Base64();
		 return new String(cifrador.doFinal(base64.decode(s.getBytes())));
	}
	
	
	/**
	 * Esta encriptado.
	 *
	 * @param s the s
	 * @return true, if successful
	 */
	public static boolean estaEncriptado(String s) {
		boolean resultado = true;
		if (s == null) {
			return true;
		}
		try {
			SecretKeySpec clave = crearClaveEncriptacion();
			Cipher cifrador = Cipher.getInstance(CIPHER_DE_SEDE_ECB_PKCS5_PADDING);
			cifrador.init(Cipher.DECRYPT_MODE, clave);
			Base64 base64 = new Base64();
		    new String(cifrador.doFinal(base64.decode(s.getBytes())));
		} catch (Exception e) {
			resultado = false;
		}		
		
		return resultado;
	}

	/**
	 * Crear clave encriptacion.
	 *
	 * @return the secret key
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws UnsupportedEncodingException 
	 */
	private static SecretKeySpec crearClaveEncriptacion() throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(CLAVE_CIFRADO.getBytes("UTF-8"));
		return new SecretKeySpec(thedigest, "AES");
	}

	/**
	 * Encriptar desencriptar.
	 *
	 * @param <T> el tipo generico
	 * @param dto el dto
	 * @param flaj el flaj
	 * @return the t
	 */
	public static <T> T encriptarDesencriptar(T dto, boolean flaj) {
		PropertyDescriptor[] propiedades = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(dto.getClass());	
		for (var pd : propiedades) {
			try {
				Field f = dto.getClass().getDeclaredField(pd.getName());
				/*if (!(verificarField(pd) || (f.isAnnotationPresent(Id.class)	|| f.isAnnotationPresent(EmbeddedId.class))) &&
						f.isAnnotationPresent(Column.class) || (!flaj && pd.getPropertyType().equals(String.class))) {
					parseValue(dto, flaj, pd);
				}*/
				
			} catch (Exception e) {
				continue;
			}
		}
		return dto;
	}

	private static  <T> T parseValue(T dto, boolean flaj,PropertyDescriptor pd) throws IllegalAccessException, InvocationTargetException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
		if (pd.getPropertyType().equals(String.class)) {
			String value = (String) pd.getReadMethod().invoke(dto);
			if (value != null && !value.trim().equals("") && value.length() > 0) {
				value = (flaj) ? CryptoUtil.encriptarCadena(value) : CryptoUtil.desencriptarCadena(value);
				pd.getWriteMethod().invoke(dto, value);
			}
		}
		return dto;
	}

	
	private static boolean verificarField(PropertyDescriptor pd) {
		return pd.getPropertyType().isEnum() || pd.getName().equalsIgnoreCase("Class");
	}

	/**
	 * Desencriptar lista.
	 *
	 * @param <T> el tipo generico
	 * @param lista el lista
	 * @return the collection
	 */
	public static <T> Collection<T> desencriptarLista(Collection<T> lista) {
		try {
			if (lista != null && !lista.isEmpty()) {
				for (var t : lista) {
					desencriptaObject(t);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return lista;
	}

	/**
	 * Desencriptar lista.
	 *
	 * @param <T> el tipo generico
	 * @param lista el lista
	 * @return the list
	 */
	public static <T> List<T> desencriptarLista(List<T> lista) {
		try {
			if (lista != null && !lista.isEmpty()) {
				for (var t : lista) {
					desencriptaObject(t);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return lista;
	}
	
	/**
	 * Encriptar lista objetos.
	 *
	 * @param <T> el tipo generico
	 * @param lista el lista
	 * @return the list
	 */
	public static <T> List<T> encriptarListaObjetos(List<T> lista) {
		try {
			if (lista != null && !lista.isEmpty()) {
				for (var t : lista) {
					encriptarDesencriptar(t, true);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return lista;
	}
	
	/**
	 * Desencriptar lista objetos.
	 *
	 * @param <T> el tipo generico
	 * @param lista el lista
	 * @return the list
	 */
	public static <T> List<T> desencriptarListaObjetos(List<T> lista) {
		try {
			if (lista != null && !lista.isEmpty()) {
				for (var t : lista) {
					encriptarDesencriptar(t, false);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return lista;
	}
	
	/**
	 * Desencriptar collecion.
	 *
	 * @param <T> el tipo generico
	 * @param lista el lista
	 * @return the list
	 */
	public static <T> List<T> desencriptarCollecion(Collection<T> lista) {
		try {
			if (lista != null && !lista.isEmpty()) {
				for (var t : lista) {
					desencriptaObject(t);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return (List<T>) lista;
	}

	/**
	 * Desencripta object.
	 *
	 * @param <T> el tipo generico
	 * @param objeto el objeto
	 * @return the t
	 */
	public static <T> T desencriptaObject(T objeto) {
		try {
			if (objeto != null) {
				PropertyDescriptor propiedad = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptor(objeto, "encriptado");
				boolean encriptado = (Boolean) propiedad.getReadMethod().invoke(objeto);
				if (encriptado) {
					encriptarDesencriptar(objeto, false);
					propiedad.getWriteMethod().invoke(objeto, false);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return objeto;
	}
}
