package com.roihunter.medialoader.domain.facebook;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PictureDetail {
	
    private Integer height;
    private Integer width;
    private String url;
    private String source;

    @JsonProperty("is_silhouette")
    private Boolean silhouette;
    
}
