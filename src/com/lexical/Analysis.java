package com.lexical;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Analysis {
	/**
	 * 记录当前进行到了第几行
	 */
	public int rowNum = 1;
	/**
	 * 记录当前进行到了第几列
	 */
	public int colNum = 1;
	/**
	 * 规则实体成员
	 */
	public RuleGraph rule;
	/**
	 * 输出文件的路径
	 */
	private String outputPath;
	
	private FileInputStream fileInputStream;
	
	private FileOutputStream fileOutputStream;
	/**
	 * 构造函数
	 * @param fileInput
	 * @param path
	 * @throws IOException
	 */
	public Analysis(FileInputStream fileInput,String path) throws IOException{
		
		this.rule = new RuleGraph();
		
		this.fileInputStream = fileInput;
		
		this.outputPath = path;
		
		File outputFile = new File(this.outputPath);
		
		if(!outputFile.exists()){
			outputFile.createNewFile();
		}
		
		this.fileOutputStream = new FileOutputStream(outputFile);
	}
	
	/**
	 * 词法分析的步骤逻辑
	 * @throws IOException 
	 */
	public void Start() throws IOException{
		System.out.println("正在进行词法分析...");
		
		int intToken = fileInputStream.read();
		
		//System.out.println((char)intToken);
		
		while(intToken != -1){
			
			if(AnalysisStep((char)intToken)){
				intToken = fileInputStream.read();
			}
			
			//System.out.println((char)intToken);
			
		}
		
		System.out.println("词法分析结束.");
		
		this.fileInputStream.close();
		
		System.out.println(rowNum);
	}
	
	/**
	 * 分析token
	 * @param token
	 */
	public boolean AnalysisStep(char token){
		if(rule.isEnter(token)){
			rowNum++;
		}
		if(!rule.isSpaceOrTable(token)){
			colNum++;
		}
		/**
		 * 状态转化返回码
		 */
		int code = rule.processState(rule.getCurrentState(), token);
		
		if(rule.getCurrentState() == 8){
			this.executeCode(code);
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * 根据规则的状态转化返回的结果码
	 * 输出相应内容
	 * @param code
	 */
	public void executeCode(int code){
		if(code == 801){
			System.out.println("第"+ rowNum +"行 " + "identifier: " + rule.getIdentifier());
		}
		if(code == 807){
			System.out.println("第"+ rowNum +"行 " + "decimal: " + rule.getDecimal());
		}
		if(code == 809){
			System.out.println("第"+ rowNum +"行 " + "operater: ->");
		}
		if(code == 800){
			System.out.println("第"+ rowNum +"行 " + "operater: =>");
		}
		if(code == 804){
			System.out.println("第"+ rowNum +"行 " + "operater: +=>");
		}
		if(code == 812){
			System.out.println("第"+ rowNum +"行 " + "operater: {");
		}
		if(code == 813){
			System.out.println("第"+ rowNum +"行 " + "operater: ;");
		}
		if(code == 814){
			System.out.println("第"+ rowNum +"行 " + "operater: }");
		}
		if(code == 815){
			System.out.println("第"+ rowNum +"行 " + "operater: :");
		}
		if(code == 816){
			System.out.println("第"+ rowNum +"行 " + "operater: ::");
		}
		rule.init();
		
	}

}
