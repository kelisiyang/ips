package entity;

public class xy2BL {
	double para_a,para_ee,para_eep;
	double x,y,centerLong;

	double Bf,Bf0;
	double itaf,tf,Wf,Vf,Mf,Nf;
	public xy2BL(double x,double y,double centerLong){
		this.para_a=Params_CGCS2000.para_a;
		this.para_ee=Params_CGCS2000.para_ee;
		this.para_eep=Params_CGCS2000.para_eep;

		this.x=x;
		this.y=y-500000;
		this.centerLong=centerLong;//经度，单位是度数

		double m0=para_a*(1-para_ee);
		double m2=3*para_ee*m0/2.0;
		double m4=5*para_ee*m2/4.0;
		double m6=7*para_ee*m4/6.0;
		double m8=9*para_ee*m6/8.0;
		double a0=m0+m2/2.0+3*m4/8.0+5*m6/16.0+35*m8/128.0;
		double a2=m2/2.0+m4/2.0+15*m6/32.0+7*m8/16.0;
		double a4=m4/8.0+3*m6/16.0+7*m8/32.0;
		double a6=1*m6/32.0+m8/16.0;
		double a8=m8/128.0;
		Bf=x/a0;
		do{
			Bf0 = Bf;

			Bf=(x+(1.0/2.0)*a2*Math.sin(2*Bf0)-(1.0/4.0)* a4*Math.sin(4*Bf0)+(1.0/6.0)* a6*Math.sin(6*Bf0)-(1.0/8.0)*a8*Math.sin(8*Bf0))/ a0;

		}while(Math.abs(Bf - Bf0) >0.0000000000001);


		itaf=Math.sqrt(para_eep)*Math.cos(Bf);
		tf=Math.tan(Bf);
		Wf=Math.sqrt(1-para_ee*Math.sin(Bf)*Math.sin(Bf));
		Vf=Math.sqrt(1+itaf*itaf);
		Nf= para_a/Wf;
		Mf=para_a*(1-para_ee)/Wf/Wf/Wf;//子午圈曲率半径
	}
	public xy2BL(double para_a,double para_ee,double para_eep,double x,double y,double centerLong){
		this.para_a=para_a;
		this.para_ee=para_ee;
		this.para_eep=para_eep;
	
		this.x=x;
		this.y=y-500000;
		this.centerLong=centerLong;//经度，单位是度数
		
		double m0=para_a*(1-para_ee);
		double m2=3*para_ee*m0/2.0;
		double m4=5*para_ee*m2/4.0;
		double m6=7*para_ee*m4/6.0;
		double m8=9*para_ee*m6/8.0;
		double a0=m0+m2/2.0+3*m4/8.0+5*m6/16.0+35*m8/128.0;
		double a2=m2/2.0+m4/2.0+15*m6/32.0+7*m8/16.0;
		double a4=m4/8.0+3*m6/16.0+7*m8/32.0;
		double a6=1*m6/32.0+m8/16.0;
		double a8=m8/128.0;
		Bf=x/a0;
		do{
	        Bf0 = Bf;
	    	
	        Bf=(x+(1.0/2.0)*a2*Math.sin(2*Bf0)-(1.0/4.0)* a4*Math.sin(4*Bf0)+(1.0/6.0)* a6*Math.sin(6*Bf0)-(1.0/8.0)*a8*Math.sin(8*Bf0))/ a0;
				
		}while(Math.abs(Bf - Bf0) >0.0000000000001);

		itaf=Math.sqrt(para_eep)*Math.cos(Bf); 
	    tf=Math.tan(Bf);
		Wf=Math.sqrt(1-para_ee*Math.sin(Bf)*Math.sin(Bf));
	    Vf=Math.sqrt(1+itaf*itaf);
		Nf= para_a/Wf;
		Mf=para_a*(1-para_ee)/Wf/Wf/Wf;//子午圈曲率半径
	}
	//返回的B是DD.DD
	public double getB() {
		//Log.i("bbbbbbbbbbb",""+Bf);
		
		//此处计算得到的B为弧度
	double	B = Bf - tf * y * y / (2.0*Mf*Nf) + tf * ( 5.0 + 3.0* tf * tf + itaf * itaf - 9.0 * itaf * itaf * tf * tf) * Math.pow(y,4) / (24.0*Mf*Math.pow(Nf,3))
				- tf * (61.0 + 90.0 * tf *tf + 45 *Math. pow(tf,4) ) * Math.pow(y,6)/ (720.0 * Mf * Math.pow(Nf,5)) ;
		//Log.i("bbbbbbbbbbb",""+B);

		B=Math.toDegrees(B);
		//System.out.println(B);
		return B;
	}
	public double getL() {
		//此处的 L是经差
		//double	L = 1.0* y/ (Nf * Math.cos(Bf));
	double	L = 1.0* y/ (Nf * Math.cos(Bf))  - 1.0 * ( 1.0 + 2.0 * tf *tf + itaf * itaf) * Math.pow(y,3)/ (6.0 *(Math.pow(Nf,3) * Math.cos(Bf)))
				+ 1.0 * (5.0 + 28.0 * tf * tf + 24 * Math.pow(tf,4) ) * Math.pow(y,5)/ (120 * Math.pow(Nf,5) * Math.cos(Bf)) ;
		//Log.i("llllllllll",""+L);
		L=Math.toDegrees(L);
        L=L+centerLong;
        //System.out.println(L);
		return L;	
	}
	  /*public static void main(String[] args) {
		xy2BL bl=new xy2BL(5081837.165, 538028.577, 126);
		System.out.println(bl.getB());
		System.out.println(bl.getL());
	}*/
}