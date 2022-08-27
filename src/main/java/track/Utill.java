package track;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class Utill {
    /**
     * Check if the input string is an integer value
     *
     * @param input
     * @return boolean
     */
    public static boolean checkIntegerVal(String input) {
        boolean isDigit = true;
        for (int i = 0; i < input.length(); i++) {
            isDigit = Character.isDigit(input.charAt(i));
            if (!isDigit) {
                break;
            }
        }
        return isDigit;
    }

    /**
     * Converting an API response to a JSON map
     */
    public static Map<String, String> toJsonMapFromResponse(String response) {
        Map<String, String> jsonMap = new HashMap<>();
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        try {
            // JavaScriptの実行
            Object obj = engine.eval(String.format("(%s)", response));
            // fetch the ScriptObjectMirror class using reflection
            Class scriptClass = Class.forName("jdk.nashorn.api.scripting.ScriptObjectMirror");
            // Get keyset in reflection
            Object[] keys = ((java.util.Set) obj.getClass().getMethod("keySet").invoke(obj)).toArray();
            // get method in reflection
            Method method_get = obj.getClass().getMethod("get", Class.forName("java.lang.Object"));

            for (Object key : keys) {
                Object val = method_get.invoke(obj, key);
                jsonMap.put(key.toString(), val.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonMap;
    }
}