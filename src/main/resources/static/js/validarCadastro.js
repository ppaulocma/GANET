function validar(form) {

    //SE LOGIN E SENHA FOREM VAZIOS
    if (form.login.value == "" || form.senha.value == "" || form.nome.value == "") {
        var alerta = document.getElementById("alerta");
        $("#alerta").slideDown();
        form.login.setAttribute("style", "border:1px solid #c73a3a");
        form.senha.setAttribute("style", "border:1px solid #c73a3a");
        form.nome.setAttribute("style", "border:1px solid #c73a3a");
        setTimeout(function () {
            alerta.setAttribute("style", "display:none");
            form.login.setAttribute("style", "border:none");
            form.senha.setAttribute("style", "border:none");
            form.nome.setAttribute("style", "border:none");
        }, 3000)

        return false;

    }

    //SE NOME FOR VAZIO
    if (form.nome.value == "") {
        var alerta = document.getElementById("nomeinvalid");
        $("#nomeinvalid").slideDown();
        form.nome.setAttribute("style", "border:1px solid #c73a3a");
        setTimeout(function () {
            alerta.setAttribute("style", "display:none");
            form.nome.setAttribute("style", "border:none");
        }, 3000)

        return false;
    }

    //SE LOGIN FOR VAZIO
    if (form.login.value == "") {
        var alerta = document.getElementById("logininvalid");
        $("#logininvalid").slideDown();
        form.login.setAttribute("style", "border:1px solid #c73a3a");
        setTimeout(function () {
            alerta.setAttribute("style", "display:none");
            form.login.setAttribute("style", "border:none");
        }, 5000)

        return false;
    }

    //SE SENHA FOR VAZIA
    if (form.senha.value == "") {
        var alerta = document.getElementById("senhainvalid");
        $("#senhainvalid").slideDown();
        form.senha.setAttribute("style", "border:1px solid #c73a3a")
        setTimeout(function () {
            alerta.setAttribute("style", "display:none")
            form.senha.setAttribute("style", "border:none")
        }, 5000)

        return false;
    }

    var data = $("#data").val()
    if (data < "1900-01-01") {
        var alerta = document.getElementById("datainvalid");
        $("#datainvalid").slideDown();
        form.dtNascimento.setAttribute("style", "border:1px solid #c73a3a")
        setTimeout(function () {
            alerta.setAttribute("style", "display:none")
            form.dtNascimento.setAttribute("style", "border:none")
        }, 5000)

        return false
    }

}
// IMPEDE CARACTERES ESPECIAIS
document.getElementById("login").onkeypress = function (e) {
    var chr = String.fromCharCode(e.which);
    if ("1234567890qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM".indexOf(chr) < 0)
        return false;
};