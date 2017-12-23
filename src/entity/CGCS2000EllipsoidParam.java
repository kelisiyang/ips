package entity;

public class CGCS2000EllipsoidParam {
    public static final double a=6378137;
    public static  final double b=6356752.3141403558;
    public static  final double c=6399593.6258640232;
    /**
     * ����f
     */
    public static  final double f=0.0033528106811823;
    /**
     * ��һƫ����e
     */
    public static  final double e1_1=0.0818191910428158;
    /**
     * ��һƫ����e^2
     */
    public static  final double e1_2=0.0066943800229008;
    /**
     * �ڶ�ƫ����e'
     */
    public static final  double e2_1=0.0820944381519172;
    /**
     * �ڶ�ƫ����e'^2
     */
    public static  final double e2_2=0.006739496775479;

    /**
     * m0=a*(1-e^2)
     */
    public static  final double m0=6335439.3270838755601904;
    /**
     * m2=(3/2)e^2*m0
     */
    public static  final double m2=63617.757701295575721464741071212;
    /**
     * m4=(5/4)e^2*m2
     */
    public static  final double m4=532.35180782162077720511106726803;
    /**
     * m6=(7/6)e^2*m4
     */
    public static  final double m6=4.157726192008881308912345274636;
    /**
     * m8=(9/8)e^2*m6
     */
    public static  final double m8=0.0313125740555351296890528169186;

    //����aֵ
    /**
     * a0=m0+(1/2)*m2+(3/8)*m4+(5/16)*m6+(35/128)*m8
     */

    public static  final double a0=6367449.1457139234269283803466441;

    /**
     * a2=(1/2)*m2+(1/2)*m4+(15/32)*m6+(7/16)*m8
     */

    public static  final double a2=32077.017387962251709067717691695;

    /**
     * a4=(1/8)*m4+(3/16)*m6+(7/32)*m8
     */

    public static  final double a4=67.330399264278910705679428451199;


    /**
     * a6=(1/32)*m6+(1/16)*m8
     */

    public static  final double a6=0.13188597937874848650907659088979;

    /**
     * a8=(1/128)*m8
     */

    public static  final double a8=2.4462948480886820069572513217656e-4;


}
