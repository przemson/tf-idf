package com.findwise;

import java.util.Comparator;

class ScoresComparator implements Comparator<IndexEntry> {
    @Override
    public int compare(IndexEntry o1, IndexEntry o2) {
        return Double.compare(o1.getScore(), o2.getScore());
    }
}
