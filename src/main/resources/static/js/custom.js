let $chatHistory;
let $button;
let $textarea;
let $chatHistoryList;
var username;


function init() {
    cacheDOM();
    bindEvents();
}

function bindEvents() {
    $button.on('click', addMessage.bind(this));
    $textarea.on('keyup', addMessageEnter.bind(this));
}

function cacheDOM() {
    $chatHistory = $('.chat-history');
    $button = $('#sendBtn');
    $textarea = $('#message-to-send');
    $chatHistoryList = $chatHistory.find('ul');
}

function render(message, userName) {
    scrollToBottom();
    // responses   
    var templateResponse = Handlebars.compile($("#message-response-template").html());
    var contextResponse = {
        response: message,
        time: getCurrentTime(),
        userName: userName
    };

    setTimeout(function () {

        $chatHistoryList.append(templateResponse(contextResponse));
        scrollToBottom();
    }.bind(this), 1500);
}

function nome() {
    $.get(url + "/registration", function (response) {
        username = response;

    })
}
function sendMessage(message) {
    if (selectedUser != undefined) {
        sendMsg(username, message);
        scrollToBottom();
        if (message.trim() !== '') {
            var template = Handlebars.compile($("#message-template").html());
            var context = {
                messageOutput: message,
                time: getCurrentTime(),
                toUserName: selectedUser
            };

            $chatHistoryList.append(template(context));
            scrollToBottom();
            $textarea.val('');
        }
    }
}

function scrollToBottom() {
    $chatHistory.scrollTop($chatHistory[0].scrollHeight);
}

function getCurrentTime() {
    return new Date().toLocaleTimeString().replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
}

function addMessage() {
    var textarea = document.getElementById("message-to-send").value
    if (/^\s*$/g.test(textarea)) {
        console.log("Texto Vazio")
    } else
        sendMessage(textarea);
}

function addMessageEnter(event) {
    // enter was pressed
    if (event.keyCode === 13) {
        addMessage();
    }
}

init();

