package com.lexical;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Analysis {
	
	public StringBuilder illegalString;
	
	int intToken;
	/**
	 * 记录当前进行到了第几行
	 */
	public int rowNum = 1;
	/**
	 * 记录当前进行到了第几列
	 */
	public int colNum = -1;
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
		
		this.illegalString = new StringBuilder();
		
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
		
		intToken = fileInputStream.read();
		
		while(intToken != -1){
			
			if(AnalysisStep((char)intToken)){
				intToken = fileInputStream.read();
			}
			
		}
		
		System.out.println("词法分析结束.");
		
		this.fileInputStream.close();
		this.fileOutputStream.close();
	}
	
	/**
	 * 分析token
	 * @param token
	 * @throws IOException 
	 */
	public boolean AnalysisStep(char token) throws IOException{
		if(token == '\n'){
			rowNum++;
			colNum = -1;
		}
		if(!rule.isSpaceOrTable(token) && token != '\r' && token != '\n'){
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
	 * @throws IOException 
	 */
	public void executeCode(int code) throws IOException{
		byte[] content;
		StringBuilder strC = new StringBuilder();boolean flagC = false;
		StringBuilder strW = new StringBuilder();boolean flagW= false;
		if(code == 801){
			flagC = true;
			if(rule.isKeyWord(rule.getIdentifier())){
				strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "key word: " + rule.getIdentifier());
			} else {
				strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "identifier: " + rule.getIdentifier());
			}
		}
		if(code == 807){
			flagC = true;
			strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "decimal: " + rule.getDecimal());
		}
		if(code == 809){
			flagC = true;
			strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "operator: ->");
		}
		if(code == 800){
			flagC = true;
			strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "operator: =>");
		}
		if(code == 804){
			flagC = true;
			strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "operator: +=>");
		}
		if(code == 812){
			flagC = true;
			strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "operator: {");
		}
		if(code == 813){
			flagC = true;
			strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "operator: ;");
		}
		if(code == 814){
			flagC = true;
			strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "operator: }");
		}
		if(code == 815){
			flagC = true;
			strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "operator: :");
		}
		if(code == 816){
			flagC = true;
			strC.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "operator: ::");
		}
		if(flagC){
			System.out.print(strC.append("\r\n").toString());
			content = strC.toString().getBytes();
			this.fileOutputStream.write(content);
		}
		if(code == -1){
			flagW = true;
			this.skipIllegalToken();
			strW.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "非法字符串：" + illegalString.toString());
		}
		if(code == -2){
			flagW = true;
			this.skipIllegalToken();
			strW.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "非法identifier：" + rule.getIdentifier() + illegalString.toString());
		}
		if(code == -3){
			flagW = true;
			this.skipIllegalToken();
			strW.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "“-”后存在非法字符： " + "-" + illegalString.toString());
		}
		if(code == -4){
			flagW = true;
			this.skipIllegalToken();
			strW.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "“+”后存在非法字符: " + "+" + illegalString.toString());
		}
		if(code == -5){
			flagW = true;
			this.skipIllegalToken();
			strW.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "非法decimal：" + rule.getDecimal() + illegalString.toString());
		}
		if(code == -10){
			flagW = true;
			this.skipIllegalToken();
			strW.append("第"+ rowNum +"行 " +"第"+ colNum +"列 " + "非法operator：" + illegalString.toString());
		}
		if(flagW){
			System.err.print(strW.append("\r\n").toString());
			content = strW.toString().getBytes();
			this.fileOutputStream.write(content);
		}
		if(this.fileOutputStream != null)
			this.fileOutputStream.flush();
		rule.init();
		this.illegalString = new StringBuilder();
	}
	
	/**
	 * 跳过并且记录错误的内容
	 * @throws IOException
	 */
	public void skipIllegalToken() throws IOException{
		while((char)intToken != '\t' && (char)intToken != '\n' && (char)intToken != ' '
				&& (char)intToken != ':' && (char)intToken != ';'
				&& (char)intToken != '+' && (char)intToken != '-'
				&& (char)intToken != '='){
			if((char)intToken != '\r'){
				illegalString.append((char)intToken);
				colNum++;
			}
			intToken = fileInputStream.read();
		}
		if((char)intToken == ' ' || (char)intToken != ':' || (char)intToken != ';'
				|| (char)intToken != '+' || (char)intToken != '-'
				|| (char)intToken != '='){
			colNum++;
		}
	}

}
