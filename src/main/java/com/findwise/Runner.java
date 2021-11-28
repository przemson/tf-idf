package com.findwise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Run with arguments: [term], e.g. fox");
            System.exit(1);
        }

        SearchEngine searchEngine = new SearchEngineImpl();
        List<String> documents;
        try (InputStream inputStream = Runner.class.getResourceAsStream("/data.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            documents = reader.lines().collect(Collectors.toList());
        }

        for (int i = 0; i < documents.size(); i++) {
            searchEngine.indexDocument(String.valueOf(i), documents.get(i));
        }

        List<IndexEntry> foundEntries = searchEngine.search(args[0]);
        if (foundEntries.isEmpty()) {
            System.out.printf("No match was found for entry: %s %n", args[0]);
        }

        for (IndexEntry entry : foundEntries) {
            System.out.printf("id: %s, score: %s %n", entry.getId(), entry.getScore());
        }
    }
}
