package com.tmq.example.gsheetinteract.util;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SheetsMgr {

    private static final String SPREADSHEET_ID = "1imhCOr1yu_4Kc3gr6bGEhADX80MpWAS2bwHfRb66g1M";
    // Time out is 5 minutes
    private static final int TIME_OUT = 5 * 60 * 1000;

    private static volatile SheetsMgr instance = null;
    private static volatile Long lastUpdate = System.currentTimeMillis();

    public static SheetsMgr getInstance() throws IOException, GeneralSecurityException {
        long current = System.currentTimeMillis();
        if (instance == null || current - lastUpdate > TIME_OUT) {
            synchronized(SheetsMgr.class) {
                if (instance == null || current - lastUpdate > TIME_OUT) {
                    instance = new SheetsMgr();
                    lastUpdate = current;
                }
            }
        }

        return instance;
    }

    private SheetsMgr() throws IOException, GeneralSecurityException {
        Sheets sheetsService = SheetsServiceUtil.getSheetsService();
        Long t1 = System.currentTimeMillis();
        this.vrColumns = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, "Sheet1!A:D")
                .setMajorDimension("COLUMNS")
                .execute();
        Long t2 = System.currentTimeMillis();

        this.vrRows = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, "Sheet1!A:D")
                .setMajorDimension("ROWS")
                .execute();

        System.out.println("Time load = " + (t2 - t1) + " (ms)");

        this.columnNames = vrRows.getValues().get(0);
    }

    // -----------------------------------------------------------------------------------------------------------------

    private ValueRange vrColumns;
    private ValueRange vrRows;
    private List<Object> columnNames;

    public String toString() {
        List<List<Object>> values = vrRows.getValues();

        StringBuilder result = new StringBuilder();
        for (List<Object> cells : values) {
            result.append(cells.toString()).append("\n");
        }

        return result.toString();
    }

    public int getColumnSize() {
        return vrColumns.getValues().size();
    }

    public int getRowSize() {
        return vrRows.getValues().size();
    }

    public List<Object> getColumnNames() {
        return vrRows.getValues().get(0);
    }

    public List<Object> searchByColumnNameContains(String columnName, String textSearch) {
        int columnSize = getColumnSize();

        int columnPosition = -1;
        for (int i = 0; i < columnSize; i++) {
            if (columnNames.get(i).equals(columnName)) {
                columnPosition = i;
            }
        }

        return searchByColumnPositionContains(columnPosition, textSearch);
    }


    /**
     * @param columnPosition value in [0 .. rowSize-1]
     */
    public List<Object> searchByColumnPositionContains(int columnPosition, String textSearch) {
        int columnSize = getColumnSize();

        if (columnPosition <0 || columnPosition >= columnSize) return new ArrayList<Object>();

        List<Object> result = new LinkedList<Object>();
        List<Object> dataInColumns = vrColumns.getValues().get(columnPosition);
        for (int i = 1; i < dataInColumns.size(); i++) {
            if (dataInColumns.get(i).toString().contains(textSearch)) {
                result.add(vrRows.getValues().get(i));
            }
        }

        return result;
    }
}
