package org.jsweet.examples.node.fs;

import static def.node.fs.Globals.readFileSync;
import static def.node.fs.Globals.readFile;
import static def.node.fs.Globals.open;
import static def.node.fs.Globals.writeSync;

import def.js.Uint8Array;

import static def.node.fs.Globals.closeSync;

import def.node.Buffer;
import def.node.nodejs.ErrnoException;

public class FileSystemAccess {

	public void printFileContent(String path) {
		System.out.println("file content: " + getFileContent(path));
	}

	public void printFileContentAsync(String path) {
		readFile(path, (ErrnoException err, Buffer content) -> {
			if (err != null) {
				System.err.println("error reading " + path);
				return;
			}
			System.out.println("file content async: " + content);
		});
	}

	public void writeFileContent(String path, String content, Runnable onWritten) {
		open(path, "w", (ErrnoException err, Double fd) -> {
			if (err != null) {
				System.err.println("error opening " + path);
				return;
			}
			System.out.println(path + " opened successfully!");
			
			writeSync(fd, content);
			closeSync(fd);
			
			System.out.println(path + " written successfully!");
			
			onWritten.run();
		});
	}

	public String getFileContent(String path) {
		Uint8Array content = readFileSync(path);
		return content.toString();
	}
}
