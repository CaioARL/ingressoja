$(document).ready(function() {
  $(".alert").delay(3000).slideUp(500);
});

function logout() {

  if(confirm("Você será deslogado!")){
    window.location.href = "/ingressoja/logout";
  }
  
}

// Verifica a sessão
setInterval(function() {
  window.location.href = "/ingressoja/expire";
}, 20 * 60000);

// Valida formulario de cadastro
function validateForm() {
  const cpf = document.getElementById('cpf').value;

  try {
    const response = fetch(`/ingressoja/validateCadastro?cpf=${cpf}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = response.text();
    if (data) {
      alert(data); // Exibe a mensagem de erro
      return false;
    }
    return true;
  } catch (error) {
    console.error('Erro na validação do cadastro: ', error);
    return false;
  }
}

// Verifica senhas e CPF
window.onload = function() {
  const senha = document.getElementById('senha');
  const confirmarSenha = document.getElementById('confirmarSenha');
  const mensagemErro = document.getElementById('mensagemErro');
  const cpf = document.getElementById('cpf');

  confirmarSenha.addEventListener('input', function() {
    if (senha.value !== confirmarSenha.value) {
      mensagemErro.textContent = 'As senhas não coincidem';
    } else {
      mensagemErro.textContent = '';
    }
  });

  cpf.addEventListener('keypress', function(event) {
    if (!/\d/.test(String.fromCharCode(event.which))) {
      event.preventDefault();
    }
  });

  cpf.addEventListener('paste', function(event) {
    const pasteData = event.clipboardData.getData('text');
    if (!/^\d*$/.test(pasteData)) {
      event.preventDefault();
    }
  });
}