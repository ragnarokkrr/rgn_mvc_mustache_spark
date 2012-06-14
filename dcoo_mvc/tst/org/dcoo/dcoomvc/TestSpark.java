package org.dcoo.dcoomvc;


import static spark.Spark.*;
import spark.*;

public class TestSpark {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		get(new Route("Hello"){

			@Override
			public Object handle(Request arg0, Response arg1) {
				return "Hello World!";
			}
		});
		
	}

}
