package util.Document;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;

public class Div extends JPanel implements HTMLElement {
	int[] percentInfo;
	int[] pxInfo;
	
	public class Style implements HTMLStyle<Style, Div> {
		@Override
		public Style size(int widthp, int width, int heightp, int height) {
			percentInfo[0] = widthp;
			percentInfo[1] = heightp;
			pxInfo[0] = width;
			pxInfo[1] = height;
			return this;
		}

		@Override
		public Style position(int xp, int x, int yp, int y) {
			percentInfo[2] = xp;
			percentInfo[3] = yp;
			pxInfo[2] = x;
			pxInfo[3] = y;
			return this;
		}
		
		@Override
		public Style background(Color color) {
			Div.this.setBackground(color);
			return this;
		}

		@Override
		public Div end() {
			// TODO Auto-generated method stub
			return Div.this;
		}
	}
	@Override
	public int[] getPercentInfo() {
		return percentInfo.clone();
	}
	@Override
	public int[] getPxInfo() {
		return pxInfo.clone();
	}
	public Style style;
	public Div(Component... elements) {
		percentInfo = new int[] {0, 0, 0, 0};
		pxInfo = new int[] {0, 0, 0, 0};
		style = new Style();
	}
}
