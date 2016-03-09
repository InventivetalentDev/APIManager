/*
 * Copyright 2016 inventivetalent. All rights reserved.
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
