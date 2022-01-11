/*
 * Copyright (C) 2021 Ivelin Yanev
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
package demo.loom.ping.server.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.net.Socket;

/**
 * 
 * @author Ivelin Yanev <bgfortran@gmail.com>
 * @since 11.01.2021
 *
 */
public class PingServer {
	static final int DEFAULT_PING_SLEEP = 113;

	public static void main(String[] args) throws IOException {
		var ping = new PingServer();

		VirtualThreadServer server = new VirtualThreadServer(ping::ping);

		System.out.println("Server up - start listening on 8787...");
		server.listen();
	}

	private void ping(Socket socket) {
		try (socket;
				var receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				var sender = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
			var message = receiver.readLine();
			System.out.printf("Message '%s'.%n", message);
			Thread.sleep(DEFAULT_PING_SLEEP);
			sender.println(message);
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		} catch (InterruptedException ex) {

			Thread.currentThread().interrupt();
		}
	}

}
