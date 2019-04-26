package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever {

	LRUCache cache;
	OriginalRetriever retriever;

	public CachingProxyRetriever(Storage storage) {
		this.cache = new LRUCache(16);
		this.retriever = new OriginalRetriever(storage);
	}

	public Object retrieve(long id) {
		Object obj = cache.find(id);

		if (obj == null) {
			obj = retriever.retrieve(id);
			cache.set(id, obj);
		}
		return obj;
	}
}
