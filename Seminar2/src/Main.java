import java.text.SimpleDateFormat;

public class Main {
    //declar un format pentru data in toata aplicatia
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
    public static void main(String[] args) {
        try {
            Apartament apart = new Apartament(100,90,4, 4, "0722222222",
                    Zona.DOROBANTI, 1000000, fmt.parse("10.10.2023"), 3,
                    new String[] {"Parcare", "Jacuzzi", "Wifi", "Apa calda", "Canalizare"});

            System.out.println(apart);

            Apartament apart1 = new Apartament(100);  //copiere a referintei
            System.out.println(apart == apart1);        //nu mai sunt aceleasi apartamente
            System.out.println(apart.equals(apart1));   //comparam daca sunt 2 apartamente cu acelasi ID
        }
        catch (Exception ex) {

        }
    }
}
