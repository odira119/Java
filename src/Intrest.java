import java.util.Scanner;
public class Intrest {
    public static void main(String[]args){
        Scanner scanner=new Scanner(System.in);

        double principle,rate,amount;
        int times,years;

        System.out.print("Enter the principle amount:");
        principle=scanner.nextDouble();

        System.out.print("Enter the rate of intrest:");
        rate=scanner.nextDouble()/100;

        System.out.print("Enter the number of times intrest applied per year:");
        times=scanner.nextInt();

        System.out.print("Enter the number of years the money is invested for:");
        years=scanner.nextInt();

        amount=principle*Math.pow((1+rate/times),times*years);

        System.out.printf("The amount after %d years is: Ksh. %.2f%n", years, amount);

        scanner.close();
    }
    
}
