import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException, JSONException, ClassNotFoundException {
        System.out.println("Hwasd");

        Integer myInt = new Integer(42);

        String[] food = {"grass", "apples"};
        String[][] na = {{"one", "two"}, {"three", "four"}};
        Animal myAnimal = new Animal(false, food, 10);

        Cat myCat = new Cat(true, food, 16, "PINK");

        String json = new Gson().toJson(myCat);
        System.out.println("LIVIU");
        System.out.println(json);
        System.out.println("LIVIU");

//        String json = new Gson().toJson(animal);
//        Animal animal = new Gson().fromJson(json, Animal.class);

        String myJson = saveObjectFieldsJson(myCat);
        System.out.println(myJson);
        Cat newCat = createObjectFromJson(myJson, Cat.class);

        String json2 = new Gson().toJson(newCat);
        System.out.println("UUUUUUUU\n" + json2);
    }

    public static <T> T createObjectFromJson(String jsonString, Class<T> aClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, JSONException, ClassNotFoundException {
        T theObject = aClass.getConstructor().newInstance();

        JSONObject json = new JSONObject(jsonString);

        Class theClass = theObject.getClass();
        while (theClass != null) {
            Field[] fields = theClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Class fieldType = field.getType();
//                System.out.print(Modifier.toString(field.getModifiers()) + " " +
//                        field.getType().toString() + " " + field.getName() + " = ");
                if (Modifier.toString(field.getModifiers()).contains("final"))
                    continue;

                else if (fieldType.isArray())
                    field.set(theObject, createArrayFromJson(json.getJSONArray(field.getName()), fieldType));//json.get(field.getName()));//result += saveArrayJson(fieldType.getComponentType(), field.get(theObject));
                else if (!isJavaLang(field.get(theObject)))
                    field.set(theObject, createObjectFromJson(json.getString(field.getName()), fieldType));//result += saveObjectFieldsJson(field.get(theObject));
                else if (fieldType.isAssignableFrom(String.class))
                    field.set(theObject, json.getString(field.getName()));
                else
                    field.set(theObject, json.get(field.getName()));
            }
            theClass = theClass.getSuperclass();
        }
        return theObject;
    }

    public static Object[] createArrayFromJson(JSONArray jsonArray, Class arrayType) throws JSONException, ClassNotFoundException {
        Object arrayObject = Array.newInstance(arrayType.getComponentType(), jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            //JSONObject jobj = jsonArray.getJSONObject(i);



            Array.set(arrayObject, i, jsonArray.get(i));
        }

        Object[] o = (Object[])arrayObject;
        return o;
    }

    public static String saveObjectFieldsJson(Object theObject) throws IllegalAccessException {
        if (isJavaLang(theObject)) {
            if (theObject.getClass().equals(String.class))
                return ("\"" + theObject + "\"");
            else
                return (theObject.toString());
        }

        String result = "{";
        Class theClass = theObject.getClass();
        while (theClass != null) {
            Field[] fields = theClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Class fieldType = field.getType();
//                System.out.print(Modifier.toString(field.getModifiers()) + " " +
//                        field.getType().toString() + " " + field.getName() + " = ");
                if (Modifier.toString(field.getModifiers()).contains("final"))
                    continue;

                result += ("\"" + field.getName() + "\":");

                if (field.get(theObject) == null)
                    continue;
                    //System.out.println("null");
                else if (fieldType.isArray())
                    result += saveArrayJson(fieldType.getComponentType(), field.get(theObject));
                else if (!isJavaLang(field.get(theObject)))
                    result += saveObjectFieldsJson(field.get(theObject));
                else if (fieldType.isAssignableFrom(String.class))
                    result += ("\"" + field.get(theObject) + "\"");
                else
                    result += (field.get(theObject));
                result += (",");
            }
            theClass = theClass.getSuperclass();
        }
        result = result.substring(0, result.length() - 1);
        result += ("}");
        return result;
    }

    public static String saveArrayJson(Class arrayType, Object theArray) throws IllegalAccessException {
        String result = "";
        int length = java.lang.reflect.Array.getLength(theArray);

        // is this an array of arrays?
        if (arrayType.isArray()) {
            // array of arrays, make a recursive call
            result += ("[");
            for (int j = 0; j < length; j++) {
                Object arr2 = java.lang.reflect.Array.get(theArray, j);
                result += saveArrayJson(arrayType.getComponentType(), arr2);
                if (j != length - 1)
                    result += (",");
            }
            result += ("]");
        } else {
            // array of data, just display it
            result += ("[");
            for (int j = 0; j < length; j++) {
                //System.out.print(java.lang.reflect.Array.get(theArray, j).toString());
                result += saveObjectFieldsJson(java.lang.reflect.Array.get(theArray, j));
                if (j != length - 1)
                    result += (",");
            }
            result += ("]");
        }
        return result;
    }

    public static boolean isJavaLang(Object check) {
        return check.getClass().getName().startsWith("java");
    }
}