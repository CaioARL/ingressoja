$(document).ready(function() {
  $(".alert").delay(3000).slideUp(500);
});

function logout() {

  if(confirm("Você será deslogado!")){
    window.location.href = "/logout";
  }
  
}

// Verifica a sessão
setInterval(function() {
  window.location.href = "/expire";
}, 20 * 60000);