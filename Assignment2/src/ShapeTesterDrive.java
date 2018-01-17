/**
 * <h1>this is the tester class for Shape!</h1>
 * It's used to verify the correctness of the Shape class
 * @author CChen
 * @version 1.0
 * @since 2016-09-24
 */
public class ShapeTesterDrive {
    /**
     * It creates an object of a class and access its 
     * instance variables and methods using the dot operator, 
     * and print out debugging messages to the console. 
     * @param args not used
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Shape a = new Shape();
        a.setFilled(true);
        a.setTheta(30);
        a.setXc(10);
        a.setYc(20);
        double[] arr1={2,5,18,27};
        a.setXLocal(arr1);
        double[] arr2={3,8,15,21};
        a.setYLocal(arr2);
        System.out.println(a.getFilled());
        System.out.println(a.getTheta());
        System.out.println(a.getXc());
        System.out.println(a.getYc());
        for (int i=0; i<4; i++){
        System.out.println(a.getXLocal()[i]);
        System.out.println(a.getYLocal()[i]);}
        a.translate(10, 20);
        a.rotate(30);
        System.out.println(a.getXc()+a.getYc());
        System.out.println(a.getTheta());
        Shape b = new Shape();
        b.setCenter(10, 20);
        System.out.println(b.getXc());
        System.out.println(b.getYc());
	}

}
