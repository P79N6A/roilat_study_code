package cn.roilat.study.java.basic.testtransient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @description 使用transient关键字不序列化某个变量
 *        注意读取的时候，读取数据的顺序一定要和存放数据的顺序保持一致
 *        
 *        一个静态变量不管是否被transient修饰，均不能被序列化。
 * @author roilat-J
 *
 */
public class TransientTest2 {
	public static void main(String[] args) {

		User2 user = new User2();
		user.setUsername("Alexia");
		user.setPasswd("123456");

		System.out.println("read before Serializable: ");
		System.out.println("username: " + user.getUsername());
		System.err.println("password: " + user.getPasswd());

		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("src/main/java/cn/roilat/study/java/basic/testtransient/user.txt"));
			os.writeObject(user); // 将User对象写进文件
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			ObjectInputStream is = new ObjectInputStream(new FileInputStream("src/main/java/cn/roilat/study/java/basic/testtransient/user.txt"));
			user = (User2) is.readObject(); // 从流中读取User的数据
			is.close();

			System.out.println("\nread after Serializable(not initial the username): ");
			System.out.println("username: " + user.getUsername());
			System.err.println("password: " + user.getPasswd());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
		    // 在反序列化之前改变username的值
		    User2.username = "jmwang";
		    
		    ObjectInputStream is = new ObjectInputStream(new FileInputStream("src/main/java/cn/roilat/study/java/basic/testtransient/user.txt"));
		    user = (User2) is.readObject(); // 从流中读取User的数据
		    is.close();
		    
		    System.out.println("\nread after Serializable: ");
		    System.out.println("username: " + user.getUsername());
		    System.err.println("password: " + user.getPasswd());
		    
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		}
	}
}

class User2 implements Serializable {
	private static final long serialVersionUID = 8294180014912103005L;

	public static String username;
	private transient String passwd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
	    User2.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}