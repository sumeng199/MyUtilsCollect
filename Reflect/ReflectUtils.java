//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.lasque.tusdk.core.utils;

import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.lasque.tusdk.core.type.ResourceType;
import org.lasque.tusdk.core.utils.TLog;

public class ReflectUtils {
    public ReflectUtils() {
    }

    public static int getResource(Class<?> var0, ResourceType var1, String var2) {
        return var0 != null && var1 != null && var2 != null?((var0 = subClass(var0, var1.getKey())) == null?0:getResourceFieldValue(var0, var2)):0;
    }

    public static int getResourceFieldValue(Class<?> var0, String var1) {
        Field var2;
        if((var2 = getField(var0, var1)) == null) {
            return 0;
        } else {
            try {
                return ((Integer)var2.get(var0)).intValue();
            } catch (Exception var3) {
                TLog.e(var3, "getResourceFieldValue: %s | %s", new Object[]{var0, var1});
                return 0;
            }
        }
    }

    public static Field getField(Class<?> var0, String var1) {
        if(var0 != null && var1 != null) {
            Field[] var5;
            if((var5 = var0.getFields()) == null) {
                return null;
            } else {
                Field[] var4 = var5;
                int var3 = var5.length;

                for(int var2 = 0; var2 < var3; ++var2) {
                    Field var6;
                    if((var6 = var4[var2]).getName().equalsIgnoreCase(var1)) {
                        return var6;
                    }
                }

                return null;
            }
        } else {
            return null;
        }
    }

    public static Class<?> subClass(Class<?> var0, String var1) {
        Class[] var5 = var0.getDeclaredClasses();
        if(var1 != null && var5 != null && var5.length != 0) {
            Class[] var4 = var5;
            int var3 = var5.length;

            for(int var2 = 0; var2 < var3; ++var2) {
                if((var0 = var4[var2]).getSimpleName().equalsIgnoreCase(var1)) {
                    return var0;
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static Class<?> reflectClass(String var0) {
        if(var0 == null) {
            return null;
        } else {
            try {
                return Class.forName(var0);
            } catch (ClassNotFoundException var1) {
                TLog.e(var1);
                return null;
            }
        }
    }

    public static <T> T classInstance(String var0) {
        return classInstance(reflectClass(var0));
    }

    public static <T> T classInstance(Class<?> var0) {
        if(var0 == null) {
            return null;
        } else {
            try {
                return var0.newInstance();
            } catch (InstantiationException var1) {
                TLog.e(var1, "classInstance", new Object[0]);
            } catch (IllegalAccessException var2) {
                TLog.e(var2, "classInstance", new Object[0]);
            }

            return null;
        }
    }

    public static StringBuilder trace(Object var0) {
        if(var0 == null) {
            return null;
        } else {
            StringBuilder var1 = (new StringBuilder(var0.getClass().getName())).append(":{\n");
            Field[] var5;
            int var4 = (var5 = var0.getClass().getFields()).length;

            for(int var3 = 0; var3 < var4; ++var3) {
                Field var2;
                if(!Modifier.isStatic((var2 = var5[var3]).getModifiers())) {
                    var1.append(var2.getName()).append(" : ");
                    Object var6;
                    if((var6 = getFieldValue(var2, var0)) == null) {
                        var1.append("null");
                    } else {
                        var1.append(var6.toString());
                    }

                    var1.append(", \n");
                }
            }

            var1.append("};");
            return var1;
        }
    }

    public static Object getFieldValue(Field var0, Object var1) {
        Object var2 = null;

        try {
            var2 = var0.get(var1);
        } catch (IllegalArgumentException var3) {
            TLog.e(var3);
        } catch (IllegalAccessException var4) {
            TLog.e(var4);
        }

        return var2;
    }

    public static Field reflectField(Class<?> var0, String var1) {
        if(var0 != null && var1 != null) {
            Field var2 = null;

            try {
                var2 = var0.getField(var1);
            } catch (NoSuchFieldException var3) {
                TLog.e(var3);
            }

            return var2;
        } else {
            return null;
        }
    }

    public static void setFieldValue(Field var0, Object var1, Object var2) {
        if(var0 != null && var1 != null && var2 != null) {
            try {
                var0.set(var1, var2);
            } catch (IllegalArgumentException var3) {
                TLog.e(var3);
            } catch (IllegalAccessException var4) {
                TLog.e(var4);
            }
        }
    }

    public static Method getMethod(Class<?> var0, String var1, Class... var2) {
        if(var0 != null && var1 != null) {
            Method var3 = null;

            try {
                var3 = var0.getMethod(var1, var2);
            } catch (NoSuchMethodException var4) {
                TLog.e(var4);
            }

            return var3;
        } else {
            return null;
        }
    }

    public static Object reflectMethod(Method var0, Object var1, Object... var2) {
        if(var0 != null && var1 != null) {
            try {
                return var0.invoke(var1, var2);
            } catch (IllegalArgumentException var3) {
                TLog.e(var3);
            } catch (IllegalAccessException var4) {
                TLog.e(var4);
            } catch (InvocationTargetException var5) {
                TLog.e(var5);
            }

            return null;
        } else {
            return null;
        }
    }

    public static Class<?> genericCollectionType(Type var0) {
        List var1;
        return (var1 = genericCollectionTypes(var0)) != null && !var1.isEmpty()?(Class)var1.get(0):null;
    }

    public static List<Class<?>> genericCollectionTypes(Type var0) {
        if(var0 != null && var0 instanceof ParameterizedType) {
            Type[] var5;
            if((var5 = ((ParameterizedType)var0).getActualTypeArguments()) != null && var5.length > 0) {
                ArrayList var1 = new ArrayList(var5.length);
                Type[] var4 = var5;
                int var3 = var5.length;

                for(int var2 = 0; var2 < var3; ++var2) {
                    var0 = var4[var2];
                    var1.add((Class)var0);
                }

                return var1;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String serialize(Object var0) {
        if(var0 == null) {
            return null;
        } else {
            String var1 = null;
            ByteArrayOutputStream var2 = new ByteArrayOutputStream();

            try {
                (new ObjectOutputStream(var2)).writeObject(var0);
                var1 = Base64.encodeToString(var2.toByteArray(), 0);
            } catch (IOException var4) {
                TLog.e(var4, "serialize: %s", new Object[]{var0});
            }

            return var1;
        }
    }

    public static <T> T deserialize(String var0) {
        if(var0 == null) {
            return null;
        } else {
            byte[] var1 = Base64.decode(var0, 0);
            ByteArrayInputStream var6 = new ByteArrayInputStream(var1);
            Object var2 = null;

            try {
                var2 = (new ObjectInputStream(var6)).readObject();
            } catch (StreamCorruptedException var3) {
                TLog.e(var3, "deserialize: %s", new Object[]{var0});
            } catch (IOException var4) {
                TLog.e(var4, "deserialize: %s", new Object[]{var0});
            } catch (ClassNotFoundException var5) {
                TLog.e(var5, "deserialize: %s", new Object[]{var0});
            }

            return var2;
        }
    }

    public static void asserts(boolean var0, String var1) {
        if(!var0) {
            throw new AssertionError(var1);
        }
    }

    public static <T> T notNull(T var0, String var1) {
        if(var0 == null) {
            throw new IllegalArgumentException(var1 + " should not be null!");
        } else {
            return var0;
        }
    }
}
