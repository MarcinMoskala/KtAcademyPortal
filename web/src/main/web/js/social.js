(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = 'https://connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v2.11&appId=526179304412216';
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

window.twttr = (function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0],
    t = window.twttr || {};
  if (d.getElementById(id)) return t;
  js = d.createElement(s);
  js.id = id;
  js.src = "https://platform.twitter.com/widgets.js";
  fjs.parentNode.insertBefore(js, fjs);

  t._e = [];
  t.ready = function(f) {
    t._e.push(f);
  };

  return t;
}(document, "script", "twitter-wjs"));

// Revoice
window.revoiceSettings = {
    handler: 'Kotlin-Academy',
    page_id: '127562941341011',
    position: 'left',
    horizontalPadding: '20',
    verticalPadding: '20',
    color: '#0084FF',
    created_at: '1514632277' // Signup date as a Unix timestamp
};
(function() {
    var revoice = window.Revoice;
    if (typeof revoice === "function") {
        revoice('init');
        revoice('update', revoiceSettings);
    } else {

        function load_revoice_script() {
            var script = document.createElement('script');
            script.type = 'text/javascript';
            script.async = true;
            script.src = 'https://www.revoice.me/newrevoice/js/revoice-frame.js';
            var scriptContent = document.getElementsByTagName('script')[0];
            scriptContent.parentNode.insertBefore(script, scriptContent);
        }

        if (window.attachEvent){
            window.attachEvent('onload', load_revoice_script);
        }
        else{
            window.addEventListener('load', load_revoice_script, false);
        }
    }
})()