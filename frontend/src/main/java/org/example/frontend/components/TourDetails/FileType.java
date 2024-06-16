package org.example.frontend.components.TourDetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FileType {
    PDF("*.pdf", "PDF files (*.pdf)"),
    JSON("*.json", "JSON files (*.json)");

    private final String extension;
    private final String extensionInfo;
}
