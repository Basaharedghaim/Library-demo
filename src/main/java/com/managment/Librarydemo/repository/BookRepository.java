package com.managment.Librarydemo.repository;

import com.models.demo.models.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
