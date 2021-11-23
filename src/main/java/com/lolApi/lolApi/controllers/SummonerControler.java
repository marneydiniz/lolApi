package com.lolApi.lolApi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lolApi.lolApi.models.Match;
import com.lolApi.lolApi.models.Metadata;
import com.lolApi.lolApi.models.Summoner;

@RestController
@RequestMapping(path = "/summoner")
public class SummonerControler {
	
	@Value("${api.key}")
	private String apiKey;
	
	@GetMapping(path = "/{name}")
	public Summoner getSummonerByName(@PathVariable String name) {
		RestTemplate restTemplate = new RestTemplate();
		
		String summonerURI = "https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + apiKey;
		Summoner summoner = restTemplate.getForObject(summonerURI, Summoner.class);
		
		if (summoner != null) {
			List<Match> matches = getMatchesByPuuid(summoner.getPuuid());
			summoner.setMatches(matches);
			
			return summoner;
		} else {
			return null;
		}
		
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/puuid/{puuid}")
	public List<Match> getMatchesByPuuid (@PathVariable String puuid){
		
		RestTemplate restTemplate = new RestTemplate();
		String matchesURI = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=20&api_key=" + apiKey;
		
		return restTemplate.getForObject(matchesURI, java.util.List.class);
	}
	
	@GetMapping("/match/{match-id}")
	public ResponseEntity<Match> getMatchInfoById (@PathVariable Metadata matchId) {
		
		RestTemplate restTemplate = new RestTemplate();
		String matcheURI = "https://americas.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey;
		
		return restTemplate.getForEntity(matcheURI, Match.class);
	}
	
}
