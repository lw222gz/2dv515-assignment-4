package application;

import static application.chart.DatasetScatterChart.createChart;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

public class MachineLearningApplication {

	public static void main(String[] args){

		MachineLearningApplication application = new MachineLearningApplication();
		application.run();
		createChart();
	}

	public void run(){

		try {
			BufferedReader br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("spiral/spiral.arff").getFile()));

			Instances data = new Instances(br);
			data.setClassIndex(data.numAttributes() - 1);

			Logistic logistic = new Logistic();
			logistic.buildClassifier(data);

			Evaluation logisticEvaluation = new Evaluation(data);

			logisticEvaluation.evaluateModel(logistic, data);

			System.out.println(logisticEvaluation.toSummaryString("Results Logistic classifier:\n", true));
			System.out.println(logisticEvaluation.toMatrixString());

			MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
			multilayerPerceptron.setHiddenLayers("72");
			multilayerPerceptron.buildClassifier(data);

			Evaluation mlpEvaluation = new Evaluation(data);
			mlpEvaluation.evaluateModel(multilayerPerceptron, data);

			System.out.println(mlpEvaluation.toSummaryString("Results multiperceptron classifier:\n", true));
			System.out.println(mlpEvaluation.toMatrixString());

		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
