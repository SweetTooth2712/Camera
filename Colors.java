import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

// Здесь указаны наиболее встречаемые цвета

public class Colors extends OpenCvPipeline {
    Mat HSV = new Mat();  
    Mat mask1, mask2 = new Mat();  
    Mat END = new Mat(); 

    Telemetry telemetry;
    String color = "";
    
    @Override
    public Mat processFrame(Mat input) {
        Scalar scalarLow, scalarHigh;

        if (color == "yellow") {
            scalarLow = new Scalar(20, 100, 100);
            scalarHigh = new Scalar(30, 255, 255);
        } else if (color == "blue") {
            scalarLow = new Scalar(90, 100, 100);
            scalarHigh = new Scalar(140, 255, 255);
        } else if (color == "green") {
            scalarLow = new Scalar(40, 100, 100);
            scalarHigh = new Scalar(75, 255, 255);
        } else {
            scalarLow = new Scalar(0, 0, 0);
            scalarHigh = new Scalar(0, 0, 0);
        }
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        if (!color.equals("red")) {
            Core.inRange(hsv, scalarLow, scalarHigh, end);
        }
        
        if (color == "red") {
            Core.inRange(hsv, new Scalar(0, 70, 50), new Scalar(8, 255, 255), mask1);
            Core.inRange(hsv, new Scalar(172, 70, 50), new Scalar(180, 255, 255), mask2);
            Core.bitwise_or(mask1, mask2, end); // Здесь присутствует функция "bitwise_or", которая позволяет объединить сразу две матрицы. Зачем это здесь нужно?
            // Дело в том, что красный цвет находится как в начале столбика Hue (оттенок) в цветовой модели HSV, так и в конце. Из-за чего рекомендуется объединять эти две матрицы 
        }
        return end;
    }
}
