package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WorldCupCsvParser {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT
            .builder()
            .setDelimiter('\t')             // Tab-getrennt!
            .setHeader("Gruppe", "Datum", "Uhrzeit", "Heim", "Gast", "Stadion", "Stadt", "Land")
            .setSkipHeaderRecord(true)
            .setIgnoreEmptyLines(true)
            .setTrim(true)
            .build();

    public List<WorldCupMatch> parse(Path csvPath) throws IOException {
        return parse(Files.newBufferedReader(csvPath, StandardCharsets.UTF_8));
    }

    public List<WorldCupMatch> parse(Reader reader) throws IOException {
        List<WorldCupMatch> matches = new ArrayList<>();

        try (
                CSVParser parser = CSV_FORMAT.parse(reader)
        ) {
            for (CSVRecord record : parser) {
                try {
                    matches.add(toMatch(record));
                } catch (Exception e) {
                    System.err.printf("Fehler in Zeile %d: %s → %s%n",
                            record.getRecordNumber(),
                            record,
                            e.getMessage());
                }
            }
        }

        return matches;
    }

    private static WorldCupMatch toMatch(CSVRecord record) {
        return new WorldCupMatch(
                record.get("Gruppe"),
                LocalDateTime.of(LocalDate.parse(record.get("Datum"), DATE_FORMAT), LocalTime.parse(record.get("Uhrzeit"), TIME_FORMAT)),
                record.get("Heim"),
                record.get("Gast"),
                record.get("Stadion"),
                record.get("Stadt"),
                record.get("Land")
        );
    }
}