import java.time.LocalDate;

public class DateCalc {
    
    public static LocalDate[] getStaticDates(){
        LocalDate exp = LocalDate.now().minusDays(180);
        LocalDate last = LocalDate.now().minusDays(150);
        LocalDate next = LocalDate.now().plusDays(30);
        return new LocalDate[] {exp,last,next};
    }

    public static LocalDate calcExpiryDate(String textDate){
        return LocalDate.of(Integer.parseInt(textDate.substring(6, 10)), Integer.parseInt(textDate.substring(3, 5)), Integer.parseInt(textDate.substring(0, 2))).plusDays(180);
    }

}
