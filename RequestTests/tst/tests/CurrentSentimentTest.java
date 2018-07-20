package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import https_service.ChangeSentimentHttpsRequest;
import response.Response;

class CurrentSentimentTest {

	@BeforeEach
	void setUp() throws Exception {
		ChangeSentimentHttpsRequest request = new ChangeSentimentHttpsRequest();
		request.sendPost("neutral");
		Response response = new Response( request.getResponse() );

		assertEquals( "NEUTRAL", response.getSentiment() );
	}

	@Test
	final void testChangeToPositive() {
		ChangeSentimentHttpsRequest changeRequest = new ChangeSentimentHttpsRequest();
		try {
			changeRequest.sendPost("positive");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Response response = new Response( changeRequest.getResponse() );
		assertEquals( "POSITIVE", response.getSentiment() );
	}
	
	@Test
	final void testChangeToNegative() {
		ChangeSentimentHttpsRequest changeRequest = new ChangeSentimentHttpsRequest();
		try {
			changeRequest.sendPost("negative");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Response response = new Response( changeRequest.getResponse() );
		assertEquals( "NEGATIVE", response.getSentiment() );
	}
	
	@Test
	final void testKeepNeutral() {
		ChangeSentimentHttpsRequest changeRequest = new ChangeSentimentHttpsRequest();
		try {
			changeRequest.sendPost("neutral");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Response response = new Response( changeRequest.getResponse() );
		assertEquals( "NEUTRAL", response.getSentiment() );
	}


}
