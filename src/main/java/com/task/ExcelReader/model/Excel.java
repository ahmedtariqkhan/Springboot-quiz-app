package com.task.ExcelReader.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Excel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mawb;
    private Date manifestDate;
    private String accountNumber;
    private String awb;
    private String orderNumber;
    private String origin;
    private String destination;
    private String shipperName;
    private String consigneeName;
    private Double weight;
    private Double declaredValue;
    private Double value;
    private Double vatAmount;
    private Double customForm;
    private Double other;
    private Double totalCharges;
    private Double customDeclaration;
    private Double ref;
    private Date customDeclarationDate;
}
