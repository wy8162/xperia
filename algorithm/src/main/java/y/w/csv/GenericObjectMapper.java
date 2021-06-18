package y.w.csv;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenericObjectMapper {
    interface Converter {
        Object convert(String s);
    }
    private static Map<Class,Converter> converterForClass = new HashMap<>();
    static {
        converterForClass.put(Integer.class, s -> Integer.parseInt(s));
        converterForClass.put(Double.class, s -> Double.parseDouble(s));
        converterForClass.put(String.class, s -> s);
        converterForClass.put(Long.class, s -> Long.parseLong(s));
        converterForClass.put(Float.class, s -> Float.parseFloat(s));
        converterForClass.put(Date.class, s -> {
            // SimpleDateFormat is not thread safe
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm/DD/yyyy");
            try {
                if (s == null || s.isEmpty())
                    return new Date();
                return dateFormat.parse(s);
            } catch (ParseException e) {
                return new Date();
            }
        });
    };

    /**
     @Parameter clazz - the class of the object to be created
     @Parameter fields - the names of the fields to be mapped
     @Parameter values - values associated with each of the fields
     @Return    an object instance of clazz
     */
    public final static <T> T getInstance(Class<T> clazz, String[] fields, String[] values) {
        try {
            T o = clazz.newInstance();

            int i = 0;
            for (String field : fields) {
                String value = i < values.length ? values[i] : "";
                Field f = clazz.getDeclaredField(field);
                boolean accessible = f.isAccessible();
                f.setAccessible(true);
                Object v = converterForClass.get(f.getType()).convert(value);
                f.set(o, v);
                f.setAccessible(accessible);

                i++;
            }

            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     @Parameter clazz - the class of the object to be created
     @Parameter fieldValue - field/value pairs with keys as names of the fields
     @Return    an object instance of clazz
     */
    public final static <T> T getInstance(Class<T> clazz, Map<String, String> fieldValues) {
        try {
            T o = clazz.newInstance();

            for (String field : fieldValues.keySet()) {
                Field f = clazz.getDeclaredField(field);
                boolean accessible = f.isAccessible();
                f.setAccessible(true);
                Object v = converterForClass.get(f.getType()).convert(fieldValues.get(field));
                f.set(o, v);
                f.setAccessible(accessible);
            }

            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
