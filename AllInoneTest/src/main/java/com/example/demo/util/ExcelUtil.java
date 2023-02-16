package com.example.demo.util;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import kr.co.ecoletree.common.util.DateUtil;

/**
 * Class Name : ExcelUtil.java<br>
 * Description : ExcelUtil
 * @author wan
 * @since 2014. 2. 3.
 * @version 1.0
 * @see
 * 
 *      <pre>
 *  Modification Information (개정이력)
 *  수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *
 *      </pre>
 */
public class ExcelUtil {
	
	/**
	 * @param column : 컬럼 번호
	 * @param record : 줄 번호
	 * @param name : 타이틀명
	 * @param size : 셀의 가로크기
	 * @param fontsize : 폰트사이즈
	 * @param sheet : sheet Object
	 * @param color : 폰트 색상
	 * @param bgcolor : 셀의 배경색상
	 * @return
	 */
	public static WritableSheet getFormatTitle(int column, int record, String name, int size, int fontsize, WritableSheet sheet, Colour color, Colour bgcolor) {
		
		// 제목컬럼 폰트, 색상설정 : 폰트, 사이즈, 글씨굵기, 이택릭체, 언더라인, 색상
		WritableFont font = new WritableFont(WritableFont.createFont("맑은 고딕"), fontsize, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, color != null ? color : Colour.WHITE);
		// 제목컬럼 생성
		WritableCellFormat cell = new WritableCellFormat(font);
		// 라벨생성
		Label label = null;
		
		try {
			// 가로정렬지정(기본값:가운데정렬)
			cell.setAlignment(Alignment.CENTRE);
			// 세로정렬지정(가운데정렬)
			cell.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 테두리지정
			cell.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 배경색상(기본값:WHITE)
			cell.setBackground(bgcolor != null ? bgcolor : Colour.GRAY_50);
			// 제목으로 사용할 라벨을 생성함.
			label = new Label(column, record, name, cell);
			// 컬럼사이즈 조정 (0번째)의 넓이 설정
			sheet.setColumnView(column, size);
			// 컬럼에 라벨을 설정함.
			sheet.addCell(label);
			
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	/**
	 * @param column : 컬럼순번
	 * @param record : 레코드순번
	 * @param data : 데이터
	 * @param alignment 가로정렬
	 * @param bgcolor : 배경색상
	 * @param wrap : 개행문자 표시여부
	 * @param size : 폰트사이즈
	 * @return
	 */
	public static Label getFormatCell(int column, int record, String data, Alignment alignment, Colour bgcolor, boolean wrap, int size) {
		
		// 제목컬럼 폰트, 색상설정 : 폰트, 사이즈, 글씨굵기, 이택릭체, 언더라인, 색상
		WritableFont font = new WritableFont(WritableFont.createFont("맑은 고딕"), size, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREY_80_PERCENT);
		// 제목컬럼 생성
		WritableCellFormat cell = new WritableCellFormat(font);
		// 라벨생성
		Label label = null;
		
		try {
			// 가로정렬지정(기본값:가운데정렬)
			cell.setAlignment(alignment != null ? alignment : Alignment.CENTRE);
			// 세로정렬지정(가운데정렬)
			cell.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 배경색상(기본값:WHITE)
			cell.setBackground(bgcolor != null ? bgcolor : Colour.WHITE);
			// 테두리지정
			cell.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 개행문자처리
			cell.setWrap(wrap);
			// 데이터를 라벨을 생성함.
			label = new Label(column, record, data, cell);
			
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return label;
	}
	
	/**
	 * @param sheet
	 * @param titleStr
	 * @return
	 */
	public static WritableSheet getSheetTitle(WritableSheet sheet, String titleStr) {
		
		// 제목컬럼 폰트, 색상설정 : 폰트, 사이즈, 글씨굵기, 이택릭체, 언더라인, 색상
		WritableFont font = new WritableFont(WritableFont.createFont("맑은 고딕"), 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREY_80_PERCENT);
		// 제목컬럼 생성
		WritableCellFormat cell = new WritableCellFormat(font);
		// 라벨생성
		Label label = null;
		
		try {
			// 가로정렬지정(기본값:가운데정렬)
			cell.setAlignment(Alignment.LEFT);
			// 세로정렬지정(가운데정렬)
			cell.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 테두리지정
			cell.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			// 배경색상(기본값:WHITE)
			cell.setBackground(Colour.WHITE);
			// 제목으로 사용할 라벨을 생성함.
			label = new Label(0, 0, titleStr, cell);
			// 컬럼사이즈 조정 (0번째)의 넓이 설정
			// sheet.setColumnView(column, size);
			// 컬럼에 라벨을 설정함.
			sheet.addCell(label);
			// 타이틀셀병합
			sheet.mergeCells(0, 0, 8, 0);
			
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	/**
	 * @param file
	 */
	public static void deleteFile(String file) {
		try {
			File f = new File(file);
			if (f.isFile())
				f.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param sheet
	 * @param titleStr
	 * @param hap : 0 부터 시작해서 병합할 셀의 갯수
	 * @return
	 */
	public static WritableSheet getSheetTitlehap(WritableSheet sheet, String titleStr, int hap) {
		// 제목컬럼 폰트, 색상설정 : 폰트, 사이즈, 글씨굵기, 이택릭체, 언더라인, 색상
		WritableFont font = new WritableFont(WritableFont.createFont("맑은 고딕"), 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREY_80_PERCENT);
		// 제목컬럼 생성
		WritableCellFormat cell = new WritableCellFormat(font);
		// 라벨생성
		Label label = null;
		try {
			// 가로정렬지정(기본값:가운데정렬)
			cell.setAlignment(Alignment.CENTRE);
			// 세로정렬지정(가운데정렬)
			cell.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 테두리지정
			// cell.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			// 배경색상(기본값:WHITE)
			cell.setBackground(Colour.WHITE);
			// 제목으로 사용할 라벨을 생성함.
			label = new Label(0, 0, titleStr, cell);
			// 컬럼사이즈 조정 (0번째)의 넓이 설정
			// sheet.setColumnView(column, size);
			// 컬럼에 라벨을 설정함.
			sheet.addCell(label);
			// 타이틀셀병합
			sheet.mergeCells(0, 0, hap, 0);
			
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	/**
	 * @param sheet
	 * @param hap
	 * @param record
	 * @return
	 */
	public static WritableSheet getSheetDatePrint(WritableSheet sheet, int hap, int record) {
		// 제목컬럼 폰트, 색상설정 : 폰트, 사이즈, 글씨굵기, 이택릭체, 언더라인, 색상
		WritableFont font = new WritableFont(WritableFont.createFont("맑은 고딕"), 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREY_80_PERCENT);
		// 제목컬럼 생성
		WritableCellFormat cell = new WritableCellFormat(font);
		// 라벨생성
		Label label = null;
		
		try {
			String datePrint = "CREATE DATE: " + DateUtil.changeDateFormat(DateUtil.getCurrentDateByFormat("yyyyMMddHHmmss"), "yyyyMMddHHmmss", "yyyy.MM.dd HH:mm:ss");
			
			// 가로정렬지정(기본값:가운데정렬)
			cell.setAlignment(Alignment.LEFT);
			// 세로정렬지정(가운데정렬)
			cell.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 테두리지정
			// cell.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			// 배경색상(기본값:WHITE)
			cell.setBackground(Colour.WHITE);
			// 제목으로 사용할 라벨을 생성함.
			label = new Label(0, record, datePrint, cell);
			// 컬럼사이즈 조정 (column번째)의 넓이 설정
			// sheet.setColumnView(column, size);
			// 컬럼에 라벨을 설정함.
			sheet.addCell(label);
			sheet.mergeCells(0, record, hap, record);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	/**
	 * @param startDate
	 * @param endDate
	 * @param sheet
	 * @param hap
	 * @param record
	 * @return
	 */
	public static WritableSheet getSheetDatePrint(String startDate, String endDate, WritableSheet sheet, int hap, int record) {
		// 제목컬럼 폰트, 색상설정 : 폰트, 사이즈, 글씨굵기, 이택릭체, 언더라인, 색상
		WritableFont font = new WritableFont(WritableFont.createFont("맑은 고딕"), 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREY_80_PERCENT);
		// 제목컬럼 생성
		WritableCellFormat cell = new WritableCellFormat(font);
		// 라벨생성
		Label label = null;
		
		try {
			String datePrint = "PERIOD : " + DateUtil.changeDateFormat(startDate, "yyyyMMdd", "yyyy.MM.dd") + " ~ " + DateUtil.changeDateFormat(endDate, "yyyyMMdd", "yyyy.MM.dd");
			
			// 가로정렬지정(기본값:가운데정렬)
			cell.setAlignment(Alignment.LEFT);
			// 세로정렬지정(가운데정렬)
			cell.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 테두리지정
			// cell.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			// 배경색상(기본값:WHITE)
			cell.setBackground(Colour.WHITE);
			// 제목으로 사용할 라벨을 생성함.
			label = new Label(0, record, datePrint, cell);
			// 컬럼사이즈 조정 (column번째)의 넓이 설정
			// sheet.setColumnView(column, size);
			// 컬럼에 라벨을 설정함.
			sheet.addCell(label);
			sheet.mergeCells(0, record, hap, record);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	/**
	 * @param sheet
	 * @param hap
	 * @param record
	 * @param data
	 * @return
	 */
	public static WritableSheet getSheetDescriptionPrint(WritableSheet sheet, int hap, int record, String data) {
		// 제목컬럼 폰트, 색상설정 : 폰트, 사이즈, 글씨굵기, 이택릭체, 언더라인, 색상
		WritableFont font = new WritableFont(WritableFont.createFont("맑은 고딕"), 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREY_80_PERCENT);
		// 제목컬럼 생성
		WritableCellFormat cell = new WritableCellFormat(font);
		// 라벨생성
		Label label = null;
		
		try {
			String datePrint = "CREATE DATE: " + DateUtil.changeDateFormat(DateUtil.getCurrentDateByFormat("yyyyMMddHHmmss"), "yyyyMMddHHmmss", "yyyy.MM.dd HH:mm:ss") + "\r\nDescription: " + data;
			
			cell.setWrap(true);
			// 가로정렬지정(기본값:가운데정렬)
			cell.setAlignment(Alignment.LEFT);
			// 세로정렬지정(가운데정렬)
			cell.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 테두리지정
			// cell.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			// 배경색상(기본값:WHITE)
			cell.setBackground(Colour.WHITE);
			// 제목으로 사용할 라벨을 생성함.
			label = new Label(0, record, datePrint, cell);
			// 컬럼사이즈 조정 (column번째)의 넓이 설정
			// sheet.setColumnView(column, size);
			// 컬럼에 라벨을 설정함.
			sheet.addCell(label);
			sheet.mergeCells(0, record, hap, record);
			sheet.setRowView(record, 50 * 20);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	/**
	 * @param startDate
	 * @param endDate
	 * @param sheet
	 * @param hap
	 * @param record
	 * @param data
	 * @return
	 */
	public static WritableSheet getSheetDescriptionPrint(String startDate, String endDate, WritableSheet sheet, int hap, int record, String data) {
		// 제목컬럼 폰트, 색상설정 : 폰트, 사이즈, 글씨굵기, 이택릭체, 언더라인, 색상
		WritableFont font = new WritableFont(WritableFont.createFont("맑은 고딕"), 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREY_80_PERCENT);
		// 제목컬럼 생성
		WritableCellFormat cell = new WritableCellFormat(font);
		// 라벨생성
		Label label = null;
		
		try {
			String datePrint = "PERIOD : " + DateUtil.changeDateFormat(startDate, "yyyyMMdd", "yyyy.MM.dd") + " ~ " + DateUtil.changeDateFormat(endDate, "yyyyMMdd", "yyyy.MM.dd") + "\r\nDescription: " + data;
			
			cell.setWrap(true);
			// 가로정렬지정(기본값:가운데정렬)
			cell.setAlignment(Alignment.LEFT);
			// 세로정렬지정(가운데정렬)
			cell.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 테두리지정
			// cell.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
			// 배경색상(기본값:WHITE)
			cell.setBackground(Colour.WHITE);
			// 제목으로 사용할 라벨을 생성함.
			label = new Label(0, record, datePrint, cell);
			// 컬럼사이즈 조정 (column번째)의 넓이 설정
			// sheet.setColumnView(column, size);
			// 컬럼에 라벨을 설정함.
			sheet.addCell(label);
			sheet.mergeCells(0, record, hap, record);
			sheet.setRowView(record, 50 * 20);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}
	
	/**
	 * @param column : 컬럼순번
	 * @param record : 줄순번
	 * @param data : 데이터 ( INT
	 * @param alignment : 가로정렬
	 * @param bgcolor : 배경색상
	 * @param wrap : 개행문자 표시여부 (자동 줄바꿈)
	 * @return
	 */
	public static Number getFormatCellInt(int column, int record, int data, Alignment alignment, Colour bgcolor, boolean wrap) {
		
		// Cell Date put
		Number num = new Number(column, record, data);
		
		try {
			// basic font format (폰트, 색상설정 : 폰트, 사이즈, 글씨굵기, 이택릭체, 언더라인, 색상)
			WritableFont font = new WritableFont(WritableFont.createFont("맑은 고딕"), 9, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.GREY_80_PERCENT);
			// Cell number format
			
			// NumberFormats exp4 = new NumberFormats();
			// WritableCellFormat cell = new WritableCellFormat(font, exp4.FORMAT1);
			WritableCellFormat cell = new WritableCellFormat(font, NumberFormats.FORMAT1);
			// 가로정렬지정(기본값:가운데정렬)
			cell.setAlignment(alignment != null ? alignment : Alignment.CENTRE);
			// 세로정렬지정(가운데정렬)
			cell.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 배경색상(기본값:WHITE)
			cell.setBackground(bgcolor != null ? bgcolor : Colour.WHITE);
			// 테두리지정
			cell.setBorder(Border.ALL, BorderLineStyle.THIN);
			// 개행문자처리
			cell.setWrap(wrap);
			// Cell Format insert
			num.setCellFormat(cell);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * @param startRowNum
	 * @param headerName
	 * @param excelFile
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static List<Map<String, String>> getExcelDataList(int startRowNum, LinkedHashMap<String, String> headerName, MultipartFile excelFile) throws Exception {
		String fileName = excelFile.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		List<Object> keyList = new ArrayList<Object>(headerName.keySet());
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		if (fileType.equals("xls")) {
			POIFSFileSystem fileSystem = new POIFSFileSystem(excelFile.getInputStream());
			HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
			
			HSSFSheet sheet = workbook.getSheetAt(0);
			int totalRow = sheet.getPhysicalNumberOfRows();
			
			for (int i = startRowNum; i < totalRow; i++) {
				HSSFRow row = sheet.getRow(i);
				Map<String, String> map = new HashMap<String, String>();
				
				for (int j = 0; j < keyList.size(); j++) {
					HSSFCell cell = row.getCell(j);
					String data = "";
					
					if (cell != null) {
						cell.setCellType(CellType.STRING);
						data = cell.getStringCellValue();
					} else {
						data = "";
					}
					map.put(String.valueOf(keyList.get(j)), data);
				}
				
				list.add(map);
			}
		} else if (fileType.equals("xlsx")) {
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			int totalRow = sheet.getPhysicalNumberOfRows();
			
			for (int i = startRowNum; i < totalRow; i++) {
				XSSFRow row = sheet.getRow(i);
				Map<String, String> map = new HashMap<String, String>();
				
				for (int j = 0; j < keyList.size(); j++) {
					XSSFCell cell = row.getCell(j);
					String data = "";
					
					if (cell != null) {
						cell.setCellType(CellType.STRING);
						data = cell.getStringCellValue();
					} else {
						data = "";
					}
					map.put(String.valueOf(keyList.get(j)), data);
				}
				
				list.add(map);
			}
		}
		
		return list;
	}

	/**
	 * 컬럼 헤더가 없는 엑셀 데이터를 파싱한다.
	 * Map의 key는 각 셀의 인덱스 번호(문자열)
	 *
	 * @param xlsxFile
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<List<Map<String, Object>>> getXlsxDataNoHeader(final File xlsxFile) throws IOException, InvalidFormatException {
		return xlsx2Map(xlsxFile, 0, 0);
	}

	/**
	 * 컬럼 헤더가 있는 엑셀 데이터를 파싱한다.
	 * Map의 key는 지정된 headerRow 값(문자열)
	 *
	 * @param xlsxFile
	 * @param headerRowIndex 컬럼 헤더로 사용할 Row index
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<List<Map<String, Object>>> getXlsxDataWithHeader(final File xlsxFile, final int headerRowIndex) throws IOException, InvalidFormatException {
		return xlsx2Map(xlsxFile, headerRowIndex+1, headerRowIndex);
	}

	/**
	 * 엑셀 데이터 파싱
	 *
	 * @param xlsxFile
	 * @param startRowNum
	 * @param headerRowIndex
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	@SuppressWarnings("resource")
	private static List<List<Map<String, Object>>> xlsx2Map(final File xlsxFile, final int startRowNum, final int headerRowIndex) throws IOException, InvalidFormatException {
		final Workbook wb = new XSSFWorkbook(xlsxFile);
//		final DataFormatter formatter = new DataFormatter();
		final List<Sheet> sheets = new ArrayList<Sheet>();
		wb.forEach(sheets::add);

		final Function<Sheet, List<Map<String, Object>>> sheetParser = sheet -> ExcelUtil.parseSheet(sheet, startRowNum, headerRowIndex);
		return sheets.stream().map(sheetParser).collect(toList());
	}

	/**
	 * 하나의 Sheet를 파싱한다.
	 *
	 * @param sheet Sheet 객체
	 * @param startRowNum 파싱을 시작할 행 인덱스 (0 based)
	 * @param headerRowIndex 헤더로 사용
	 * @return
	 */
	private static List<Map<String, Object>> parseSheet(final Sheet sheet, final int startRowNum, final int headerRowIndex) {
		final List<Row> rows = new ArrayList<Row>();
		sheet.forEach(rows::add);

		final Function<Map<Integer, Object>, Map<String, Object>> headerConverter; // index(int)로 설정된 헤더를 문자열로 변환하는 함수
		if (startRowNum > 0 && headerRowIndex <= startRowNum) { // 컬럼 헤더 사용 여부
			final Map<Integer, String> headers = new HashMap<Integer, String>();
			parseRow(rows.get(headerRowIndex)).forEach((idx, value) -> headers.put(idx, String.valueOf(value)));
			headerConverter = applyHeaders(headers::get);
		} else {
			headerConverter = applyHeaders(String::valueOf);
		}

		return rows.subList(startRowNum, rows.size()).stream()
				.map(ExcelUtil::parseRow)
				.map(headerConverter)
				.collect(toList());
	}

	/**
	 * 하나의 Row를 파싱한다.
	 *
	 * @param row
	 * @return
	 */
	private static Map<Integer, Object> parseRow(final Row row) {
		final List<Cell> cells = new ArrayList<Cell>();
		row.forEach(cells::add);
		return cells.stream()
				.collect(toMap(Cell::getColumnIndex, ExcelUtil::getCellValue));
	}

	/**
	 * 하나의 Cell을 파싱한다.
	 *
	 * @param cell
	 * @return
	 */
	private static Object getCellValue(final Cell cell) {
		final Object value;

		switch (cell.getCellType()) {
			case BOOLEAN:
				value = cell.getBooleanCellValue();
				break;

			case NUMERIC:
				value = cell.getNumericCellValue();
				break;

			case STRING:
				value = cell.getStringCellValue();
				break;

			case BLANK:
			case FORMULA:
			default:
				value = null;
				break;
		}

		return value;
	}

	/**
	 * index(int)로 설정된 헤더를 문자열로 변환하는 함수를 인자로 받아 파싱된 row(=cell data: Map)에 적용하는 함수를 리턴
	 *
	 * @param getHeaderString
	 * @return
	 */
	private static Function<Map<Integer, Object>, Map<String, Object>> applyHeaders(final Function<Integer, String> getHeaderString) {
		return indexBasedCellData -> {
			final Map<String, Object> cellData = new HashMap<String, Object>();
			indexBasedCellData.forEach((idx, val) -> cellData.put(getHeaderString.apply(idx), val));
			return cellData;
		};
	}

	public static void main(String[] args) throws IOException, InvalidFormatException {
		final File xlsx = Paths.get("/home/incognito/Downloads/CSV1911050842.xlsx").toFile();
		System.out.println(getXlsxDataNoHeader(xlsx).get(0).get(1));
//		System.out.println(getXlsxDataWithHeader(xlsx, 0).get(0).get(1));
	}
}
