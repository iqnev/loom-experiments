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
package demo.loom.ping.client.impl;

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
public class PingClient {

	public static void main(String[] args) throws UncheckedIOException, InterruptedException {
		if (args.length == 0)
			throw new IllegalArgumentException("Please specify the messsage");

		VirtualThreadClient vClient = new VirtualThreadClient(PingClient::sendMessageAndWaitForReply, 99);
		vClient.sendMessages(args[0]);
	}

	private static void sendMessageAndWaitForReply(String message) {
		System.out.printf("Sending: '%s'%n", message);
		try (var socket = new Socket("localhost", 8787);
				var receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				var sender = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
			sender.println(message);
			sender.flush();
			var reply = receiver.readLine();
			System.out.printf("Received: '%s'.%n", reply);
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}
	}
}
