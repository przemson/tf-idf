package com.findwise;

class IndexEntryImpl implements IndexEntry, Comparable<IndexEntry> {

    private String id;
    private double score;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public double getScore() {
        return this.score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public int compareTo(IndexEntry o) {
        return Double.compare(o.getScore(), this.getScore());
    }
}

