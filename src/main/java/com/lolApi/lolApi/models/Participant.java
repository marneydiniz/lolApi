package com.lolApi.lolApi.models;

import lombok.Data;

@Data
public class Participant {
	
	private int assists;
	private int champLevel;
	private int championId;
	private String championName;
	private int kills;
	private int deaths;

}
