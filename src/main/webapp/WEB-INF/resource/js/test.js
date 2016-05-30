var intervalId = self.setInterval("loadProcess()",1000);
var statusId = self.setInterval("loadStatus()",2000);
function loadProcess()
{
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
            var processJson = xmlhttp.responseText;
            var obj = JSON.parse(processJson);
            document.getElementById("progress-bar").setAttribute('aria-valuenow', obj.percent);
            document.getElementById("progress-bar").setAttribute('style', 'width: ' + obj.percent + '%');
            document.getElementById("processMsg").innerText = obj.percentMsg;
            if (obj.percent == 100) {
                window.location.reload();
            } else if (obj.percent > 103){
                intervalId = window.clearInterval(intervalId);
            }
        }
    }
    xmlhttp.open("POST","/ajax/process",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    var params = document.getElementById("runBtn").getAttribute('key');
    xmlhttp.send(params);
}

function runTask()
{
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        intervalId = self.setInterval("loadProcess()",1000);
        document.getElementById("runBtn").removeAttribute('onclick');
        document.getElementById("runBtn").innerText = '运行中...'
    }
    var path = document.getElementById("runBtn").getAttribute('path');
    var params = document.getElementById("runBtn").getAttribute('content');
    xmlhttp.open("POST",path,true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send(params);
}


function loadStatus()
{
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
            var processJson = xmlhttp.responseText;
            var obj = JSON.parse(processJson);
            document.getElementById("threadStatus").innerText = obj.threadStatus;
            document.getElementById("memStatus").innerText = obj.memStatus;
        }
    }
    xmlhttp.open("POST","/ajax/thread",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send();
}