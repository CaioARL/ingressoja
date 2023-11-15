$(document).ready(function() {
  $(".alert").slideUp(1200);
});

function confirmacao() {
  if(confirm("Você será deslogado!")){
    window.location.href = '/home?logado=false';
  };
}