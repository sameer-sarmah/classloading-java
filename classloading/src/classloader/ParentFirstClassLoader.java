package classloader;

public class ParentFirstClassLoader extends ClassLoader {
	 
   public ParentFirstClassLoader(ClassLoader parent) {
       super(parent);
   }


   @Override
   public Class<?> loadClass(String name)
       throws ClassNotFoundException {
       return super.loadClass(name);
   }

}