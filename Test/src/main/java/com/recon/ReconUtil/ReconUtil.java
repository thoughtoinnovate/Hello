package com.recon.ReconUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Optional;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;

import com.recon.ReconUtil.Model.DiffResults;

public class ReconUtil {

	public Optional<DiffResults> findDifferences(String firstFile, String secondFile) {

		Diff xmlFileDiff = DiffBuilder.compare(firstFile).withTest(secondFile).build();

		boolean hasDifferences = xmlFileDiff.hasDifferences();
		System.out.println(hasDifferences);

		if (!hasDifferences) {
			return Optional.empty();
		}

		DiffResults diffResults = new DiffResults();

		Iterator<Difference> diffItr = xmlFileDiff.getDifferences().iterator();

		while (diffItr.hasNext()) {

			String diff = diffItr.next().toString();
			System.out.println(diff);

		}

		return Optional.ofNullable(diffResults);

	}

	public static void main(String... args) throws IOException {
		ReconUtil reconUtil = new ReconUtil();

		String firstFileContent = reconUtil.readFileToString("src/main/resources/Employee1.xml");
		String secondFileContent = reconUtil.readFileToString("src/main/resources/Employee2.xml");

		reconUtil.findDifferences(firstFileContent, secondFileContent);

	}

	public String readFileToString(String path) throws IOException {
		File f = new File(path);
		InputStream in = new FileInputStream(f);
		BufferedReader bff = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();

		String line = bff.readLine();
		while (null != line) {
			sb.append(line.trim());
			line = bff.readLine();
		}

		in.close();
		bff.close();

		return sb.toString();

	}

}
