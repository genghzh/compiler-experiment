package com.lexical;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @version 1.0.0
 * @author zk
 * 2017年5月14日 13:50
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String filepath = "C:\\Users\\zk\\workspace\\test.txt";
		
		String outputFileName = "lexical_result.txt";
		
		try {
			Analyser analyser = new Analyser(filepath,outputFileName);
			analyser.Start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
