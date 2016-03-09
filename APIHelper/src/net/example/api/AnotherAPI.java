package net.example.api;

import com.example.api.MyAPI;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.inventivetalent.apihelper.API;
import org.inventivetalent.apihelper.APIManager;

public class AnotherAPI implements API, Listener {

	@Override
	public void load() {
		//Require MyAPI here instead of the plugin class
		APIManager.require(MyAPI.class, null);
	}

	@Override
	public void init(Plugin plugin) {
		APIManager.registerEvents(this, this);
	}

	@Override
	public void disable(Plugin plugin) {
	}
}
