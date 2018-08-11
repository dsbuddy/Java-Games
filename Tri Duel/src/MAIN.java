import java.util.Random;

public class MAIN {
public static boolean jimmy=true;
public static boolean alex=true;
public static boolean me=true;

	public static void main(String[] args) {
		double temp1=0;
		int temp=0;
		Random gen = new Random(100);
		Random mor =new Random(1);
		int who=0;
		int round=0;
		//temp1=mor.nextDouble();
		//System.out.println(temp1);
		//temp1=(int)(temp1*100);
		//System.out.println(temp1);
		
		
		
		round++;
		do{
		
			if(me==true){//start of me
			
		
			if(jimmy==true){
					temp1=mor.nextDouble();
					temp1=(int)(temp1*100);
				if (temp>33){
					if(jimmy==true){
						jimmy=false;
					System.out.println("you shot jimmy...");
					}
				}
			}			
		}//end of me
			
		if(jimmy==true){//jimmy
			if(alex==true){
			if (gen.nextInt(100)>50){
				alex=false;
				System.out.println("jimmy shot alec... you have a chance...");
			}
		}
			else{
				if(gen.nextInt(100)>50){
					me=false;
					System.out.println("jimmy shot you...RIP");
			}
		}	
	}//end of jimmy
			
		if(alex==true){
			if(jimmy==true){
			jimmy=false;
			System.out.println("Alex shot jimmy...");
			}
		}
		else{
			me=false;
			System.out.println("Alex shot you...RIP");
		}
		
	}while(me&&jimmy==true || me&& alex==true || jimmy&& alex==true);
			
		
		

		if(me==true){
			 System.out.println("you lived! wow youre good");
			}
		if(jimmy==true){
				 System.out.println("jimmy lived! guess that practice paid off");
			}
		if(alex==true){
				 System.out.println("alex lived, guess he was to good for you");
			}

}

}
