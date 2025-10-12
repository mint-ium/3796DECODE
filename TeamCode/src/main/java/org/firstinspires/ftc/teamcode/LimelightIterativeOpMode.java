package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

@TeleOp(name="Limelight Iterative", group="Iterative Opmode")
public class LimelightIterativeOpMode extends OpMode {

    private Limelight3A limelight;

    // This runs once when the user selects the OpMode and presses INIT
    @Override
    public void init() {
        // Initialize the Limelight3A object using its name in the hardware map
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.addData("Status", "Limelight Initialized");
    }

    // This runs in a loop after the user presses INIT and before they press START
    @Override
    public void init_loop() {
        telemetry.addData("Status", "Waiting for Start");
        telemetry.update();
    }

    // This runs once when the user presses START
    @Override
    public void start() {
        // Optional: Perform any actions that need to happen at the very beginning of the match
    }

    // This runs repeatedly after the user presses START
    @Override
    public void loop() {
        // Get the list of all AprilTag detections from the Limelight]
        LLResult llResult = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = llResult.getFiducialResults();

        // Check if any tags were detected
        if (fiducialResults != null && !fiducialResults.isEmpty()) {
            // Loop through each detected fiducial
            for (LLResultTypes.FiducialResult fiducial : fiducialResults) {
                // Access the ID of the detected fiducial
                int tagId = fiducial.getFiducialId();

                telemetry.addData("Found AprilTag", "ID: %d", tagId);
                telemetry.addData("Target X", fiducial.getTargetXDegrees()); // Example of other data
            }
        } else {
            telemetry.addData("Status", "No AprilTags detected");
        }
        telemetry.update();
    }

    // This runs once when the user presses STOP
    @Override
    public void stop() {
        // Optional: Perform any cleanup when the OpMode stops
    }
}
