package json_lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * 
 * @author James Morrow
 *
 */
public interface JSONable {
    public JSONObject toJSON();

    default int[] toIntArray(JSONArray j) {
        int[] array = new int[j.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = j.getInt(i);
        }
        return array;
    }

    default float[] toFloatArray(JSONArray j) {
        float[] array = new float[j.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = j.getFloat(i);
        }
        return array;
    }

    default double[] toDoubleArray(JSONArray j) {
        double[] array = new double[j.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = j.getDouble(i);
        }
        return array;
    }

    default boolean[] toBooleanArray(JSONArray j) {
        boolean[] array = new boolean[j.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = j.getBoolean(i);
        }
        return array;
    }

    default char[] toCharArray(JSONArray j) {
        char[] array = new char[j.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = (char) j.getInt(i);
        }
        return array;
    }

    default String[] toStringArray(JSONArray j) {
        String[] array = new String[j.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = j.getString(i);
        }
        return array;
    }

    default ArrayList<Integer> toIntArrayList(JSONArray j) {
        ArrayList<Integer> alist = new ArrayList<Integer>(j.size());
        for (int i = 0; i < j.size(); i++) {
            alist.add(j.getInt(i));
        }
        return alist;
    }

    default ArrayList<Float> toFloatArrayList(JSONArray j) {
        ArrayList<Float> alist = new ArrayList<Float>(j.size());
        for (int i = 0; i < j.size(); i++) {
            alist.add(j.getFloat(i));
        }
        return alist;
    }

    default ArrayList<Double> toDoubleArrayList(JSONArray j) {
        ArrayList<Double> alist = new ArrayList<Double>(j.size());
        for (int i = 0; i < j.size(); i++) {
            alist.add(j.getDouble(i));
        }
        return alist;
    }

    default ArrayList<Boolean> toBooleanArrayList(JSONArray j) {
        ArrayList<Boolean> alist = new ArrayList<Boolean>(j.size());
        for (int i = 0; i < j.size(); i++) {
            alist.add(j.getBoolean(i));
        }
        return alist;
    }

    default ArrayList<String> toStringArrayList(JSONArray j) {
        ArrayList<String> alist = new ArrayList<String>(j.size());
        for (int i = 0; i < j.size(); i++) {
            alist.add(j.getString(i));
        }
        return alist;
    }

    default JSONArray jsonify(JSONable[] data) {
        JSONArray j = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            JSONable datum = data[i];
            j.setJSONObject(i, datum.toJSON());
        }
        return j;
    }

    default JSONArray jsonify(byte[] array) {
        JSONArray jarray = new JSONArray();
        for (int i = 0; i < array.length; i++) {
            jarray.setInt(i, array[i]);
        }
        return jarray;
    }

    default JSONArray jsonify(int[] data) {
        JSONArray j = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            j.setInt(i, data[i]);
        }
        return j;
    }

    default JSONArray jsonify(float[] data) {
        JSONArray j = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            j.setFloat(i, data[i]);
        }
        return j;
    }

    default JSONArray jsonify(boolean[] data) {
        JSONArray j = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            j.setBoolean(i, data[i]);
        }
        return j;
    }

    default JSONArray jsonify(double[] data) {
        JSONArray j = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            j.setDouble(i, data[i]);
        }
        return j;
    }

    default JSONArray jsonify(long[] data) {
        JSONArray j = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            j.setLong(i, data[i]);
        }
        return j;
    }

    default JSONArray jsonify(String[] data) {
        JSONArray j = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            j.setString(i, data[i]);
        }
        return j;
    }

    default JSONArray jsonify(List<? extends JSONable> data) {
        JSONArray j = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            JSONable datum = data.get(i);
            j.setJSONObject(i, datum.toJSON());
        }
        return j;
    }

}