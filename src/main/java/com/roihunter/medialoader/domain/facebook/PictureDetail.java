package com.roihunter.medialoader.domain.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PictureDetail {
	
    private Integer height;
    private Integer width;
    private String url;

    @JsonProperty("is_silhouette")
    private Boolean silhouette;
    
}
