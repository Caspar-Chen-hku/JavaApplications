import java.awt.Color;
/**
 * <h1>This is the Shape class!</h1>
 * The Shape class is used to model general shapes. 
 * It has private instance variables for storing 
 * color, fill-type, orientation, screen coordinates of 
 * the center, and the local coordinates of the
 * vertices of a shape. It has getters and setters for accessing 
 * its private instance variables. It
 * also has methods for translating and rotating a shape, 
 * and for getting the screen coordinates of the vertices 
 * of a shape.
 * <p>
 * @author CChen
 * @version 1.0
 * @since 2016-09-24
 */
public class Shape {
	/**
	 * These are the instance variables of Shape, among which color defines
	 * its color, filled defines whether the shape is filled or not, theta defines
	 * the orientation, xc and yc define the coordinates of its center, and xLocal
	 * and yLocal are two arrays that define the coordinates of the vertices
	 */
   private Color color;
   private boolean filled;
   private double theta;
   private double xc;
   private double  yc;
   private double[] xLocal;
   private double[] yLocal;
       /**
        * a method for retrieving the color of the shape.
        * @return Color It returns the instance variable color.
        */
    public  Color getColor(){return color;}
      /**
       * a method for retrieving the fill-type of the shape.
       * @return boolean It returns the instance variable filled
       */
    public   boolean getFilled(){return filled;}
       /**
        *  a method for retrieving the orientation (in radians) 
        *  of the shape in the screen coordinate system.
        * @return double It returns the instance variable theta
        */
     public  double getTheta(){return theta;}
       /**
        * a method for retrieving the x-coordinate of the center 
        * of the shape in the screen coordinate system.
        * @return double It returns the instance variable xc
        */
     public  double getXc(){return xc;}
       /**
        * ¨C a method for retrieving the y-coordinate of the center 
        * of the shape in the screen coordinate system.
        * @return double It returns the instance variable yc
        */
     public  double getYc(){return yc;}
       /**
        * a method for retrieving the x-coordinates of the vertices 
        * of the shape in its local coordinate system
        * @return double[] It returns the instance variable xLocal
        */
     public  double[] getXLocal(){return xLocal;}
       /**
        * a method for retrieving the y-coordinates of the vertices 
        * of the shape in its local coordinate system.
        * @return double[] It returns the instance variable yLocal
        */
     public  double[] getYLocal(){return yLocal;}
       /**
        * a method for setting the color of the shape
        * @param color the value to be set
        */
     public  void setColor(Color color){this.color = color;}
       /**
        * a method for setting the fill-type of the shape
        * @param filled the value to be set
        */
     public  void setFilled(boolean filled){this.filled=filled;}
       /**
        * a method for setting the orientation of the shape
        * @param theta the value to be set
        */
     public  void setTheta(double theta){this.theta=theta;}
       /**
        * a method for setting the x-coordinate of the center 
        * of the shape in the screen coordinate system.
        * @param xc the value to be set
        */
     public  void setXc(double xc){this.xc=xc;}
       /**
        * a method for setting the y-coordinate of the center 
        * of the shape in the screen coordinate system.
        * @param yc the value to be set
        */
     public  void setYc(double yc){this.yc=yc;}
       /**
        * a method for setting the coordinates of the
        * center of the shape in the screen coordinate system
        * @param xc the value to be set to xc
        * @param yc the value to be set to yc
        */
     public  void setCenter(double xc, double yc){
    	   this.xc=xc;
    	   this.yc=yc;}
       /**
        * a method for setting the x-coordinates of the
        * vertices of the shape in its local coordinate system
        * @param xLocal the value to be set
        */
    public   void setXLocal(double[] xLocal){this.xLocal = xLocal;}
       /**
        * a method for setting the y-coordinates of the
        * vertices of the shape in its local coordinate system
        * @param yLocal the value to be set
        */
    public   void setYLocal(double[] yLocal){this.yLocal=yLocal;}
       /**
        * a method for translating the center of the
        * shape by dx and dy, respectively, along the x and y 
        * directions of the screen coordinate system. 
        * (dx and dy should be added to xc and yc respectively.)
        * @param dx the value to be added to xc
        * @param dy the value to be added to yc
        */
    public   void translate(double dx, double dy){
    	   xc+=dx;
    	   yc+=dy;}
       /**
        * a method for rotating the shape about its center by an 
        * angle of dt (in radians). (dt should be added to theta.)
        * @param dt the value to be added to theta
        */
     public  void rotate(double dt){theta+=dt;}
       /**
        * a method for retrieving the x-coordinates of the vertices 
        * of the shape in the screen coordinate system
        * @return int[] the respective coordinates
        */
     public  int[] getX(){
    	   int lth = xLocal.length;
    	   int[] arr = new int[lth];
    	   for (int i=0; i<lth; i++){
    		   arr[i]= (int) Math.round(xLocal[i]*Math.cos(theta)
    		-yLocal[i]*Math.sin(theta)+xc);
    	   }
    	   return arr;
       }
       /**
        * a method for retrieving the y-coordinates of the vertices 
        * of the shape in the screen coordinate system
        * @return int[] the respective coordinates
        */
    public   int[] getY(){
    	   int lth = yLocal.length;
    	   int[] arr = new int[lth];
    	   for (int i=0; i<lth; i++){
    		   arr[i]= (int) Math.round(xLocal[i]*Math.sin(theta)
    		+yLocal[i]*Math.cos(theta)+yc);
    	   }
    	   return arr;
       }
       
}
;