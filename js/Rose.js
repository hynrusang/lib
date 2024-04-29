/*
Rose library
info:
This library can download other JavaScript programes by module from my website.
It was very simple and powerful to use more then jade.

How it works:
Inside the script tag to your site by type=module.

Note:
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
    return `./${versionInfo[name].release}/${name}.js`;
}
const loadModule = async (name, version) => {
    const roseModule = await import(dataParser({name: name, version: version}));
    return roseModule;
}

export default loadModule;