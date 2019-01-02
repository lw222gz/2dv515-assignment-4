package application.chart;

import static java.lang.Double.parseDouble;
import static java.util.Objects.nonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class DatasetScatterChart extends Application {

	public static void createChart(){
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Scatter Chart Sample");
		final NumberAxis xAxis = new NumberAxis(-1, 1, 1);
		final NumberAxis yAxis = new NumberAxis(-1, 1, 1);
		final ScatterChart<Number,Number> sc = new
				ScatterChart<>(xAxis,yAxis);

		sc.setTitle("Dataset scatterplot");

		sc.getData().addAll(parseDataset());
		Scene scene  = new Scene(sc, 500, 400);
		stage.setScene(scene);
		stage.show();
	}

	private Collection<XYChart.Series<Number, Number>> parseDataset() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("spiral/spiral.csv").getFile()));

			Map<String, XYChart.Series<Number, Number>> seriesToData = new HashMap<>();

			//read first line to skip x,y,class row
			String line = br.readLine();

			while (nonNull(line = br.readLine())){
				String[] datapoint = line.split(",");
				if(!seriesToData.containsKey(datapoint[2])){
					XYChart.Series<Number, Number> serie = new XYChart.Series<>();
					serie.setName(datapoint[2]);
					seriesToData.put(datapoint[2], serie);
				}

				seriesToData.get(datapoint[2]).getData().add(new XYChart.Data(parseDouble(datapoint[0]), parseDouble(datapoint[1])));
			}

			return seriesToData.values();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error occurred when trying to create the dataset plot.");
		}
	}
}
