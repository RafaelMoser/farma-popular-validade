import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static String[] weekdaysPTBR = {"Domingo","Segunda","Terça","Quarta","Quinta","Sexta","Sábado"};
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String getTodayDate(){
        return LocalDate.now().format(formatter);
    }

    public static String formatMainString(LocalDate[] dates){
        StringBuilder sb = new StringBuilder();
        sb.append("Receita vencida: ");
        sb.append(dates[0].format(formatter));
        sb.append(" | ");
        sb.append("Última retirada: ");
        sb.append(dates[1].format(formatter));
        sb.append(" | ");
        sb.append(" Próxima retirada: ");
        sb.append(dates[2].format(formatter));
        sb.append(" (");
        sb.append(weekdaysPTBR[dates[2].getDayOfWeek().getValue()]);
        sb.append(")");

        return sb.toString();
    }

    public static String formatCalcString(LocalDate date){
        StringBuilder sb = new StringBuilder();
        sb.append("Vence: ");
        sb.append(date.format(formatter));
        return sb.toString();
    }

    public static String quickYearFix(String date){
        if(date.length() == 8){
            StringBuilder sb = new StringBuilder(date);
            sb.insert(6, "20");
            return sb.toString();
        }
        else return date;
    }

    public static void handleDateCalcInput(String date){
        
    }

    public static boolean isValidDate(String date) {
			if (date == null || date.length() != 10) return false;
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			df.setLenient(false);
			try {
				df.parse(date);
				return true;
			} catch (ParseException ex) {
				return false;
			}
        }
    
}
