package com.lolApi.lolApi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lolApi.lolApi.models.Match;
import com.lolApi.lolApi.models.Summoner;

@RestController
@RequestMapping(path = "/summoner")
public class SummonerControler {
	
	@Value("${api.key}")
	private String apiKey;
	
	@GetMapping
	public Summoner getSummonerByName(@RequestParam(name = "name") String name) {
		RestTemplate restTemplate = new RestTemplate();
		
		String summonerURI = "https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + name + "?api_key=" + apiKey;
		Summoner summoner = restTemplate.getForObject(summonerURI, Summoner.class);
		
		if (summoner != null) {
			List<Match> summonerMatchList = new ArrayList<Match>();
			summoner.setMatches(summonerMatchList);
			for (String match : getMatchesByPuuid(summoner.getPuuid())) {
				summoner.getMatches().add(getMatchInfoById(match));
			}
			return summoner;
		} else {
			return null;
		}
		
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("/macthesbypuuid={puuid}")
	public List<String> getMatchesByPuuid (@PathVariable String puuid){
		
		RestTemplate restTemplate = new RestTemplate();
		String matchesURI = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=20&api_key=" + apiKey;
		
		return restTemplate.getForObject(matchesURI, java.util.List.class);
	}
	
	@GetMapping("/matchinfobyid={matchId}")
	public Match getMatchInfoById (@PathVariable String matchId) {
		
		RestTemplate restTemplate = new RestTemplate();
		String matcheInfoURI = "https://americas.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey;
		
		return restTemplate.getForObject(matcheInfoURI, Match.class);
	}
	
	@GetMapping("/summonerbypuuid={puuid}")
	public Summoner getSummonerInfoByPuuid (@PathVariable String puuid) {
		
		RestTemplate restTemplate = new RestTemplate();
		String summonerInfoURI = "https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/" + puuid + "?api_key=" + apiKey;
		
		return restTemplate.getForObject(summonerInfoURI, Summoner.class);
	}
	
}
