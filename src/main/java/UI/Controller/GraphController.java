package UI.Controller;

import Serial.SerialDataReader;
import javafx.application.Platform;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;
import java.util.Date;
import Loader.Loader;

public class GraphController {

    private CategoryAxis lineChartX;
    private NumberAxis lineChartY;
    private LineChart<String, Number> lineChart;
    private XYChart.Series<String, Number> upDataSeries;
    private XYChart.Series<String, Number> downDataSeries;


    private SerialDataReader reader;

    Thread updateThread;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    public GraphController(LineChart<String, Number> lineChart){
        this.reader = DeviceController.currentReader;
        System.out.println("Reader Status: " + reader);
        this.lineChart = lineChart;
        this.lineChartX = (CategoryAxis)this.lineChart.getXAxis();
        this.lineChartY = (NumberAxis)this.lineChart.getYAxis();

        this.lineChartX.setAnimated(false);
        this.lineChartX.setLabel("Time (HH:mm:ss)");

        this.lineChartY.setAnimated(false);
        this.lineChartY.setLabel("Reading");


        this.upDataSeries = new XYChart.Series<>();
        upDataSeries.setName("Upstream Data");
        lineChart.getData().add(upDataSeries);
        this.downDataSeries = new XYChart.Series<>();

        lineChart.getData().add(downDataSeries);
        downDataSeries.setName("Downstream Data");


        updateThread = new Thread(() -> {
            while(true) {
                if(Loader.SHUTDOWN_ACTIVE){
                    System.out.println("Thread Existing...");
                    break;
                }

                System.err.println("Reader: " + reader);
                if (reader.isDataFresh()) {
                    Platform.runLater(() -> {
                        String reading = reader.getLastReading();
                        String[] measure = formatReading(reading);
                        if(measure.length != 3){
                            System.err.println("Data packet did not conform to expected output (" + measure.length + ")");
                            System.err.println("Saw: " + reading);
                        }else{
                            // <timestamp>:<upstream>,<downstream>
                            Date now = new Date();
                            upDataSeries.getData()
                                    .add(new XYChart.Data<>(simpleDateFormat.format(now), Double.parseDouble(measure[1])));
                            downDataSeries.getData()
                                    .add(new XYChart.Data<>(simpleDateFormat.format(now), Double.parseDouble(measure[2])));
                        }

                    });
                }
                System.out.println("Update Thread sleeping...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        updateThread.start();



    }

    private String[] formatReading(String reading){
        return reading.split(",");

    }

}
