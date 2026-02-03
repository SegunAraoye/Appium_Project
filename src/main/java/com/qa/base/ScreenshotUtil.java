package com.qa.base;

import java.io.File;
import java.nio.file.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotUtil {

    public static String captureScreenshot(String testName) {
        try {
            File src = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.FILE);

            String path = "test-output/screenshots/" + testName + ".png";
            File dest = new File(path);
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());

            return path;
        } catch (Exception e) {
            return null;
        }
    }
}



