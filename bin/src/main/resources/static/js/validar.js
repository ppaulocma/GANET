function validar(form) {

    //SE LOGIN E SENHA FOREM VAZIOS
    if (form.login.value == "" && form.senha.value == "") {
        var alerta = document.getElementById("alerta");
        $("#alerta").slideDown();
        form.login.setAttribute("style", "border:1px solid #c73a3a");
        form.senha.setAttribute("style", "border:1px solid #c73a3a");
     
        setTimeout(function () {
            alerta.setAttribute("style", "display:none");
            form.login.setAttribute("style", "border:none");
            form.senha.setAttribute("style", "border:none");
       
        }, 5000)
    
        return false;
    }

    //SE LOGIN FOR VAZIO
    if (form.login.value == "") {
        var alerta = document.getElementById("invalid");
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
        var alerta = document.getElementById("invalid");
        $("#senhainvalid").slideDown();
        form.senha.setAttribute("style", "border:1px solid #c73a3a")
        setTimeout(function () {
            alerta.setAttribute("style", "display:none")
            form.senha.setAttribute("style", "border:none")
        }, 5000)

        return false;
    }    
}

// IMPEDE CARACTERES ESPECIAIS
document.getElementById("login").onkeypress = function (e) {
    var chr = String.fromCharCode(e.which);
    if ("1234567890qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM".indexOf(chr) < 0)
        return false;
};


