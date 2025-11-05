package org.firstinspires.ftc.teamcode.limelighttesting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;
@TeleOp
public class DistanceCalculator extends OpMode {
   private Limelight3A limelight;


    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
    }

    @Override
    public void start() {
        super.start();
        limelight.start();
        limelight.pipelineSwitch(0);
    }

    @Override
    public void loop() {
        LLResult llResult = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = llResult.getFiducialResults();

        if (llResult != null && llResult.isValid()) {
            telemetry.addData("Status", "AprilTag(s) detected");
            telemetry.addData("Tx", "%.1f", llResult.getTx());
            //Prints Total Area (Ta)
            double ta = llResult.getTa();
            double distance = (161.1*Math.pow(ta,-0.5858));
            //         constant^^^                 ^^^
            telemetry.addData("ta",ta);

            //print distance (cm)
            telemetry.addData("Distance(cm)", distance);

        } else {
            telemetry.addData("Status", "No AprilTags detected");
        }
    }
}
