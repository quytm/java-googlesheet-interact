package com.tmq.example.gsheetinteract.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SheetsServiceUtil {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();

        return new Sheets.Builder(transport, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(transport))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}