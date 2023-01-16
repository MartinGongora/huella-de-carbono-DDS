package Server;

import spark.Spark;
import spark.debug.DebugScreen;

import java.io.IOException;

public class Server{

	public static void main(String[] args) throws IOException {
		new Server().run();
	}

	public void run() throws IOException {
		//staticFiles.externalLocation("upload");
		Spark.port(getHerokuAssignedPort());
		Router.init();
		DebugScreen.enableDebugScreen();
	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 9000; //return default port if heroku-port isn't set (i.e. on localhost)
	}
}
