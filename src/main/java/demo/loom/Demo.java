/*
 * Copyright (C) 2022 Ivelin Yanev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo.loom;

import java.io.IOException;
import java.util.Arrays;
import demo.loom.ping.client.impl.PingClient;
import demo.loom.ping.server.impl.PingServer;
import demo.loom.service.Services;

/**
 * 
 * @author Ivelin Yanev <bgfortran@gmail.com>
 * @since 11.01.2022
 *
 */
public class Demo {

	public static void main(String[] args) throws InterruptedException, IOException {

		if (args.length == 0)
			throw new IllegalArgumentException("Please specify the demo");

		var demo = args[0];
		var demoArgs = Arrays.copyOfRange(args, 1, args.length);

		switch (demo) {
		case "ExecutorServices" -> Services.main(demoArgs);
		case "PingServer" -> PingServer.main(demoArgs);
		case "PingClient" -> PingClient.main(demoArgs);
		default -> throw new IllegalArgumentException("Unknown experiment: " + demo);
		}

	}

}
