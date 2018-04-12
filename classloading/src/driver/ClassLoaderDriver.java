package driver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import classloader.BinaryClassLoader;
import classloader.ParentFirstClassLoader;
import classloader.SelfFirstClassLoader;
import models.Address;

public class ClassLoaderDriver {
	public static void main(String[] args) throws Exception {
		 Optional<Object> option=untypedOrderFromParentFirstCL(); 
		 Object order=option.get();
		
		 Optional<Object> option2=untypedOrderFromSelfFirstCL(); 
		 Object order2=option.get();


		String dir="./src";
		String className="Address";
		String pkgName="models";
		String FQN=pkgName+"."+className;
		BinaryClassLoader binaryClassLoader=new BinaryClassLoader(dir);
	    Class<?> addressClass = null;
        try {
        	addressClass = binaryClassLoader.loadClass(FQN);
        } catch (ClassNotFoundException e) {
            System.out.println("MyClassLoader cannot load as ClassA is not yet defined");
        }
        binaryClassLoader.defineClass(pkgName,className);
        addressClass = binaryClassLoader.loadClass(FQN);
        Constructor constructor=addressClass.getConstructor(new Class[] { String.class, String.class, String.class,String.class, String.class });
        Object addressInstance = constructor.newInstance(new Object[] { "firstName", "lastName", "city" ,"street","PIN"});
        System.out.println(addressClass.getName());
        //Address add=(Address)addressInstance; will give ClassCastException as the class loader are different for both 
		
	}
//Order and PlaceOrder are loaded by SelfFirstClassLoader other internal classes by system class loaded
	public static Optional<Object> untypedOrderFromSelfFirstCL() {
		SelfFirstClassLoader loader = new SelfFirstClassLoader(Address.class.getClassLoader());
		Object order = null;
		Optional<Object> option = Optional.empty();
		try {
			Class<?> OrderClassLoadedViaSelfFirstCL = loader.loadClass("models.Order");
			Class<?> PlaceOrderClassLoadedViaSelfFirstCL = loader.loadClass("models.PlaceOrder");
			Object placeOrderInstance2 = PlaceOrderClassLoadedViaSelfFirstCL.newInstance();
		    order = PlaceOrderClassLoadedViaSelfFirstCL.getMethod("generateOrder").invoke(placeOrderInstance2);
			option=Optional.of(order);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return option;
	}

	public static Optional<Object> untypedOrderFromParentFirstCL() {
		ParentFirstClassLoader loader = new ParentFirstClassLoader(Address.class.getClassLoader());
		Optional<Object> option = Optional.empty();
		try {
			Class<?> OrderClassLoadedViaParentFirstCL = loader.loadClass("models.Order");
			Class<?> PlaceOrderClassLoadedViaParentFirstCL = loader.loadClass("models.PlaceOrder");
			Object placeOrderInstance = PlaceOrderClassLoadedViaParentFirstCL.newInstance();
			Object order = PlaceOrderClassLoadedViaParentFirstCL.getMethod("generateOrder").invoke(placeOrderInstance);
			option=Optional.of(order);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return option;
	}
	

}
