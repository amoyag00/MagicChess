import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import voiceRecognition.VoiceController;

class VoiceControllerTest {

	@Test
	void testParseCorrect() {
		VoiceController controller=new VoiceController();
		assertTrue(controller.parse("alfa 1 echo 5").equals("1,1,5,5"));
	}
	
	@Test
	void testParseWithNoiseAtBegin() {
		VoiceController controller=new VoiceController();
		assertTrue(controller.parse("hola bueno alfa 1 echo 5").equals("1,1,5,5"));
	}
	
	@Test
	void testParseWithNoiseAtEnd() {
		VoiceController controller=new VoiceController();
		assertTrue(controller.parse("hola bueno hotel 7 eco 6 te toca").equals("8,7,5,6"));
	}
	
	@Test
	void testParseMidWord() {
		VoiceController controller=new VoiceController();
		assertTrue(controller.parse("hola bueno alpha 1 palabras en medio charlie 4 te toca").equals("1,1,3,4"));
	}
	
	@Test
	void testBadOrigin() {
		VoiceController controller=new VoiceController();
		assertTrue(controller.parse("hola bueno alpha queso 1 palabras en medio charlie 4 te toca").equals("notACommand"));
	}
	
	@Test
	void testBadDestination() {
		VoiceController controller=new VoiceController();
		assertTrue(controller.parse("hola bueno alpha 1 palabras en medio charlie marvel malo 4 te toca").equals("notACommand"));
	}

}
