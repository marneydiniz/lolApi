package com.lolApi.lolApi.models;

import java.util.List;

import lombok.Data;

@Data
public class Summoner {

	private String id;
	private String accountId;
	private String puuid;
	private String name;
	private int profileIconId;
	private long summonerLevel;
	private long revisionDate;
	
	private List<Match> matches;
	
}
