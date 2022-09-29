package com.company.files;

import com.company.db.Database;
import com.company.entity.Customer;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public interface WorkWithFiles {

    Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    String BASE_FOLDER = "src/main/resources/documents";
    File CUSTOMER_FILE = new File(BASE_FOLDER, "customers.json");
    File INCOME_FILE = new File(BASE_FOLDER, "income.json");
    File SPEND_FILE = new File(BASE_FOLDER, "spend.json");

    static void readCustomerList(){
        if(!CUSTOMER_FILE.exists()) return;

        try {
            List customers = GSON.fromJson(new BufferedReader(new FileReader(CUSTOMER_FILE)),
                    new TypeToken<List<Customer>>() {
                    }.getType());
            Database.customerList.clear();
            Database.customerList.addAll(customers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void writeCustomerList(){
        try (PrintWriter writer = new PrintWriter(CUSTOMER_FILE)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(writer,Database.customerList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    static File generateCustomerExcelFile() {
//
//        File file = new File(BASE_FOLDER, "customers.xlsx");
//
//        try (FileOutputStream out = new FileOutputStream(file);
//            XSSFWorkbook workbook = new XSSFWorkbook();
//        ) {
//
//            XSSFSheet sheet = workbook.createSheet();
//
//            XSSFRow header = sheet.createRow(0);
//            header.createCell(0).setCellValue("Chat id");
//            header.createCell(1).setCellValue("First name");
//            header.createCell(2).setCellValue("Last name");
//            header.createCell(3).setCellValue("Phone number");
//
//            int i=0;
//            for (Customer customer : Database.customerList) {
//                i++;
//                XSSFRow row = sheet.createRow(i);
//                row.createCell(0).setCellValue(customer.getChatId());
//                row.createCell(1).setCellValue(customer.getFirstName());
//                row.createCell(2).setCellValue(customer.getLastName());
//                row.createCell(3).setCellValue(customer.getPhoneNumber());
//            }
//
//            for (int j = 0; j < 4; j++) {
//                sheet.autoSizeColumn(j);
//            }
//
//            workbook.write(out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return file;
//    }

    static File generateCustomerExcelFileByAll() {
        return null;
    }

    static File generateCustomerExcelFileByIncome() {
        return null;
    }

    static File generateCustomerExcelFileBySpend() {
        return null;
    }


    static void writeToIncomeJson() {
        try (PrintWriter writer = new PrintWriter(INCOME_FILE)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(writer,Database.incomeList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static void writeToSpendJson() {
        try (PrintWriter writer = new PrintWriter(SPEND_FILE)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(writer,Database.spendList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
