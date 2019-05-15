package com.tmq.example.gsheetinteract;

import com.tmq.example.gsheetinteract.util.SheetsMgr;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetsQuickstart {

    public static void main(String... args) throws IOException, GeneralSecurityException {

        SheetsMgr tcUtil = SheetsMgr.getInstance();

        System.out.println("----Print all:");
        System.out.println(tcUtil.toString());
        System.out.println("----Column size:");
        System.out.println(tcUtil.getColumnSize());
        System.out.println("----Row size:");
        System.out.println(tcUtil.getRowSize());
        System.out.println("----All Column name:");
        System.out.println(tcUtil.getColumnNames());
        System.out.println("----Search in `name` column with `textSearch` = `Quy`:");
        System.out.println(tcUtil.searchByColumnNameContains("name", "Quy"));
        System.out.println("----Search in column 1th with `textSearch` = `Quy`:");
        System.out.println(tcUtil.searchByColumnPositionContains(1, "Quy"));


    }
}