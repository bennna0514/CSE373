package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import search.models.Webpage;

import java.net.URI;
/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {
    // This field must contain the IDF score for every single word in all
    // the documents.
    private IDictionary<String, Double> idfScores;

    // This field must contain the TF-IDF vector for each webpage you were given
    // in the constructor.
    //
    // We will use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

    private IDictionary<URI, Double> normDocument;
    // Feel free to add extra fields and helper methods.

    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.

        this.idfScores = this.computeIdfScores(webpages);
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
        this.normDocument = this.getNorm();
    }

    // Note: this method, strictly speaking, doesn't need to exist. However,
    // we've included it so we can add some unit tests to help verify that your
    // constructor correctly initializes your fields.
    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }

    // Note: these private methods are suggestions or hints on how to structure your
    // code. However, since they're private, you're not obligated to implement exactly
    // these methods: feel free to change or modify these methods however you want. The
    // important thing is that your 'computeRelevance' method ultimately returns the
    // correct answer in an efficient manner.

    /**
     * Return a dictionary mapping every single unique word found
     * in every single document to their IDF score.
     * 
     * 
     */
    private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
        IDictionary<String, Double> temp = new ChainedHashDictionary<String, Double>();       
        IDictionary<String, Integer> wordCount = new ChainedHashDictionary<String, Integer>(); 
        for (Webpage page: pages) {
            //ISet<String> docwords = new ChainedHashSet<String>();
            ISet<String> words = new ChainedHashSet<String>();
            for (String doc: page.getWords()) {
                if (!words.contains(doc)) {
                    words.add(doc);
                    
                    if (!wordCount.containsKey(doc)) {
                        wordCount.put(doc, 1);
                    }
                    else {
                        wordCount.put(doc, wordCount.get(doc) +1);
                    }
                }
                
                /*
                if(!docwords.contains(doc)) {
                    docwords.add(doc);
                }
                if(!store.contains(doc)) {
                    store.add(doc);
                }
                */
                
            }
            //docwordset.put(page, docwords);
        }
 
        System.out.println("IDF 1");
        
        for (KVPair<String, Integer> word  : wordCount) {
            /*
            Double counter = 0.00;
            for(KVPair<Webpage, ISet<String>> web: docwordset) {
                if(web.getValue().contains(pair)) {
                    counter++;
                }
                
            }
            */
            temp.put(word.getKey(), Math.log(pages.size() / (double) word.getValue()));
        }
       
        System.out.println("IDF 2");
        
        return temp;
    }

    


    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) score.
     *
     * The input list represents the words contained within a single document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
        IDictionary<String, Double> store = new ChainedHashDictionary<String, Double>();

        IDictionary<String, Double> calculate = new ChainedHashDictionary<String, Double>();
        
        for (String unique: words) {
            if (store.containsKey(unique)) {
                store.put(unique, store.get(unique) +1.00);
            }
            else {
                store.put(unique, 1.00);
            }
            
        }
        
        for (KVPair<String, Double> pair: store) {
            calculate.put(pair.getKey(), pair.getValue()/words.size());
        }
        
        
        return calculate;
        
    }

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
        
        //computes Tf-IDF vector for every document. Loop over every document
        //attach IDictionary to URI       
        //each term and its corresponding TF-IDF score for a particular document 
        //inside a IDictionary<String, Double>. This dictionary is the TF-IDF vector.
        IDictionary<URI, IDictionary<String, Double>> result = 
                new ChainedHashDictionary<URI, IDictionary<String, Double>>();
        IDictionary<String, Double> store = new ChainedHashDictionary<String, Double>();
        IDictionary<String, Double> calculate = new ChainedHashDictionary<String, Double>();
        IDictionary<String, Double> test = new ChainedHashDictionary<String, Double>();
        
       

        System.out.println("?");

        for (Webpage web: pages) {
            //IDictionary of the IDFs
            store = computeTfScores(web.getWords());
            
            
            //for each word (with a Tf score) check if it there is a corresponding idfscore.
            //and then calculate
            for (KVPair<String, Double> words: store) {
                calculate.put(words.getKey(), words.getValue() * idfScores.get(words.getKey()));
            
                    if (store.containsKey(words.getKey())) {
                        store.put(words.getKey(), calculate.get(words.getKey()));
                        
                    }
                
            }
            
            
            result.put(web.getUri(), store);
        }

        System.out.println("!!");
        
        
        return result;
    }



    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query and the
     * URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
        // Note: The pseudocode we gave you is not very efficient. When implementing,
        // this method, you should:
        //
        // 1. Figure out what information can be precomputed in your constructor.
        //    Add a third field containing that information.
        //
        // 2. See if you can combine or merge one or more loops.
        /*
                documentVector = look up document TF-IDF vector using pageUri
                queryVector = compute query TF-IDF vector

                numerator = 0.0
                foreach word in query:
                    docWordScore = if   documentVector.containsKey(word)
                                   then documentVector.get(word) 
                                   else 0.0
                    queryWordScore = queryVector.get(word)
                    numerator += docWordScore * queryWordScore
                
                denominator = norm(documentVector) * norm(queryVector)

                if   denominator != 0
                then return numerator / denominator
                else return 0.0

                double norm(vector):
                output = 0.0
                foreach pair in vector:
                    score = pair.getValue()
                    output += score * score
                return sqrt(output)
        */
        IDictionary<String, Double> documentVector = documentTfIdfVectors.get(pageUri);
        IDictionary<String, Double> queryVector = computeQuery(query);
        
        //docWordScore & queryWordScore may need to be IDictionaries
        //actually maybe not if they get reset used in numerator and then reset
        Double numerator = 0.0;
        Double denominator = 0.0;
        for (String word: query) {
            Double docWordScore  = 0.0;
            //Double queryWordScore = 0.0;
            if (documentVector.containsKey(word)) {
                docWordScore = documentVector.get(word);
            }
            Double queryWordScore = queryVector.get(word);
            
            numerator += docWordScore * queryWordScore;
            
            
        }
        denominator = this.normDocument.get(pageUri) * norm(queryVector);
        if (denominator != 0) {
            return numerator/denominator;
            
        } 
        else {
        return 0.0;
        }
    }

    private Double norm(IDictionary<String, Double> vector) {
        Double output = 0.0;
        for (KVPair<String, Double> pair: vector) {
            Double score = pair.getValue();
            output += score * score;
            
        }
        return Math.sqrt(output);
    }

    //calculate TF-IDF Vector for query
    //You should not recompute the IDF values: just use the ones you computed earlier.
    private IDictionary<String, Double> computeQuery(IList<String> query) {
        IDictionary<String, Double> store = new ChainedHashDictionary<String, Double>();
        IDictionary<String, Double> calculate = new ChainedHashDictionary<String, Double>();
        
        store = computeTfScores(query);
        
        for (KVPair<String, Double> words: store) {
            if (this.idfScores.containsKey(words.getKey())) {
                calculate.put(words.getKey(), words.getValue() * idfScores.get(words.getKey()));
            }
            else if (!this.idfScores.containsKey(words.getKey())) {
                calculate.put(words.getKey(),  0.00);
            }
            
        }
        for (KVPair<String, Double> changedWords: calculate) {
            if (store.containsKey(changedWords.getKey())) {
                store.put(changedWords.getKey(), changedWords.getValue());
            }
        }
        

        
        return store;
    }

    private IDictionary<URI, Double> getNorm(){
        IDictionary<URI, Double> normDictionary = new ChainedHashDictionary<>();
        for (KVPair<URI, IDictionary<String, Double>> pair : documentTfIdfVectors){
            normDictionary.put(pair.getKey(), norm(pair.getValue()));
        }
        return normDictionary;
    }
}

