import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends Frame implements ActionListener {
    private User user;
    private List<Appliance> appliances;
    private Button addButton;
    private Button logoutButton;
    private Panel centerPanel;

    public MainFrame(User user) {
        this.user = user;
        setLayout(new BorderLayout());

        Panel topPanel = new Panel();
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        logoutButton = new Button("Logout");
        logoutButton.addActionListener(this);
        topPanel.add(logoutButton);

        add(topPanel, BorderLayout.NORTH);

        centerPanel = new Panel();
        centerPanel.setLayout(new GridLayout(0, 10, 3, 3)); 

        addTitles();
        refreshAppliances();

        add(centerPanel, BorderLayout.CENTER);

        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        addButton = new Button("Add Appliance");
        addButton.addActionListener(this);
        bottomPanel.add(addButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setTitle("User Home Electrical Appliances");
        setSize(900, 400); 
        setVisible(true);
    }

    private void addTitles() {
        centerPanel.add(new Label("Name"));
        centerPanel.add(new Label("Type"));
        centerPanel.add(new Label("Rating"));
        centerPanel.add(new Label("Consumption (kWh)"));
        centerPanel.add(new Label("Hours/Day"));
        centerPanel.add(new Label("Days/Month"));
        centerPanel.add(new Label("Units/Hour"));
        centerPanel.add(new Label("Units/Month"));
        centerPanel.add(new Label("Edit"));
        centerPanel.add(new Label("Delete"));
    }

    public void refreshAppliances() {
        centerPanel.removeAll();
        addTitles();

        ApplianceDao applianceDao = new ApplianceDao();
        appliances = applianceDao.getAppliancesByUserId(user.getId());

        for (Appliance appliance : appliances) {
            Label nameLabel = new Label(appliance.getName());
            centerPanel.add(nameLabel);

            Label typeLabel = new Label(appliance.getType());
            centerPanel.add(typeLabel);

            Label ratingLabel = new Label(String.valueOf(appliance.getRating()));
            centerPanel.add(ratingLabel);

            double yearlyConsumption = appliance.getConsumption();
            double consumptionPerHour = yearlyConsumption / (365 * appliance.getHoursPerDay());
            Label consumptionLabel = new Label(String.format("%.2f kWh", consumptionPerHour));
            centerPanel.add(consumptionLabel);

            int hoursPerDay = appliance.getHoursPerDay();
            Label hoursLabel = new Label(String.valueOf(hoursPerDay));
            centerPanel.add(hoursLabel);

            int daysPerMonth = appliance.getDaysPerMonth();
            Label daysLabel = new Label(String.valueOf(daysPerMonth));
            centerPanel.add(daysLabel);

            double unitsPerHour = consumptionPerHour;
            Label unitsPerHourLabel = new Label(String.format("%.2f", unitsPerHour));
            centerPanel.add(unitsPerHourLabel);

            double unitsPerMonth = unitsPerHour * hoursPerDay * daysPerMonth;
            Label unitsPerMonthLabel = new Label(String.format("%.2f", unitsPerMonth));
            centerPanel.add(unitsPerMonthLabel);

            Button editButton = new Button("Edit");
            editButton.setPreferredSize(new Dimension(60, 25));
            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new EditApplianceDialog(MainFrame.this, appliance);
                }
            });
            centerPanel.add(editButton);

            Button deleteButton = new Button("Delete");
            deleteButton.setPreferredSize(new Dimension(60, 25)); 
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ApplianceDao applianceDao = new ApplianceDao();
                    if (applianceDao.deleteAppliance(appliance.getId())) {
                        refreshAppliances();
                    }
                }
            });
            centerPanel.add(deleteButton);
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            new LoginFrame();
            dispose();
        } else if (e.getSource() == addButton) {
            new AddApplianceDialog(this, user); 
        }
    }
}
