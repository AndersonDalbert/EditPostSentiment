package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import https_service.ChangeSentimentHttpsRequest;
import response.Response;

public class OldAndNewValuesTest {

	@BeforeEach
	void setUp() throws Exception {
		ChangeSentimentHttpsRequest request = new ChangeSentimentHttpsRequest();
		request.sendPost("neutral");
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
		assertEquals( "neutral", response.getOldValue() );
		assertEquals( "positive", response.getNewValue() );
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
		assertEquals( "neutral", response.getOldValue() );
		assertEquals( "negative", response.getNewValue() );
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
		assertEquals( "add_tag", response.getLastRequestField() );
	}

}
