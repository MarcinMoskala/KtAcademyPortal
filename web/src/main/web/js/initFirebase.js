// TODO This should be moved to React
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

messaging.requestPermission()
  .then(function() {
    setUpToken();
  })
  .catch(function(err) {
  });

messaging.onTokenRefresh(function() {
  messaging.getToken()
      .then(function(refreshedToken) {
        setTokenSentToServer(false);
        sendTokenToServer(refreshedToken);
      })
      .catch(function(err) {
      });
});

messaging.onMessage(function(payload) {
  console.log("Message received. ", payload);
});

function setUpToken() {
  messaging.getToken()
    .then(function(currentToken) {
      if (currentToken) {
        sendTokenToServer(currentToken);
      } else {
        setTokenSentToServer(false);
      }
    })
    .catch(function(err) {
      setTokenSentToServer(false);
    });
}

function sendTokenToServer(currentToken) {
  if (!isTokenSentToServer()) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "notifications/register", true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhr.onreadystatechange = function () {
      if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
        setTokenSentToServer(true);
      }
    };
    xhr.send("{\"type\":\"Web\", \"token\": \"" + currentToken + "\"}");
  } else {
  }
}

function isTokenSentToServer() {
  return window.localStorage.getItem('tokenSentToServer') == 1;
}

function setTokenSentToServer(sent) {
  window.localStorage.setItem('tokenSentToServer', sent ? 1 : 0);
}