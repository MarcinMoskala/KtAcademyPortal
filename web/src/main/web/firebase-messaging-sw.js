importScripts('https://www.gstatic.com/firebasejs/4.6.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.6.2/firebase-messaging.js');

var config = {
    apiKey: "AIzaSyCA6efb9eL7J-8MgjGedFe0U7fTno5zhv4",
    authDomain: "kotlinacademy-d9d13.firebaseapp.com",
    databaseURL: "https://kotlinacademy-d9d13.firebaseio.com",
    projectId: "kotlinacademy-d9d13",
    storageBucket: "kotlinacademy-d9d13.appspot.com",
    messagingSenderId: "1091715558873"
};
firebase.initializeApp(config);

const messaging = firebase.messaging();