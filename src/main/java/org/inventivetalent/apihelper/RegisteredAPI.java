package org.inventivetalent.apihelper;

import org.bukkit.plugin.Plugin;
import org.inventivetalent.apihelper.exception.HostRegistrationException;
import org.inventivetalent.apihelper.exception.MissingHostException;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unused", "WeakerAccess"})
public class RegisteredAPI {

	protected final API api;
	protected final Set<Plugin> hosts = new HashSet<>();

	protected boolean initialized = false;
	protected Plugin initializerHost;

	protected boolean eventsRegistered = false;

	public RegisteredAPI(API api) {
		this.api = api;
	}

	public void registerHost(Plugin host) throws HostRegistrationException {
		if (this.hosts.contains(host)) {
			throw new HostRegistrationException("API host '" + host.getName() + "' for '" + this.api.getClass().getName() + "' is already registered");
		}
		this.hosts.add(host);
	}

	public Plugin getNextHost() throws MissingHostException {
		if (this.api instanceof Plugin && ((Plugin) this.api).isEnabled()) {
			return (Plugin) this.api;//The API-Plugin is enable, so this is the best choice
		}
		if (hosts.isEmpty()) {
			throw new MissingHostException("API '" + this.api.getClass().getName() + "' is disabled, but no other Hosts have been registered");//Someone forgot to properly register a host for the API
		}
		for (Plugin host : this.hosts) {
			if (host.isEnabled()) {
				return host;//Return the first enabled plugin
			}
		}
		throw new MissingHostException("API '" + this.api.getClass().getName() + "' is disabled and all registered Hosts are as well");
	}

	/**
	 * Initializes the API (if not already initialized)
	 * <p>
	 * Calls {@link API#init(Plugin)}
	 */
	public void init() {
		if (initialized) {
			return;//Only initialize once
		}
		this.api.init(initializerHost = getNextHost());
		initialized = true;
	}

	/**
	 * Disables the API (if not already disabled)
	 * <p>
	 * Calls {@link API#disable(Plugin)}
	 */
	public void disable() {
		if (!initialized) {
			return;
		}
		this.api.disable(initializerHost);
		initialized = false;
	}

}
