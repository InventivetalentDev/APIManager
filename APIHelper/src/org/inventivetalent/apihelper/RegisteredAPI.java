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

import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RegisteredAPI<P extends API & Plugin> {

	protected final P api;
	protected final Set<Plugin> hosts = new HashSet<>();

	protected boolean initialized = false;

	public RegisteredAPI(P api) {
		this.api = api;
	}

	public void registerHost(Plugin host) throws IllegalArgumentException {
		if (this.hosts.contains(host)) { throw new IllegalArgumentException("Host API host '" + host.getName() + "' for '" + this.api.getName() + "'"); }
		this.hosts.add(host);
	}

	public Plugin getNextHost() throws IllegalStateException {
		if (this.api.isEnabled()) {
			return this.api;//The API-Plugin is enable, so this is the best choice
		}
		if (hosts.isEmpty()) {
			throw new IllegalStateException("API '" + this.api.getName() + "' is disabled, but no other Hosts have been registered");//Someone forgot to properly register a host for the API
		}
		for (Iterator<Plugin> iterator = this.hosts.iterator(); iterator.hasNext(); ) {
			Plugin host = iterator.next();
			if (host.isEnabled()) {
				return host;//Return the first enabled plugin
			}
		}
		throw new IllegalStateException("API '" + this.api.getName() + "' is disabled and all registered Hosts are as well");
	}

	public void init() {
		if (initialized) {
			return;//Only initialize once
		}
		this.api.init(getNextHost());
		initialized = true;
	}

}
