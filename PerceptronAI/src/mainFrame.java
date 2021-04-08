import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class mainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtLearningRate;
	private JTextField txtMaxIteration;
	private JPanel panelMain;
	private JLabel lblperformance;
	private boolean flag = false;
	private boolean test = false;

	private int x = 0;
	private int y = 0;
	private int expectedY;
	private ArrayList<DataLine> linesOfData;
	private Perceptron p;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainFrame() {
		linesOfData = new ArrayList<DataLine>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelMain = new JPanel();
		panelMain.setBackground(Color.WHITE);
		panelMain.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	                jPanel1MouseClicked(evt);
	            }
	        });
	
		contentPane.add(panelMain, BorderLayout.CENTER);
		
		JPanel panelSettings = new JPanel();
		panelSettings.setBackground(SystemColor.activeCaption);
		contentPane.add(panelSettings, BorderLayout.EAST);
		panelSettings.setBorder(new EmptyBorder(50, 10, 50, 10));
		GridBagLayout gbl_panelSettings = new GridBagLayout();
		gbl_panelSettings.columnWidths = new int[] {96};
		gbl_panelSettings.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelSettings.columnWeights = new double[]{0.0};
		gbl_panelSettings.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		
		panelSettings.setLayout(gbl_panelSettings);
		
		JLabel lblNewLabel = new JLabel("Learning rate");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panelSettings.add(lblNewLabel, gbc_lblNewLabel);
		
		txtLearningRate = new JTextField();
		GridBagConstraints gbc_txtLearningRate = new GridBagConstraints();
		gbc_txtLearningRate.fill = GridBagConstraints.BOTH;
		gbc_txtLearningRate.insets = new Insets(0, 0, 5, 0);
		gbc_txtLearningRate.gridx = 0;
		gbc_txtLearningRate.gridy = 1;
		panelSettings.add(txtLearningRate, gbc_txtLearningRate);
		txtLearningRate.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Maximum Number of iterations");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panelSettings.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtMaxIteration = new JTextField();
		GridBagConstraints gbc_txtMaxIteration = new GridBagConstraints();
		gbc_txtMaxIteration.insets = new Insets(0, 0, 5, 0);
		gbc_txtMaxIteration.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaxIteration.gridx = 0;
		gbc_txtMaxIteration.gridy = 3;
		panelSettings.add(txtMaxIteration, gbc_txtMaxIteration);
		txtMaxIteration.setColumns(10);
		
		lblperformance = new JLabel("Performance : ");
		lblperformance.setForeground(Color.DARK_GRAY);
		GridBagConstraints gbc_performance = new GridBagConstraints();
		gbc_performance.insets = new Insets(0, 0, 5, 0);
		gbc_performance.gridx = 0;
		gbc_performance.gridy = 8;
		panelSettings.add(lblperformance, gbc_performance);
		
		
		
		JButton learn = new JButton("Learn");
		learn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!lblNewLabel.getText().isBlank() && !lblNewLabel_1.getText().isBlank() && !(linesOfData.size()==0)) {
				Double learningRate = Double.parseDouble(txtLearningRate.getText());
				Integer iterations = Integer.parseInt(txtMaxIteration.getText());
				p = new Perceptron(linesOfData, learningRate, iterations);
				drawLineSeparation();
				test = true;
				double performance = p.performance();
				NumberFormat defaultFormat = NumberFormat.getPercentInstance();
				defaultFormat.setMaximumFractionDigits(1);

				lblperformance.setText("Performance: " + defaultFormat.format(performance));	
				}
			}
		});
	
		GridBagConstraints gbc_learn = new GridBagConstraints();
		gbc_learn.fill = GridBagConstraints.BOTH;
		gbc_learn.insets = new Insets(0, 0, 5, 0);
		gbc_learn.gridx = 0;
		gbc_learn.gridy = 5;
		panelSettings.add(learn, gbc_learn);
		
		JButton clear = new JButton("Clear");
		clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearPanel(evt);
                test = false;
            }
        });
		GridBagConstraints gbc_clear = new GridBagConstraints();
		gbc_clear.fill = GridBagConstraints.BOTH;
		gbc_clear.gridx = 0;
		gbc_clear.gridy = 6;
		panelSettings.add(clear, gbc_clear);
	}
	
	 private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {
	        if (evt.getButton() == MouseEvent.BUTTON1) {
	            x = evt.getX();
	            y = evt.getY();
	            if(!test) {
		            click_left(x, y);
	            }else {
	            	expectedY = p.testNode(x, y);
	            	if(expectedY == -1) {
	            		
	            		drawRect(x, y, -1);
	            	}else if(expectedY == 1) {
	            		drawRect(x,y ,1);
	            	}
	            }
	            
	        }
	        if (evt.getButton() == MouseEvent.BUTTON3) {
	            x = evt.getX();
	            y = evt.getY();
	            if(!test) {
		            click_right(x, y);
	            }
	        }
	    }
	

	private void drawRect(int x, int y, int i) {
		Graphics g = this.getGraphics();
		if(i == -1) {
			g.setColor(Color.RED);
		}else if(i == 1) {
			g.setColor(Color.BLACK);
		}
	        System.out.println("  right " + x + "," + y);

	        g.fillRect(x + 10, y + 31, 7, 6);
	}

	private void clearPanel(java.awt.event.ActionEvent evt) {
	        repaint();
	        lblperformance.setText("Performance :");
	        linesOfData.clear();
	    }
	 
	 private void click_left(int x, int y) {

	        flag = true;
	        DataLine point = new DataLine(x , y , -1);//-1
	        linesOfData.add(point);
	        System.out.println(" left " + point.getX() + "," + point.getY());
	        drawOval();
	    }

	    private void click_right(int x, int y) {
	        DataLine point = new DataLine(x , y , 1);//1
	        DataLine point2 = new DataLine(x + 6 , y , 1);//1

	        linesOfData.add(point);
	        linesOfData.add(point2);

	        System.out.println("  right " + point.getX() + "," + point.getY());
	        flag = false;
	        drawPoly();
	    }
	    
	    private void drawOval() {
	    	    int x = this.x + 10;
		        int y = this.y + 33;

		        Graphics g = this.getGraphics();
		        System.out.println("  right " + x + "," + y);

		        g.setColor(Color.red);
		        g.fillOval(x, y, 7, 6);
	    }
	    
	    private void drawPoly() {
	    	 int x = this.x + 13;
		        int y = this.y + 33;

		        Graphics g = this.getGraphics();
		        System.out.println("  right " + x + "," + y);

		        g.setColor(Color.black);
		        g.fillPolygon(new int[]{x - 3, x, x + 3}, new int[]{y - 3, y + 3, y - 3}, 3);
	    }
	    

		private void drawLineSeparation() {
			Graphics g = panelMain.getGraphics();
		    g.setColor(Color.black);
			p.drawLine(g, panelMain.getWidth() );
		}
		
		@Override
		public void repaint() {
			super.repaint();
			linesOfData.clear();
		}


}
