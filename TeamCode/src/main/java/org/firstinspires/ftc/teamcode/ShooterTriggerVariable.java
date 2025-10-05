package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ShooterTriggerVariable extends OpMode {

    double velocity;

    MotorCode shooter = new MotorCode();

    @Override
    public void init() {
        shooter.init(hardwareMap);
        velocity = 0;
    }

    @Override
    public void loop() {

        if (gamepad1.left_trigger > 0.05) {
            velocity = gamepad1.left_trigger * -1.0;
            shooter.setMotorSpeed(velocity);
        }else if(gamepad1.right_trigger > 0.05) {
            velocity = gamepad1.right_trigger;
            shooter.setMotorSpeed(velocity);
        } else {
            shooter.setMotorSpeed(0);
        }
    }
}
