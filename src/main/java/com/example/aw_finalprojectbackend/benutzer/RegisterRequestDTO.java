package com.example.aw_finalprojectbackend.benutzer;

public record RegisterRequestDTO(String benutzerName, String passwort1, String passwort2, String vorname, String nachname, int alter, String geschlecht) {}