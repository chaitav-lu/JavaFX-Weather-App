package WeatherPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import java.util.*;
import java.text.DecimalFormat;

public class WeatherAppController {
	
	private Map<String, List<Map<String, Object>>> cityWeather;
	private Map<String, List<Map<String, Object>>> postalCodeWeather;
	
    @FXML
    private ToggleButton cTempBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private ToggleButton fTempBtn;

    @FXML
    private TextField humidity1;

    @FXML
    private TextField humidity2;

    @FXML
    private TextField humidity3;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchValue;

    @FXML
    private TextField temp1;

    @FXML
    private TextField temp2;

    @FXML
    private TextField temp3;

    @FXML
    private ToggleGroup tempBtns;

    @FXML
    private TextField weather1;

    @FXML
    private TextField weather2;

    @FXML
    private TextField weather3;

    @FXML
    private TextField wind1;

    @FXML
    private TextField wind2;

    @FXML
    private TextField wind3;

    @FXML
    private void onClear(ActionEvent event) {
    	searchValue.setText("");
    	clearForecast();
    }

    @FXML
    private void onSearch(ActionEvent event) {
    	String searchString = searchValue.getText().trim().toLowerCase();
        List<Map<String, Object>> cityForecast = cityWeather.get(searchString);
        List<Map<String, Object>> codeForecast = postalCodeWeather.get(searchString);
        if (cityForecast != null) updateForecast(cityForecast);
        else if (codeForecast != null) updateForecast(codeForecast);
        else {
        	searchValue.setText("City or Postal code not found");
        	clearForecast();
        }
    }

    @FXML
    private void onTempToggle(ActionEvent event) {
    	boolean isCelcius = event.getSource() == cTempBtn;
    	temp1.setText(convertTemp(temp1.getText(),isCelcius));
    	temp2.setText(convertTemp(temp2.getText(),isCelcius));
    	temp3.setText(convertTemp(temp3.getText(),isCelcius));
    }

    private void updateForecast(List<Map<String, Object>> list) {
    	Map<String, Object> day1 = list.get(0);
    	weather1.setText(day1.get("condition") + "");
    	temp1.setText(fTempBtn.isSelected() ? convertTemp(day1.get("temperature") + "", false) : day1.get("temperature") + "");
    	humidity1.setText(day1.get("humidity") + "");
    	wind1.setText(day1.get("wind") + "");
    	
    	Map<String, Object> day2 = list.get(1);
    	weather2.setText(day2.get("condition") + "");
    	temp2.setText(fTempBtn.isSelected() ? convertTemp(day2.get("temperature") + "", false) : day2.get("temperature") + "");
    	humidity2.setText(day2.get("humidity") + "");
    	wind2.setText(day2.get("wind") + "");

    	Map<String, Object> day3 = list.get(2);
    	weather3.setText(day3.get("condition") + "");
    	temp3.setText(fTempBtn.isSelected() ? convertTemp(day3.get("temperature") + "", false) : day3.get("temperature") + "");
    	humidity3.setText(day3.get("humidity") + "");
    	wind3.setText(day3.get("wind") + "");    	
    }
    
    private void clearForecast() {
    	weather1.setText("");
    	temp1.setText("");
    	humidity1.setText("");
    	wind1.setText("");

    	weather2.setText("");
    	temp2.setText("");
    	humidity2.setText("");
    	wind2.setText("");

    	weather3.setText("");
    	temp3.setText("");
    	humidity3.setText("");
    	wind3.setText("");    	
    } 
    
    private String convertTemp(String temp, boolean isCelsius) {
    	try {
    		float tempValue = Float.parseFloat(temp);
    		if(isCelsius) tempValue = (float) ((tempValue - 32f) / 1.8f);
    		else tempValue = (float) ((tempValue * 1.8f) + 32f);
    		return Float.valueOf(new DecimalFormat("#.00").format(tempValue)) + "";
    	}
    	catch (NumberFormatException e) { return ""; }
    }

    private List<Map<String, Object>> createForecast(String[] conditions, float[] temperatures, float[] humidities, float[] windSpeeds) {        
    	List<Map<String, Object>> forecast = new ArrayList<>();
        for (int i = 0; i < 3; i++) 
            forecast.add(createData(conditions[i], temperatures[i], humidities[i], windSpeeds[i]));
        return forecast;
    }

    private Map<String, Object> createData(String condition, float temperature, float humidity, float wind) {
        Map<String, Object> data = new HashMap<>();
        data.put("condition", condition);
        data.put("temperature", temperature);
        data.put("humidity", humidity);
        data.put("wind", wind);
        return data;
    }
    
    
	// called by FXMLLoader to initialize the controller
    public void initialize() {
    	cityWeather = new HashMap<>();
    	cityWeather.put("thunder bay", createForecast(new String[] {"Snow","Snow","Cloudy"}, new float[]{-1f, 0f, -6f}, new float[]{87f, 85f, 60f}, new float[]{29f, 29f, 23f}));
    	cityWeather.put("toronto", createForecast(new String[] {"Windy","Light rain","Cloudy"}, new float[]{6f, 7f, 8f}, new float[]{66f, 79f, 74f}, new float[]{29f, 26f, 24f}));
    	cityWeather.put("ottawa", createForecast(new String[] {"Cloudy","Cloudy","Cloudy"}, new float[]{9f, 9f, 16f}, new float[]{26f, 46f, 71f}, new float[]{16f, 18f, 16f}));
    	cityWeather.put("vancouver", createForecast(new String[] {"Cloudy","Light rain","Light rain"}, new float[]{8f, 8f, 8f}, new float[]{82f, 80f, 90f}, new float[]{8f, 11f, 21f}));
    	cityWeather.put("calgary", createForecast(new String[] {"Cloudy","Cloudy","Partly cloudy"}, new float[]{-6f, 0f, 4f}, new float[]{75f, 70f, 64f}, new float[]{11f, 10f, 11f}));

    	postalCodeWeather = new HashMap<>();
    	postalCodeWeather.put("p7b 5e1", createForecast(new String[] {"Snow","Snow","Cloudy"}, new float[]{-1f, 0f, -6f}, new float[]{87f, 85f, 60f}, new float[]{29f, 29f, 23f}));
    	postalCodeWeather.put("m5s 1a1", createForecast(new String[] {"Windy","Light rain","Cloudy"}, new float[]{6f, 7f, 8f}, new float[]{66f, 79f, 74f}, new float[]{29f, 26f, 24f}));
    	postalCodeWeather.put("k1n 6n5", createForecast(new String[] {"Cloudy","Cloudy","Cloudy"}, new float[]{9f, 9f, 16f}, new float[]{26f, 46f, 71f}, new float[]{16f, 18f, 16f}));
    	postalCodeWeather.put("v6t 1z4", createForecast(new String[] {"Cloudy","Light rain","Light rain"}, new float[]{8f, 8f, 8f}, new float[]{82f, 80f, 90f}, new float[]{8f, 11f, 21f}));
    	postalCodeWeather.put("t2n 1n4", createForecast(new String[] {"Cloudy","Cloudy","Partly cloudy"}, new float[]{-6f, 0f, 4f}, new float[]{75f, 70f, 64f}, new float[]{11f, 10f, 11f}));

    	cTempBtn.setSelected(true);
    }
}
