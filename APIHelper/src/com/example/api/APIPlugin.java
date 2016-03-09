package com.example.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.apihelper.APIManager;

public class APIPlugin extends JavaPlugin {

	MyAPI apiInstance = new MyAPI();

	@Override
	public void onLoad() {
		APIManager.registerAPI(apiInstance, this);
	}

	@Override
	public void onEnable() {
		APIManager.initAPI(MyAPI.class);
	}

	@Override
	public void onDisable() {
		APIManager.disableAPI(MyAPI.class);
	}
}
