package com.roihunter.medialoader.service;

import org.apache.tomcat.websocket.AuthenticationException;

public interface IService<T> {
	
	T create(final String accessToken) throws AuthenticationException;
	
	void delete(final String id);
	
	T get(final String id);

}
