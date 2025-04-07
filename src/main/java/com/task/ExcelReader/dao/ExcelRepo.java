package com.task.ExcelReader.dao;

import com.task.ExcelReader.model.Excel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelRepo extends JpaRepository<Excel, Integer> {

}
