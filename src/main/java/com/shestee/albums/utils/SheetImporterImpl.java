package com.shestee.albums.utils;

import com.shestee.albums.entity.enums.LengthType;
import com.shestee.albums.entity.enums.Medium;
import com.shestee.albums.service.AlbumService;
import com.shestee.albums.service.AlbumServiceImpl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.Iterator;

@Component
public class SheetImporterImpl implements SheetImporter {
    private Connection connection;
    private static String filename = "src/main/resources/sheet/plyty.xlsx";
    private final String user = "sa";
    private final String password = "";

    private AlbumService albumService;

    @Autowired
    public SheetImporterImpl(AlbumService albumService) {
        this.albumService = albumService;
    }

    public void copyFromXclToDB() {
        int batchSize = 20;

        connection = null;
        try {
            long start = System.currentTimeMillis();

            FileInputStream inputStream = new FileInputStream(filename);

            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.iterator();

            connection = DriverManager.getConnection("jdbc:h2:file:./db", user, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO albums (artist, title, medium, length_type, genre, label, catalogue, year, own_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            int count = 0;

            rowIterator.next(); // skip the header row

            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                //Iterator<Cell> cellIterator = nextRow.cellIterator();  --------niepotrzebne w tym układzie

                for (int i=0; i<firstSheet.getLastRowNum(); i++) {
                    String artist;
                    String title;
                    Medium medium;
                    LengthType lengthType;
                    String genre;
                    String label;
                    String catalogue;
                    int year;
                    int ownId;

                    Cell nextCell = nextRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    int columnIndex = nextCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            ownId = (int)nextCell.getNumericCellValue();
                            statement.setInt(9, ownId);
                            break;
                        case 1:
                            artist = nextCell.getStringCellValue();
                            statement.setString(1, artist);
                            break;
                        case 2:
                            title = nextCell.getStringCellValue();
                            statement.setString(2, title);
                            break;
                        case 3:
                            if (nextCell.getStringCellValue().equals("")) {
                                statement.setString(3, Medium.OTHER.toString());
                            } else {
                                medium = Medium.valueOf(nextCell.getStringCellValue().toUpperCase());
                                statement.setString(3, medium.toString());
                            }
                            break;
                        case 4:
                            if (nextCell.getStringCellValue().equals("")) {
                                statement.setString(4, LengthType.OTHER.toString());
                            } else {
                                lengthType = LengthType.valueOf(nextCell.getStringCellValue().toUpperCase());
                                statement.setString(4, lengthType.toString());
                            }
                            break;
                        case 5:
                            genre = nextCell.getStringCellValue();
                            statement.setString(5, genre);
                            break;
                        case 6:
                            label = nextCell.getStringCellValue();
                            statement.setString(6, label);
                            break;
                        case 7:
                            catalogue = nextCell.getStringCellValue();
                            statement.setString(7, catalogue);
                            break;
                        case 8:
                            year = (int) nextCell.getNumericCellValue();
                            statement.setInt(8, year);
                            break;
                    }

                }

                statement.addBatch();

                if (++count % batchSize == 0) {
                    statement.executeBatch();
                }

            }

            workbook.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.printf("Import done in %d ms\n", (end - start));

        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        }
    }


    public void addSongsFromXCLsheet() {
        int batchSize = 20;

        connection = null;
        try {
            long start = System.currentTimeMillis();

            FileInputStream inputStream = new FileInputStream(filename);

            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet firstSheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = firstSheet.iterator();

            connection = DriverManager.getConnection("jdbc:h2:file:./db", user, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO songs (track_number, title, music, lyrics, album_id) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            int count = 0;

            rowIterator.next(); // skip the header row

            while (rowIterator.hasNext()) {
                Row nextRow = rowIterator.next();
                //Iterator<Cell> cellIterator = nextRow.cellIterator();  --------niepotrzebne w tym układzie

                for (int i=0; i<firstSheet.getLastRowNum(); i++) {
                    String trackNumber;
                    String title;
                    String music;
                    String lyrics;
                    int ownId;

                    Cell nextCell = nextRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    int columnIndex = nextCell.getColumnIndex();

                    switch (columnIndex) {
                        case 0:
                            ownId = (int) nextCell.getNumericCellValue();
                            statement.setInt(5, albumService.getIdByOwnId(ownId));
                            break;
                        case 1:
                            trackNumber = nextCell.getStringCellValue();
                            statement.setString(1, trackNumber);
                            break;
                        case 2:
                            title = nextCell.getStringCellValue();
                            statement.setString(2, title);
                            break;
                        case 3:
                            music = nextCell.getStringCellValue();
                            statement.setString(3, music);
                            break;
                        case 4:
                            lyrics = nextCell.getStringCellValue();
                            statement.setString(4, lyrics);
                            break;
                    }

                }

                statement.addBatch();

                if (++count % batchSize == 0) {
                    statement.executeBatch();
                }

            }

            workbook.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

            long end = System.currentTimeMillis();
            System.out.printf("Import done in %d ms\n", (end - start));

        } catch (IOException ex1) {
            System.out.println("Error reading file");
            ex1.printStackTrace();
        } catch (SQLException ex2) {
            System.out.println("Database error");
            ex2.printStackTrace();
        }
    }


}
