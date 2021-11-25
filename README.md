# Instructions

If you run into any issues while reviewing our app, or have any additional questions/clarifications, please reply to the original peer review email we sent.
We will try to respond ASAP.

## Clone

First, you must clone this repo to your computer using `https://github.com/masranber/MaintenanceBuddy.git`

## Opening Project

To open the project, you must have Android Studio installed. Android Studio can be installed from here:
https://developer.android.com/studio

Once you have Android Studio installed and running, you can open the project by going to File -> Open and then navigate to the location on your computer where you cloned this repo.
Open the project by selecting the root project folder ("MaintenanceBuddy").

## Building Project

Once the project is open, you must sync the project's dependencies. This can be done automatically using Gradle Sync (Android Studio normally does this automatically when
you open a project, but just to be safe do it again). To do this, click the "Sync Project with Gradle Files" button in the upper right corner.

![sync gradle](https://i.stack.imgur.com/JpPsM.png)

Once the Gradle sync has completed, build the project by clicking the build button (little hammer icon).

Alternatively, if you don't care to build it yourself or you run into issues while building it, you can use a prebuilt, presigned APK we've built for testing.
APKs are basically archives containing everything needed to run the app including the built executable (same as IPA file on iOS).

This APK is located in the root repo directory and is called "MaintenanceBuddy_PeerReview.apk".

## Running on Android Device

There are two options to run the app.

### Real Android Device

The first (and easiest) is to use a real Android device by connecting it to your computer with a USB cable. If you use Android Studio to install the app on the device, you will need to
enable USB debugging in the device settings first: https://developer.android.com/studio/debug/dev-options.

If you want to install the APK we provided, simply copy the APK to your device's
internal storage. Then using a file browser on your device, click the APK which will bring up a prompt from Android to install the app. It can be run just like any other app you install
through Google Play.

If you want to build and install the app via Android Studio, with the device plugged in, click the play button in Android Studio (make sure your connected device is selected
in the dropdown menu to the left of the play button). Android Studio will automatically build, install, and run the app on the target device.

### Emulated Android Device

Option 2 is to run the app on an emulated Android device. Here are instructions on how to download the official Android emulator for Android Studio: https://developer.android.com/studio/run/emulator

Once the emulator is installed, you should see it appear in the dropdown menu next to the play button (top of the screen). Then just click the play button and the app
will be built, installed, and run on the emulated Android device.

The provided APK can also be installed on the Android emulator. While the emulator is running, simply drag and drop the APK from your computer onto the emulated
device's home screen. This should bring up the Android prompt for installing APKs.

## Test Suite

To run our test suite, follow the above steps through "Building Project". In Android Studio select "Unit Tests" from the launch configurations dropdown menu
(to the immediate right of the build button, it will say "app" by default). Then click the play button to run the test suite. You will see the results on the tests
printed out in the console at the bottom of the screen.

# Bug Reporting

For bug tracking, please use the “Issues” tab on the linked GitHub repo to report any bugs you encounter during testing. You can report a bug by clicking “New Issue” from the “Issues” tab,
filling out the form, and clicking “Submit new issue”. We’ve been using a different repo for our development and internal bug tracking,
so you don’t need to worry about identifying yourself as part of the Movie Night team when opening issues. There are two issue labels you should tag your issues with:

Bug – For any bugs and/or crashes you encounter

Enhancement – For any suggestions you have to improve the code that aren’t necessarily bugs (e.g. you find a certain UI design to be confusing or not clear)
