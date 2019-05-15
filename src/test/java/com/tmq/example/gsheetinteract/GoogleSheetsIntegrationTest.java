package com.tmq.example.gsheetinteract;

import com.tmq.example.gsheetinteract.util.SheetsMgr;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class GoogleSheetsIntegrationTest {

    @Test
    public void readDataFormSheet() throws IOException, GeneralSecurityException {

        SheetsMgr tcUtil = SheetsMgr.getInstance();

        System.out.println(tcUtil.toString());
        System.out.println(tcUtil.getColumnSize());
        System.out.println(tcUtil.getRowSize());
        System.out.println(tcUtil.getColumnNames());
        System.out.println(tcUtil.searchByColumnNameContains("name", "Quy"));
        System.out.println(tcUtil.searchByColumnPositionContains(1, "Quy"));

    }
}