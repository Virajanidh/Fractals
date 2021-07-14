
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;


@SuppressWarnings("serial")
public class Fractal extends JPanel { // inherit JPanel 
	
	private double re_start,re_end,im_start,im_end;
	private int iter;
	private int size=800; // size fixed for 800x800 pixels
	private boolean select; //mark Mandelbrot values or Julia values
	protected boolean validity;
	Set mb=null;

	
	//constructors
	
	//for Mandelbrot
	public Fractal(double re_start,double re_end,double im_start,double im_end,int iter) {
		select=true;
		this.re_start=re_start;
		this.re_end=re_end;
		this.im_start=im_start;
		this.im_end=im_end;
		this.iter=iter;
		
		 //passing arguments to the constructor in Set class
		mb = new Set(re_start,re_end,im_start,im_end);
		// set the panel size 
		setPreferredSize(new Dimension(size,size));
		
	}
	
	//for Julia set
	public Fractal(double xc,double yc,int iter) {
		select=false;
		this.re_start=xc;
		this.im_start=yc;
		this.iter=iter;
		
		 //passing arguments to the constructor in Set class
		mb = new Set(re_start,im_start);
		// set the panel size 
		setPreferredSize(new Dimension(size, size));
		
	}
	
	private static void printPoint(Graphics2D frame, Color c, double x,double y) {

		frame.setColor(c); 
		frame.draw(new Line2D.Double(x,y,x,y)); // draw a line in double coordinates
		
	}
	
	public void paintComponent(Graphics g) {
		
		// call paintComponent from parent class 
		super.paintComponent(g); 
		
		for(int i=0 ; i<=size ; i++) {
			for(int j =0 ; j<=size ; j++) {
				Color color=null;
				if(select) {
				
					mb.interestR(i,j,size);//create coordinates in the region of interest for Mandelbrot set
					//check whether they are in Mandelbrot set
					this.validity= mb.checkValidity(mb.getX(),mb.getY(),iter,select); 
					//get the color for values not in Mandelbrot set
					color = Color.getHSBColor((((float)mb.getIter()%255.0f)/255.0f),1.0f,0.9f);
					
				}
				else {
					
					mb.interestR(i,j,size);//create coordinates in the region of interest for Julia set
					//check whether they are in Julia set
					this.validity = mb.checkValidity(mb.getX(),mb.getY(),iter,select);
					//get the color for values not in Julia set
					color = Color.getHSBColor((((float)mb.getIter())%255.0f)/255.0f,1.0f,1.0f);
					
				}	
			
				//plot point with colors
				if(validity){
					 
					printPoint((Graphics2D)g,Color.black,i,j); //if point in the given set, they are black
				}	 
				else{
				
					printPoint((Graphics2D)g,color,i,j);//points which are not in the given set
				}
			
			}
		}
	}
	
	//if the given arguments are not enough or in wrong order
	public static void errorMessage() {
		System.out.println("use: \nfor Mandelbrot set < Mandelbrot re_start re_end im_start im_end iterations >");
		System.out.println("for Julia set < Julia xc yc iterations >");
	}
	//if iterations are negative value
	public static void errorIter() {
		System.out.println("Error:Number of Iterations must be positive value");
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		
		double xc=-0.4,yc=0.6,re_start=-1,re_end=1,im_start=-1,im_end=1; //initialize the variables
		int iter=1000; //number of iterations
		JFrame frame=null; 
		try{
			//if no arguments
			if(args.length==0) { 
			errorMessage();
			}
			//if wrong type inputs are added
			else if((!"Mandelbrot".equals(args[0]))&&(!"Julia".equals(args[0]))) { 
				System.out.println("Wrong input set name");
			}
			//if the input format is incorrect for Mandelbrot set
			else if((args[0].equals("Mandelbrot"))&&(args.length!=1)&&(args.length!=5)&&(args.length!=6)) {
				errorMessage();
			}
			//if the input format is incorrect for Julia set
			else if((args[0].equals("Julia"))&&(args.length!=1)&&(args.length!=4)&&(args.length!=3)) {
				errorMessage();
			}
		
			else if (args.length>0){
			
				//for Mandelbrot set
				if(args[0].equals("Mandelbrot")) {
				
					if((args.length==6)||(args.length==5)) { //if arguments for region of interest are found
					
						//all arguments are passed as Strings.below converted them into needed data type
						re_start = Double.parseDouble(args[1]); //left bound of real part of region of interest
						re_end   = Double.parseDouble(args[2]); //right bound of real part of region of interest
						im_start = Double.parseDouble(args[3]); //lower bound of real part of region of interest
						im_end = Double.parseDouble(args[4]);   //upper bound of real part of region of interest
						if(args.length>5) { //if preferred number of iterations are added
							iter = Integer.parseInt(args[5]);
							if(iter<0) {
								errorIter(); 
								System.exit(0); // Terminate JVM   
							}
						}
		
					}
		
					frame = new JFrame("Mandelbrot Set");  // set the title of the frame   
					frame.setContentPane(new Fractal(re_start,re_end,im_start,im_end,iter));// set the content of the frame
				
				}
				//For julia set
				else if(args[0].equals("Julia")) {
					if((args.length==3)||(args.length==4)){
						//for Julia set C is constant xc is real part and yc is imaginary part of C
						xc  = Double.parseDouble(args[1]); 
						yc = Double.parseDouble(args[2]);
						if(args.length>3) { //if preferred number of iterations are added
							iter  = Integer.parseInt(args[3]);
							if(iter<0) {
								errorIter(); 
								System.exit(0); // Terminate JVM   
							}
						}
					}
					frame = new JFrame("Julia Set");     // set the title of the frame
					frame.setContentPane(new Fractal(xc,yc,iter));// set the content of the frame
				}
			
			
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
				frame.pack(); 
				frame.setLocationRelativeTo(null); 
				frame.setVisible(true); 
			
			}
			

		}
		//if values are in incorrect fromat
		catch(NumberFormatException e){
			System.out.println("Enter values in suitable format");
			errorMessage();
		}
	}
}


