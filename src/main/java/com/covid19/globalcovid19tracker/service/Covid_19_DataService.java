package com.covid19.globalcovid19tracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.covid19.globalcovid19tracker.models.LocationStats;

@Service
public class Covid_19_DataService {

	private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

	private List<LocationStats> allStats = new ArrayList<LocationStats>();

	public List<LocationStats> getAllStats() {
		return allStats;
	}

	@PostConstruct
	@Scheduled(cron = "* * * * * *")
	public void fetchCovidData() throws IOException, InterruptedException {

		List<LocationStats> newStats = new ArrayList<LocationStats>();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(COVID_DATA_URL)).build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

		StringReader in = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			LocationStats locationStats = new LocationStats();
			locationStats.setState(record.get("Province/State"));
			locationStats.setCountry(record.get("Country/Region"));
			locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));
			locationStats.setDiffFromPreviousDay(Integer.parseInt(record.get(record.size() - 1))-Integer.parseInt(record.get(record.size() - 2)));
			newStats.add(locationStats);
		}
		
		this.allStats=newStats;
	}

}
