package org.firstinspires.ftc.teamcode.limelighttesting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;
public class Localization extends OpMode {
    private Limelight3A limelight;
    private IMU imu;

    @Override
    public void init() {
        imu = hardwareMap.get(IMU.class, "imu");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(10000);
        limelight.start();
    }

    @Override
    public void loop() {
        LLResult llResult = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = llResult.getFiducialResults();
        double robotYaw = imu.getRobotYawPitchRollAngles().getYaw();
        limelight.updateRobotOrientation(robotYaw);
        if (llResult.isValid() && llResult != null) {
            Pose3D botpose_mt2 = llResult.getBotpose_MT2();
            if (botpose_mt2 != null) {
                double x = botpose_mt2.getPosition().x;
                double y  =  botpose_mt2.getPosition().y;
                telemetry.addData("MT2 Location:", "(" + x + ", " + y + ")");
                telemetry.addData("Angle", botpose_mt2.getOrientation());
            }
        }
    }
}
