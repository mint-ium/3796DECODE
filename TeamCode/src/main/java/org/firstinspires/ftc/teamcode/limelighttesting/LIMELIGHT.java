package org.firstinspires.ftc.teamcode.limelighttesting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import java.util.List;

@TeleOp
public class LIMELIGHT extends OpMode {
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
    int mode=0;

    @Override
    public void loop() {
        //Setting Up Fiducial Results
        LLResult llResult = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = llResult.getFiducialResults();

        //Protects against nulls and tells if tag is detected
        if (llResult != null && llResult.isValid()) {
            telemetry.addData("Status", "AprilTag(s) detected");
            telemetry.addData("Tx","%.1f", llResult.getTx());

        }else{
            telemetry.addData("Status", "No AprilTags detected");
        }
        
        telemetry.addData("Pipeline", mode);
            if (gamepad1.dpadUpWasPressed()) {
                if (mode >= 3) {
                    mode = 0;
                } else {
                    mode += 1;
                    limelight.pipelineSwitch(mode);
                }
            } else if (gamepad1.dpadDownWasPressed()) {
                if (mode <= -1) {
                    mode = 0;
                } else {
                    mode -= 1;
                    limelight.pipelineSwitch(mode);
                }

            }

        int tagID = -1;
        //Get the ID of the April Tag
        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            telemetry.addData("ID", "%d", fr.getFiducialId());
            tagID = fr.getFiducialId();
        }
        //if tag is ____ do this
        if(tagID == 20 || tagID == 24 ){
            telemetry.addData("Valid ID", tagID);
        }

    }

}
