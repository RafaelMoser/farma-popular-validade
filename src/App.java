import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {
			FarmaFrame f = new FarmaFrame();
			f.setMainText(Utils.formatMainString(DateCalc.getStaticDates()));
			f.setCalcText(Utils.formatCalcString(DateCalc.calcExpiryDate(f.getCurrentCalcText())));
			f.showWindow();
		});
    }
    
}
