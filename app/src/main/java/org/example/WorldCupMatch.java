package org.example;

import java.time.LocalDateTime;

public record WorldCupMatch(
        String group,
        LocalDateTime startTS,
        String home,
        String guest,
        String stadium,
        String city,
        String country) {
}
