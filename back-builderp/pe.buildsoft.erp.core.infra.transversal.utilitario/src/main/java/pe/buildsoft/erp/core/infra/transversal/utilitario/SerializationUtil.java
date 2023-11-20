package pe.buildsoft.erp.core.infra.transversal.utilitario;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * This class is a utility class for performing the serialization and
 * deserialization operations provided the required information.
 *
 */
public class SerializationUtil {

	/** Read the object from Base64 string. */
	public static Object fromString(String serializedObject) {
		Base64 base64 = new Base64();
		try {
			if (StringUtil.isNotNullOrBlank(serializedObject)) {
				byte[] data = base64.decode(serializedObject);
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
				Object o = ois.readObject();
				ois.close();
				return o;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/** Write the object to a Base64 string. */
	public static String toString(Object object) {
		String serializedObject = "";
		Base64 base64 = new Base64();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.close();
			serializedObject = base64.encodeToString(baos.toByteArray());
		} catch (Exception e) {
			serializedObject = "Error==>" + e.getMessage();
		}
		return serializedObject;

	}
}