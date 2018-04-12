package classloader;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SelfFirstClassLoader extends ClassLoader {
	 
   public SelfFirstClassLoader(ClassLoader parent) {
       super(parent);
   }

   private Class<?> getClass(String name)
       throws ClassNotFoundException {
       String file = name.replaceAll("\\.", "/") + ".class";
       byte[] byteOfClass = null;
       try {
    	   byteOfClass = loadClassData(file);
           Class<?> klass = defineClass(name, byteOfClass, 0, byteOfClass.length);
           resolveClass(klass);
           return klass;
       } catch (IOException e) {
           e.printStackTrace();
           return null;
       }
       catch (SecurityException e) {
            return super.loadClass(name);
       }
   }

   @Override
   public Class<?> loadClass(String name)
       throws ClassNotFoundException {
       System.out.println("loading class '" + name + "'");
       return getClass(name);
   
   }

   private byte[] loadClassData(String FQN) throws IOException {
       InputStream stream = getClass().getClassLoader().getResourceAsStream(FQN);
       int size = stream.available();
       byte byteOfClass[] = new byte[size];
       DataInputStream in = new DataInputStream(stream);
       in.readFully(byteOfClass);
       in.close();
       return byteOfClass;
   }
}