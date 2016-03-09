package org.example.plugin;

import com.example.api.MyAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.apihelper.APIManager;

public class MyAwesomePlugin extends JavaPlugin {

	@Override
	public void onLoad() {
		APIManager.require(MyAPI.class, this);
	}

	@Override
	public void onEnable() {
		APIManager.initAPI(MyAPI.class);
	}

}

