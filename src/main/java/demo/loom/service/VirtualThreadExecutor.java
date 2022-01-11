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
package demo.loom.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Ivelin Yanev <bgfortran@gmail.com>
 * @since 11.01.2022
 *
 */
public class VirtualThreadExecutor {

	public static void start() throws InterruptedException {

		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

		for (int i = 0; i < 1000; i++) {
			executor.submit(new RandomNumbers());
		}

		executor.shutdown();

		if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
			executor.shutdownNow();
		}
	}

}
