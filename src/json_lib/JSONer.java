package json_lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import processing.data.JSONObject;

/**
 * 
 * @author James Morrow
 *
 */

//TODO: save and load arrays
//TODO: order if-statements that check for field types by use frequency
//TODO: go back and be more thoughtful about exception handling
//ISSUE: if a subclass has a field of the same name as its superclass, only the superclass field gets saved. 
//ISSUE: if a class extends a class from an external library, it can't be a JSONer so there needs to be a way for the user
//       to incorporate such classes into the saving and loading process. Furthermore, even if the user defines their own
//       class hierarchy but only wants some of the subclasses to extend JSONer, we'll have a problem.

public abstract class JSONer implements JSONable {
	public JSONer() {}
	
	public JSONer(JSONObject json) {
		Class c = this.getClass();
		
		while (c != JSONer.class) {
			Field[] fields = c.getDeclaredFields();
			try {
				for (int i=0; i<fields.length; i++) {
					fields[i].setAccessible(true);
					if (fields[i].getType() == char.class) {
						String s = json.getString(fields[i].getName());				
						fields[i].setChar(this, s.charAt(0));
					}
					else if (fields[i].getType() == int.class || fields[i].getType() == byte.class || 
							  fields[i].getType() == short.class) {
						fields[i].setInt(this, json.getInt(fields[i].getName()));
					}
					else if (fields[i].getType() == long.class) {
						fields[i].setLong(this, json.getLong(fields[i].getName()));
					}
					else if (fields[i].getType() == float.class) {
						fields[i].setFloat(this, json.getFloat(fields[i].getName()));
					}
					else if (fields[i].getType() == double.class) {
						fields[i].setDouble(this, json.getDouble(fields[i].getName()));
					}
					else if (fields[i].getType() == boolean.class) {
						fields[i].setBoolean(this, json.getBoolean(fields[i].getName()));
					}
					else if (fields[i].getType() == String.class) {
						fields[i].set(this, json.getString(fields[i].getName()));
					}
					else {
						Class fieldType = fields[i].getType();
						if (JSONer.class.isAssignableFrom(fieldType)) {
							JSONObject nestedJSONObject = json.getJSONObject(fieldType.getName());
							if (nestedJSONObject != null) {
								Constructor fieldCons = null;
								try {
									fieldCons = fieldType.getConstructor(JSONObject.class);
								} catch (NoSuchMethodException | SecurityException e) {
									e.printStackTrace();
								}
								try {
									fields[i].set(this, fieldCons.newInstance(json.getJSONObject(fieldType.getName())));
								} catch (IllegalArgumentException | InstantiationException | InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}
					}
					fields[i].setAccessible(false);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			c = c.getSuperclass();
		}
	}

	@Override
	public JSONObject toJSON() {
		return toJSON(this);
	}
	
	private static JSONObject toJSON(Object obj) {
		JSONObject json = new JSONObject();
		
		//write the values of the this class's declared fields to the json object.	
		Class c = obj.getClass();
		while (c != JSONer.class) {
			Field[] fields = c.getDeclaredFields();
			
			for (int i=0; i<fields.length; i++) {
				if (fields[i].getType() == char.class) {
					json.setString(fields[i].getName(), getValue(obj, fields[i]).toString());
				}
				else if (fields[i].getType() == short.class) {
					json.setInt(fields[i].getName(), (int)((short)getValue(obj, fields[i])));
				}
				else if (fields[i].getType() == int.class) {
					json.setInt(fields[i].getName(), (int)getValue(obj, fields[i]));
				}
				else if (fields[i].getType() == byte.class) {
					json.setInt(fields[i].getName(), (int)((byte)getValue(obj, fields[i])));
				}
				else if (fields[i].getType() == long.class) {
					json.setLong(fields[i].getName(), (long)getValue(obj, fields[i]));
				}
				else if (fields[i].getType() == float.class) {
					json.setFloat(fields[i].getName(), (float)getValue(obj, fields[i]));
				}
				else if (fields[i].getType() == double.class) {
					json.setDouble(fields[i].getName(), (double)getValue(obj, fields[i]));
				}
				else if (fields[i].getType() == boolean.class) {
					json.setBoolean(fields[i].getName(), (boolean)getValue(obj, fields[i]));
				}
				else if (fields[i].getType() == String.class) {
					json.setString(fields[i].getName(), (String)getValue(obj, fields[i]));
				}
				else {
					Object value = getValue(obj, fields[i]);
					if (value != null && value instanceof JSONer) {
						JSONObject nestedJSONObject = toJSON(value);
						json.setJSONObject(fields[i].getName(), nestedJSONObject);
					}
				}
			}
			
			c = c.getSuperclass();
		}
		return json;
	}
	
	private static Object getValue(Object obj, Field f) {
		try {
			f.setAccessible(true);
			Object value = f.get(obj);
			f.setAccessible(false);
			return value;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
