package com.lexical;

public class RuleGraph {
	
	private int currentState;
	
	public RuleGraph(){
		this.currentState = 0;
	}
	
	public RuleGraph(int state){
		this.currentState = state;
	}
	
	/**
	 * 获得当前词法分析的状态位置
	 * @return
	 */
	public int getCurrentState(){
		return currentState;
	}
	
	/**
	 * 修改当前状态位置
	 * @param state
	 */
	public void setState(int state){
		this.currentState = state;
	}
	/**
	 * 根据当前状态和token选择下一个状态的编号
	 * 如果不符合任何条件返回错误码-8888
	 * 部分符合条件返回当前状态编号乘以负一加负一
	 * 当前位终止状态时返回编码8888
	 * @param state
	 * @param token
	 * @return
	 */
	public int processState(int state, char token){
		if(state == 0){
			if(this.isEnter(token)){
				return 0;
			}
			if(this.isSpaceOrTable(token)){
				return 0;
			}
			if(this.isCharacter(token)){
				return 1;
			}
			if(this.isPlus(token)){
				return 4;
			}
			if(this.isMinus(token)){
				return 3;
			}
			if(this.isNumber(token)){
				return 5;
			}
			if(this.isMinus(token)){
				return 3;
			}
			if(this.isPlus(token)){
				return 4;
			}
			if(this.isEqual(token)){
				return 10;
			}
			if(this.isLeftBracket(token)){
				return 12;
			}
			if(this.isSemicolon(token)){
				return 13;
			}
			if(this.isRightBracket(token)){
				return 14;
			}
			if(this.isColon(token)){
				return 15;
			}
			return -1;
		}
		if(state == 1){
			if(this.isCharacter(token)){
				return 1;
			}
			if(this.isNumber(token)){
				return 1;
			}
			if(this.isUnderline(token)){
				return 2;
			}
			if(this.notNumAndCharact(token)){
				return 8;
			}
			return -2;
		}
		if(state == 2){
			if(this.isCharacter(token)){
				return 1;
			}
			if(this.isNumber(token)){
				return 1;
			}
			return -3;
		}
		if(state == 3){
			if(this.isNumber(token)){
				return 5;
			}
			if(this.isGreaterThan(token)){
				return 9;
			}
			return -4;
		}
		if(state == 4){
			if(this.isNumber(token)){
				return 5;
			}
			if(this.isEqual(token)){
				return 10;
			}
			return -5;
		}
		if(state == 5){
			if(this.isNumber(token)){
				return 5;
			}
			if(this.isPoint(token)){
				return 6;
			}
			return -6;
		}
		if(state == 6){
			if(this.isNumber(token)){
				return 7;
			}
			return -7;
		}
		if(state == 7){
			if(this.isNumber(token)){
				return 7;
			}
			if(!this.isNumber(token)){
				return 8;
			}
		}
		if(state == 8){
			return 8888;
		}
		if(state == 9){
			return 8;
		}
		if(state == 10){
			if(this.isGreaterThan(token)){
				return 11;
			}
			return -12;
		}
		if(state == 11){
			return 8;
		}
		if(state == 12){
			return 8;
		}
		if(state == 13){
			return 8;
		}
		if(state == 14){
			return 8;
		}
		if(state == 15){
			if(this.isColon(token)){
				return 16;
			}
			if(!this.isColon(token)){
				return 8;
			}
		}
		if(state == 16){
			return 8;
		}
		return -8888;
	}
	
	/**
	 * 判断是否为数字0|..|9
	 * @param token
	 * @return
	 */
	public boolean isNumber(char token){
		
		if(token <= '9' && token >= '0'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为空格、换行、制表符
	 * @param token
	 * @return
	 */
	public boolean isSpaceOrTable(char token){
		
		if(token == ' ' || token == '\t'){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 判断是否为换行
	 * @param token
	 * @return
	 */	
	public boolean isEnter(char token){
		if(token == '\n'){
			return true;
		} else {
			return false;
		}		
	}
	
	/**
	 * 判断是否为大小写字母a|..|zA|..|Z
	 * @param token
	 * @return
	 */
	public boolean isCharacter(char token){
		
		if((token <= 'Z' && token >= 'A') || (token <= 'z' && token >= 'a')){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是是否为关键字
	 * @param token
	 * @return
	 */
	public boolean isKeyWord(String token){
		if(token.equals("thread")){
			return true;
		}
		if(token.equals("features")){
			return true;
		}
		if(token.equals("flows")){
			return true;
		}
		if(token.equals("properties")){
			return true;
		}
		if(token.equals("end")){
			return true;
		}
		if(token.equals("none")){
			return true;
		}
		if(token.equals("in")){
			return true;
		}
		if(token.equals("out")){
			return true;
		}
		if(token.equals("data")){
			return true;
		}
		if(token.equals("port")){
			return true;
		}
		if(token.equals("event")){
			return true;
		}
		if(token.equals("parameter")){
			return true;
		}
		if(token.equals("flow")){
			return true;
		}
		if(token.equals("source")){
			return true;
		}
		if(token.equals("sink")){
			return true;
		}
		if(token.equals("path")){
			return true;
		}
		if(token.equals("constant")){
			return true;
		}
		if(token.equals("access")){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为下划线 _
	 * @param token
	 * @return
	 */
	public boolean isUnderline(char token){
		if(token == '_'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 不是字母也不是数字
	 * @param token
	 * @return
	 */
	public boolean notNumAndCharact(char token){
		if(!isNumber(token) && !isCharacter(token)){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为加号
	 * @param token
	 * @return
	 */
	public boolean isPlus(char token){
		if(token == '+'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为等于号
	 * @param token
	 * @return
	 */
	public boolean isEqual(char token){
		if(token == '='){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为大于号
	 * @param token
	 * @return
	 */
	public boolean isGreaterThan(char token){
		if(token == '>'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为小于号
	 * @param token
	 * @return
	 */
	public boolean isLessThan(char token){
		if(token == '<'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为左大括号
	 * @param token
	 * @return
	 */
	public boolean isLeftBracket(char token){
		if(token == '{'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为右大括号
	 * @param token
	 * @return
	 */
	public boolean isRightBracket(char token){
		if(token == '}'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为分号
	 * @param token
	 * @return
	 */
	public boolean isSemicolon(char token){
		if(token == ';'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是会为冒号
	 * @param token
	 * @return
	 */
	public boolean isColon(char token){
		if(token == ':'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为减号
	 * @param token
	 * @return
	 */
	public boolean isMinus(char token){
		if(token == '-'){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为点
	 * @param token
	 * @return
	 */
	public boolean isPoint(char token){
		if(token == '.'){
			return true;
		} else {
			return false;
		}
	}

}
