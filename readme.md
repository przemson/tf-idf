Implements simple search engine for inverted index search, see:  https://en.wikipedia.org/wiki/Tf%E2%80%93idf

###### How it works
The program computes TF-IDF score for a searched term and returns a list of `IndexEntry` items with the calcualted 
score.

For example: <br>

We have 4 documents in the corpus:<br>
`the brown fox jumped over the brown dog` <br>
`the lazy brown dog sat in the corner` <br>
`the red fox bit the lazy dog` <br>
`the lazy BROWN dog sat in the shadow` <br>

When searched for a term 'fox' we'd get: <br>
id: 0, score: 0.03762874945799765 <br>
id: 2, score: 0.043004285094854454

###### Usage:
1) Run from cmd line: java -jar tfidf-search-1.0-SNAPSHOT.jar 'searchTerm' <br> 
   e.g.  java -jar  tfidf-search-1.0-SNAPSHOT.jar fox
2) Run from Runner.main method with 1 argument (search term)

###### Output:
id: 2 - score: 0.043004285094854454 <br> 
id: 0 - score: 0.03762874945799765

###### Test data
A file 'data.txt' in resources folder holds corpus (all documents) data where each line of the line represents a 
single document