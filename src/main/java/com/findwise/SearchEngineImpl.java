package com.findwise;

import java.util.*;

public class SearchEngineImpl implements SearchEngine {

    private final Map<String, String> docCorpus;
    private final List<IndexEntry> docEntries;
    private Comparator<IndexEntry> comparator;
    private static final String PUNCTUATIONS = ".,;:";

    public SearchEngineImpl() {
        this.docCorpus = new HashMap<>();
        this.docEntries = new ArrayList<>();
        this.comparator = new ScoresComparator();
    }

    public void setComparator(Comparator<IndexEntry> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void indexDocument(String id, String content) {
        if (!assertValidInput(id) || !assertValidInput(content)) {
            throw new IllegalArgumentException("Provide non empty arguments");
        }
        docCorpus.putIfAbsent(id, format(content));
    }

    @Override
    public List<IndexEntry> search(String term) {
        if (!assertValidInput(term)) {
            throw new IllegalArgumentException("Provide non empty string");
        }
        List<IndexEntry> indexEntryList = computeTfIdfForCorpus(format(term));
        indexEntryList.sort(comparator);

        return indexEntryList;
    }

    private String format(String str) {
        return str.toLowerCase().trim().replaceAll(PUNCTUATIONS, "");
    }

    private List<IndexEntry> computeTfIdfForCorpus(String term) {
        if (getNumOccurrencesInCorpus(term) == 0) {
            return Collections.emptyList();
        }
        docCorpus.forEach((key, value) -> {
            List<String> words = tokenizeDocument(value);
            double termFreq = Collections.frequency(words, term);
            if (termFreq > 0) {
                IndexEntry indexEntry = new IndexEntryImpl();
                indexEntry.setId(key);
                indexEntry.setScore(computeTf(termFreq, words.size()) * computerIdf(term));
                docEntries.add(indexEntry);
            }
        });
        return docEntries;
    }

    private double computerIdf(String term) {
        return Math.log10(docCorpus.size() / getNumOccurrencesInCorpus(term));
    }

    private double computeTf(double termFrequency, double size) {
        return termFrequency / size;
    }

    private double getNumOccurrencesInCorpus(String term) {
        return docCorpus.values().stream()
                        .filter(document -> tokenizeDocument(document).stream().anyMatch(term::equalsIgnoreCase))
                        .count();
    }

    private List<String> tokenizeDocument(String term) {
        return Arrays.asList(term.split("\\s"));
    }

    private boolean assertValidInput(String term) {
        return term != null && !term.isBlank();
    }

}