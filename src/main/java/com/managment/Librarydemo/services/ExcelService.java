package com.managment.Librarydemo.services;

import lombok.extern.slf4j.Slf4j;
import models.Book;
import models.Types;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Slf4j
@Service
public class ExcelService {

    public List<Book> readBooksFromExcel(String filePath) {
        List<Book> books = new ArrayList<>();
        try (InputStream excelFile = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(excelFile)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip header row if needed
            if (rowIterator.hasNext()) {
                rowIterator.next(); // Skip header row
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Book book = new Book();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            book.setTitle(cell.getStringCellValue());
                            break;
                        case 1:
                            book.setTypes(Types.valueOf(cell.getStringCellValue()));
                            break;
                        case 2:
                            book.setAuthorName(cell.getStringCellValue());
                            break;
                        case 3:
                            book.setPrice(cell.getNumericCellValue());
                            break;
                    }
                }

                books.add(book);
                log.info(String.valueOf((book.getId())));
                log.info(book.getTitle());
                log.info(book.getAuthorName());
                log.info(String.valueOf(book.getTypes()));
                log.info(String.valueOf(book.getPrice()));

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return books;
    }
}
