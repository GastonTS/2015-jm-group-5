package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import org.apache.commons.lang3.text.WordUtils;

public interface WithEnumUtils {

	public default String getFormattedName() {
		return WordUtils.capitalize(this.name().toLowerCase()).replaceAll("_", " ");
	}
	
	public abstract String name();	
}
