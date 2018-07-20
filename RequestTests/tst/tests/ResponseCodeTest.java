package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import https_service.ChangeSentimentHttpsRequest;

class ResponseCodeTest {

	@BeforeEach
	void setUp() throws Exception {
		try {
			ChangeSentimentHttpsRequest request = new ChangeSentimentHttpsRequest();
			request.sendPost("neutral");
			assertEquals(200, request.getResponseCode());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	final void testChangeToPositive() {
		try {
			ChangeSentimentHttpsRequest changeRequest = new ChangeSentimentHttpsRequest();
			changeRequest.sendPost("positive");
			assertEquals(200, changeRequest.getResponseCode());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	final void testChangeToNegative() {
		try {
			ChangeSentimentHttpsRequest request = new ChangeSentimentHttpsRequest();
			request.sendPost("negative");
			assertEquals(200, request.getResponseCode());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	final void testKeepNeutral() {
		try {
			ChangeSentimentHttpsRequest request = new ChangeSentimentHttpsRequest();
			request.sendPost("neutral");
			assertEquals(200, request.getResponseCode());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
