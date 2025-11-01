package org.firstinspires.ftc.teamcode.limelighttesting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;

@TeleOp
public class LimelightTurnTable extends OpMode {
   private DcMotor motorTurn;
   private Limelight3A limelight;

    @Override
    public void init() {

        motorTurn = hardwareMap.get(DcMotor.class, "motorTurn");
        motorTurn.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorTurn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
    }

    @Override
    public void start() {
        super.start();
        limelight.start();
        limelight.pipelineSwitch(0);
    }

    int tx;

    @Override
    public void loop() {
        //Get Fiducial Results
        LLResult llResult = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = llResult.getFiducialResults();

        //Protects against nulls and tells if tag is detected
        if (llResult != null && llResult.isValid()) {
            telemetry.addData("Status", "AprilTag(s) detected");
            //reduces tx to a single digit number
            tx = (int) llResult.getTx();
            telemetry.addData("Tx",tx);

        } else {
            telemetry.addData("Status", "No AprilTags detected");
        }

        if(tx > 5){
            motorTurn.setPower(0.1);
        }else if (tx < -5){
            motorTurn.setPower(-0.1);
        }else{
            motorTurn.setPower(0);
        }
    }
}
