package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.MecanumDriveCode;
import org.firstinspires.ftc.teamcode.mechanisms.MotorCode;


@TeleOp
public class TeleOP extends OpMode {

    MecanumDriveCode drive = new MecanumDriveCode();
    MotorCode shooter = new MotorCode();

    double velocity;
    double forward, strafe, rotate;
    int mode = -1;

    @Override
    public void init() {
        drive.init(hardwareMap);
        telemetry.addData("Mode", "null");
        shooter.init(hardwareMap);
        velocity = 0;
    }

    @Override
    public void loop() {
        telemetry.addData("Velocity", velocity);

        //for trigger shooter
        if (gamepad2.left_trigger > 0.05) {
            velocity = gamepad2.left_trigger * -1.0;
            shooter.setMotorSpeed(velocity);
        }else if(gamepad2.right_trigger > 0.05) {
            velocity = gamepad2.right_trigger;
            shooter.setMotorSpeed(velocity);
        } else {
            shooter.setMotorSpeed(0);
        }

        //for drive
        forward = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        if (mode == -1) {
            telemetry.addData("Mode", "null");
        } else if (mode == 0) {
            telemetry.addData("Mode", "Robot Oriented");
            drive.drive(forward, strafe, rotate);
        } else if (mode == 1) {
            telemetry.addData("Mode", "Field Oriented");
            drive.driveFieldRelative(forward, strafe, rotate);
        }
        if (gamepad1.dpad_down) {
            mode = 0;
        } else if (gamepad1.dpad_up) {
            mode = 1;
        }
    }
}
