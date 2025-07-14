/*
Rose Module Loader
Info:
This module loader facilitates importing modules from a specified URL, enhancing module management in JavaScript projects.

How it works:
To use this module loader, import it into your JavaScript module. 
Then, call the loadModule function, providing the module name and version as arguments. 
The loader dynamically fetches the module based on the provided version information and returns a Promise that resolves to the imported module.

Notes:
Version numbers or selector (such as developer, pre-release, release) must be specified as strings.
Rose can only import modules with version 2.X or higher.

Example Usage:
(index.html)
<script src="/resource/js/init/module.js" type="module"></script>

(module.js)
import loadModule from "https://hynrusang.github.io/lib/js/Rose.js";
const [Dynamic, LiveData] = await Promise.all([
    loadModule("dynamic", "2.0"),
    loadModule("livedata", "2.0")
]);
export { Dynamic, LiveData }
*/
const versionInfo = {
    dynamic: {
        release: "2.0",
        pre_release: "2.1",
        developer: "2.1"
    },
    livedata: {
        release: "2.0",
        pre_release: "2.0",
        developer: "2.0"
    }
};
const dataParser = (name, version) => {
    version = version.trim();
    if (versionInfo[name][version]) return `./${versionInfo[name][version]}/${name}.js`;
    if (2 <= parseInt(version[0])) return `./${version}/${name}.js`;
    console.warn(`Rose can only import 2.X or higher modules. ${version} by migrating to ${versionInfo[name].release}.`);
    return `./${versionInfo[name].release}/${name}.js`;
}
const loadModule = async (name, version) => {
    const roseModule = await import(dataParser(name, version));
    return roseModule;
}

export default loadModule;
