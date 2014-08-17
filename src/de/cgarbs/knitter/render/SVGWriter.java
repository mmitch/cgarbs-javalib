package de.cgarbs.knitter.render;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.IOException;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import de.cgarbs.knitter.data.Project;

/**
 * SVG rendering backend
 * 
 * @author mitch
 *
 */
public class SVGWriter extends AbstractRenderer
{
	
	public SVGWriter(Project project) throws IOException
	{
		super(project);
	}

	@Override
	/**
	 * renders the whole project to a file in SVG format
	 * @param p
	 */
	public void render()
	{
		try
		{
			// RENDER WHOLE PAGE
			{ 
				SVGGraphics2D svg = renderPage(0, 0, r.getWidth(), r.getHeight(), 0, 0);
	
				// write SVG to target file
				svg.stream(p.getTargetFile(), true);
			}
			
			// RENDER MULTIPAGE
			{
				String filename = p.getTargetFile();
				filename = filename.replace(".svg", "");
				
				ensurePortrait();
				
				// DIN A aspect ratio - landscape format
				double pageAspect  = 210d / 297d; // landscape
				double pixelAspect = (double) p.getScaleX() / (double) p.getScaleY(); 
				
				int pageHeight = (int) Math.floor(r.getWidth() * pageAspect * pixelAspect);
				// calculate height of last page
				int lastPageHeight = r.getHeight() % pageHeight;
				while (lastPageHeight > 0 && lastPageHeight < pageHeight * 0.1)
				{
					// last page less than 10% filled, rescale to get rid of it
					// this could be calculated, but I don't want to think of a formula, just use a loop :-)
					pageHeight++;
					lastPageHeight = r.getHeight() % pageHeight;
				}

				int pageNo = 1;
				int height = pageHeight;
				for (int y=0; y<r.getHeight(); y+=pageHeight, pageNo++)
				{
					if (y + height > r.getHeight())
					{
						height = r.getHeight() - y;
					}
					
					SVGGraphics2D svg = renderPage(0, y, r.getWidth(), height, 0, r.getHeight() - pageHeight - y);
					
					svg.stream(filename + "." + pageNo + ".svg", true);
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * renders a single page
	 * 
	 * @param X x start position to render
	 * @param Y y start position to render
	 * @param W width to render
	 * @param H height to render
	 * @param C columns offset of rendered page
	 * @param R row offset of rendered page
	 * @return rendered page
	 */
	private SVGGraphics2D renderPage(int X, int Y, int W, int H, int C, int R)
	{
		// FIXME: C and R really needed?
		
		// init variables
		int[] rgb = new int[4];
		SVGGraphics2D svg = initSVG();
		
		// cache values
		int SCALE_X = p.getScaleX();
		int SCALE_Y = p.getScaleY();
		int OFFSET  = p.getOffset();
		int MAX_XT = W * SCALE_X;
		int MAX_YT = H * SCALE_Y;
		int GRIDTEXTMOD = p.getGridTextMod();
		Color TEXTCOLOR = p.getTextColor();
		Color GRIDCOLOR = p.getGridColor();

		// render pixels into squares
		svg.setStroke(new BasicStroke(p.getGridWidthSmall()));
		for (int ys=Y, yt=0; ys<Y+H; ys++, yt+=SCALE_Y)
		{
			// OPTIMIZATION: use only one block for adjacent pixels with the same color
			int lastXt = 0;
			Color lastColor = null;
			for (int xs=X, xt=0; xs<X+W; xs++, xt+=SCALE_X)
			{
				rgb = r.getPixel(xs, ys, rgb);
				Color newColor = new Color(rgb[0], rgb[1], rgb[2]);
				if (newColor.equals(lastColor))
				{
					// just stack this for larger blocks
				}
				else
				{
					if (lastColor != null)
					{
						svg.setPaint(lastColor);
						svg.fill(new Rectangle( lastXt, yt, xt - lastXt, SCALE_Y));
					}
					lastColor = newColor;
					lastXt = xt;
				}
			}
			svg.setPaint(lastColor);
			svg.fill(new Rectangle( lastXt, yt, MAX_XT - lastXt, SCALE_Y));
		}
		
		// thin grid
		svg.setPaint(GRIDCOLOR);
		for (int xs=X, xt=0, col=C+W; xs<X+W; xs++, xt+=SCALE_X, col--)
		{
			if (col % GRIDTEXTMOD != 0)
			{
				svg.drawLine(xt, 0, xt, MAX_YT);
			}
		}
		for (int ys=Y, yt=0, row=R+H+1; ys<Y+H; ys++, yt+=SCALE_Y, row--)
		{
			if (row % GRIDTEXTMOD != 1)
			{
				svg.drawLine(0, yt, MAX_XT, yt);
			}
		}
		svg.drawLine(MAX_XT, 0, MAX_XT, MAX_YT);
		svg.drawLine(0, MAX_YT, MAX_XT, MAX_YT);

		// thick grid
		svg.setStroke(new BasicStroke(p.getGridWidthBig(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
		for (int xs=X, xt=0, col=C+W; xs<X+W; xs++, xt+=SCALE_X, col--)
		{
			if (col % GRIDTEXTMOD == 0)
			{
				svg.drawLine(xt, 0, xt, MAX_YT);
			}
		}
		for (int ys=Y, yt=0, row=R+H+1; ys<Y+H; ys++, yt+=SCALE_Y, row--)
		{
			if (row % GRIDTEXTMOD == 1)
			{
				svg.drawLine(0, yt, MAX_XT, yt);
			}
		}

		// texts
		svg.setFont(new Font(p.getFontName(), Font.PLAIN, SCALE_Y - OFFSET * 2));
		svg.setPaint(TEXTCOLOR);
					
		for (int xs=X, xt=0, col=C+W; xs<X+W; xs++, xt+=SCALE_X, col--)
		{
			if (col % GRIDTEXTMOD == 0)
			{
				svg.drawString(String.valueOf(col), xt + OFFSET, MAX_YT  - OFFSET);
				svg.drawString(String.valueOf(col), xt + OFFSET, SCALE_Y - OFFSET);
			}
		}			
		for (int ys=Y, yt=0, row=R+H+1; ys<Y+H; ys++, yt+=SCALE_Y, row--)
		{
			if (row % GRIDTEXTMOD == 0)
			{
				svg.setPaint(TEXTCOLOR);
				svg.drawString(String.valueOf(row), OFFSET, 				   yt - OFFSET);
				svg.drawString(String.valueOf(row), MAX_XT - SCALE_X + OFFSET, yt - OFFSET);
			}
		}
		
		return svg;
	}

	private SVGGraphics2D initSVG()
	{
		// Get a DOMImplementation.
		DOMImplementation domImpl = GenericDOMImplementation
				.getDOMImplementation();

		// Create an instance of org.w3c.dom.Document.
		String svgNS = "http://www.w3.org/2000/svg";
		Document document = domImpl.createDocument(svgNS, "svg", null);

		// Create an instance of the SVG Generator.
		return new SVGGraphics2D(document);
	}
}
