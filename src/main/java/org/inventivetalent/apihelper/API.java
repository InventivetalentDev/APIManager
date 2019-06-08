package org.inventivetalent.apihelper;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Classes implementing {@link API} MUST NOT extend {@link org.bukkit.plugin.java.JavaPlugin}!
 * This class may implement {@link Listener}
 */
public interface API {

	/**
	 * First method called, similar to {@link Plugin#onLoad()}
	 * <p>
	 * Use this for {@link APIManager#require(Class, Plugin)} if you are making an API
	 */
	void load();

	/**
	 * Second method called, similar to {@link Plugin#onEnable()}
	 * <p>
	 * Use this for {@link APIManager#registerEvents(API, Listener)} and other things that require a Plugin instance
	 *
	 * @param host {@link Plugin} instance
	 */
	void init(Plugin host);

	/**
	 * Last method called, similar to {@link Plugin#onDisable()}
	 * <p>
	 * This is likely to not be called at all.
	 * Add a note to your documentation to require implementing plugins to call {@link APIManager#disableAPI(Class)} if you need this method
	 *
	 * @param host {@link Plugin} instance
	 */
	void disable(Plugin host);
}
