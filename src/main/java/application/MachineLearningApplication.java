package application;

import static application.chart.DatasetScatterChart.createChart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

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

			Instances train = new Instances(br);
			train.setClassIndex(train.numAttributes() - 1);

			Logistic logistic = new Logistic();
			logistic.buildClassifier(train);

			Evaluation logisticEvaluation = new Evaluation(train);
			logisticEvaluation.crossValidateModel(logistic, train, 10, new Random(7));

			System.out.println(logisticEvaluation.toSummaryString("Results Logistic classifier:\n", true));
			System.out.println(logisticEvaluation.toMatrixString());

			MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
			multilayerPerceptron.setHiddenLayers("72");
			multilayerPerceptron.buildClassifier(train);

			Evaluation mlpEvaluation = new Evaluation(train);
			mlpEvaluation.crossValidateModel(multilayerPerceptron, train, 10, new Random(7));

			System.out.println(mlpEvaluation.toSummaryString("Results multiperceptron classifier:\n", true));
			System.out.println(mlpEvaluation.toMatrixString());

		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
