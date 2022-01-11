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

import java.util.Random;

/**
 * 
 * @author Ivelin Yanev <bgfortran@gmail.com>
 * @since 11.01.2022
 *
 */
public class RandomNumbers implements Runnable {
	Random random = new Random();

	public void run() {
		for (int i = 0; i < 120; i++) {
			try {
				char a = (char) (random.nextInt(26) + 'a');
				char b = (char) (random.nextInt(26) + 'a');
				System.out.println("c = " + a + b);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
