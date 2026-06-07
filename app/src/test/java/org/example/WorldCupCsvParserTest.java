package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class WorldCupCsvParserTest {

    @Test
    void parse_shouldReturn48Matches() throws IOException {
        List<WorldCupMatch> data = loadMatches();
        Assertions.assertEquals(48, data.size());
    }

    @Test
    void parse_allFieldsShouldBeNotNull() throws IOException {
        List<WorldCupMatch> data = loadMatches();

        Assertions.assertAll(
                "Alle Felder aller Matches müssen not null sein",
                data.stream().flatMap(match -> Stream.of(
                        () -> assertNotNull(match.group(),
                                "gruppe ist null: " + matchInfo(match)),
                        () -> assertNotNull(match.startTS(),
                                "datum ist null: " + matchInfo(match)),
                        () -> assertNotNull(match.home(),
                                "heim ist null: " + matchInfo(match)),
                        () -> assertNotNull(match.guest(),
                                "gast ist null: " + matchInfo(match)),
                        () -> assertNotNull(match.stadium(),
                                "stadion ist null: " + matchInfo(match)),
                        () -> assertNotNull(match.city(),
                                "stadt ist null: " + matchInfo(match)),
                        () -> assertNotNull(match.country(),
                                "land ist null: " + matchInfo(match))
                ))
        );
    }

    private List<WorldCupMatch> loadMatches() throws IOException {
        return new WorldCupCsvParser().parse(new InputStreamReader(getClass().getResourceAsStream("/input_alle_spiele_nurVorrund.csv")));
    }

    private String matchInfo(WorldCupMatch match) {
        return "Gruppe %s: %s vs %s".formatted(match.group(), match.home(), match.guest());
    }
}