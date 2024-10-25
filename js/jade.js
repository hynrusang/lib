/*
jade.js Library
Info:
This library facilitates downloading JavaScript programs from a specified website, offering advantages over the conventional HTML script tag.

How it works:
To utilize this library, the <script src="https://hynrusang.github.io/js-lib/jade.js"> tag is employed, with scripts to be included separated by semicolons. 
The first argument designates the script name to include, while the second specifies the version. 
Versions may specify a particular version number (e.g., "1.0.0"), use "release" to automatically select the latest version, or "developer" for a version tailored for developers.

Notes:
To utilize jade.js, HTML scripts must be written as external scripts rather than internal ones.
Versions must be valid version numbers, "release", "prerelease", or "developer".

Example Usage:
<script src="https://hynrusang.github.io/lib/js/jade.js">
    dynamic, developer;
    livedata, developer;
</script>
<jade src="init.js"></jade>
*/
const __$$IMPLEMENTHREF = "https://hynrusang.github.io/lib/js/";
const __$$VERSIONINFO = {
    dynamic: ["1.4", "1.4", "1.4"],
    livedata: ["1.2", "1.3", "1.4"]
};
for (let data of document.querySelector(`script[src="${__$$IMPLEMENTHREF}jade.js"]`).innerHTML
    .replaceAll("\n", "")
    .replaceAll("\t", "")
    .split(" ").join("")
    .split(";")) {
    if (data != "") {
        const __$$ELEMENT = document.createElement("script");
        __$$ELEMENT.async = false;
        __$$ELEMENT.src = (data.split(",")[1] == "developer") ? `${__$$IMPLEMENTHREF}${__$$VERSIONINFO[data.split(",")[0]][2]}/${data.split(",")[0]}.js`
            :(data.split(",")[1] == "prerelease") ? `${__$$IMPLEMENTHREF}${__$$VERSIONINFO[data.split(",")[0]][1]}/${data.split(",")[0]}.js`
            : (data.split(",")[1] == "release") ? `${__$$IMPLEMENTHREF}${__$$VERSIONINFO[data.split(",")[0]][0]}/${data.split(",")[0]}.js`
            : `${__$$IMPLEMENTHREF}${data.split(",")[1]}/${data.split(",")[0]}.js`;
        document.body.appendChild(__$$ELEMENT);
    }
}
window.onload = () => {
    for (let data of document.querySelectorAll("jade")) {
        const __$$ELEMENT = document.createElement("script");
        if (data.attributes.src) __$$ELEMENT.src = data.getAttribute("src");
        if (data.attributes.type) __$$ELEMENT.type = data.getAttribute("type")
        __$$ELEMENT.async = false;
        document.body.appendChild(__$$ELEMENT);
        data.remove();
    }
    document.querySelector(`script[src="${__$$IMPLEMENTHREF}jade.js"]`).remove();
}
