package search.analyzers;

import java.net.URI;

import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import search.models.Webpage;

public class EnhancedQueryAnalyzer {
	private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;
	private IDictionary<URI, Double> documentTfIdfNorms;
	private IDictionary<URI, IList<String>> documentBodies;

	public EnhancedQueryAnalyzer(ISet<Webpage> webpages) {
		this.documentTfIdfNorms = new ChainedHashDictionary<URI, Double>();
	}

	public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
		return this.documentTfIdfVectors;
	}

	private boolean implementBlackList(IList<String> query, IDictionary<String, Double> words) {
		IList<String> blackList = new DoubleLinkedList<String>();
		boolean blackListWord = false;
		for (String s : query) {
			if (s.contains("-")) {
				if (words.containsKey(s.replace("-", ""))) {
					blackListWord = true;
				} else {
					blackList.add(s);
				}
			}
		}
		for (String s : blackList) {
			query.delete(query.indexOf(s));
		}
		return blackListWord;
	}

	private IList<IList<String>> implementExactPhraseSearch(IList<String> query) {
		IList<IList<String>> exactPhrasesLib = new DoubleLinkedList<IList<String>>();
		IList<String> copyPhrase = new DoubleLinkedList<String>();
		int exactPhrases = 0;
		for (String s : query) {
			boolean startWithParen = (s.charAt(0) == '"');
			boolean endWithParen = (s.charAt(s.length() - 1) == '"');
			boolean openParenthesis = true;
			if (openParenthesis) {
				if (endWithParen) {
					exactPhrasesLib.get(exactPhrases).add(s.replace("\"", ""));
					openParenthesis = false;
					exactPhrases++;
				} else {
					exactPhrasesLib.get(exactPhrases).add(s);
				}
			} else {
				if (startWithParen) {
					copyPhrase.add(s.replace("\"", ""));
					exactPhrasesLib.add(copyPhrase);
					openParenthesis = true;
				}
			}
		}
		return exactPhrasesLib;
	}
}


