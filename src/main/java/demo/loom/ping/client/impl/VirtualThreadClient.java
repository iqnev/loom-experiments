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

import java.io.UncheckedIOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredExecutor;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import demo.loom.ping.client.Client;
import static java.util.Objects.requireNonNull;

/**
 * 
 * @author Ivelin Yanev <bgfortran@gmail.com>
 * @since 11.01.2021
 *
 */
public class VirtualThreadClient implements Client {
	private final Consumer<String> client;
	private final int messageCount;

	public VirtualThreadClient(Consumer<String> client, int messageCount) {
		this.client = requireNonNull(client);
		this.messageCount = messageCount;
	}

	@Override
	public void sendMessages(String messageRoot) throws UncheckedIOException, InterruptedException {
		try (var executor = StructuredExecutor.open()) {
			var handler = new StructuredExecutor.ShutdownOnFailure();
			IntStream.range(0, messageCount).forEach(counter -> {
				String message = messageRoot + " " + counter;
				Runnable send = () -> client.accept(message);
				executor.execute(send);
			});

			executor.join();
			handler.throwIfFailed();
		} catch (ExecutionException ex) {
			if (ex.getCause() instanceof RuntimeException runtimeException)
				throw runtimeException;
			else
				throw new RuntimeException(ex.getCause());
		}

	}

}
