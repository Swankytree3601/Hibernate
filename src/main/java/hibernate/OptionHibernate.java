package hibernate;

import java.util.Scanner;

public class OptionHibernate {

    public void choseOption(){

        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("¿Qué quieres hacer?");
            System.out.println("1. Probar conexión de la base de datos.");
            System.out.println("2. ");
            System.out.println("3. ");
            System.out.println("4. ");
            System.out.println("4. ");
            System.out.println("0. Salir");

            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Option 1");
                    option1();

                    break;
                case 2:
                    System.out.println("Option 2");
                    option2();

                    break;
                case 3:
                    System.out.println("Option 3");
                    option3();

                    break;
                case 4:
                    System.out.println("Option 4");
                    option4();

                    break;
                case 5:
                    System.out.println("Option 5");
                    option5();

                    break;
                case 0:
                    System.out.println("Saliendo...");

                    break;
                default:
                    System.out.println("Opcion no valida");
            }

        }while (option != 0);

    }

    protected void option1(){

    }

    protected void option2(){

    }

    protected void option3(){

    }

    protected void option4(){

    }

    protected void option5(){

    }
}
