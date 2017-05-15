package com.lexical;

public class RuleGraph {
	
	private StringBuilder identifier;
	
	private StringBuilder decimal;
	
	public StringBuilder operator;
	
	private int currentState;
	
	public RuleGraph(){
		this.currentState = 0;
		this.identifier = new StringBuilder();
		this.operator = new StringBuilder();
		this.decimal = new StringBuilder();
	}
	
	public RuleGraph(int state){
		this.currentState = state;
		this.identifier = new StringBuilder();
		this.operator = new StringBuilder();
		this.decimal = new StringBuilder();
	}
	
	/**
	 * 将记录值归零
	 */
	public void init(){
		this.identifier = new StringBuilder();
		this.operator = new StringBuilder();
		this.decimal = new StringBuilder();
		this.currentState = 0;
	}
	
	/**
	 * 获得数字
	 * @return
	 */
	public String getDecimal(){
		return this.decimal.toString();
	}
	
	/**
	 * 获得identifier
	 * @return
	 */
	public String getIdentifier(){
		return this.identifier.toString();
	}
	
	/**
	 * 储存当前token
	 * @param token
	 */
	public void appendIdentifier(char token){
		
		this.identifier.append(token);
	}
	/**
	 * 储存当前token
	 * @param token
	 */
	public void appendDecimal(char token){
		
		this.decimal.append(token);
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
	 * 如果不符合任何条件返回错误码-8
	 * 当前位终止状态时返回编码888
	 * 获得identifier时返回801
	 * 获得decimal时返回807
	 * 获得=>,800
	 * 获得+=>,804
	 * 获得->,809
	 * 获得{,812
	 * 获得;,813
	 * 获得},814
	 * 获得:,815
	 * 获得::,816
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
				this.appendIdentifier(token);
				this.setState(1);
				return 1;
			}
			if(this.isPlus(token)){
				this.operator.append(token);
				this.appendDecimal(token);
				this.setState(4);
				return 4;
			}
			if(this.isMinus(token)){
				this.appendDecimal(token);
				this.setState(3);
				return 3;
			}
			if(this.isNumber(token)){
				this.appendDecimal(token);
				this.setState(5);
				return 5;
			}
			if(this.isMinus(token)){
				this.setState(3);
				return 3;
			}
			if(this.isPlus(token)){
				this.setState(4);
				return 4;
			}
			if(this.isEqual(token)){
				this.operator.append(token);
				this.setState(10);
				return 10;
			}
			if(this.isLeftBracket(token)){
				this.setState(12);
				return 12;
			}
			if(this.isSemicolon(token)){
				this.setState(13);
				return 13;
			}
			if(this.isRightBracket(token)){
				this.setState(14);
				return 14;
			}
			if(this.isColon(token)){
				this.setState(15);
				return 15;
			}
			/**
			 * 从开始状态产生的错误码
			 * 一般因为遇到了非法token
			 */
			this.setState(8);
			return -1;
		}
		if(state == 1){
			if(this.isCharacter(token)){
				this.appendIdentifier(token);
				return 1;
			}
			if(this.isNumber(token)){
				this.appendIdentifier(token);
				return 1;
			}
			if(this.isUnderline(token)){
				this.appendIdentifier(token);
				this.setState(2);
				return 2;
			}
			/**
			 * 获得标识符identifier
			 */
			if(this.notNumAndCharact(token)){
				this.setState(8);
				return 801;
			}
//			if(token == ' ' || token == '\r' || token == '\n' 
//					|| token == ';' || token == '\t' || token == ':'
//					|| token == '-'|| token == '='|| token == '+'){
//				this.setState(8);
//				return 801;
//			}
			/**
			 * 非法identifier
			 */
			this.setState(8);
			return -2;
		}
		if(state == 2){
			if(this.isCharacter(token)){
				this.appendIdentifier(token);
				this.setState(1);
				return 1;
			}
			if(this.isNumber(token)){
				this.appendIdentifier(token);
				this.setState(1);
				return 1;
			}
			/**
			 * 非法identifier
			 */
			this.setState(8);
			return -2;
		}
		if(state == 3){
			if(this.isNumber(token)){
				this.appendDecimal(token);
				this.setState(5);
				return 5;
			}
			if(this.isGreaterThan(token)){
				this.setState(9);
				return 9;
			}
			/**
			 * “-”后存在非法字符
			 */
			this.setState(8);
			return -3;
		}
		if(state == 4){
			if(this.isNumber(token)){
				this.appendDecimal(token);
				this.setState(5);
				return 5;
			}
			if(this.isEqual(token)){
				this.operator.append(token);
				this.setState(10);
				return 10;
			}
			/**
			 * “+”后存在非法字符
			 */
			this.setState(8);
			return -4;
		}
		if(state == 5){
			if(this.isNumber(token)){
				this.appendDecimal(token);
				return 5;
			}
			if(this.isPoint(token)){
				this.appendDecimal(token);
				this.setState(6);
				return 6;
			}
			/**
			 * 非法decimal
			 */
			this.setState(8);
			return -5;
		}
		if(state == 6){
			if(this.isNumber(token)){
				this.appendDecimal(token);
				this.setState(7);
				return 7;
			}
			/**
			 * 非法decimal
			 */
			this.setState(8);
			return -5;
		}
		if(state == 7){
			if(this.isNumber(token)){
				this.appendDecimal(token);
				return 7;
			}
			/**
			 * 获得标识符decimal
			 */
			if(!this.isNumber(token)){
				this.setState(8);
				return 807;
			}
			/**
			 * 非法decimal
			 */
			this.setState(8);
			return -5;
		}
		if(state == 8){
			return 888;
		}
		/**
		 * 获得->
		 */
		if(state == 9){
			this.setState(8);
			return 809;
		}
		if(state == 10){
			if(this.isGreaterThan(token)){
				this.operator.append(token);
				this.setState(11);
				return 11;
			}
			/**
			 * 非法operator
			 */
			this.setState(8);
			return -10;
		}
		/**
		 * 获得+=>或者=>
		 */
		if(state == 11){
			if(this.operator.toString().equals("+=>")){
				this.setState(8);
				return 804;
			}
			if(this.operator.toString().equals("=>")){
				this.setState(8);
				return 800;
			}
			/**
			 * 非法operator
			 */
			this.setState(8);
			return -10;
		}
		/**
		 * 获得{
		 */
		if(state == 12){
			this.setState(8);
			return 812;
		}
		/**
		 * 获得;
		 */
		if(state == 13){
			this.setState(8);
			return 813;
		}
		/**
		 * 获得}
		 */
		if(state == 14){
			this.setState(8);
			return 814;
		}
		if(state == 15){
			if(this.isColon(token)){
				this.setState(16);
				return 16;
			}
			/**
			 * 获得:
			 */
			if(!this.isColon(token)){
				this.setState(8);
				return 815;
			}
		}
		/**
		 * 获得：：
		 */
		if(state == 16){
			this.setState(8);
			return 816;
		}
		this.setState(8);
		return -8;
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
		if(token == '\n' || token == '\r'){
			return true;
		} else {
			return false;
		}		
	}
	
	/**
	 * 判断是否为大小写字母a|..|z|A|..|Z
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
