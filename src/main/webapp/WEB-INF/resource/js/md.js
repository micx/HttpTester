
function parseMD() {
    var mdText = document.getElementById("textinput").innerText;
    document.getElementById("preview").innerHTML = markdown.toHTML(mdText);
}