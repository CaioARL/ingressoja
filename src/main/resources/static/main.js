// SlideUp para alerts
$(document).ready(function () {
  $(".alert").delay(3000).slideUp(500);
});

// Show modal sair da conta
document.addEventListener("DOMContentLoaded", function () {
  var logoutButton = document.getElementById('logoutAccount');
  if (logoutButton) {
    logoutButton.addEventListener('click', function () {
      $('#confirmLogoutModal').modal('show');
    });
  }
});

function logout() {
  window.location.href = "/ingressoja/logout";
}

// Verifica a sessão
setInterval(function () {
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

// Verifica senhas
function validatePassword() {
  const senha = document.getElementById('senha');
  const confirmarSenha = document.getElementById('confirmarSenha');
  const mensagemErro = document.getElementById('mensagemErro');

  if (confirmarSenha.value != "") {
    if (senha.value != confirmarSenha.value) {
      mensagemErro.textContent = 'As senhas não coincidem';
    } else {
      mensagemErro.textContent = '';
    }
  }
}

// Verifica pattern CPF
function mascara(i) {
  var v = i.value;
  v = v.replace(/\D/g, ''); // Remove tudo o que não é dígito
  v = v.replace(/(\d{3})(\d)/, '$1.$2'); // Coloca um ponto entre o terceiro e o quarto dígitos
  v = v.replace(/(\d{3})(\d)/, '$1.$2'); // Coloca um ponto entre o terceiro e o quarto dígitos
  v = v.replace(/(\d{3})(\d{2})$/, '$1-$2'); // Coloca um hífen entre o terceiro e o quarto dígitos
  i.value = v;
}

// Visibilidade da senha
document.addEventListener("DOMContentLoaded", function () {
  function togglePasswordVisibility(inputId, toggleId) {
    document.getElementById(toggleId).addEventListener("click", function (e) {
      const passwordInput = document.getElementById(inputId);
      if (passwordInput.type === "password") {
        passwordInput.type = "text";
        e.target.classList.add("fa-eye-slash");
        e.target.classList.remove("fa-eye");
      } else {
        passwordInput.type = "password";
        e.target.classList.add("fa-eye");
        e.target.classList.remove("fa-eye-slash");
      }
    });
  }

  togglePasswordVisibility("senha", "togglePassword");
  togglePasswordVisibility("confirmarSenha", "toggleConfirmPassword");
});

// Máscara para o CPF
window.onload = function () {
  var cpfField = document.getElementById('cpfPerfil');
  window.vg_cpf = cpfField.value;
  var maskedCpf = vg_cpf.substr(0, 3) + '.***.***-**';
  cpfField.value = maskedCpf;
}

// Show modal deletar conta
document.addEventListener("DOMContentLoaded", function () {
  var deleteButton = document.getElementById('deleteAccount');
  if (deleteButton) {
    deleteButton.addEventListener('click', function () {
      $('#confirmDeleteModal').modal('show');
    });
  }
});

function deleteAccount() {
  window.location.href = "/ingressoja/excluirConta?cpf=" + vg_cpf;
}