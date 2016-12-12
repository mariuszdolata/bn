package crawler.proby;

import java.lang.reflect.Field;

public class MainProba {

	public static void main(String[] args) {
		Proba proba = new Proba();
		try {
			showParam(proba.getClass());
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static <T> void showParam(Class<T> clazz) throws NoSuchFieldException, SecurityException{
		Field[] fields = clazz.getDeclaredFields();
		for(Field field:fields){
			System.out.println(field.getName()+ ", "+field.getType().getName());
		}
	
		Field f = clazz.getDeclaredField("str1");
		try {
			f.set(clazz, "abc");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Koniec przypisywania wartoœci");
		System.out.println("clazz.str1="+clazz.toString());
		
		
	}

}
