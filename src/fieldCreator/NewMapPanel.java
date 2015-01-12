package fieldCreator;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import field.Landscape;
import field.Map;
import field.MapUtilities;
import field.Terrain;

// The drawing surface of the map
public class NewMapPanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {	
	public static final int NORMAL = 0, ADD_ROW = 1, RMV_ROW = 2, ADD_COL = 3, RMV_COL = 4;
	
	private Map map;
	private boolean terrainVisible = true, landscapeVisible = true, logicscapeVisible = true;
	private int mode = NORMAL;
	private boolean gridVisible = false;
	private Rectangle visibleArea;
	
	private double magnification = 1;
	private int gridLength = (int) (40 * magnification);


	private MouseEvent moved = null;
	private KeyEvent keyPressed = null;
	private JScrollPane scroll;

    // Constructor
	public NewMapPanel (Map map, JScrollPane scroll) {
    	this.map = map;
    	this.scroll = scroll;
    	
    	// Set the layout
    	setBackground(Color.white);
        
        // Set the panel properties
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
    }
	
	public void toggleTerrain() {
		terrainVisible = !terrainVisible;
	}
	
	public void toggleLandscape() {
		landscapeVisible = !landscapeVisible;
	}
	
	public void toggleLogicscape() {
		logicscapeVisible = !logicscapeVisible;
	}
	
	public void toggleGrid() {
		gridVisible = !gridVisible;
	}

	// Retrieve the map
	public Map getMap() {
		return map;
	}
	
	public void replaceMap(Map map) {
		this.map = map;
		
		// Resize the panel
		setPreferredSize(new Dimension(map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
		
		revalidate();
	}

	@Override
    public void paintComponent (Graphics g) { // displays the map on the screen
        super.paintComponent(g);
        
    	// Calculate the area visible in the scroll pane
        visibleArea = new Rectangle(scroll.getViewport().getViewPosition().x, scroll.getViewport().getViewPosition().y,
    			scroll.getViewport().getWidth(), scroll.getViewport().getHeight());
        
        Graphics2D g2 = (Graphics2D) g;
        Font normal = g2.getFont();
        
        // Scale the Graphics object to the current magnification
        g2.scale(magnification, magnification);
        
        if (terrainVisible) {
			// Draw the terrain
        	for (int row = visibleArea.y/gridLength; row < (visibleArea.y + visibleArea.height)/gridLength + 1; row++)
    			for (int col = visibleArea.x/gridLength; col < (visibleArea.x + visibleArea.width)/gridLength + 1; col++) {
    				// Prevent drawing of areas that are out of bounds
    				if (row > -1 && row < getMap().getTerrain().length && col > -1 && col < getMap().getTerrain()[0].length) {
    					g2.drawImage(map.getTerrain()[row][col].getImage(),
    							col * 40, row * 40, null);
    				}
				}
		}
        
        if (landscapeVisible) {
        	// Draw the landscape
        	for (int row = visibleArea.y/gridLength - 5; row < (visibleArea.y + visibleArea.height)/gridLength + 5; row++)
    			for (int col = visibleArea.x/gridLength - 1; col < (visibleArea.x + visibleArea.width)/gridLength + 5; col++) {
    				// Prevent drawing of areas that are out of bounds
    				if (row > -1 && row < getMap().getTerrain().length && col > -1 && col < getMap().getTerrain()[0].length) {
    					if (map.getLandscape()[row][col] != null)
    						g2.drawImage(map.getLandscape()[row][col].getImage(), 
    								col*40 - map.getLandscape()[row][col].getOffset().x, 
    								row*40 - map.getLandscape()[row][col].getOffset().y, null);
    				}
        		}
        }

        if (logicscapeVisible) {
        	// Draw the logicscape
        	for (int row = visibleArea.y/gridLength; row < (visibleArea.y + visibleArea.height)/gridLength + 1; row++)
        		for (int col = visibleArea.x/gridLength; col < (visibleArea.x + visibleArea.width)/gridLength + 1; col++) {
        			// Prevent drawing of areas that are out of bounds
        			if (row > -1 && row < getMap().getTerrain().length && col > -1 && col < getMap().getTerrain()[0].length) {
        				// Color in the logicscape
        				if (map.getLogicscape()[row][col] == '1') {
        					g2.setColor(new Color(255, 0, 0, 100)); // Transparent red
        					g2.fillRect(col*40, row*40, 40, 40);
        				} else if (map.getLogicscape()[row][col] == ' ') {
        					// Do nothing
        				} else { // Change the highlight colour and print the character
        					g2.setColor(new Color(0, 0, 255, 100));
        					g2.fillRect(col*40, row*40, 40, 40);
        					g2.setColor(Color.yellow);
        					g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        					g2.drawString("" + map.getLogicscape()[row][col], col*40+15, row*40+25);
        				}
        			}
        		}
        }
        
        g2.setColor(Color.white);
        g2.setFont(normal);
        
        if (gridVisible) {
        	for (int row = visibleArea.y/gridLength; row < (visibleArea.y + visibleArea.height)/gridLength + 1; row++)
        		for (int col = visibleArea.x/gridLength; col < (visibleArea.x + visibleArea.width)/gridLength + 1; col++) {
        			g2.drawLine(col*40, row*40, col*40, visibleArea.width);
        			g2.drawLine(col*40, row*40, visibleArea.height, row*40);
        			g2.drawString(row + "", col*40 + 2, row*40 + 14);
        			g2.drawString(col + "", col*40 + 2, row*40 + 34);
        		}
        }
        
        if (keyPressed != null && moved.isControlDown()) { // Preview landscape
        	// Calculate row and column
			int row = moved.getY() / gridLength;
			int col = moved.getX() / gridLength;
			
			Landscape l = MapUtilities.convertLandscape(convertToChar(keyPressed));
			
			if (l != null) { // Null pointer check
				g2.drawImage(l.getImage(), col * 40 - l.getOffset().x, row * 40
						- l.getOffset().y, null);
			}
		} else if (keyPressed != null && 
				Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) { // Preview landscape
			// Calculate row and column
			int row = moved.getY() / gridLength;
			int col = moved.getX() / gridLength;

			Landscape l = MapUtilities.convertLandscape(keyPressed.getKeyChar());

			if (l != null) { // Null pointer check
				g2.drawImage(l.getImage(), col * 40 - l.getOffset().x, row * 40
						- l.getOffset().y, null);
			}
		} else if (keyPressed != null) { // Preview terrain
			// Calculate row and column
			int row = moved.getY() / gridLength;
			int col = moved.getX() / gridLength;

			Terrain t = MapUtilities.convertTerrain(keyPressed.getKeyChar());
			g2.drawImage(t.getImage(), col*40, 
					row*40, null);
		}
    }
	
    public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
    
	//////////////////////////////////////////////////////////////////////////////////
	// KeyListener methods

	// Forwards the KeyEvent to the buttons
	public KeyEvent getKeyPressed() {
    	return keyPressed;
    }
	
	@Override
	public void keyPressed(KeyEvent e) {
		// Check num lock to activate alternate set of keys
		if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK)) {
			e.setKeyChar((char) (e.getKeyChar() + 127));
		} else if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
			e.setKeyChar((char) (e.getKeyChar() + 383));
		}
		
		if (mode == ADD_ROW && e.getKeyCode() == KeyEvent.VK_END) {
			map.addRow(map.getTerrain().length);
			setPreferredSize(new Dimension(map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
			revalidate();
		} else if (mode == ADD_COL && e.getKeyCode() == KeyEvent.VK_END) {
			map.addColumn(map.getTerrain()[0].length);
			setPreferredSize(new Dimension(map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
			revalidate();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			mode = NORMAL;
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			if (magnification < 0.99) {
				magnification += 0.1;
				gridLength = (int) (40 * magnification);
				setPreferredSize(new Dimension(
		        		map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
				revalidate();
				repaint();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			if (magnification > 0.11) {
				magnification -= 0.1;
				gridLength = (int) (40 * magnification);
				setPreferredSize(new Dimension(
		        		map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
				revalidate();
				repaint();
			}
		} else if (e.getKeyCode() != KeyEvent.VK_CONTROL && e.getKeyCode() != KeyEvent.VK_SHIFT
				&& e.getKeyCode() != KeyEvent.VK_ALT && e.getKeyCode() != KeyEvent.VK_NUM_LOCK
				&& e.getKeyCode() != KeyEvent.VK_CAPS_LOCK)
			keyPressed = e;
	}

	@Override
	// Remove the event once the key has been released
	public void keyReleased(KeyEvent e) {
		keyPressed = null;
		repaint(); // Gets rid of the floating preview
	}

	@Override
	// Do nothing
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		int row, col;
		// Get mouse position
		Point p = getMousePosition();
		
		// Don't do anything if out of bounds
		if (p == null)
			return;
		
		// Calculate row and column
		row = p.y / gridLength;
		col = p.x / gridLength;
		
		// Exit method if the coordinates aren't on the map
		if (row >= map.getTerrain().length || col >= map.getTerrain()[0].length)
			return;
		
		if (keyPressed != null && getMousePosition() != null) {
			// Figure out the correct object correspondence
			if (e.isControlDown()) { // Landscape
				char key = convertToChar(keyPressed);
				map.add(MapUtilities.convertLandscape(key),
						row, col);
			} else if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) { // Still Landscape
				map.add(MapUtilities.convertLandscape(keyPressed.getKeyChar()), row, col);
			} else if (e.isAltDown()) { // Event
				map.add(keyPressed.getKeyChar(), row, col);
			} else { // Terrain
				map.add(MapUtilities.convertTerrain(keyPressed.getKeyChar()),
						row, col);
			}
			repaint();
		}
	}

	@Override
	// Sets the location for previewing the selected tile
	public void mouseMoved(MouseEvent e) {
		moved = e;
		if (keyPressed != null)
			repaint();
	}

	@Override
	// Store the letter in the corresponding array based on mouse position
	public void mouseClicked(MouseEvent e) {
		int row, col;
		// Get mouse position
		Point p = getMousePosition();
		
		// Don't do anything if out of bounds
		if (p == null)
			return;
		
		// Calculate row and column
		row = p.y / gridLength;
		col = p.x / gridLength;
		
		// Exit method if the coordinates aren't on the map
		if (row >= map.getTerrain().length || col >= map.getTerrain()[0].length)
			return;
		
		// Check for adding/removing things first
		if (mode == ADD_ROW) {
			map.addRow(p.y / gridLength);
			setPreferredSize(new Dimension(map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
			repaint();
		} else if (mode == RMV_ROW) {
			map.removeRow(p.y / gridLength);
			setPreferredSize(new Dimension(map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
			repaint();
		} else if (mode == ADD_COL) {
			map.addColumn(p.x / gridLength);
			setPreferredSize(new Dimension(map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
			repaint();
		} else if (mode == RMV_COL) {
			map.removeColumn(p.x / gridLength);
			setPreferredSize(new Dimension(map.getTerrain()[0].length*gridLength, map.getTerrain().length*gridLength));
			repaint();
		} else if (keyPressed != null) {
			// Figure out the correct object correspondence
			if (e.isControlDown()) { // Landscape
				char key = convertToChar(keyPressed);
				map.add(MapUtilities.convertLandscape(key), row, col);
			} else if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) { // Still Landscape
				map.add(MapUtilities.convertLandscape(keyPressed.getKeyChar()), row, col);
			} else if (e.isAltDown()) { // Event
				map.add(keyPressed.getKeyChar(), row, col);
			} else { // Terrain
				map.add(MapUtilities.convertTerrain(keyPressed.getKeyChar()), row, col);
			}

			repaint();
		}
	}

	/**
	 * Recovers the character denoted by the pressed key when the
	 * CTRL key has been held down.
	 * 
	 * @param keyPressed - the KeyEvent to convert
	 * @return the properly converted character
	 */
	private char convertToChar(KeyEvent keyPressed) {
		if (keyPressed.getKeyCode() == KeyEvent.VK_OPEN_BRACKET)
			return '[';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET)
			return ']';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_BACK_SLASH)
			return '\\';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_P)
			return 'p';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_O)
			return 'o';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_L)
			return 'l';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_K)
			return 'k';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_Q)
			return 'q';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_W)
			return 'w';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_E)
			return 'e';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_A)
			return 'a';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_S)
			return 's';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_D)
			return 'd';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_Z)
			return 'z';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_X)
			return 'x';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_C)
			return 'c';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_F)
			return 'f';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_G)
			return 'g';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_R)
			return 'r';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_T)
			return 't';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_V)
			return 'v';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_B)
			return 'b';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_N)
			return 'n';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_M)
			return 'm';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_Y)
			return 'y';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_U)
			return 'u';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_H)
			return 'h';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_J)
			return 'j';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_6)
			return '6';
		else if (keyPressed.getKeyCode() == KeyEvent.VK_MINUS)
			return '-';
		
		// Default return
		return keyPressed.getKeyChar();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	
}
