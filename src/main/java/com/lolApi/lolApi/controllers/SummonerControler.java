package com.lolApi.lolApi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lolApi.lolApi.models.Summoner;

@RestController
@RequestMapping(path = "/summoner")
public class SummonerControler {
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(path = "/{name}")
	public Summoner getSummonerByName(@PathVariable String name) {
		RestTemplate restTemplate = new RestTemplate();
		String summonerURI = "https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=RGAPI-27c3557a-f5de-4e02-8c89-ad30a8b525a3";
		
		Summoner summoner = restTemplate.getForObject(summonerURI, Summoner.class);
		if (summoner != null) {
			ResponseEntity<java.util.List> matches = getMatchesByPuuid(summoner.getPuuid());
			summoner.setMatches(matches.getBody());
			return summoner;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/puuid/{puuid}")
	public ResponseEntity<java.util.List> getMatchesByPuuid (@PathVariable String puuid){
		
		RestTemplate restTemplate = new RestTemplate();
		String matchesURI = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=20&api_key=RGAPI-27c3557a-f5de-4e02-8c89-ad30a8b525a3";
		
		return restTemplate.getForEntity(matchesURI, java.util.List.class);
	}
	
}
