package com.workrise.jenny.services

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream

class ExcelServiceTest  {
    @Test
    fun testCreateExcelFileWithUserInput_fileCreatedWithData() {
        // Create an instance of ExcelService
        val excelService = ExcelService()

        // Prepare test data
        val data = listOf(
            mapOf("Name" to "John Doe", "Age" to 30),
            mapOf("Name" to "Jane Doe", "Age" to 28)
        )

        // Call the createExcelFileWithUserInput function
        excelService.createExcelFileWithUserInput(data)

        // Check if the output file exists
        val outputFilePath = "src/main/resources/output/generated.xlsx"
        val outputFile = File(outputFilePath)
        assertTrue(outputFile.exists(), "The output file should be created with the given data.")

        // Read the content of the output file and check if the data is correct
        FileInputStream(outputFilePath).use { inputStream ->
            XSSFWorkbook(inputStream).use { workbook ->
                val sheet = workbook.getSheetAt(0)
                data.forEachIndexed { rowIndex, rowMap ->
                    val row = sheet.getRow(rowIndex + 1)
                    rowMap.values.forEachIndexed { cellIndex, value ->
                        val cell = row.getCell(cellIndex)
                        assertEquals(value.toString(), cell.stringCellValue, "The cell value should match the input data.")
                    }
                }
            }
        }
    }

    @Test
    fun testCreateExcelFileWithUserInput_emptyData() {
        // Create an instance of ExcelService
        val excelService = ExcelService()

        // Prepare empty test data
        val data = emptyList<Map<String, Any>>()

        // Call the createExcelFileWithUserInput function
        excelService.createExcelFileWithUserInput(data)

        // Check if the output file exists
        val outputFilePath = "src/main/resources/output/generated.xlsx"
        val outputFile = File(outputFilePath)
        assertTrue(outputFile.exists(), "The output file should be created even with empty data.")

        // Read the content of the output file and check if it has no data rows
        FileInputStream(outputFilePath).use { inputStream ->
            XSSFWorkbook(inputStream).use { workbook ->
                val sheet = workbook.getSheetAt(0)
                val headerRow = sheet.getRow(0)
                val lastRowNum = sheet.lastRowNum
                assertEquals(headerRow.rowNum, lastRowNum, "The output file should only contain the header row.")
            }
        }
    }

}