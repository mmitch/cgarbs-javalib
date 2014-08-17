package de.cgarbs.knitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;

import de.cgarbs.knitter.data.Project;
import de.cgarbs.knitter.render.SVGWriter;

public class Test
{

	public static void main(String[] args)
	{
		try
		{
			System.out.println("test start");

			Project p = new Project();
			new SVGWriter(p).render();
			System.out.println("default rendered");
			
			PDFTranscoder t = new PDFTranscoder();
			TranscoderInput input = new TranscoderInput(new FileInputStream(new File("/tmp/test.svg")));
			TranscoderOutput output = new TranscoderOutput(new FileOutputStream(new File("/tmp/test.pdf")));
			t.transcode(input, output);
			System.out.println("default PDF rendered");
			
			p.setSourceFile("/home/mitch/Dropbox/schnucki/Rainbowdashvornefertig.png");
			p.setTargetFile("/home/mitch/Dropbox/schnucki/Rainbowdashvornefertig.svg");
			p.setMaschen(22);
			p.setReihen(33);
			new SVGWriter(p).render();
			System.out.println("dash front rendered");
			
			p.setSourceFile("/home/mitch/Dropbox/schnucki/Rainbowdashhintenfertig.png");
			p.setTargetFile("/home/mitch/Dropbox/schnucki/Rainbowdashhintenfertig.svg");
			p.setMaschen(22);
			p.setReihen(33);
			new SVGWriter(p).render();
			System.out.println("dash back rendered");

			System.out.println("test end");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (TranscoderException e)
		{
			e.printStackTrace();
		}

	}

}
