/**
 * <h1>This is the RegularPolygon class!</h1>
 * The RegularPolygon class is a sub-class of the Shape class, 
 * and is used to model regular nsided polygons. Besides the 
 * properties it inherited from the Shape class, the 
 * RegularPolygon class also declares a number of private 
 * instance variables for storing the number of sides and 
 * radius of a polygon. It has getters and setters for 
 * accessing its private instance variables. It also has 
 * methods for setting the local coordinates of the vertices 
 * of a polygon and for checking if a point (in the 
 * screen coordinate system) is contained by a polygon
 * @author CChen
 * @version 1.0
 * @since 2016-09-24
 */
public class RegularPolygon extends Shape{
	/**
	 * These are two instance variables specially for RegularPolygon.
	 * numberOfSides defines the number of its sides, radius defines
	 * the size
	 */
	private int numOfSides;
	private double radius;
	/**
	 * Here are 3 different kinds of constructors
	 * @param n the possible value of numOfSides
	 * @param r the possible value of radius
	 */
	public RegularPolygon(int n, double r){
		if (n>=3) numOfSides=n;
		else numOfSides=3;
		if (r>=0) radius=r;
		else radius = 0;
		this.setVertices();
	}
    public RegularPolygon(int n){
    	if (n>=3) numOfSides=n;
    	else numOfSides=3;
    	radius=1;
    	this.setVertices();
    }
    public RegularPolygon(){
    	numOfSides=3;
    	radius=1;
    	this.setVertices();
    }
    /**
     * This is a private method that is used by other methods
     * It sets the vertices according to numOfSides and radius
     */
    private void setVertices(){
    	double[] xs = new double[numOfSides];
    	double[] ys = new double[numOfSides];
    	if (numOfSides%2==1){
    		for (int i=0; i<numOfSides; i++){
    			xs[i]=radius*Math.cos(2*i*Math.PI/numOfSides);
    			ys[i]=radius*Math.sin(2*i*Math.PI/numOfSides);
    			}
    	}
    	else{
    		double start=Math.PI/numOfSides;
    		for (int i=0; i<numOfSides; i++){
    			xs[i]=radius*Math.cos(start+2*i*(Math.PI)/numOfSides);
    			ys[i]=radius*Math.sin(start+2*i*(Math.PI)/numOfSides);
    			}
    	}
    	setXLocal(xs);
    	setYLocal(ys);
    }
    /**
     * a method for retrieving the x-coordinates of the vertices 
     * of the shape in the screen coordinate system
     * @return int[] the respective coordinates
     */
    public  int[] getX(){
 	   int[] arr = new int[12];
 	   double[] arrx = getXLocal();
 	   double[] arry = getYLocal();
 	   for (int i=0; i<getNumOfSides(); i++){
 		   arr[i]= (int) Math.round(arrx[i]*Math.cos(getTheta())
 		-arry[i]*Math.sin(getTheta())+getXc());
 	   }
 	   return arr;}
    /**
     * a method for retrieving the y-coordinates of the vertices 
     * of the shape in the screen coordinate system
     * @return int[] the respective coordinates
     */
    public   int[] getY(){
    	int[] arr = new int[12];
  	   double[] arrx = getXLocal();
  	   double[] arry = getYLocal();
 	   for (int i=0; i<getNumOfSides(); i++){
 		   arr[i]= (int) Math.round(arrx[i]*Math.sin(getTheta())
 		+arry[i]*Math.cos(getTheta())+getYc());
 	   }
 	   return arr;
    }
    /**
     * return the value of numOfSides
     * @return int numOfSides
     */
    public int getNumOfSides(){return numOfSides;}
    /**
     * return the value of radius
     * @return double radius
     */
    public double getRadius(){return radius;}
    /**
     * set numOfSides
     * @param n possible value of numOfSides
     */
    public void setNumOfSides(int n) {
    	numOfSides=n;
    	if (numOfSides<3) numOfSides=3;
    	setVertices();}
    /**
     * set radius
     * @param r possible value of radius
     */
   public void setRadius(double r){
    	radius=r;
    	if (radius<0) radius=0;
    	setVertices();}
    /**
     * decide whether a point is inside a polygon
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return true or false
     */
   public boolean contains(double x, double y){
	   double angle = getTheta();
	   double dis = Math.sqrt((x-getXc())*(x-getXc())+
			   (y-getYc())*(y-getYc()));
	   double xx = (x-getXc())*Math.cos(angle)+(y-getYc())*Math.sin(angle);
	   double degree = Math.acos(xx/dis);
       double[] xs = getXLocal();
    	double test=xs[numOfSides/2 ];
    	for (int i=0; i<numOfSides; i++){
    		if (xx<test) return false;
    		degree += 2*Math.PI/numOfSides;
    		xx=dis*Math.cos(degree);
    	}                                  
    	return true;
    } 
}
