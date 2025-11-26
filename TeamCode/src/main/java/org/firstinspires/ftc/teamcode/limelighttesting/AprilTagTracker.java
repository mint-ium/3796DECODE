package org.firstinspires.ftc.teamcode.limelighttesting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;

@TeleOp
public class AprilTagTracker extends OpMode {
    private DcMotor motorTurn;
    private Limelight3A limelight;
    double power = 0;

    SimplePID pid = new SimplePID(0.03, 0.00, 0.002);

    @Override
    public void init() {
        motorTurn = hardwareMap.get(DcMotor.class, "motorTurn");
        motorTurn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        LLResult llResult = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = llResult.getFiducialResults();

        boolean hasTarget = llResult.isValid();

        if(hasTarget){
            double tx = llResult.getTx();
            power = pid.update(tx,0);

            //Clamp Motor Power
            if(power > 0.5) power = 0.5;
            if(power < 0.5) power = -0.5;

            motorTurn.setPower(power);

            telemetry.addData("tx", tx);
            telemetry.addData("motor", power);
        }else{
            motorTurn.setPower(power);
            pid.reset();
            telemetry.addLine("NO TAG");
        }
    }
}
