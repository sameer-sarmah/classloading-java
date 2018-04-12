package classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class BinaryClassLoader extends ClassLoader {
    private final String externalDir;
    public BinaryClassLoader(final String externalDir) {
        this.externalDir =  System.getProperty("user.dir") + "/" + externalDir;
    }
 
    public void defineClass(final String pkgName,final String className)  {
    	String pkgPath=pkgName.replaceAll("\\.", "/");
		File sourceFile=new File(externalDir+File.separator+pkgPath+File.separator+className+".java");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	    compiler.run(null, null, null, sourceFile.getPath());
	    File classFile=new File(externalDir+File.separator+pkgPath+File.separator+className+".class");
	    byte[] classFileBinary;
		try {
			classFileBinary = Files.readAllBytes(classFile.toPath());
			defineClass(pkgName+"."+className, classFileBinary, 0, classFileBinary.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
    }
     

}