package com.increff.employee.util;

import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.data.SalesReportData;
import com.increff.employee.pojo.BrandPojo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

@Component
public class CsvFileGenerator {

    public void writeBrandsToCsv(List<BrandPojo> brandPojoList, Writer writer) {
//        System.out.println("here");
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            printer.printRecord("ID","Brand","Category");
            for (BrandPojo brandPojo : brandPojoList) {
                printer.printRecord(brandPojo.getId(), brandPojo.getBrand(), brandPojo.getCategory());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeInventoryToCsv(List<InventoryData> inventoryDataList, Writer writer) {
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            printer.printRecord("Brand","Category","Quantity");
            for (InventoryData i: inventoryDataList) {
                printer.printRecord(i.getBrand(), i.getCategory(), i.getQuantity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSalesToCsv(List<SalesReportData> salesReportDataList, PrintWriter writer) {
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            printer.printRecord("Brand","Category","Quantity","Revenue");
            for (SalesReportData salesReportData: salesReportDataList) {
                printer.printRecord(salesReportData.getBrand(), salesReportData.getCategory(), salesReportData.getQuantity(), salesReportData.getRevenue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}












