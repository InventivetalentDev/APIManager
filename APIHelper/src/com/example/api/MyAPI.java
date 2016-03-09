package com.example.api;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.inventivetalent.apihelper.API;
import org.inventivetalent.apihelper.APIManager;

public class MyAPI implements API, Listener {
	@Override
	public void load() {
	}

	@Override
	public void init(Plugin plugin) {
		APIManager.registerEvents(this, this);
	}

	@Override
	public void disable(Plugin plugin) {
	}
}
