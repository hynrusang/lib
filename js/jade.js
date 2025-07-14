/*
jade.js Library

How to Use:
<script src="https://hynrusang.github.io/lib/js/jade.js">
    dynamic, developer;
    livedata, developer;
</script>
<jade src="init.js"></jade>

Notes:
- Each entry is: moduleName, version;
- Version can be "release", "prerelease", "developer", or explicit version like "1.0.0".
*/

const JADE_BASE_URL = "https://hynrusang.github.io/lib/js/";
const JADE_VERSIONS = {
    dynamic: ["1.4", "1.4", "1.4"],
    livedata: ["1.2", "1.3", "1.4"]
};

const jadeLoader = document.querySelector(`script[src="${JADE_BASE_URL}jade.js"]`);
jadeLoader.innerHTML
    .replace(/[\n\t]/g, "")
    .split(";")
    .map(line => line.trim())
    .filter(Boolean)
    .forEach(entry => {
        const [name, version] = entry.split(",").map(s => s.trim());
        const versionList = JADE_VERSIONS[name];
        const resolvedVersion = 
            version === "developer"  ? versionList[2] :
            version === "prerelease" ? versionList[1] :
            version === "release"    ? versionList[0] : version;

        const script = document.createElement("script");
        script.async = false;
        script.src = `${JADE_BASE_URL}${resolvedVersion}/${name}.js`;
        document.body.appendChild(script);
    });
    
window.onload = () => {
    document.querySelectorAll("jade").forEach(jade => {
        const script = document.createElement("script");
        if (jade.hasAttribute("src")) script.src = jade.getAttribute("src");
        if (jade.hasAttribute("type")) script.type = jade.getAttribute("type");
        
        script.async = false;
        document.body.appendChild(script);
        jade.remove();
    });
    jadeLoader.remove();
}