# JSON-Tools-For-Processing
Utilities to make saving and loading in Processing more convenient.

# Code Reduction
In Processing, to save an array of floating points, xs, to a JSONObject, json, you would typically write something like:

```java
JSONArray js = new JSONArray();
for (int i=0; i<xs.length; i++) {
    js.setFloat(i, xs[i]);
}
json.setJSONArray("xs", js);
```

With this library, you can instead write:

```java
json.setJSONArray("xs", Util.jsonify(js));
```

To load an array of floating points from a JSONObject, you would typically write something like this:

```java
JSONArray js = json.getJSONArray("xs");
float[] xs = new float[js.size()];
for (int i=0; i<js.size(); i++) {
    xs[i] = js.getFloat(i);
}
```

With this library, you can instead write:

```java
float[] xs = Util.toFloatArray(json.getJSONArray("xs"));
```

# JSONer
JSONer, a class in this library, is a more radical experiment in using reflection to automatically save the state of an object. It is a work in progress.
