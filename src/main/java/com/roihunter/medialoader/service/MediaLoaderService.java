package com.roihunter.medialoader.service;

public interface MediaLoaderService<T> {
	
	T create(final String accessToken);
	
	void delete(final String id);
	
	T get(final String id);

}
