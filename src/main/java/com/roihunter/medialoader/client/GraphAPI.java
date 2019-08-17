package com.roihunter.medialoader.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.roihunter.medialoader.domain.facebook.FacebookUser;

import feign.Headers;

@FeignClient(value = "graph-api", url = "${services.api.facebook.graph.url}")
@Headers({"Content-type", "application/json"})
public interface GraphAPI {
	
	@GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
	FacebookUser load(@RequestParam(name = "fields") final String[] fields, 
			@RequestParam(name = "access_token") final String accessToken);

}
