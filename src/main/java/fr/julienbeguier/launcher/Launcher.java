package fr.julienbeguier.launcher;

import java.util.Map;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fr.julienbeguier.configuration.Configuration;
import fr.julienbeguier.network.RssClient;

public class Launcher {

	public Launcher() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		Configuration configuration = Configuration.getInstance();
		Map<String, Object> settings = configuration.getSettings();
		
		RssClient rc = new RssClient((String) settings.get(configuration.getKeyServerIp())/*, (Integer) settings.get(configuration.getKeyServerPort())*/);
		rc.login((String) settings.get(configuration.getKeyAuthenticationLogin()), (String) settings.get(configuration.getKeyAuthenticationPassword()));
	}

	public static void main(String[] args) {
		new Launcher();
	}
}
