package yapaysiniragi;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * This program demonstrates how to draw XY line chart with XYDataset using
 * JFreechart library.
 *
 * @author www.codejava.net
 *
 */
public class Grafik extends JFrame {

    List<Double> list;

    public Grafik() {
        super("EĞİTİM MSE GRAFİĞİ");
        list = new ArrayList<>();

        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    void calistir() {
        JPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);
    }

    private JPanel createChartPanel() {
        String chartTitle = "MSE GRAFİĞİ";
        String xAxisLabel = "ITERASYON";
        String yAxisLabel = "MSE";

        XYDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
                xAxisLabel, yAxisLabel, dataset);

        customizeChart(chart);

        
        File imageFile = new File("msegrafik.png");
        int width = 640;
        int height = 480;

        try {
            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        return new ChartPanel(chart);
    }
    XYSeries series1 = new XYSeries("MSE");

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < list.size(); i++) {
            series1.add(i, list.get(i));
        }

        dataset.addSeries(series1);

        return dataset;
    }

    private void customizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // sets paint color for each series
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GRAY);
        renderer.setSeriesPaint(2, Color.YELLOW);

        // sets thickness for series (using strokes)
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesStroke(1, new BasicStroke(1.0f));
        renderer.setSeriesStroke(2, new BasicStroke(1.0f));

        // sets paint color for plot outlines
        plot.setOutlinePaint(Color.GRAY);
        plot.setOutlineStroke(new BasicStroke(1.0f));

        // sets renderer for lines
        plot.setRenderer(renderer);

        // sets plot background
        plot.setBackgroundPaint(Color.WHITE);

        // sets paint color for the grid lines
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

    }

}
