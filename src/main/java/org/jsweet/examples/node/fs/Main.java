/* 
 * Copyright (C) 2015 Louis Grignon <louis.grignon@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jsweet.examples.node.fs;

import static def.dom.Globals.console;
import static def.node.Globals.process;
import static def.node.util.Globals.inspect;
import static jsweet.util.Lang.function;

/**
 * Main class, Globals name ensures that the code will run in global context,
 * wrapping class will be erased at runtime.
 * 
 * @author Louis Grignon
 *
 */
class Globals {

	public static void main(String[] args) {

		console.log("running JSweet + Node.JS FS example");

		FileSystemAccess fs = new FileSystemAccess();
		fs.printFileContent("test.txt");
		fs.printFileContent("test2.csv");

		final String writtenFilePath = "userInput.txt";
		System.out.println("what do you want to write in " + process.cwd() + "/" + writtenFilePath);

		process.stdin.resume();
		process.stdin.setEncoding("utf8");

		process.stdin.on("data", function(text -> {
			process.stdin.pause();
			String cleanText = inspect(text);
			console.log("writing to " + process.cwd() + "/" + writtenFilePath + ": " + cleanText);

			fs.writeFileContent(writtenFilePath, cleanText, () -> {

				String writtenContent = fs.getFileContent(writtenFilePath);

				System.out.println("thanks! file has been written, the content is: \n" + writtenContent);
			});
		}));
	}

}
