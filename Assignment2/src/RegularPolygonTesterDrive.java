/**
 * <h1>This is the tester class for RegularPolygon!</h1>
 * It tests the correctness of RegularPolyon
 * @author CChen
 * @version 1.0
 * @since 2016-09-24
 */
public class RegularPolygonTesterDrive {
    /**
     * create a new RegularPolygon and test the relevant 
     * implementation
     * @param args not used
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        RegularPolygon a = new RegularPolygon(5,10);
        System.out.println(a.getNumOfSides());
        System.out.println(a.getRadius());
        for (int i=0; i<5; i++){
            System.out.println(a.getX()[i]);
            System.out.println(a.getY()[i]);}
        RegularPolygon b = new RegularPolygon();
        b.setNumOfSides(4);
        b.setRadius(5);
        double x=1;
        double y=1;
        System.out.println(b.contains(x,y));
        x+=5;
        y+=7;
        System.out.println(b.contains(x, y));
        RegularPolygon c = new RegularPolygon(6);
        c.setRadius(6);
        System.out.println(c.contains(x,y));
        x=1;
        y=1;
        System.out.println(c.contains(x, y));
        RegularPolygon d = new RegularPolygon();
        d.setFilled(true);
        d.setTheta(30);
        d.setXc(10);
        d.setYc(20);
        double[] arr1={2,5,18,27};
        d.setXLocal(arr1);
        double[] arr2={3,8,15,21};
        d.setYLocal(arr2);
        System.out.println(d.getFilled());
        System.out.println(d.getTheta());
        System.out.println(d.getXc());
        System.out.println(d.getYc());
        for (int i=0; i<4; i++){
        System.out.println(d.getXLocal()[i]);
        System.out.println(d.getYLocal()[i]);}
        d.translate(10, 20);
        d.rotate(30);
        System.out.println(d.getXc()+d.getYc());
        System.out.println(d.getTheta());
	}
}
