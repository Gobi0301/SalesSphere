package com.scube.crm.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileCopy {
	public void copy(String fromFileName, String toFileName) throws IOException {
		final File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);

		if (!fromFile.exists()) {
			throw new IOException("FileCopy: " + "no such source file: "
					+ fromFileName);
		}
		if (!fromFile.isFile()) {
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ fromFileName);
		}
		if (!fromFile.canRead()) {
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ fromFileName);
		}

		if (toFile.isDirectory()) {
			toFile = new File(toFile, fromFile.getName());
		}

		if (toFile.exists()) {
			if (!toFile.canWrite()) {
				throw new IOException("FileCopy: "
						+ "destination file is unwriteable: " + toFileName);
			}
			System.out.flush();
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			final String response = in.readLine();
			if (null !=response && response.equals("Y") && !response.equals("y")) {
				throw new IOException("FileCopy: "
						+ "existing file was not overwritten.");
			}
		} else {
			String parent = toFile.getParent();
			if (parent == null) {
				parent = System.getProperty("user.dir");
			}
			final File dir = new File(parent);
			if (!dir.exists()) {
				throw new IOException("FileCopy: "
						+ "destination directory doesn't exist: " + parent);
			}
			if (dir.isFile()) {
				throw new IOException("FileCopy: "
						+ "destination is not a directory: " + parent);
			}
			if (!dir.canWrite()) {
				throw new IOException("FileCopy: "
						+ "destination directory is unwriteable: " + parent);
			}
		}

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			final byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1) {
				to.write(buffer, 0, bytesRead); // write
			}
		} finally {
			if (from != null) {
				try {
					from.close();
				} catch (final IOException e) {
					;
				}
			}
			if (to != null) {
				try {
					to.close();
				} catch (final IOException e) {
					;
				}
			}
		}
	}
}
