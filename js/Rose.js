/*
Rose Module Loader

How to Use:
import loadModule from "https://hynrusang.github.io/lib/js/Rose.js";
const [Dynamic, LiveData] = await Promise.all([
    loadModule("dynamic", "2.0"),
    loadModule("livedata", "2.0")
]);

Notes:
- Only supports modules version 2.X or higher.
- Version selectors (e.g., "release", "pre_release", "developer") must be strings.
*/
const versionInfo = {
    dynamic: { release: "2.0", pre_release: "2.1", developer: "2.1" },
    livedata: { release: "2.0", pre_release: "2.0", developer: "2.0" }
};

const getModulePath = (name, version) => {
    const trimmed = version.trim();
    const vmap = versionInfo[name];

    if (vmap[trimmed]) return `./${vmap[trimmed]}/${name}.js`;
    if (2 <= parseInt(trimmed[0])) return `./${trimmed}/${name}.js`;
    console.warn(`Rose only supports 2.X or higher. Downgrade attempt to ${trimmed} -> defaulting to ${vmap.release}`);
    return `./${vmap.release}/${name}.js`;
};


const loadModule = async (name, version) => import(dataParser(name, version));

export default loadModule;
