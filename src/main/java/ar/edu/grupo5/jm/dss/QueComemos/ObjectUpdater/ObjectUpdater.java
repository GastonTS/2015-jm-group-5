package ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater;

import java.lang.reflect.Field;

public interface ObjectUpdater {

	default public void update(Object oldObject, Object newObject) {
		this.sonObjetosDeMismaClase(oldObject, newObject);

		Field[] properties = oldObject.getClass().getDeclaredFields();

		for (Field property : properties) {

			if (property.isAnnotationPresent(Updateable.class)) {

				try {
					property.setAccessible(true);
					Object value = property.get(newObject);
					if (value != null) {
						property.set(oldObject, value);
					}
				} catch (SecurityException e) {
					throw new PropertyFailInSetAccessible("No se pudo settear " + "accesible la propiedad: " + property.getName()
							+ ". Deshabilite el securityManager");

				} catch (IllegalAccessException e) {
					/*
					 * Gracias a las excepciones chequeadas tengo que hacer un
					 * catch y no hacer nada este catch no tiene sentido porque
					 * dadas las condiciones definidas arriba siempre va a poder
					 * acceder al metodo, ya que el atributo se settea acesible
					 * en todo caso va a fallar el setAccessible(true) y lanzará
					 * su SecurityException
					 */
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					/*
					 * Nunca va a pasar esto porque chequea que los objetos
					 * tengan la misma clase entonces van a tener los mismos
					 * atributos provistos por esa sola clase. Ya que no se
					 * trabaja con atributos heradados
					 */
					e.printStackTrace();
				}
			}
		}
	}

	public default void sonObjetosDeMismaClase(Object oldObject, Object newObject) {
		if (!oldObject.getClass().equals(newObject.getClass())) {
			throw new ObjectIsFromADifferentClass("El objeto actualizado es de la clase " + oldObject.getClass().getName()
					+ ", mientras que el objeto a actualizar es de la clase " + newObject.getClass().getName());
		}
	}

	public default boolean sonObjetosActualizados(Object oldObject, Object newObject) {

		this.sonObjetosDeMismaClase(oldObject, newObject);
		boolean flag = true;
		Field[] properties = oldObject.getClass().getDeclaredFields();

		for (Field property : properties) {

			if (property.isAnnotationPresent(Updateable.class)) {

				try {
					property.setAccessible(true);

					Object oldValue = property.get(oldObject);
					Object newValue = property.get(newObject);
					if ((!oldValue.equals(newValue)) && (!newValue.equals(null))) {
						flag = false;
						break;// corto el ciclo como un campeon(?, ojalá no vean
								// esto
						// regret nothing
					}

				} catch (SecurityException e) {
					throw new PropertyFailInSetAccessible("No se pudo settear " + "accesible la propiedad: " + property.getName()
							+ ". Deshabilite el securityManager");
				} catch (IllegalAccessException e) {
					/*
					 * Gracias a las excepciones chequeadas tengo que hacer un
					 * catch y no hacer nada este catch no tiene sentido porque
					 * dadas las condiciones definidas arriba siempre va a poder
					 * acceder al metodo, ya que el atributo se settea acesible
					 * en todo caso va a fallar el setAccessible(true) y lanzará
					 * su SecurityException
					 */
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					/*
					 * Nunca va a pasar esto porque chequea que los objetos
					 * tengan la misma clase entonces van a tener los mismos
					 * atributos provistos por esa sola clase. Ya que no se
					 * trabaja con atributos heradados
					 */
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
