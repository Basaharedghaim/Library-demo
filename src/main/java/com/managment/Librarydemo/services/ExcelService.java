package com.managment.Librarydemo.services;

import com.managment.Librarydemo.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import com.models.demo.models.entity.*;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
public class ExcelService {
    private List<Book> allBooks=new LinkedList<>();
    final private BookRepository bookRepository;

    @Autowired
    public ExcelService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    public HashSet<Book> booksList (){
        HashSet<Book> books=new HashSet<>();
        books.addAll(bookRepository.findAll());
        return books;
    }

    public void readBooksFromExcel(String filePath) {
        try (InputStream excelFile = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(excelFile)) {
            HashSet<Book> uniqueExcel = booksList();

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

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


                uniqueExcel.add(book); // Add to HashSet if it's not already present
            }

            ArrayList<Book> sortedBooks = new ArrayList<Book>(uniqueExcel);
            Collections.sort(sortedBooks); // Sort books by price

            // Save unique books to the database
            for (Book uniqueBook : sortedBooks) {
                bookRepository.save(uniqueBook);
                log.info(String.valueOf((uniqueBook.getId())));
                log.info(uniqueBook.getTitle());
                log.info(uniqueBook.getAuthorName());
                log.info(String.valueOf(uniqueBook.getTypes()));
                log.info(String.valueOf(uniqueBook.getPrice()));
            }

        } catch (Exception e) {
            log.error("Error reading or saving books from Excel: " + e.getMessage());
        }
    }



}
