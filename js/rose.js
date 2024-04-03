/*
rose library
info:
This library can download other JavaScript programes by module from my website.
It was very simple and powerful to use.

How it works:
Inside the script tag to your site by type=module.
Also, write in the script similar to the following.

// ex: rose.js
import getm from "https://hynrusang.github.io/lib/js/rose.js";

const dynamic = getm("dynamic", "pre_release");
const livedata = getm("dynamic", "release");

export { dynamic, livedata }
//

Then, to access the module imported into rose, you can do the following.

// ex: other.js
import { dynamic } from "./rose.js";
dynamic.Fragment(...);
//

Whole picture:
*/
const versionInfo = {
    dynamic: {
        release: null,
        pre_release: null,
        developer: "2.0"
    },
    livedata: {
        release: null,
        pre_release: null,
        developer: "2.0"
    }
};
const dataParser = ({name, version}) => {
    version = version.trim();
    if (versionInfo[name][version]) return `./${versionInfo[name][version]}/${name}.js`;
    if (2 <= parseInt(version[0])) return `./${version}/${name}.js`;
    console.log(`%Rose can only import 2.X or higher modules. ${version} by migrating to 2.0.`, "color: red");
    return `./2.0/${name}.js`;
}
const get = async (name, version) => {
    const roseModule = await import(dataParser({name: name, version: version}));
    return roseModule;
}
