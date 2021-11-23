package com.lolApi.lolApi.models;

import java.util.List;

import lombok.Data;

@Data
public class Metadata {
	
	private String matchId;
	private List<String> participants;

}
