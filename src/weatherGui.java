import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class weatherGui implements ActionListener, ItemListener {
    private JLabel name;
    private JLabel temp;
    private JLabel condition;
    private JLabel conditionIcon;
    private JTextField weatherEntryField;
    private Networking client;
    private weatherInfo weather;
    private JCheckBox showCBox;

    public weatherGui()
    {
        name = new JLabel();
        temp = new JLabel();
        condition = new JLabel();
        conditionIcon = new JLabel();
        weatherEntryField = new JTextField();
        client = new Networking();
        weather = null;
        showCBox = new JCheckBox();

        setup();
    }

    private void setup()
    {
        JFrame frame = new JFrame("Very Small Weather Channel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel welcomeLabel = new JLabel("Weather!");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.blue);

        JPanel logoPanel = new JPanel();
        logoPanel.add(welcomeLabel);

        JPanel inputPanel = new JPanel();
        JLabel locationLabel = new JLabel("Enter Zip Code/Location:");
        weatherEntryField = new JTextField(12);
        JButton submitBut = new JButton("Submit");
        JButton clearBut  = new JButton("Clear");
        showCBox = new JCheckBox("Show Celsius");
        inputPanel.add(locationLabel);
        inputPanel.add(weatherEntryField);
        inputPanel.add(submitBut);
        inputPanel.add(clearBut);
        inputPanel.add(showCBox);

        JPanel infoPanel = new JPanel();
        name = new JLabel("");
        temp = new JLabel("");
        condition = new JLabel("");
        conditionIcon = new JLabel("");
        ImageIcon image = new ImageIcon("src/placeholder.jpg");
        conditionIcon = new JLabel(image);

        infoPanel.add(name);
        infoPanel.add(temp);
        infoPanel.add(condition);
        infoPanel.add(conditionIcon);

        frame.add(logoPanel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.SOUTH);

        submitBut.addActionListener(this);
        clearBut.addActionListener(this);
        showCBox.addItemListener(this);

        frame.pack();
        frame.setVisible(true);



    }

    private void loadWeather(String loc)
    {
        weather = client.getWeather(loc);
        name.setText(weather.getName());
        if (showCBox.isSelected()) {
            temp.setText("  Temperature: " + weather.getTempC());
        } else {
            temp.setText("  Temperature: " + weather.getTempF());
        }
        condition.setText("  Condition: " + weather.getCondtion());
        try {
            URL imageURL = new URL(weather.getIcon());
            BufferedImage image = ImageIO.read(imageURL);
            ImageIcon icon = new ImageIcon(image);
            conditionIcon.setIcon(icon);
        } catch (IOException ee) { }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());  // cast source to JButton
        String text = button.getText();

        if(text.equals("Submit"))
        {
            String enteredLoc = weatherEntryField.getText();
            loadWeather(enteredLoc);
        }
        else if(text.equals("Clear"))
        {
            weather = null;
            name.setText("");
            temp.setText("");
            condition.setText("");
            conditionIcon.setText("");
            ImageIcon image = new ImageIcon("src/placeholder.jpg");
            conditionIcon.setIcon(image);
            weatherEntryField.setText("");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (showCBox.isSelected() && weather != null) {
            temp.setText("Temperature: " + weather.getTempC());
        } else if(!showCBox.isSelected() && weather != null) {
            temp.setText("Temperature: " + weather.getTempF());
        }
    }
}
