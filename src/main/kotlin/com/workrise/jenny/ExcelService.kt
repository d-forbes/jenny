package com.workrise.jenny

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.FileOutputStream

@Service
class ExcelService {
    fun createExcelFileWithUserInput(userInput: String) {
        val templateFilePath = "src/main/resources/templates/standard_hourly.xlsx"

        val newWorkbook = XSSFWorkbook(templateFilePath)
        val sheet = newWorkbook.getSheetAt(0)
        val rowIndex = 0
        val columnIndex = 0
        var row: Row? = sheet.getRow(rowIndex)
        if (row == null) {
            row = sheet.createRow(rowIndex)
        }
        val cell = row?.createCell(columnIndex, CellType.STRING)
        cell?.setCellValue(userInput)
        val outputStream = FileOutputStream("src/main/resources/generated.xlsx")
        newWorkbook.write(outputStream)
        outputStream.close()
        newWorkbook.close()
    }
}
