This project is aimed at monitoring and processing clipboard contents in Android devices while running in the background using a foreground service.

Due to Android 10+ security restrictions, it is impossible to capture clipboard content while in the background. To work around this issue, the app uses a notification with a button that, when pressed, logs (to process) the current clipboard content by briefly bringing the application into the foreground.
