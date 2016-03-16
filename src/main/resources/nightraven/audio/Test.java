package nightraven.audio;

import java.io.File;

public class Test {

	public static void main(String[] args) {
		
		try(AudioCore audioCore = new AudioCore(); Source source = new Source(); Source source2 = new Source();) {
			audioCore.setListenerData(0, 0, 0);
			//source.setLooping(true);
			
			int buffer = audioCore.loadSound(new File("bounce.wav"));
			source.play(buffer);

			char c = ' ';
			while(c != 'q') {
				c = (char) System.in.read();
				
				if(c == 'p') {
					if(source.isPlaying()) {
						System.out.println("Pausing Sound");
						source.pause();
					} else {
						System.out.println("Playing Sound");
						//source.continuePlaying();
						source.play(buffer);
					}
				}
				
				if(c == 'o') {
					if(source2.isPlaying()) {
						System.out.println("Pausing Sound");
						source2.pause();
					} else {
						System.out.println("Playing Sound");
						source2.play(buffer);
						source2.setPitch((float) (0.9 + (Math.random() / 5)));
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Failed!");
		}
		
		
	}
}
