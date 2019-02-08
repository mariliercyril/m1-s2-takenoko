package com.raccoon.takenoko.tool;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import org.apache.log4j.spi.LoggingEvent;

import org.junit.jupiter.api.*;

import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * This class allows to complete test of the class {@link PropertiesFileReader}.
 */
public class PropertiesFileReaderTest {

	// Gets the LOGGER of the PropertiesFileReader
	private static final Logger LOGGER = Logger.getLogger(PropertiesFileReader.class);

	// Gets the SINGLE instance of the PropertiesFileReader
	private static final PropertiesFileReader PROPERTIES_FILE_READER = PropertiesFileReader.getInstance();

    // An appender (which should be a mock)
	private Appender mockAppender;

	@BeforeEach
	public void setUp() {

	    // Creates a mock appender
		mockAppender = mock(Appender.class);

		// Adds the mock appender to the LOGGER
		LOGGER.addAppender(mockAppender);
	}

	@AfterEach
	public void cleanUp() {

		// Removes the mock appender from the LOGGER
		LOGGER.removeAppender(mockAppender);
	}

	@Test
	@DisplayName("assert that it equals to NullPointerException when we try to get properties from a file which does not exist")
	public void testGetProperty_whenFileDoesNotExist() {

		// Tries to get property with the name of a file which does not exist
		PROPERTIES_FILE_READER.getStringProperty("fileName", "key", "defaultValue");

		// Captures the return of the LOGGER, which has just been captured
		// when we try to get property with the name of a file which does not exist
		ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
		verify(mockAppender).doAppend(argument.capture());

		assertThrows(NullPointerException.class, () -> argument.getValue().getThrowableInformation().getThrowable().toString());
	}

	@Test
	@DisplayName("assert that it equals to NullPointerException when we try to get properties from a file of which the name is null")
	public void testGetProperty_whenFileNameIsNull() {

		// Tries to get property with the name of a file of which the name is null
		PROPERTIES_FILE_READER.getStringProperty(null, "key", "defaultValue");

		// Captures the return of the LOGGER, which has just been captured
		// when we try to get property with a file name which is null
		ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
		verify(mockAppender).doAppend(argument.capture());

		assertThrows(NullPointerException.class, () -> argument.getValue().getThrowableInformation().getThrowable().toString());
	}

	@Test
	@DisplayName("assert that it is true when we try to get a property which exists")
	public void testGetProperty_whenTryToGetKey() {

		assertTrue((PROPERTIES_FILE_READER.getStringProperty("test", "key", "defaultValue")).equals("value"));
	}

	@Test
	@DisplayName("assert that it is false when we try to get a property which does not exist, 1st case: the default value is not the expected value")
	public void testGetProperty_whenTryToGetTooFirstCase() {

		assertFalse((PROPERTIES_FILE_READER.getStringProperty("test", "too", "defaultValue")).equals("value"));
	}

	@Test
	@DisplayName("assert that it is true when we try to get a property which does not exist, 2nd case: the default value is the expected value")
	public void testGetProperty_whenTryToGetTooSecondCase() {

		assertTrue((PROPERTIES_FILE_READER.getStringProperty("test", "too", "defaultValue")).equals("defaultValue"));
	}

}
