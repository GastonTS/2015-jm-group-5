package ar.edu.grupo5.jm.dss.QueComemos.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface ObjectUpdater {

	default public void update(Object update){
	    if(!this.getClass().equals(update.getClass())){
	    	throw new ObjectIsFromADifferentClass  ("El objeto actualizado es de la clase " 
	    			+ this.getClass().getName() 
	    			+ ", mientras que el objeto a actualizar es de la clase " 
	    			+ update.getClass().getName());
	    }

	    Method[] getters = this.getClass().getDeclaredMethods();
	    Method setter = null;

	    for(Method getter: getters){
	    	
	        if(getter.getName().startsWith("get")
	        		&& ( Modifier.isPublic(getter.getModifiers()))){

	            String getterName = getter.getName();
	            String setterName = getterName.replace("get", "set");
	           
	            try {
	            	setter = this.getClass().getMethod(setterName, getter.getReturnType());
	                Object value = getter.invoke(update);
	                setter.getReturnType().toString();
	                if(value != null){
	                    setter.invoke(this, value);
	                }
	            } catch (NoSuchMethodException e) {
	                throw new SetterIsMissingException ("Falta definir el metodo " 
	                		+ setterName);
	        /* 
	         *  }  catch (SecurityException e) {
	         *   	throw new SetterIsNotPublicException ("El metodo " 
	         *       		+ setterName + " debería ser publico");
	         * Creo que no hace falta esta excepcion porque si no llega a ser public
	         * al igual que el getter no reconoce el metodo. Y los getters 
	         * por condicion son solo publicos
	         */
	            } catch (IllegalAccessException e) {
					/* 
					 * Gracias a las excepciones chequeadas tengo que hacer un catch y no hacer nada 
					 * este catch no tiene sentido porque dadas las condiciones definidas arriba siempre 
					 * va a poder acceder al metodo, ya que es obtenido solo de esa clase misma
	            	*/
	            	e.printStackTrace();
				} catch (IllegalArgumentException e) {
					/*
					 * Si un getter te da un tipo diferente de lo que
					 * recibe el setter por parametro entonces no es el
					 * metodo correcto. Aunque en este caso no le veo mucho
					 * sentido largar otra  excepcion diferente
					 */
				} catch (InvocationTargetException e) {
					/*
					 *  No debería hacerme cargo de las excepciones acá
					 *  Ya que si un getter o setter produce una excepcion a
					 *  esta interfaz no le interesa
					 *  pero al ser chequeadas me obliga a catchearla
					 */
					e.printStackTrace();
				} 
	        }
	    }
	}
}
