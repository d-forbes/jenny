package com.workrise.jenny

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

@Service
class ExcelService {
    @Throws(IOException::class)
    fun createExcelFileWithUserInput(data: List<Map<String, Any>>) {
        val templateFile = File("src/main/resources/templates/standard_hourly.xlsx")
        val outputFilePath = "src/main/resources/output/generated.xlsx"
        val outputFile = File("src/main/resources/output/generated.xlsx")
        templateFile.copyTo(outputFile, overwrite = true)

        val inputStream = FileInputStream(outputFilePath)
        val newWorkbook = XSSFWorkbook(inputStream)
        val sheet = newWorkbook.getSheetAt(0)
        data.forEachIndexed { rowIndex, rowMap ->
            val row = sheet.createRow(rowIndex + 1)
            rowMap.values.forEachIndexed { cellIndex, value ->
                val cell = row.createCell(cellIndex)
                cell.setCellValue(value.toString())
            }
        }
        inputStream.close()
        val outputStream = FileOutputStream(outputFilePath)
        newWorkbook.write(outputStream)
        outputStream.close()
        newWorkbook.close()

    }
}
