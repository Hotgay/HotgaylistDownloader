package autoloader;

import java.io.IOException;

import autoloader.conf.Configuration;
import autoloader.view.View;

public class Main {

	public static void main(String[] args) throws IOException {

		View.addMessage("Welcome");

		// Load configuration
		View.addMessage("Load configuration file");
		Configuration conf = Configuration.loadConfiguration("config.yaml", "./videos");

		// Manage reference
		ReferenceDownloader.manageReference(conf);

		// Write configuration
		View.addMessage("Write configuration file");
		Configuration.writeConfiguration(conf);

		View.addMessage("Exit app");
		System.exit(0);
	}

}