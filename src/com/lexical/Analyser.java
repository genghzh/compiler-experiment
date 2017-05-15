package com.lexical;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Analyser {
	
	/**
	 * 文件的路径
	 */
	private String filepath;
	/**
	 * 
	 */
	private String outputPath;
	/**
	 * 
	 */
	private String outputFileName ;
	/**
	 * 
	 */
	private File file = null;
	/**
	 * 文件输入流
	 */
	private FileInputStream fileInputStream;
	
	/**
	 * 初始化文件
	 * 打开文件输入流
	 * @param path
	 * @throws FileNotFoundException
	 */
	public Analyser(String path,String filename) throws FileNotFoundException{
		this.filepath = path;
		this.file = new File(filepath);
		if(fileExist(this.file)){
			this.fileInputStream = new FileInputStream(file);
		} else {
			System.err.println(filepath + "\nFile doesn't exist!");
		}
		
		this.outputFileName = filename;
		
		this.outputPath = System.getProperty("user.dir") + "\\" + this.outputFileName;
	}
	
	/**
	 * 判断指定文件是否存在
	 * @param file
	 * @return
	 */
	public boolean fileExist(File file){
		if(file == null){
			return false;
		} else {
			return true;
		}
	}
	
	public void Start() throws IOException{
		
		Analysis analysis = new Analysis(this.fileInputStream,this.outputPath);
		
		analysis.Start();
		
		fileInputStream.close();
	}

}
