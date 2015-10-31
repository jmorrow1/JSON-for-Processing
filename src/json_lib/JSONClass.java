package json_lib;

import java.lang.reflect.Field;

import processing.data.JSONObject;

/**
 * 
 * @author James Morrow
 *
 */
public abstract class JSONClass implements JSONable {
	private Field[] fields;
	
	public JSONClass(JSONObject json) {
		Field[] fields = getDeclaredFields();
		try {
			for (int i=0; i<fields.length; i++) {
				fields[i].setAccessible(true);
				if (fields[i].getType() == int.class) {
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
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		//write the values of the this class's declared fields to the json object.
		Field[] fields = getDeclaredFields();
		Object[] values = getDeclaredFieldValues();			
		assert fields.length == values.length;		
		for (int i=0; i<fields.length; i++) {
			if (fields[i].getType() == int.class) {
				json.setInt(fields[i].getName(), (int)values[i]);
			}
			else if (fields[i].getType() == long.class) {
				json.setLong(fields[i].getName(), (long)values[i]);
			}
			else if (fields[i].getType() == float.class) {
				json.setFloat(fields[i].getName(), (float)values[i]);
			}
			else if (fields[i].getType() == double.class) {
				json.setDouble(fields[i].getName(), (double)values[i]);
			}
			else if (fields[i].getType() == boolean.class) {
				json.setBoolean(fields[i].getName(), (boolean)values[i]);
			}
			else if (fields[i].getType() == String.class) {
				json.setString(fields[i].getName(), (String)values[i]);
			}
		}
		//TODO write the values of the declared fields of the superclasses to this class.
		return json;
	}
	
	private final Object[] getDeclaredFieldValues() {
		Field[] fields = getDeclaredFields();
		Object[] values = new Object[fields.length];
		for (int i=0; i<fields.length; i++) {
			fields[i].setAccessible(true);
			try {
				values[i] = fields[i].get(this);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			fields[i].setAccessible(false);
		}
		return values;
	}
	
	private final Field[] getDeclaredFields() {
		if (fields != null) return fields;
		
		return this.getClass().getDeclaredFields();
	}
}
