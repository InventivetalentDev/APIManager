/*
 * Copyright 2015-2016 inventivetalent. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and contributors and should not be interpreted as representing official policies,
 *  either expressed or implied, of anybody else.
 */

package org.inventivetalent.apihelper;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class APIManager {

	private static final Map<API, RegisteredAPI> HOST_MAP = new HashMap<>();
	private static final Logger                  LOGGER   = Logger.getLogger("APIManager");

	/**
	 * Register a new API
	 *
	 * @param api {@link API} to register. Must implement {@link Plugin}
	 * @param <P> Class implementing both {@link API} &amp; {@link Plugin}
	 * @return a new {@link RegisteredAPI}
	 * @throws IllegalArgumentException if the API is already registered
	 */
	public static <P extends API & Plugin> RegisteredAPI<P> registerAPI(P api) throws IllegalArgumentException {
		if (HOST_MAP.containsKey(api)) { throw new IllegalArgumentException("API for '" + api.getName() + "' is already registered"); }
		RegisteredAPI<P> registeredAPI = new RegisteredAPI<P>(api);
		HOST_MAP.put(api, registeredAPI);

		LOGGER.fine("'" + api.getName() + "' registered as new API");
		return registeredAPI;
	}

	/**
	 * Register events for an API
	 *
	 * @param api      {@link API} to register events for
	 * @param <P>      Class implementing both {@link API} &amp; {@link Plugin}
	 * @param listener {@link Listener} to register
	 * @return the {@link API}
	 * @throws IllegalArgumentException if the API is not registered
	 * @throws IllegalStateException    if no Hosts are available
	 */
	public static <P extends API & Plugin> P registerEvents(P api, Listener listener) throws IllegalArgumentException, IllegalStateException {
		if (!HOST_MAP.containsKey(api)) { throw new IllegalArgumentException("API for '" + api.getName() + "' is not registered"); }
		RegisteredAPI registeredAPI = HOST_MAP.get(api);
		if (registeredAPI.eventsRegistered) {
			return api;//Only register events once
		}
		Bukkit.getPluginManager().registerEvents(listener, registeredAPI.getNextHost());
		registeredAPI.eventsRegistered = true;
		return api;
	}

	/**
	 * Initializes an API. Should be called in {@link Plugin#onEnable()}
	 *
	 * @param api {@link API} to initialize
	 * @param <P> Class implementing both {@link API} &amp; {@link Plugin}
	 * @throws IllegalArgumentException if the API or the Host is not registered
	 * @throws IllegalStateException    if no Hosts are available
	 */
	public static <P extends API & Plugin> void initAPI(P api, Plugin host) throws IllegalArgumentException, IllegalStateException {
		if (!HOST_MAP.containsKey(api)) { throw new IllegalArgumentException("API for '" + api.getName() + "' is not registered"); }
		RegisteredAPI<P> registeredAPI = HOST_MAP.get(api);
		if (api != host && !registeredAPI.hosts.contains(host)) { throw new IllegalArgumentException("Host '" + host.getName() + "' is not registered for '" + api.getName() + "'"); }
		registeredAPI.init();
	}

	/**
	 * Disables an API. Should be called in {@link Plugin#onDisable()}
	 *
	 * @param api {@link API} to disable
	 * @param <P> Class implementing both {@link API} &amp; {@link Plugin}
	 * @throws IllegalArgumentException if the Host is not registered
	 * @throws IllegalStateException    if no Hosts are available
	 */
	public static <P extends API & Plugin> void disableAPI(P api, Plugin host) throws IllegalArgumentException, IllegalStateException {
		if (!HOST_MAP.containsKey(api)) { return; }
		RegisteredAPI<P> registeredAPI = HOST_MAP.get(api);
		if (api != host && !registeredAPI.hosts.contains(host)) { throw new IllegalArgumentException("Host '" + host.getName() + "' is not registered for '" + api.getName() + "'"); }
		registeredAPI.disable();

		HOST_MAP.remove(api);
	}

	/**
	 * Register a new Host for an API
	 *
	 * @param api  {@link API} to register the host for
	 * @param host {@link Plugin}-Host to register
	 * @param <P>  Class implementing both {@link API} &amp; {@link Plugin}
	 * @return the updated {@link RegisteredAPI}
	 * @throws IllegalArgumentException if the API is not registered or the Host is already registered
	 */
	public static <P extends API & Plugin> RegisteredAPI<P> registerAPIHost(P api, Plugin host) throws IllegalArgumentException {
		if (!HOST_MAP.containsKey(api)) { throw new IllegalArgumentException("API for '" + api.getName() + "' is not registered"); }
		RegisteredAPI<P> registeredAPI = HOST_MAP.get(api);
		registeredAPI.registerHost(host);

		LOGGER.fine("'" + host.getName() + "' registered as Host for '" + api + "'");
		return registeredAPI;
	}

	/**
	 * Get the next available Host for an API
	 *
	 * @param api {@link API} to get the Host for
	 * @param <P> Class implementing both {@link API} &amp; {@link Plugin}
	 * @return an available {@link Plugin}-Host
	 * @throws IllegalArgumentException if the API is not registered
	 * @throws IllegalStateException    if no Hosts are available
	 */
	public static <P extends API & Plugin> Plugin getAPIHost(P api) throws IllegalArgumentException, IllegalStateException {
		if (!HOST_MAP.containsKey(api)) { throw new IllegalArgumentException("API for '" + api.getName() + "' is not registered"); }
		return HOST_MAP.get(api).getNextHost();
	}

}
