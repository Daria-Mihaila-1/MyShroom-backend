package com.example.MyShroom_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;

@SpringBootApplication
public class MyShroomBackendApplication {

	public static void main(String[] args) throws NoSuchFieldException {
//		String name = System.mapLibraryName("pytorch_jni.dll"); // extends name with .dll, .so or .dylib
//		InputStream inputStream = null;
//		OutputStream outputStream = null;
//		try {
//			inputStream = MyShroomBackendApplication.class.getResourceAsStream("/" +  name);
//			File fileOut = new File("your lib path");
//			outputStream = new FileOutputStream(fileOut);
//			IOUtils.copy(inputStream, outputStream);
//
//			System.load(fileOut.toString());//loading goes here
//		} catch (Exception e) {
//			//handle
//		} finally {
//			if (inputStream != null) {
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					//log
//				}
//			}
//			if (outputStream != null) {
//				try {
//					outputStream.close();
//				} catch (IOException e) {
//					//log
//				}
//			}
		String libraryPath = System.getProperty("java.library.path");

		String[] paths = libraryPath.split(System.getProperty("path.separator"));
		System.out.println(paths[0]);
		for (String path : paths) {
			File folder = new File(path);
			if (folder.isDirectory()) {
				System.out.println("Contents of " + folder.getAbsolutePath() + ":");
				File[] files = folder.listFiles();
				for (File file : files) {
					if (file.getAbsolutePath().contains("pytorch_jni"))
						System.out.println("\t" + file.getAbsolutePath());
				}
			}
		}
		System.out.println(System.getProperty("sun.arch.data.model"));
		System.out.println(SpringVersion.getVersion());
		SpringApplication.run(MyShroomBackendApplication.class, args);
	}

}
