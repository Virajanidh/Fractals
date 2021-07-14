
public class Set {

	
	private double re_start ;
	private double re_end;
	private double im_start;
	private double im_end;
	protected boolean validity; //variable to check if it is in given set
	private static double x;
	private static double y;
	private int n_iter; //valid iterations for given set
	private double xc,yc;  //for C value in Julia set
	
	//Constructor for Mandelbrot set
	public Set(double re_start,double re_end,double im_start,double im_end) {
		
		this.re_start=re_start;
		this.re_end=re_end;
		this.im_start=im_start;
		this.im_end=im_end;
		
		
	}
	//Constructor for Julia set
	public Set(double re_start,double im_start) {
		this.xc=re_start;
		this.yc=im_start;
		this.re_start=-1;
		this.re_end=1;
		this.im_start=-1;
		this.im_end=1;
		
	}
	
	
	//get coordinates in interest region
	public void interestR(int i,int j,int size){
	
		x=(((double)i*(re_end-re_start))/(double)size)-Math.abs(re_start); //real part , Mandelbrot set :for C ,Julia set: for Z
		y=(((double)j*(im_start-im_end))/(double)size)+Math.abs(im_end); //imaginary part , Mandelbrot set :for C ,Julia set: for Z
		
	}
	
	//check whether coordinates in the given set
	public boolean checkValidity(double x,double y,int iter,boolean select){		
		double zx_now;  //real part of Z
		double zy_now;  //imaginary part of Z
		if(select){
		//Z increasing continuously C must be calculated
		//initially Z=0
			zx_now=0; 
			zy_now=0; 
		}
		else{
		//C constant and Z varying
			zx_now=x; 
			zy_now=y;
			x=xc; //real part of C value
			y=yc; //imaginary part of C value
		}
		n_iter=0; 
		
		while(iter-- >0){
		
			//Z(n+1)=Z(n)+C
			double zx_next=Math.pow(zx_now,2)-Math.pow(zy_now,2)+x; //get the real part of next Z value
			double zy_next=(2*zx_now*zy_now)+y;     //get the imaginary part of next Z value
			zx_now=zx_next;
			zy_now=zy_next;
			n_iter++;
			
			//check whether absolute value of current z is higher than 2 ( ABS|Zn|>2)
			//if then point is not in given set
			//In oder to reduce the computation time  ABS(Zn^2) > 4 checked
			if((Math.pow(zx_now,2)+Math.pow(zy_now,2))>4)
			{	
				// C is not in the given set
				return validity=false;
			}	
		}	
		return validity=true;   //C is in the given set
	}
	
	//coordinates are passed as x and y in Mandelbrot set and C value passed as x and y in Julia set
	@SuppressWarnings("static-access")
	public double getX() { return this.x; } 
	@SuppressWarnings("static-access")
	public double getY() { return this.y; }
	public int getIter() { return this.n_iter; }
	
	
}
