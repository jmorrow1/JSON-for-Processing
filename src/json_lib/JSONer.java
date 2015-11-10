package json_lib;

import java.lang.reflect.Field;

import processing.data.JSONObject;

/**
 * 
 * @author James Morrow
 *
 */

//TODO: add saving and loading of non-primitive fields
//TODO: order if-statements that check for field types by use frequency
//ISSUE: if a subclass has a field of the same name as its superclass, only the superclass field gets saved. 
//I know why this happens, but the question is what to do about it.

public abstract class JSONer implements JSONable {
	private Field[] fields;
	
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
		JSONObject json = new JSONObject();
		
		//write the values of the this class's declared fields to the json object.	
		Class c = this.getClass();
		while (c != JSONer.class) {
			Field[] fields = c.getDeclaredFields();
			
			for (int i=0; i<fields.length; i++) {
				if (fields[i].getType() == char.class) {
					json.setString(fields[i].getName(), getValue(fields[i]).toString());
				}
				else if (fields[i].getType() == short.class) {
					json.setInt(fields[i].getName(), (int)((short)getValue(fields[i])));
				}
				else if (fields[i].getType() == int.class) {
					json.setInt(fields[i].getName(), (int)getValue(fields[i]));
				}
				else if (fields[i].getType() == byte.class) {
					json.setInt(fields[i].getName(), (int)((byte)getValue(fields[i])));
				}
				else if (fields[i].getType() == long.class) {
					json.setLong(fields[i].getName(), (long)getValue(fields[i]));
				}
				else if (fields[i].getType() == float.class) {
					json.setFloat(fields[i].getName(), (float)getValue(fields[i]));
				}
				else if (fields[i].getType() == double.class) {
					json.setDouble(fields[i].getName(), (double)getValue(fields[i]));
				}
				else if (fields[i].getType() == boolean.class) {
					json.setBoolean(fields[i].getName(), (boolean)getValue(fields[i]));
				}
				else if (fields[i].getType() == String.class) {
					json.setString(fields[i].getName(), (String)getValue(fields[i]));
				}
				else {
					Object value = getValue(fields[i]);
					if (value instanceof JSONer) {
						JSONObject nestedJSONObject = ((JSONer) value).toJSON();
						json.setJSONObject(fields[i].getName(), nestedJSONObject);
					}
					else {
						
					}
				}
			}
			
			c = c.getSuperclass();
		}
		return json;
	}
	
	private Object getValue(Field f) {
		try {
			f.setAccessible(true);
			Object obj = f.get(this);
			f.setAccessible(false);
			return obj;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
