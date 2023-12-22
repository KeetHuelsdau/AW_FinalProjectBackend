package com.example.aw_finalprojectbackend.stimmung;

import java.util.Optional;

public record StimmungRequestDTO(int rating, Optional <String> kommentar) {
}