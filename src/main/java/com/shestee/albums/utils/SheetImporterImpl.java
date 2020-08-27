package com.shestee.albums.utils;

import com.shestee.albums.config.AuthenticationFacade;
import com.shestee.albums.entity.Album;
import com.shestee.albums.entity.Song;
import com.shestee.albums.entity.User;
import com.shestee.albums.entity.enums.LengthType;
import com.shestee.albums.entity.enums.Medium;

import com.shestee.albums.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class SheetImporterImpl implements SheetImporter {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationFacade authenticationFacade;

    private static String filename = "src/main/resources/sheet/plyty.xlsx";

    public List<Album> getAlbumsFromXclSheet() {
        User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());

        List<Album> albums = new ArrayList<>();

        String artist = "";
        String title = "";
        Medium medium = Medium.OTHER;
        LengthType lengthType = LengthType.OTHER;
        String genre = "";
        String label = "";
        String catalogue = "";
        int year = 0;
        String info = "imp. from file";
        int sheetAlbumId = 0;
        int userId;

        try {
            long start = System.currentTimeMillis();

            FileInputStream inputStream = new FileInputStream(filename);

            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();

            rowIterator.next(); // skip the header row

            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();

                for (int i=0; i<firstSheet.getLastRowNum(); i++) {

                    Cell nextCell = nextRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    int columnIndex = nextCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            sheetAlbumId = (int)nextCell.getNumericCellValue();
                            break;
                        case 1:
                            artist = nextCell.getStringCellValue();
                            break;
                        case 2:
                            title = nextCell.getStringCellValue();
                            break;
                        case 3:
                            if (nextCell.getStringCellValue().equals("")) {
                                medium = Medium.OTHER;
                            } else {
                                medium = Medium.valueOf(nextCell.getStringCellValue().toUpperCase());
                            }
                            break;
                        case 4:
                            if (nextCell.getStringCellValue().equals("")) {
                                lengthType = LengthType.OTHER;
                            } else {
                                lengthType = LengthType.valueOf(nextCell.getStringCellValue().toUpperCase());
                            }
                            break;
                        case 5:
                            genre = nextCell.getStringCellValue();
                            break;
                        case 6:
                            label = nextCell.getStringCellValue();
                            break;
                        case 7:
                            catalogue = nextCell.getStringCellValue();
                            break;
                        case 8:
                            year = (int) nextCell.getNumericCellValue();
                            break;
                    }

                }

                userId = user.getId();
                albums.add(new Album(artist, title, medium, lengthType, genre, label, catalogue, year, info, sheetAlbumId, userId));
            }

            workbook.close();

            long end = System.currentTimeMillis();
            System.out.printf("Import done in %d ms\n", (end - start));

        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        }

        return albums;
    }


    public List<Song> getSongsFromXclSheet() {
        List<Song> songs = new ArrayList<>();

        String trackNumber = "";
        String title = "";
        String music = "";
        String lyrics = "";
        int sheetAlbumId = 0;

        try {
            long start = System.currentTimeMillis();

            FileInputStream inputStream = new FileInputStream(filename);

            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet firstSheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = firstSheet.iterator();

            rowIterator.next(); // skip the header row

            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();

                for (int i=0; i<firstSheet.getLastRowNum(); i++) {


                    Cell nextCell = nextRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    int columnIndex = nextCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            sheetAlbumId = (int) nextCell.getNumericCellValue();
                            break;
                        case 1:
                            trackNumber = nextCell.getStringCellValue();
                            break;
                        case 2:
                            title = nextCell.getStringCellValue();
                            break;
                        case 3:
                            music = nextCell.getStringCellValue();
                            break;
                        case 4:
                            lyrics = nextCell.getStringCellValue();
                            break;
                    }
                }
                songs.add(new Song(trackNumber, title, music, lyrics, sheetAlbumId, 0));
            }

            workbook.close();

            long end = System.currentTimeMillis();
            System.out.printf("Import done in %d ms\n", (end - start));

        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        }

        return songs;
    }
}