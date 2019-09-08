/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.addons.dv8.matrix.comp.data.output;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class XlsxDumper  {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	public XlsxDumper() {
	}

	public boolean output(String filename,CompareResult result) {
		startFile();

		// create header row
		XSSFRow headerRow = sheet.createRow(0);
		String[] header = new String[] {"LeftFile","RightFile","Type","LeftWeight","RightWeight"};
		
		
		for (int i = 0; i < header.length; i++) {
			XSSFCell cell = headerRow.createCell(i);
			cell.setCellValue(header[i]);
		}

		int rowIndex = 1;
		for ( PairDiff addedPairs:result.getPairAddedDetail().values()){
			for (WeightDiff weight:addedPairs.weightDiffs) {
				XSSFRow row = sheet.createRow(rowIndex++);
				String[] files = addedPairs.key.split("-->");
				row.createCell(0).setCellValue(files[0]);
				row.createCell(1).setCellValue(files[1]);
				row.createCell(2).setCellValue(weight.getType());
				row.createCell(3).setCellValue(weight.getLeft());
				row.createCell(4).setCellValue(weight.getRight());
			}
			
		}
		closeFile(filename);
		return true;
	}


	private void closeFile(String filename) {
		try {
	        FileOutputStream out = new FileOutputStream(filename);
	        workbook.write(out);
	        workbook.close();
	        out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startFile() {
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Diff");
	}

}
