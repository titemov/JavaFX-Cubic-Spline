public class Splines extends Initializer {
    public static double getLen(double x1, double y1,double x2, double y2){
        return Math.pow((Math.pow(x2-x1,2)+Math.pow(y2-y1,2)),0.5);
    }

    public static void cubicSpline(double[][] cc, String mode){
        switch (mode) {
            //c1 means c0 (first equation)
            case "Fixed":
                System.out.println("Fixed");
                //C1'(x_0)=(f_0)' and C_n'(x_n)=(f_n)'
                fixed(cc);
                break;

            case "Soft":
                System.out.println("Soft");
                //C1''(x_0)=C_n''(x_n)=0
                soft(cc);
                break;
            case "Cyclic":
                System.out.println("Cyclic");
                //C1(x_0)=C_n(x_n) and C1'(x_0)=C_n'(x_n) and C1(x_0)=C_n''(x_n)
                cyclic(cc);
                break;
            case "Acyclic":
                System.out.println("Acyclic");
                acyclic(cc);
                //C1(x_0)= -1 * C_n(x_n) and C1'(x_0)= -1 * C_n'(x_n) and C1(x_0)= -1 * C_n''(x_n)
                break;
            default:
                System.out.println("Incorrect mode");
                break;
        }
    }

    private static void fixed(double[][] cc) {
        int cc_len=cc[0].length;
        double[] t = new double[cc_len-1];
        double[][] m = new double[cc_len][cc_len];
        double[][] p = new double[cc_len][2];
        double[][] r = new double[cc_len][2];

        for(int i=0;i<cc_len-1;i++){
            t[i]=getLen(cc[0][i],cc[1][i],cc[0][i+1],cc[1][i+1]);
        }

        m[0][0]=1;
        m[m.length-1][m.length-1]=1;
        for(int i=1;i<m.length-1;i++){
            m[i][0+(i-1)]=t[i];
            m[i][1+(i-1)]=2*(t[i]+t[i-1]);
            m[i][2+(i-1)]=t[i-1];
        }

        r[0][0]=endpoint_coor[0][0];
        r[0][1]=endpoint_coor[1][0];
        r[cc_len-1][0]=endpoint_coor[0][1];
        r[cc_len-1][1]=endpoint_coor[1][1];

        for(int i=1;i<r.length-1;i++){
            r[i][0]=3/(t[i-1]*t[i])*((Math.pow(t[i-1],2)*(cc[0][i+1]-cc[0][i]))+(Math.pow(t[i],2)
                    *(cc[0][i]-cc[0][i-1])));//for x
            r[i][1]=3/(t[i-1]*t[i])*((Math.pow(t[i-1],2)*(cc[1][i+1]-cc[1][i]))+(Math.pow(t[i],2)
                    *(cc[1][i]-cc[1][i-1])));;//for y
        }

        p=Matrix.multiply(Matrix.inverseOf(m),r);
        p[0][0]=endpoint_coor[0][0];
        p[0][1]=endpoint_coor[1][0];
        p[cc_len-1][0]=endpoint_coor[0][1];
        p[cc_len-1][1]=endpoint_coor[1][1];

        setCurveCoor(t,p,cc);
    }
    private static void soft(double[][] cc) {
        int cc_len=cc[0].length;
        double[] t = new double[cc_len-1];
        double[][] m = new double[cc_len][cc_len];
        double[][] p = new double[cc_len][2];
        double[][] r = new double[cc_len][2];

        for(int i=0;i<cc_len-1;i++){
            t[i]=getLen(cc[0][i],cc[1][i],cc[0][i+1],cc[1][i+1]);
        }

        m[0][0]=1;
        m[0][1]=0.5;
        m[m.length-1][m.length-1]=4;
        m[m.length-1][m.length-2]=2;
        for(int i=1;i<m.length-1;i++){
            m[i][0+(i-1)]=t[i];
            m[i][1+(i-1)]=2*(t[i]+t[i-1]);
            m[i][2+(i-1)]=t[i-1];
        }

        r[0][0]=3/(2*t[0])*(cc[0][1]-cc[0][0]);//x
        r[0][1]=3/(2*t[0])*(cc[1][1]-cc[1][0]);//y
        r[cc_len-1][0]=6/t[t.length-1]*(cc[0][cc_len-1]-cc[0][cc_len-2]);//x
        r[cc_len-1][1]=6/t[t.length-1]*(cc[1][cc_len-1]-cc[1][cc_len-2]);//y

        for(int i=1;i<r.length-1;i++){
            r[i][0]=3/(t[i-1]*t[i])*((Math.pow(t[i-1],2)*(cc[0][i+1]-cc[0][i]))+(Math.pow(t[i],2)
                    *(cc[0][i]-cc[0][i-1])));//for x
            r[i][1]=3/(t[i-1]*t[i])*((Math.pow(t[i-1],2)*(cc[1][i+1]-cc[1][i]))+(Math.pow(t[i],2)
                    *(cc[1][i]-cc[1][i-1])));//for y
        }

        p=Matrix.multiply(Matrix.inverseOf(m),r);

        //Matrix.printmat(p);

        setCurveCoor(t,p,cc);
    }
    private static void cyclic(double[][] cc) {
        int cc_len=cc[0].length;
        double[] t = new double[cc_len-1];
        double[][] m = new double[cc_len-1][cc_len-1];
        double[][] p = new double[cc_len-1][2];
        double[][] r = new double[cc_len-1][2];

        for(int i=0;i<cc_len-1;i++){
            t[i]=getLen(cc[0][i],cc[1][i],cc[0][i+1],cc[1][i+1]);
        }

        m[0][0]=2*(1+t[t.length-1]/t[0]);
        m[0][1]=t[t.length-1]/t[0];
        m[0][m[0].length-1]=1;
        m[m.length-1][m.length-1]=2*(t[t.length-1]+t[t.length-1-1]);
        m[m.length-1][m.length-2]=t[t.length-1];
        for(int i=1;i<m.length-1;i++){
            m[i][0+(i-1)]=t[i];
            m[i][1+(i-1)]=2*(t[i]+t[i-1]);
            m[i][2+(i-1)]=t[i-1];
        }

        r[0][0]=3*(t[t.length-1]/Math.pow(t[0],2))*(cc[0][1]-cc[0][0])-3/t[t.length-1]
                *(cc[0][cc[0].length-2]-cc[0][cc[0].length-1]);//x
        r[0][1]=3*(t[t.length-1]/Math.pow(t[0],2))*(cc[1][1]-cc[1][0])-3/t[t.length-1]
                *(cc[1][cc[0].length-2]-cc[1][cc[0].length-1]);//y

        for(int i=1;i<r.length;i++){
            r[i][0]=3/(t[i-1]*t[i])*((Math.pow(t[i-1],2)*(cc[0][i+1]-cc[0][i]))+(Math.pow(t[i],2)
                    *(cc[0][i]-cc[0][i-1])));//for x
            r[i][1]=3/(t[i-1]*t[i])*((Math.pow(t[i-1],2)*(cc[1][i+1]-cc[1][i]))+(Math.pow(t[i],2)
                    *(cc[1][i]-cc[1][i-1])));;//for y
        }

        p=Matrix.multiply(Matrix.inverseOf(m),r);

        double[][] temp_p = new double[p.length+1][2];
        System.arraycopy(p, 0, temp_p, 0, p.length);
        p=temp_p;
        p[cc_len-1][0]=p[0][0];
        p[cc_len-1][1]=p[0][1];

        setCurveCoor(t,p,cc);
    }
    private static void acyclic(double[][] cc) {
        int cc_len=cc[0].length;
        double[] t = new double[cc_len-1];
        double[][] m = new double[cc_len-1][cc_len-1];
        double[][] p = new double[cc_len-1][2];
        double[][] r = new double[cc_len-1][2];

        for(int i=0;i<cc_len-1;i++){
            t[i]=getLen(cc[0][i],cc[1][i],cc[0][i+1],cc[1][i+1]);
        }

        m[0][0]=2*(1+t[t.length-1]/t[0]);
        m[0][1]=t[t.length-1]/t[0];
        m[0][m[0].length-1]=-1;
        m[m.length-1][m.length-1]=2*(t[t.length-1]+t[t.length-1-1]);
        m[m.length-1][m.length-2]=t[t.length-1];
        for(int i=1;i<m.length-1;i++){
            m[i][0+(i-1)]=t[i];
            m[i][1+(i-1)]=2*(t[i]+t[i-1]);
            m[i][2+(i-1)]=t[i-1];
        }

        r[0][0]=3*(t[t.length-1]/Math.pow(t[0],2))*(cc[0][1]-cc[0][0])+3/t[t.length-1]
                *(cc[0][cc[0].length-2]-cc[0][cc[0].length-1]);//x
        r[0][1]=3*(t[t.length-1]/Math.pow(t[0],2))*(cc[1][1]-cc[1][0])+3/t[t.length-1]
                *(cc[1][cc[0].length-2]-cc[1][cc[0].length-1]);//y

        for(int i=1;i<r.length;i++){
            r[i][0]=3/(t[i-1]*t[i])*((Math.pow(t[i-1],2)*(cc[0][i+1]-cc[0][i]))+(Math.pow(t[i],2)
                    *(cc[0][i]-cc[0][i-1])));//for x
            r[i][1]=3/(t[i-1]*t[i])*((Math.pow(t[i-1],2)*(cc[1][i+1]-cc[1][i]))+(Math.pow(t[i],2)
                    *(cc[1][i]-cc[1][i-1])));;//for y
        }

        p=Matrix.multiply(Matrix.inverseOf(m),r);

        double[][] temp_p = new double[p.length+1][2];
        System.arraycopy(p, 0, temp_p, 0, p.length);
        p=temp_p;
        p[cc_len-1][0]=-p[0][0];
        p[cc_len-1][1]=-p[0][1];

        setCurveCoor(t,p,cc);
    }

    public static void setCurveCoor(double[] t,double[][] p,double[][] cc){
        double[][] f = new double[1][4];
        double[][] new_p= new double[4][2];

        for(int i=0;i<p.length-1;i++){
            for(int n=0;n<amountOfDots+1;n++){//idk why +1, but it works fine
                double tau=((double) 1 /amountOfDots)*n;
                f[0][0]=2*Math.pow(tau,3)-3*Math.pow(tau,2)+1;
                f[0][1]=-2*Math.pow(tau,3)+3*Math.pow(tau,2);
                f[0][2]=tau*(Math.pow(tau,2)-2*tau+1)*t[i];
                f[0][3]=tau*(Math.pow(tau,2)-tau)*t[i];
                new_p[0][0]=cc[0][i];//x
                new_p[0][1]=cc[1][i];//y
                new_p[1][0]=cc[0][i+1];//x
                new_p[1][1]=cc[1][i+1];//y
                new_p[2][0]=p[i][0];//x
                new_p[2][1]=p[i][1];//y
                new_p[3][0]=p[i+1][0];//x
                new_p[3][1]=p[i+1][1];//y
                double[][] temp=Matrix.multiply(f,new_p);
                curve_coor[0][n+i*amountOfDots]=temp[0][0];
                curve_coor[1][n+i*amountOfDots]=temp[0][1];
            }
        }
    }
}