# JSON-Tools-For-Processing
Utilities to make saving and loading in Processing more convenient.

# Code Reduction
To save an array of floating points, xs, in Processing to a JSONObject, json, you would write something like:

```java
JSONArray js = new JSONArray();
for (int i=0; i<xs.length; i++) {
    js.setFloat(i, xs[i]);
}
json.setJSONArray("xs", js);
```

With this library, you can write:

```java
json.setJSONArray("xs", Util.toFloatArray(js));
```

# JSONer
JSONer, a class in this library, is a more radical experiment in using reflection to automatically save the state of an object. It is a work in progress.
