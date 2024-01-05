package com.example.SchoolSystem.printer;

import com.example.SchoolSystem.printer.timetable.TimetableExcel;
import com.example.SchoolSystem.school.AppConfig;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.time.DayName;
import com.example.SchoolSystem.school.web.dto.lesson.LessonDto;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Primary
@RequiredArgsConstructor
public class ExcelPrinter implements IPrinter {

    XSSFSheet sheet;
    TimetableExcel timetable;

    private final AppConfig appConfig;


    @Override
    public void print(TimetableExcel timetable) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        this.timetable = timetable;
        sheet = workbook.createSheet("Timetable");

        List<String> schoolClasses = timetable.getAllSchoolClasses();

        AtomicInteger lastRowNumber = new AtomicInteger(1);

        schoolClasses.forEach(schoolClass -> {
            lastRowNumber.set(createSingleClassTimetable(schoolClass, lastRowNumber.get()));
            lastRowNumber.incrementAndGet();
        });


        String currentWorkingDirectory = System.getProperty("user.dir");

        System.out.println("Current Working Directory: " + currentWorkingDirectory);

        FileOutputStream out = new FileOutputStream(
                new File(currentWorkingDirectory + File.separator +
                        String.format("timetable_%s.xlsx", timetable.getId())));

        workbook.write(out);
        out.close();
    }

    private int createSingleClassTimetable(String schoolClass,int initialRowNumber) {
        createInitialSchoolClassRow(initialRowNumber, schoolClass);

        int rowNumber;

        for (rowNumber = initialRowNumber + 1; rowNumber <= initialRowNumber + appConfig.getMaxHoursPerDay(); rowNumber++) {
            int lessonNumber = rowNumber - initialRowNumber - 1;
            createGivenLessonNumberRow(rowNumber,schoolClass,lessonNumber);
        }

        return rowNumber;
    }

    private void createInitialSchoolClassRow(int initialRowNumber, String schoolClass) {
        XSSFRow initialRow = sheet.createRow(initialRowNumber);

        initialRow.createCell(0).setCellValue(schoolClass);
       // CellRangeAddress mergedRegion = new CellRangeAddress(initialRowNumber, initialRowNumber,1, 2);
      //  sheet.addMergedRegion(mergedRegion);
        initialRow.createCell(1).setCellValue(DayName.MONDAY.toString());

      //  mergedRegion = new CellRangeAddress(initialRowNumber, initialRowNumber,3,4);
      //  sheet.addMergedRegion(mergedRegion);
        initialRow.createCell(2).setCellValue(DayName.TUESDAY.toString());

      //  mergedRegion = new CellRangeAddress(initialRowNumber, initialRowNumber,5,6);
      //  sheet.addMergedRegion(mergedRegion);
        initialRow.createCell(3).setCellValue(DayName.WEDNESDAY.toString());

      //  mergedRegion = new CellRangeAddress( initialRowNumber, initialRowNumber,7, 8);
     //   sheet.addMergedRegion(mergedRegion);
        initialRow.createCell(4).setCellValue(DayName.THURSDAY.toString());

     //   mergedRegion = new CellRangeAddress( initialRowNumber, initialRowNumber,9, 10);
        //   sheet.addMergedRegion(mergedRegion);
        initialRow.createCell(5).setCellValue(DayName.FRIDAY.toString());
    }

    private void createGivenLessonNumberRow(int rowNumber, String schoolClass, int number) {
        XSSFRow row = sheet.createRow(rowNumber);

        AtomicInteger i = new AtomicInteger(1);
        Arrays.stream(DayName.values()).sorted().forEach(day -> {
            row.createCell(i.get())
                    .setCellValue(getLessonSubject(timetable.getConcreteLesson(schoolClass, day.toString(), number)));
//            row.createCell(i.get() + 1)
//                    .setCellValue(getLessonTeacher(timetable.getConcreteLesson(schoolClass, day.toString(), number)));
            i.addAndGet(1);
        });

    }

    private String getLessonTeacher(List<LessonDto> lesson) {
        if (lesson.isEmpty())
            return "";
        return lesson.get(0).teacher();
    }

    private String getLessonSubject(List<LessonDto> lesson) {
        if (lesson.isEmpty())
            return "";
        return lesson.get(0).subject();
    }


}
