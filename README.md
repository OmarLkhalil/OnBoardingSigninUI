<!DOCTYPE html>
<html>
<head>
</head>
<body>
  <h1>Project Name: Onboarding, Sign In, Sign Up, Reset Password</h1>

  <h2>Screenshots</h2>
  <p>You can find the screenshots and design inspiration for this project <a href="https://www.behance.net/gallery/173019695/OnboardingLoginRegister-Food-Mobile-App-Design?tracking_source=search_projects|mobile+ui+login+register">here</a>.</p>

  <h2>Features</h2>
  <ul>
    <li>Welcome Screen: The app greets users with a visually appealing welcome screen, implemented using a view pager and animated indicators to introduce the app's features.</li>
    <li>Home Screen: Users can choose between logging in or signing up from the home screen.</li>
    <li>Sign Up: Users can create a new account by providing their email, phone number, and password. Firebase Authentication is used to securely store user credentials, while the data is saved in the Firestore database.</li>
    <li>Sign In: Registered users can sign in using their email and password through Firebase Authentication, allowing them access to personalized content and features.</li>
    <li>Reset Password: In case users forget their password, the app provides a password reset option using a phone number. It utilizes SMS to send a verification code and automatically reads the received SMS for a seamless password reset experience.</li>
    <li>UI Design: The app boasts an intuitive and visually appealing user interface. Custom XML designs are utilized to enhance the overall look and feel of the application.</li>
  </ul>

  <h2>Installation and Setup</h2>
  <ol>
    <li>Clone the project repository.
      <pre><code>git clone https://github.com/your-username/sample-project.git</code></pre>
    </li>
    <li>Open the project in your preferred development environment.</li>
    <li>Configure Firebase:
      <ul>
        <li>Create a new project on the <a href="https://console.firebase.google.com/">Firebase Console</a>.</li>
        <li>Enable Firebase Authentication and Firestore for the project.</li>
        <li>Download the <code>google-services.json</code> file and place it in the appropriate directory within your project.</li>
      </ul>
    </li>
    <li>Build and run the project on your Android emulator or device.</li>
  </ol>

  <h2>Dependencies</h2>
  <p>The following dependencies were used in this project:</p>
  <ul>
    <li>Firebase Authentication</li>
    <li>Firebase Firestore</li>
    <li>ViewPager2</li>
    <li>Animated Indicators</li>
    <li>SMS Retriever API</li>
  </ul>
  <p>Please refer to the project's <code>build.gradle</code> file for the specific versions of the dependencies used.</p>

  <h2>Contributing</h2>
  <p>Contributions to this project are welcome. If you encounter any issues or have suggestions for improvements, please feel free to submit a pull request.</p>

  <h2>Acknowledgments</h2>
  <p>The UI design inspiration for this project is based on the work by <a href="https://www.behance.net/wddinislam">Din Islam</a>.</p>

  <p>Enjoy the project!</p>
</body>
</html>
