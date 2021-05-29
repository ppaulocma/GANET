let stompClient;
let selectedUser;
let newMessages = new Map();
var username;

fetchAll()

function connectToChat(userName) {
    let user = userName;
    console.log("connecting to chat...")
    nome();
    let socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to: " + frame);
        stompClient.subscribe("/topic/messages/" + userName, function (response) {
            let data = JSON.parse(response.body);
            if (selectedUser === data.fromLogin) {
                setTimeout(function() {
                    $.get("/setStatus/" + userName + "/" + data.fromLogin, function () {})
                } , 1000)
                render(data.message, data.fromLogin)
            } else {
                setTimeout(fetchAll, 1000)
            }
        });
    });
}


function sendMsg(from, text) {
    var para = selectedUser;
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();
     
    today = dd + '/' + mm + '/' + yyyy;

    var data  = JSON.stringify({
        message: text,
        fromLogin: from,
        to: para,
        
    })

    $.ajax({
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        type: 'POST',
        url: '/mensagem',
        data: data,
    });
    
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
    setTimeout( fetchAll, 1000)

}
registration()
function registration() {

    $.get("/registration", function (response) {
        username = response;
        connectToChat(username);
    })

}

function selectUser(userName, foto, nome) {
    
    var para = selectedUser;
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();

    today = dd + '/' + mm + '/' + yyyy;


    $('.chat-history div').remove();
    $('.chat-history div').empty();

    $.get("/setStatus/" + username + "/" + userName, function () {})

    $.get("/getMensagens/" + username + "/" + userName, function (mensagem) {
        tamanho = mensagem.length
        var dataAnterior = 0;
        var dataSeguinte = 0;
        for (let i = 0; i < tamanho; i++) {

            if (mensagem[i].fromLogin == username) {

                if (mensagem[i].data != dataSeguinte || i == 0) {
                    if (mensagem[i].data == today) {
                        data = "HOJE"
                    } else {
                        data = mensagem[i].data
                    }
                    var template = Handlebars.compile($("#message-divisao").html());
                    var context = {

                        time: data

                    };

                    $chatHistoryList.append(template(context));
                    scrollToBottom();
                }
                var template = Handlebars.compile($("#message-template").html());
                var context = {
                    messageOutput: mensagem[i].message,
                    time: mensagem[i].horario,
                    toUserName: username
                };

                $chatHistoryList.append(template(context));
                scrollToBottom();
                dataSeguinte = mensagem[i].data
                dataAnterior = mensagem[i].data;


            } else {
                if (mensagem[i].data != dataAnterior || i == 0) {
                    if (mensagem[i].data == today) {
                        data = "HOJE"
                    } else {
                        data = mensagem[i].data
                    }
                    var template = Handlebars.compile($("#message-divisao").html());
                    var context = {

                        time: data

                    };

                    $chatHistoryList.append(template(context));
                    scrollToBottom();
                }
                var templateResponse = Handlebars.compile($("#message-response-template").html());
                var contextResponse = {
                    response: mensagem[i].message,
                    time: mensagem[i].horario,
                    userName: userName
                };

                $chatHistoryList.append(templateResponse(contextResponse));
                scrollToBottom();
                dataAnterior = mensagem[i].data
                dataSeguinte = mensagem[i].data
            }
        }
    })

    console.log("Usuario Selecionado: " + userName);
    selectedUser = userName;
    let isNew = document.getElementById("newMessage_" + userName) !== null;
    if (isNew) {
        let element = document.getElementById("newMessage_" + userName);
        element.parentNode.removeChild(element);
    }
    $('#img-user').html('');
    $('#img-user').append('<img alt="avatar" height="55px"src=' + foto + ' width="55px" />');
    $('#selectedUserId').html('');
    $('#selectedUserId').append(nome);
    var chat_div = document.getElementById("chat-div") 
    var chat_zero = document.getElementById("chat-zero")
    chat_zero.setAttribute("style", "display: none;")
    chat_div.setAttribute("style", "display: flex;")   
}

function fetchAll() {
    let users
    $.get("/findHistorico", function (response) {
        users = response;

        let usersTemplateHTML = "";
        for (let i = 0; i < users.length; i++) {
            let foto;
            if (users[i].foto == null) {
                foto = "usuario-fotos/default.png";
            } else {
                foto = "usuario-fotos/foto/" + users[i].id +"/"+ users[i].foto;
            }
            usersTemplateHTML = usersTemplateHTML + '<a href="#" onclick="selectUser(\'' + users[i].login + '\',\'' + foto + '\',\'' + users[i].nome + '\')"><div class="tudo">\n' +
                '                <img src=' + foto + ' width="55px" height="55px" style ="border-radius: 100%;" />\n' +
                '                <div class="about">\n' +
                '                    <div class="name" style="color: white">' + users[i].nome + '</div>\n' +
                '                    <div class="name" style="color: gray">' + users[i].login + '</div>\n' +
                '                </div><div id="userNameAppender_' + users[i].login + '" class="div-notif"></div>\n' +
                '            </div></a>';

            getStatus(users[i].login, username)
        }
        $('#usersList').html(usersTemplateHTML);
    });

}

function getStatus(de, para) {
    $.get("/getStatus/" + de + "/" + para, function (qtdMsg) {
        if (qtdMsg > 0) {
            let isNew = document.getElementById("newMessage_" + de) !== null;
            if (isNew) {
                let element = document.getElementById("newMessage_" + de);
                element.parentNode.removeChild(element);
            }
            var user = document.getElementById("userNameAppender_" + de)
            $(user).append('<div id="newMessage_' + de + '" class="notif">' + qtdMsg + '</div>');
        }
    });
}
