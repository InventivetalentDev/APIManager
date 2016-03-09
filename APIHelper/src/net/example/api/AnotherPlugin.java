package net.example.api;

import com.example.api.MyAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.apihelper.APIManager;

public class AnotherPlugin extends JavaPlugin {

	AnotherAPI apiInstance = new AnotherAPI();

	@Override
	public void onLoad() {
		//Don't require here

		//But register our API
		APIManager.registerAPI(apiInstance, this);
	}

	@Override
	public void onEnable() {
		APIManager.initAPI(MyAPI.class);
	}
}
