package org.firstinspires.ftc.teamcode.limelighttesting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

@TeleOp
public class LimelightRotations extends OpMode {

    private Servo CRservo;
    private Limelight3A limelight;


    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        CRservo = hardwareMap.get(Servo.class, "CRservo");
    }

    @Override
    public void start(){
        limelight.start();

    }

    @Override
    public void loop() {
        LLResult llResult = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = llResult.getFiducialResults();
        for(LLResultTypes.FiducialResult fr : fiducialResults){
            telemetry.addData("Fiducial", "ID:%d",fr.getFiducialId());
        }
        if (llResult != null) {
            if (llResult.isValid()) {
                telemetry.addData("Tx", llResult.getTx());
                CRservo.setPosition(1);
            }
        }
    }
}
