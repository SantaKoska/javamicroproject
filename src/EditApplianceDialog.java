import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditApplianceDialog extends Dialog implements ActionListener {
    private Appliance appliance;
    private TextField nameField;
    private TextField typeField;
    private TextField ratingField;
    private TextField consumptionField;
    private TextField hoursField;
    private TextField daysField;
    private Button saveButton;

    public EditApplianceDialog(Frame parent, Appliance appliance) {
        super(parent, "Edit Appliance", true);
        this.appliance = appliance;

        setLayout(new GridLayout(0, 2));

        add(new Label("Appliance Name:"));
        nameField = new TextField(appliance.getName());
        add(nameField);

        add(new Label("Appliance Type:"));
        typeField = new TextField(appliance.getType());
        add(typeField);

        add(new Label("Rating (Stars):"));
        ratingField = new TextField(String.valueOf(appliance.getRating()));
        add(ratingField);

        add(new Label("Electricity Consumption (kWh/Year):"));
        consumptionField = new TextField(String.valueOf(appliance.getConsumption()));
        add(consumptionField);

        add(new Label("Hours per Day:"));
        hoursField = new TextField(String.valueOf(appliance.getHoursPerDay()));
        add(hoursField);

        add(new Label("Days per Month:"));
        daysField = new TextField(String.valueOf(appliance.getDaysPerMonth()));
        add(daysField);

        saveButton = new Button("Save");
        saveButton.addActionListener(this);
        add(saveButton);

        setSize(400, 300);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        appliance.setName(nameField.getText());
        appliance.setType(typeField.getText());
        appliance.setRating(Integer.parseInt(ratingField.getText()));
        appliance.setConsumption(Double.parseDouble(consumptionField.getText()));
        appliance.setHoursPerDay(Integer.parseInt(hoursField.getText()));
        appliance.setDaysPerMonth(Integer.parseInt(daysField.getText()));

        ApplianceDao applianceDao = new ApplianceDao();
        if (applianceDao.updateAppliance(appliance)) {
            System.out.println("Appliance updated successfully!");
            ((MainFrame) getParent()).refreshAppliances();
        } else {
            System.out.println("Failed to update appliance!");
        }

        dispose();
    }
}
