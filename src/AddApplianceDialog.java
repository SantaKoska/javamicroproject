import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddApplianceDialog extends Dialog implements ActionListener {
    private TextField nameField;
    private Choice typeChoice;
    private TextField ratingField;
    private TextField consumptionField;
    private TextField hoursPerDayField;
    private TextField daysPerMonthField;
    private Button addButton;
    private Button cancelButton;
    private MainFrame mainFrame;
    private User user;

    public AddApplianceDialog(MainFrame mainFrame, User user) {
        super(mainFrame, "Add Appliance", true);
        this.mainFrame = mainFrame;
        this.user = user;
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new Label("Name:"));
        nameField = new TextField();
        add(nameField);

        add(new Label("Type:"));
        typeChoice = new Choice();
        typeChoice.add("Fridge");
        typeChoice.add("AC");
        typeChoice.add("Oven");
        typeChoice.add("Intention Cooker");
        typeChoice.add("Other");
        add(typeChoice);

        add(new Label("Rating (stars):"));
        ratingField = new TextField();
        add(ratingField);

        add(new Label("Yearly Consumption (kWh):"));
        consumptionField = new TextField();
        add(consumptionField);

        add(new Label("Hours per day:"));
        hoursPerDayField = new TextField();
        add(hoursPerDayField);

        add(new Label("Days per month:"));
        daysPerMonthField = new TextField();
        add(daysPerMonthField);

        addButton = new Button("Add");
        addButton.addActionListener(this);
        add(addButton);

        cancelButton = new Button("Cancel");
        cancelButton.addActionListener(this);
        add(cancelButton);

        setSize(300, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText();
            String type = typeChoice.getSelectedItem();
            int rating = Integer.parseInt(ratingField.getText());
            double consumption = Double.parseDouble(consumptionField.getText());
            int hoursPerDay = Integer.parseInt(hoursPerDayField.getText());
            int daysPerMonth = Integer.parseInt(daysPerMonthField.getText());

            Appliance appliance = new Appliance();
            appliance.setName(name);
            appliance.setType(type);
            appliance.setRating(rating);
            appliance.setConsumption(consumption);
            appliance.setHoursPerDay(hoursPerDay);
            appliance.setDaysPerMonth(daysPerMonth);
            appliance.setUserId(user.getId());

            ApplianceDao applianceDao = new ApplianceDao();
            if (applianceDao.addAppliance(appliance)) {
                mainFrame.refreshAppliances();
                dispose(); 
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}
