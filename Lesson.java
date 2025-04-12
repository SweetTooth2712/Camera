import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Lesson extends OpenCvPipeline {

    Mat HSV = new Mat();
    Mat END = new Mat(); // Здесь мы указываем "матрицы"
    // Матрица — поток в памяти камеры. Например, Mat input — поток входного изображения, который посылается на камеру.  

    Scalar HighOrange = new Scalar(25, 255, 255);
    Scalar LowOrange = new Scalar(10, 100, 100);

    int pixels;

    Telemetry telemetry;

    public Lesson(Telemetry telemetry){
        this.telemetry = telemetry;
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV);
        Core.inRange(HSV, LowOrange, HighOrange, END);

        for (int i = 0; i < input.width(); i++) {
            for (int j = 0; j < input.height(); j++) {
                if (END.get(j, i)[0] == 255) {
                    pixels++;

                }
            }
        }
        telemetry.addData("Pixels", pixels);
        telemetry.update();
        pixels = 0;

        return END;
    }
}
